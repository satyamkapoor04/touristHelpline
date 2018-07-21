package com.example.root.touristhelpline2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    EditText username;
    EditText password;
    EditText email;
    RadioGroup gender;
    Button register;
    ProgressDialog progressDialog;
    String user;
    String pass;
    String em;
    String gen;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        gender = findViewById(R.id.radioGender);
        email = findViewById(R.id.email);
        register = findViewById(R.id.register);
        progressDialog = new ProgressDialog(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = username.getText(). toString();
                pass = password.getText().toString();
                em = email.getText().toString();
                RadioButton rr = findViewById(gender.getCheckedRadioButtonId());
                gen = rr.getText().toString();
                boolean success = true;
                if (TextUtils.isEmpty(user)) {
                    username.setError("Username Required");
                    success = false;
                }
                if (TextUtils.isEmpty(pass)) {
                    password.setError("Password Required");
                    success = false;
                } else if (pass.length() > 12 || pass.length() < 6) {
                    password.setError("6-12 characters");
                    success = false;
                }
                if (TextUtils.isEmpty(em)) {
                    email.setError("Email Required");
                    success = false;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(em).matches()) {
                    email.setError("Invalid email");
                    success = false;
                }

                if (success) {
                    new UserRegister().execute();
                }
            }
        });

    }

    class UserRegister extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();
            HashMap<String,String> values = new HashMap<>();
            values.put ("username",user);
            values.put ("password",pass);
            values.put ("gender",gen);
            values.put ("email",em);
            return requestHandler.sendPostRequest(URLs.RegisterURL,values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (TextUtils.isEmpty(s)) {
                Toast.makeText(Register.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    JSONObject str = new JSONObject(s);
                    if (!str.getBoolean("error")) {
                        Toast.makeText(Register.this, str.getString("message"), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Register.this, str.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
