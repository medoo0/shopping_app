package com.alaa.microprocess.lrahtk.Fragment;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.View.HomePage;
import com.bumptech.glide.Glide;

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
                                            if (!checkPermissionForCamera()) {
                                                requestPermissionForCamera();
                                            } else {
                                                openCamera();
                                            }
                                            break;

                                        // Open Gallery
                                        case 1:
                                            if (!checkPermissionForًWriteExternal()) {
                                                requestPermissionForExternalWrite();
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

    public boolean checkPermissionForًWriteExternal() {  //check if permmision done .
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void requestPermissionForCamera() {  // camera request of permision

            requestPermissions( new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, Gallary_camera);

    }

    public void requestPermissionForExternalWrite() {  // READ_EXTERNAL_STORAGE request of permision

       requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Gallary_intent);

    }

    // OPEN CAMERA
    public void openCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Gallary_camera);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallary_camera && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            UploadPhotouri = data.getData();
            IMageView.setImageBitmap(photo);
        }

        if (requestCode == Gallary_intent && resultCode == RESULT_OK) {

            UploadPhotouri = data.getData();
            Glide.with(getActivity()).load(UploadPhotouri)
                    .into(IMageView);
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
