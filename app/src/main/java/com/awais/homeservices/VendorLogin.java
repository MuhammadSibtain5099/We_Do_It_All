package com.awais.homeservices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class VendorLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_login);
        Switch onOffSwitch = (Switch)  findViewById(R.id.switch1);
        onOffSwitch.setChecked(true);
        Button loginAsVendor = findViewById(R.id.btnLogin);
        loginAsVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),VendorActivity.class);
                startActivity(intent);
                finish();
            }
        });
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (!isChecked){
                   Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                   startActivity(intent);
                   finish();
               }
                Log.v("Switch State=", ""+isChecked);
            }

        });
    }
}