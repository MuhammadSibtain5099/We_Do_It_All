package com.awais.homeservices.Vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.awais.homeservices.R;

public class VendorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);
        Intent intent = getIntent();
     TextView textView = findViewById(R.id.txtID);
     textView.setText(intent.getStringExtra("id"));
    }
}