package com.example.androidmaterialdesign;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {

    private EditText emailInputTextField, passwordInputTextField;
    private Button loginButton;
    private String insertedEmail, insertedPassword;
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
            this.insertedEmail = getEmailFromTextField();
            this.insertedPassword = getPasswordFromTextField();
            if(this.insertedEmail.equals("testUser123") && this.insertedPassword.equals("testPassword123")) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("loggedUserEmail", this.insertedEmail);
                startActivity(intent);
            } else if(this.insertedEmail.equals("testUser123") && !this.insertedPassword.equals("testPassword123")){
                textInputLayoutEmail.setHelperText("");
                textInputLayoutPassword.setHelperText("Wrong password!");
            } else if(!this.insertedEmail.equals("testUser123") && this.insertedPassword.equals("testPassword123")){
                textInputLayoutEmail.setHelperText("Wrong e-mail address!");
                textInputLayoutPassword.setHelperText("");
            } else if(!this.insertedEmail.equals("testUser123") && !this.insertedPassword.equals("testPassword123")){
                textInputLayoutEmail.setHelperText("Wrong e-mail address!");
                textInputLayoutPassword.setHelperText("Wrong password!");
            }
        });
        this.registerTextView.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        });
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
