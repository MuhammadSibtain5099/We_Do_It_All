package com.awais.homeservices.Vendor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Vendor_SignUp extends AppCompatActivity {
    AutoCompleteTextView autoCompleteTextView;
    List<String> servicesList = new ArrayList<>();
    List<Services> servicesLis = new ArrayList<>();
    private Button CallLogin, SignUp;
    ArrayAdapter arrayAdapter;
    private TextInputLayout fName, lName, Address, email, contactNo, password, CNIC;
    private String strfirstName, strlastName, strDob, strAddrees, strServiceType, stremail, strcontactNo, strpassword, strCNIC;
    private Button btnDOB;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor__sign_up);
        autoCompleteTextView = findViewById(R.id.spinnerForVendorType);
        getServices();
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
 //               Toast.makeText(getApplicationContext(),servicesLis.get(position).getTitle(),Toast.LENGTH_LONG).show();

                strServiceType= String.valueOf(servicesLis.get(position).getId());
            }
        });
        btnDOB = findViewById(R.id.btnDob);
        fName = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);
        Address = findViewById(R.id.txtAddress);
        email = findViewById(R.id.emailR);
        contactNo = findViewById(R.id.contactNoR);
        password = findViewById(R.id.passwordR);
        CNIC = findViewById(R.id.txtCNIC);
        CallLogin = findViewById(R.id.loginScreen);
        SignUp = findViewById(R.id.SignUp);
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select Your Date of Birth");
        final MaterialDatePicker materialDatePicker = builder.build();
        btnDOB.setOnClickListener(v -> {
            materialDatePicker.show(getSupportFragmentManager(), "Date_Picker");
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onPositiveButtonClick(Object selection) {
                btnDOB.setText("DOB: " + materialDatePicker.getHeaderText());
                strDob = materialDatePicker.getHeaderText();
                strDob = strDob.replace(" ","-");

                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .appendPattern("dd-MMM-yyyy")
                        .toFormatter(Locale.UK);
                LocalDate ld = LocalDate.parse(strDob, formatter);

                strDob=(ld).toString();

            }
        });

        CallLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), VendorLogin.class);
                startActivity(intent);
                finish();
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   SignUp();
//                Intent intent = new Intent(SignUp.this, UserDashboard.class);
//                startActivity(intent);
//                finish();
                registerUser();
            }
        });

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
                                for (int i = 0; i < userJson.length(); i++) {
                                    JSONObject us = userJson.getJSONObject(i);
                                    //creating a new user object
                                    Services user = new Services(
                                            us.getInt("id"),
                                            us.getString("s_title")
                                    );
                                    servicesList.add(us.getString("s_title"));
                                    servicesLis.add(user);



                                }
                                strServiceType= String.valueOf(servicesLis.get(0).getId());
                                arrayAdapter = new ArrayAdapter(Vendor_SignUp.this, R.layout.spinner_item, servicesList);
                                autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(), false);
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

    public void registerUser() {
        strfirstName = fName.getEditText().getText().toString().trim();
        strlastName = lName.getEditText().getText().toString().trim();
        strAddrees = Address.getEditText().getText().toString().trim();
        stremail = email.getEditText().getText().toString().trim();
        strcontactNo = contactNo.getEditText().getText().toString().trim();
        strpassword = password.getEditText().getText().toString().trim();
        strCNIC = CNIC.getEditText().getText().toString().trim();
        //  strServiceType = servicesLis;
        email.setError(null);
        password.setError(null);
        fName.setError(null);
        if (TextUtils.isEmpty(strfirstName)) {
            fName.setError("Full Name is required!");
            return;
        } else if (TextUtils.isEmpty(stremail)) {
            email.setError("Email is required!");
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(stremail).matches()) {
            email.setError("please enter correct email!");
            return;
        } else if (TextUtils.isEmpty(strcontactNo)) {

            contactNo.setError("Contact No is required!");
            return;
        } else if (strcontactNo.length() < 11) {
            contactNo.setError("Mobile number must be 11 character");
            return;
        } else if (TextUtils.isEmpty(strpassword)) {
            password.setError("Password is required!");
            return;
        } else {

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("registering...");
            progressDialog.setMessage("please wait while we are checking your data");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(true);


            //if everything is fine
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.Add_new_Vendor,
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
                                    Intent intent = new Intent(getApplicationContext(), VendorActivity.class);
                                    intent.putExtra("id",obj.getString("userid"));
                                    startActivity(intent);
                                    finish();

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
                    params.put("f_name", strfirstName);
                    params.put("l_name", strlastName);
                    params.put("dob", strDob);
                    params.put("phoneno", strcontactNo);
                    params.put("email", stremail);
                    params.put("password", strpassword);
                    params.put("address", strAddrees);
                    params.put("cnic", strCNIC);
                    params.put("serviceid",strServiceType );
                    return params;
                }
            };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }


    }
}