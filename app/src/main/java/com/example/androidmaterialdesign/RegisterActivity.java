package com.example.androidmaterialdesign;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidmaterialdesign.model.User;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private EditText inputName, inputSurname, inputEmail,
            inputPassword, inputLogin, inputConfirmPassword;
    private Button buttonRegister;
    private TextInputLayout textInputLayoutConfirmPassword;

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
            } catch (JSONException | InterruptedException e) {
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
        this.inputConfirmPassword = findViewById(R.id.input_ConfirmPassword);
        this.textInputLayoutConfirmPassword = findViewById(R.id.TextInputLayout_confirmPassword);
        initListeners();
    }

    private void registerNewUser() throws JSONException, InterruptedException {
        if(inputConfirmPassword.getText().toString().equals(inputPassword.getText().toString())){
            textInputLayoutConfirmPassword.setHelperText("");
            User registeredUser = new User(inputLogin.getText().toString(), inputPassword.getText().toString(), inputName.getText().toString(),
                    inputSurname.getText().toString(), inputEmail.getText().toString());
            String insertedDataAsJson = "{\'userLogin\' : \'"+registeredUser.getUserLogin() +"\'," +
                    " \'userEmail\' : \'"+registeredUser.getUserEmail()+"\'," +
                    " \'userPassword\' : \'"+registeredUser.getUserPassword()+"\'," +
                    " \'userName\' : \'"+registeredUser.getUserName()+"\'," +
                    " \'userSurname\' : \'"+registeredUser.getUserSurname()+"\'}";
            JSONObject jsonObject = new JSONObject(insertedDataAsJson);
            String requestUrl = "http://192.168.0.103:8080/users/add";
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest objectRequest = new JsonObjectRequest(
                    Request.Method.POST, requestUrl, jsonObject,
                    response-> Log.e("REST RESPONSE: ", response.toString()),
                    error-> Log.e("REST RESPONSE: ", error.toString())
            );
            requestQueue.add(objectRequest);
            Toast.makeText(RegisterActivity.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
            Thread.sleep(1000);
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }else{
            textInputLayoutConfirmPassword.setHelperText("Passwords don't match");
        }
    }
}