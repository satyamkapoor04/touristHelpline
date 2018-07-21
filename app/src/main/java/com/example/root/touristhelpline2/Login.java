package com.example.root.touristhelpline2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    TextView register;
    EditText username;
    EditText password;
    Button send;
    ProgressDialog progressDialog;
    private static final String url = URLs.LoginURL;
    String user;
    String pass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });


        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        send = findViewById(R.id.login);
        progressDialog = new ProgressDialog(this);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = username.getText().toString();
                pass = password.getText().toString();
                boolean success = true;
                if (TextUtils.isEmpty(user)) {
                    username.setError("Username required");
                    username.requestFocus();
                    success = false;
                }
                if (TextUtils.isEmpty(pass)) {
                    password.setError("Password Required");
                    password.requestFocus();
                    success= false;
                }

                if (success) {
                    new UserLogin().execute();
                }
            }
        });

    }

    public class UserLogin extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            HashMap<String,String> map = new HashMap<>();
            map.put ("username",user);
            map.put("password",pass);

            return requestHandler.sendPostRequest(url,map);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           if (progressDialog.isShowing())
                progressDialog.dismiss();
            if (TextUtils.isEmpty(s)) {
                Toast.makeText(Login.this, "Check Internet Connectivity", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    JSONObject str = new JSONObject(s);
                    if (!str.getBoolean("error")) {
                        JSONObject user = str.getJSONObject("user");
                        String usern = user.getString("username");
                        String usergender = user.getString("gender");
                        String useremail = user.getString("email");
                        User mainUser = new User(usern,usergender,useremail);
                        SharedPrefManager.getmInstance(getApplicationContext()).userLogin(mainUser);
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra ("username",usern);
                        intent.putExtra("gender",usergender);
                        intent.putExtra("email",useremail);
                        finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(Login.this, str.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
