package com.awais.homeservices.Vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.awais.homeservices.Model.Vendor;
import com.awais.homeservices.R;
import com.awais.homeservices.URLs;
import com.awais.homeservices.VolleySingleton;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    ImageView imageButton;
    Button imageBack ;
    TextInputLayout edFName,edLName,edMobile,edAddress,edCnic,edStatus;
   String UserId;
   TextView txtFullName,txtServiceTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Intent intent = getIntent();
        imageButton = findViewById(R.id.profile_image);
        UserId = intent.getStringExtra("id");
        txtFullName = findViewById(R.id.full_name);
        txtServiceTitle = findViewById(R.id.txtServiceTitle);
        Glide.with(getApplicationContext())
                .load(URLs.Get_Image + "1.png.")
                .into(imageButton);
        edFName = findViewById(R.id.inputFName);
        edLName = findViewById(R.id.inputLName);
        edMobile = findViewById(R.id.inputmobile);
        edAddress = findViewById(R.id.inputaddress);
        edCnic = findViewById(R.id.inputCnic);
        edStatus = findViewById(R.id.inputStatus);
        imageBack = findViewById(R.id.btnBack);
        edLName.setEnabled(false);
        edFName.setEnabled(false);
        edStatus.setEnabled(false);
        edCnic.setEnabled(false);
        edAddress.setEnabled(false);
        edMobile.setEnabled(false);
        getUser(UserId);

    }

        public void getUser(String UserId) {

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Fetching User Detail...");
            progressDialog.setMessage("please wait while We are Getting Your data");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(true);


            //if everything is fine
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.Get_Vendor_By_ID,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                //converting response to json object
                                JSONObject obj = new JSONObject(response);

                                //if no error in response
                                if (!obj.getBoolean("error")) {
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    JSONArray jsonArray = obj.getJSONArray("userid");

                                    for (int i=0;i<jsonArray.length();i++){
                                        JSONObject obj1 = jsonArray.getJSONObject(i);
                                        Vendor vendor = new Vendor(obj1.getString("id"),
                                                obj1.getString("f_name"),
                                                obj1.getString("l_name"),
                                                obj1.getString("cnic"),
                                                obj1.getString("serviceid"),
                                                obj1.getString("email"),
                                                obj1.getString("phoneno"),
                                                obj1.getString("address"),
                                                obj1.getString("status"))
                                                ;
                                        txtFullName.setText(vendor.getF_name()+" "+vendor.getL_name());
                                        edFName.getEditText().setText(vendor.getF_name());
                                        edLName.getEditText().setText(vendor.getL_name());
                                        edMobile.getEditText().setText(vendor.getPhoneno());

                                        edCnic.getEditText().setText(vendor.getCnic());
                                        edAddress.getEditText().setText(vendor.getAddress());
                                        edStatus.getEditText().setText(vendor.getStatus());
                                        txtServiceTitle.setText(obj1.getString("s_title"));
                                    }

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", UserId);

                    return params;
                }
            };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }


    }
