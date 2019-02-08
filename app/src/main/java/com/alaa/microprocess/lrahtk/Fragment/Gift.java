package com.alaa.microprocess.lrahtk.Fragment;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.ApiClient.ApiMethod;
import com.alaa.microprocess.lrahtk.ApiClient.ApiRetrofit;
import com.alaa.microprocess.lrahtk.Dialog.AnimatedDialog;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.View.HomePage;
import com.alaa.microprocess.lrahtk.pojo.Order;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;


public class Gift extends Fragment implements View.OnClickListener {

    ImageView IMageView;
    Uri UploadPhotouri = null;
    private final int Gallary_camera = 1;
    private final int Gallary_intent = 2;
    AnimatedDialog dialog ;
    SharedPreferences preferences;
    String  token , LastOrderId;
    Button btnOrder;
    EditText etName , etSchool , etClassroom , etSubject ;
    File file;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gift, container, false);
        HomePage.texttoolbar.setText("الهدايا");
        IMageView = view.findViewById(R.id.photo);
        btnOrder   = view.findViewById(R.id.btnOrder);
        etName = view.findViewById(R.id.StudentName);
        etSchool = view.findViewById(R.id.school);
        etClassroom = view.findViewById(R.id.classRoom);
        etSubject = view.findViewById(R.id.subject);

        dialog = new AnimatedDialog(getActivity());

        preferences = getActivity().getSharedPreferences("Sign_in_out", Context.MODE_PRIVATE);

        if (preferences.getString("AreInOrNot", "").equals("IN")) {

            token = preferences.getString("Token","");
            LastOrderId = preferences.getString("LastOrderId","");
        }


        IMageView.setOnClickListener(this);


        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!etName.getText().toString().isEmpty()
                        && !etSchool.getText().toString().isEmpty()
                        && !etClassroom.getText().toString().isEmpty()
                        &&  file != null
                        ){


//                    Log.d("xxxccc",etName.getText().toString() + " \n"
//                                            + etSchool.getText().toString() + " \n"
//                            + etClassroom.getText().toString() + " \n"
//                            + file.toString() + " \n");

                    com.alaa.microprocess.lrahtk.pojo.Gift gift = new com.alaa.microprocess.lrahtk.pojo.Gift();
                    gift.setName(etName.getText().toString());
                    gift.setSchool(etSchool.getText().toString());
                    gift.setClass_(etClassroom.getText().toString());
                    uploadDate(LastOrderId,gift);


                }

            }
        });


        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == IMageView) {

            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setTitle("اختار المصدر")
                    .setIcon(R.drawable.icon)
                    .setItems(new CharSequence[]{
                                    "بواسطة الكاميرا ",
                                    "اختيار من معرض الصور"},
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        // Open Camera
                                        case 0:
                                            if (!checkPermissionForCamera() && !checkPermissionForًWriteExternal()) {
                                                requestPermissionForCamera();
                                            } else {
                                                openCamera();
                                            }
                                            break;

                                        // Open Gallery
                                        case 1:
                                            if (!checkPermissionForReadExternal()) {
                                                requestPermissionForExternalRead();
                                            } else {

                                                openGallary();
                                            }


                                            break;
                                    }
                                }
                            })
                    .setNegativeButton("الغاء", null);
            alert.create().show();


        }
    }

    private void openGallary() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, Gallary_intent);
    }

    public boolean checkPermissionForCamera() {  //check if permmision done .
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPermissionForReadExternal() {  //check if permmision done .
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }


    public boolean checkPermissionForًWriteExternal() {  //check if permmision done .
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE );
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void requestPermissionForCamera() {  // camera request of permision

            requestPermissions( new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, Gallary_camera);

    }

    public void requestPermissionForExternalRead() {  // READ_EXTERNAL_STORAGE request of permision

       requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, Gallary_intent);

    }

    // OPEN CAMERA
    public void openCamera() {


        //** Add following code block before start camera or file browsing
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());


         file = new File(Environment.getExternalStorageDirectory(),  "Maktbtk/temp/"+"temp"+".png");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }else{
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            UploadPhotouri = FileProvider.getUriForFile(
                    getActivity(),
                    "com.alaa.microprocess.lrahtk.provider", //(use your app signature + ".provider" )
                    file);
        } else{
            UploadPhotouri = Uri.fromFile(file);
        }



        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, UploadPhotouri);
        startActivityForResult(intent , Gallary_camera);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallary_camera && resultCode == RESULT_OK) {

            Glide.with(getActivity()).load(UploadPhotouri)
                    .into(IMageView);

        }

        if (requestCode == Gallary_intent && resultCode == RESULT_OK) {
            try {

                UploadPhotouri = data.getData();

                Glide.with(getActivity()).load(UploadPhotouri)
                        .into(IMageView);

            }catch (Exception e){

            }

        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "Permission denied to camera", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 2: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallary();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "Permission denied to read your storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    void uploadDate(String OrderId , com.alaa.microprocess.lrahtk.pojo.Gift gift  ){

//        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
//
//
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient.Builder client = new OkHttpClient.Builder();
//        client.readTimeout(60, TimeUnit.SECONDS);
//        client.writeTimeout(60, TimeUnit.SECONDS);
//        client.connectTimeout(60, TimeUnit.SECONDS);
//        client.addInterceptor(interceptor);
//        client.addInterceptor(new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//
//                request = request
//                        .newBuilder()
//                        .addHeader("Content-Type","application/json")
//                        .addHeader("Authorization", "Bearer " + token)
//                        .build();
//
//                return chain.proceed(request);
//            }
//        });
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(ApiRetrofit.API_BASE_URL)
//                .client(client.build())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ApiMethod service = retrofit.create(ApiMethod.class);
//        Call <ResponseBody> call = service.GiftPost(OrderId,gift,filePart);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if(response.isSuccess()){
//                    Toast.makeText(getActivity(), "nice", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Toast.makeText(getActivity(), " not nice", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(getActivity(), " failure ", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

}
