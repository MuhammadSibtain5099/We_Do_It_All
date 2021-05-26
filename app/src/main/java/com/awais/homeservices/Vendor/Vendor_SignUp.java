package com.awais.homeservices.Vendor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.awais.homeservices.MainActivity;
import com.awais.homeservices.Model.Services;
import com.awais.homeservices.R;
import com.awais.homeservices.SharedPrefManager;
import com.awais.homeservices.URLs;
import com.awais.homeservices.User.User;
import com.awais.homeservices.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vendor_SignUp extends AppCompatActivity {
     AutoCompleteTextView autoCompleteTextView;
     List<String> servicesList = new ArrayList<>();
     List<Services> servicesLis = new ArrayList<>();
    String []option;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor__sign_up);
          autoCompleteTextView=findViewById(R.id.spinnerForVendorType);



        getServices();

    }



    private void getServices() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.Get_All_Service,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONArray userJson = obj.getJSONArray("user");
                                for (int i=0;i<userJson.length();i++) {
                                    JSONObject us = userJson.getJSONObject(i);
                                    //creating a new user object
                                    Services user = new Services(
                                            us.getInt("id"),
                                            us.getString("s_title")
                                    );
                                    servicesList.add(us.getString("s_title"));
                                    servicesLis.add(user);



                                }
                                ArrayAdapter arrayAdapter = new ArrayAdapter(Vendor_SignUp.this,R.layout.spinner_item,servicesList);
                                autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(),false);
                                autoCompleteTextView.setAdapter(arrayAdapter);


                              } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

}