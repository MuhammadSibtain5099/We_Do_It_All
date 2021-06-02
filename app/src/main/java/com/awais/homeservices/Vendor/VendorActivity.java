package com.awais.homeservices.Vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VendorActivity extends AppCompatActivity {


     TextView fullname;
     ImageView logOutB;
     Button userStatus;
     ImageView imageButton;
     Button btnEditProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);
        Intent intent = getIntent();
        getUser(intent.getStringExtra("id"));
        imageButton = findViewById(R.id.profileB);
        btnEditProfile = findViewById(R.id.editProfileB);
        findViewById(R.id.logOutB).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(),VendorLogin.class);
               startActivity(intent);
               finish();
           }
       });
        btnEditProfile.setOnClickListener(v -> {
            Intent intents = new Intent(getApplicationContext(),EditProfile.class);
            intents.putExtra("id",intent.getStringExtra("id"));
            startActivity(intents);

        });
       userStatus = findViewById(R.id.todoB);
        Glide.with(getApplicationContext())
                .load(URLs.Get_Image+"1.jpg")
                .into(imageButton);

        TextView textView = findViewById(R.id.txtID);
        textView.setText("ID : "+intent.getStringExtra("id"));

        fullname = findViewById(R.id.txtFullName);

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
                                      Vendor   vendor = new Vendor(obj1.getString("id"),
                                                obj1.getString("f_name"),
                                                obj1.getString("l_name"),
                                                obj1.getString("cnic"),
                                                obj1.getString("serviceid"),
                                                obj1.getString("email"),
                                                obj1.getString("phoneno"),
                                                obj1.getString("address"),
                                              obj1.getString("status"))
                                              ;

                                        fullname.setText(vendor.getF_name()+" "+vendor.getL_name());

                                        userStatus.setText(vendor.getStatus());
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
