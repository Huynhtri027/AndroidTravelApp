package com.sutd.zhangzhexian.travelapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Lakshita on 11/15/2015.
 */
public class UserLogin extends AppCompatActivity {

    EditText Name;
    EditText Country;
    EditText Age;
    Button go;
    static String name, country, age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Standard Generated stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        Name = (EditText)findViewById(R.id.userName);
        Country = (EditText)findViewById(R.id.userCountry);
        Age = (EditText)findViewById(R.id.userAge);
        go = (Button)findViewById(R.id.travel);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });
    }

}
