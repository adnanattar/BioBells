package com.tkiet.biobells;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    private static final String TAG = "TAG";
    EditText mFullName, mEmail, mPassword, mPhone, mAadhar;
    Button mRegisterBtn, mPWD;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;
    Spinner choose_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);
        mFullName   = findViewById(R.id.fullName);
        mEmail      = findViewById(R.id.email);
        mPhone      = findViewById(R.id.phone);
        mPassword   = findViewById(R.id.pwd);
        mPWD = findViewById(R.id.showHideBtn);
        mAadhar = findViewById(R.id.aadhar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }

        //Choose User
        choose_user = findViewById(R.id.choose_user);

        // Initializing a String Array
        String[] list_users = new String[]{
//                "Select Users",
                "Patient",
                "Doctor"
        };

        final List<String> userList = new ArrayList<>(Arrays.asList(list_users));

        {
            // Initializing an ArrayAdapter
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item,list_users){
                @Override
                public boolean isEnabled(int position){
                    if(position == 0)
                    {
                        // Disable the first item from Spinner
                        // First item will be use for hint
                        return false;
                    }
                    else
                    {
                        return true;
                    }
                }
                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if(position == 0){
                        // Set the hint text color gray
                        tv.setTextColor(Color.BLACK);
                    }
                    else {
                        tv.setTextColor(Color.BLACK);
                    }
                    return view;
                }
            };

            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
            choose_user.setAdapter(spinnerArrayAdapter);

            choose_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItemText = (String) parent.getItemAtPosition(position);
                    // If user change the default selection
                    // First item is disable and it is used for hint
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        };


        mPWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                global.hideKeyboard();

                if(mPWD.getText().equals("Hide"))
                {
                    mPWD.setText("Show");
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else if(mPWD.getText().equals("Show"))
                {
                    mPWD.setText("Hide");
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });


        View parent_view = findViewById(android.R.id.content);
        mRegisterBtn = findViewById(R.id.registerBtn);
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullName   = mFullName.getText().toString();
                final String email      = mEmail.getText().toString().trim();
                final String phone      = mPhone.getText().toString();
                final String aadhar     = mAadhar.getText().toString();
                String password = mPassword.getText().toString().trim();
                final String users = choose_user.getSelectedItem().toString();

//                String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

                //Date And Time
                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String RegistrationDate = df.format(date);




                if(fullName.isEmpty()){
                    mFullName.setError("Full Name is Required");
                    return;
                }

                if(fullName.matches(".*\\d.*"))
                {
                    mFullName.setError("Full Name contains invalid data");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(!TextUtils.isEmpty(email)) {
                    EditText email1 = (EditText) findViewById(R.id.email);
                    if (!Patterns.EMAIL_ADDRESS.matcher(email1.getText().toString()).matches()) {
                        mEmail.setError("Invalid Email Address");
                        return;
                    }
                }

//                if(users.equals("Select Users")){
//                    TextView errorText = (TextView)choose_user.getSelectedView();
//                    errorText.setError("");
//                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
//                    errorText.setText("Please Select Proper User");
//                }

                if(phone.isEmpty()){
                    mPhone.setError("Mobile Number is Required");
                    return;
                }

                if(!TextUtils.isEmpty(phone)) {
                    EditText phone1 = (EditText) findViewById(R.id.phone);
                    if (!Patterns.PHONE.matcher(phone1.getText().toString()).matches()) {
                        mPhone.setError("Invalid Mobile Number");
                        return;
                    }
                }


                if(aadhar.isEmpty()){
                    mAadhar.setError("Aadhar Number is Required");
//                    //Aadhar
//                    String adharString =" ^[2-9]{1}[0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$ ";
//                    EditText adhar1 = (EditText) findViewById(R.id.aadhar);
//                    Pattern p = Pattern.compile(adharString);
//                    Matcher m = p.matcher(aadhar);
//                    m.matches();
//                    mAadhar.setError("Invalid Aadhar Number");
                    return;

                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password must contains minimum 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);


                // register the user in firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            // send verification link

                            FirebaseUser fuser = fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Registration.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                }
                            });

                            Toast.makeText(Registration.this, "User Created.", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();

                            user.put("fName",fullName);
                            user.put("email",email);
                            user.put("phone",phone);
                            user.put("user",users);

                            user.put("currentDoctor", "");
                            user.put("bloodGroup", "");
                            user.put("state", "");
                            user.put("city", "");

                            //Date And Time
                            user.put("RegistrationDate",RegistrationDate);
                            user.put("ProfileID",userID);


                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            Intent i = new Intent(getApplicationContext(),Home.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.putExtra("EXIT", true);
                            startActivity(i);
                            finish();


                        }else {
                            Toast.makeText(Registration.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        //Go to Login Page
        mLoginBtn = findViewById(R.id.GotoLogin);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

    }
}