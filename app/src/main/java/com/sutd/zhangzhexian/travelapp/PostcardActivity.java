package com.sutd.zhangzhexian.travelapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Lakshita on 11/4/2015.
 */
public class PostcardActivity extends Fragment implements View.OnClickListener {

    View root;

    public Button button1;
    public Button button2;
    public ImageView image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.picture_postcard, container, false);
        button1 = (Button) root.findViewById(R.id.button1);
        button2 = (Button) root.findViewById(R.id.button2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                Intent i1 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i1, 1);
                break;

            case R.id.button2:
                Toast.makeText(getActivity().getApplicationContext(), "Take a screenshot to save!", Toast.LENGTH_LONG).show();
                break;
            }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1964 && resultCode == Activity.RESULT_OK && data != null) {
            Uri pickedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContext().getContentResolver().query(pickedImage, filePath, null, null, null);
            try{
                cursor.moveToFirst();}
            catch(NullPointerException e){
                Toast.makeText(getActivity().getApplicationContext(), "NullPointerException", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            image.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            cursor.close();
        }
    }
}


