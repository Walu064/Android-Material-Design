package com.example.androidmaterialdesign;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInputTextField, passwordInputTextField;
    private Button loginButton;
    private String insertedLogin, insertedPassword;
    private TextView registerTextView;
    private TextInputLayout textInputLayoutEmail, textInputLayoutPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_login);
        initFrontComponents();
        textInputLayoutEmail.setHelperText("");
        textInputLayoutPassword.setHelperText("");
    }

    private void initListeners(){
        this.loginButton.setOnClickListener(view -> {
            this.insertedLogin = getEmailFromTextField();
            this.insertedPassword = getPasswordFromTextField();
            String requestUrl = "http://192.168.0.103:8080/user/"+insertedLogin;
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest objectRequest = new JsonObjectRequest(
                    Request.Method.GET, requestUrl, null,
                    response -> {
                        try {
                            Log.e("REST RESPONSE ", response.getString("userPassword"));
                            if(insertedPassword.equals(response.getString("userPassword"))){
                                startMainActivity(insertedLogin);
                            }else{
                                textInputLayoutPassword.setHelperText("Wrong password!");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error ->{ Log.e("REST RESPONSE ", error.toString());
                        textInputLayoutPassword.setHelperText("User "+insertedLogin+" doesn't exist.");
                    }
            );
            requestQueue.add(objectRequest);
        });
        this.registerTextView.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void startMainActivity(String username){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void initFrontComponents(){
        this.emailInputTextField = findViewById(R.id.emailEditText);
        this.passwordInputTextField = findViewById(R.id.passwordEditText);
        this.loginButton = findViewById(R.id.registerButton);
        this.registerTextView = findViewById(R.id.textViewRegister);
        this.textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        this.textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        initListeners();
    }

    private String getEmailFromTextField(){
        return this.emailInputTextField.getText().toString();
    }

    private String getPasswordFromTextField(){
        return this.passwordInputTextField.getText().toString();
    }

}
