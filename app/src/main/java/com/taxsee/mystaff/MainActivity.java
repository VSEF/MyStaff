package com.taxsee.mystaff;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private EditText et_login, et_password;
    private CheckBox cb_save;
    private Button btn_login;
    private TextView tv_sendLoginPassword;

    public static SharedPreferences sharedPreferences;

    public static final String PREFERANCE = "myPref";
    public static final String LOGIN_KEY = "login_key";
    public static final String PASSWORD_KEY = "password_key";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_login = findViewById(R.id.et_login);
        et_password = findViewById(R.id.et_password);
        cb_save = findViewById(R.id.cb_box);
        btn_login = findViewById(R.id.btn_login);
        tv_sendLoginPassword = findViewById(R.id.tv_forget);


        sharedPreferences = getSharedPreferences(PREFERANCE,
                Context.MODE_PRIVATE);

        if ((sharedPreferences.contains(LOGIN_KEY))&&(sharedPreferences.contains(PASSWORD_KEY))) {
            et_login.setText(sharedPreferences.getString(LOGIN_KEY, ""));
            et_password.setText(sharedPreferences.getString(PASSWORD_KEY, ""));
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login = et_login.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                if(et_login.getText().toString().trim().equals("")){
                    et_login.setError("Please enter login");
                }

                if(et_password.getText().toString().trim().equals("")) {
                    et_password.setError("Please enter password");
                }

                if(!et_login.getText().toString().trim().equals("")&& !et_password.getText().toString().trim().equals("")) {


                try {

                    String receivedData= new DownloadTask().execute("https://contact.taxsee.com/Contacts.svc/Hello?login=" + login + "&password=" + password).get();

                    if(receivedData.contains("null")){

                        Toast.makeText(getApplicationContext(),"Successfully logged in",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                        startActivity(intent);
                    }else{
                            Toast.makeText(getApplicationContext(),"Failed to login. Try again.",Toast.LENGTH_LONG).show();
                    }

                }catch (ExecutionException | InterruptedException ei){
                    ei.printStackTrace();
                }
                }

            }
        });
    }

    public void saveLoginPassword(View v){

        if  (cb_save.isChecked()) {
            String loginText = et_login.getText().toString();
            String passwordText = et_password.getText().toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(LOGIN_KEY, loginText);
            editor.putString(PASSWORD_KEY, passwordText);
            editor.apply();
            Toast.makeText(getApplicationContext(), "Login and Password saved successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendLoginPassword(View v){

        if (sharedPreferences.contains(LOGIN_KEY) && sharedPreferences.contains(PASSWORD_KEY)) {
            et_login.setText(sharedPreferences.getString(LOGIN_KEY, ""));
            Toast.makeText(getApplicationContext(),"Login is: " + sharedPreferences.getString(LOGIN_KEY, "")
                + "\nPassword is: " + sharedPreferences.getString(PASSWORD_KEY, ""), Toast.LENGTH_LONG).show();
        }
    }
}
