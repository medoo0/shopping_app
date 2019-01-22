package com.alaa.microprocess.lrahtk.Fragment;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.View.HomePage;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


public class Gift extends Fragment implements View.OnClickListener {

    ImageView IMageView;
    Uri UploadPhotouri = null;
    private final int Gallary_camera = 1;
    private final int Gallary_intent = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gift, container, false);
        HomePage.texttoolbar.setText("الهدايا");
        IMageView = view.findViewById(R.id.photo);
        IMageView.setOnClickListener(this);


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


        File file = new File(Environment.getExternalStorageDirectory(),  "Maktbtk/temp/"+"temp"+".png");
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

}
