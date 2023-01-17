package com.example.androidmaterialdesign;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private EditText inputName, inputSurname, inputEmail, inputPassword, inputLogin;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_register);
        initFrontComponents();
    }

    private void initListeners(){
       this.buttonRegister.setOnClickListener(view -> {
            try {
                registerNewUser();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private void initFrontComponents(){
        this.buttonRegister = findViewById(R.id.buttonRegister);
        this.inputLogin = findViewById(R.id.input_login);
        this.inputName = findViewById(R.id.input_name);
        this.inputSurname = findViewById(R.id.input_surname);
        this.inputEmail = findViewById(R.id.input_email);
        this.inputPassword = findViewById(R.id.input_password);
        initListeners();
    }

    private void registerNewUser() throws JSONException {
        String name, surname, email, password, login;
        login = inputLogin.getText().toString();
        name = inputName.getText().toString();
        surname = inputSurname.getText().toString();
        email = inputEmail.getText().toString();
        password = inputPassword.getText().toString();
        String insertedDataAsJson = "{\'userLogin\' : \'"+login+"\', \'userEmail\' : \'"+email+"\', \'userPassword\' : \'"+password+"\', \'userName\' : \'"+name+"\', \'userSurname\' : \'"+surname+"\'}";
        JSONObject jsonObject = new JSONObject(insertedDataAsJson);
        String requestUrl = "http://127.0.0.1:8080/users/add";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST, requestUrl, jsonObject,
                response-> Log.e("REST RESPONSE: ", response.toString()),
                error-> Log.e("REST RESPONSE: ", error.toString())
        );
        requestQueue.add(objectRequest);
    }
}