package com.example.todolistapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import com.example.todolistapp.R;
import com.example.todolistapp.database.UserDatabaseHandler;
import com.example.todolistapp.helper.AnimateView;
import com.example.todolistapp.models.User;
import com.google.android.material.textfield.TextInputEditText;


public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        AnimateView.startAnimation(R.id.sign_up_bg , this , 1350);
        Button signup = findViewById(R.id.SignUpButton);
        TextInputEditText user =  findViewById(R.id.SignUpUsername);
        TextInputEditText pass =  findViewById(R.id.SignUpPassword);
        UserDatabaseHandler userHandler = new UserDatabaseHandler(this);
        signup.setOnClickListener(view -> {
            try {
                if(user.getText().toString().isEmpty() || pass.getText().toString().isEmpty()){
                    throw new Exception();
                }

                User signed = userHandler
                        .checkUserNamePassword(user.getText().toString() , pass.getText().toString());
                if(signed.getUser_id()>=0){
                    Toast.makeText(this, "User already registered", Toast.LENGTH_SHORT).show();
                    throw new Exception();
                }
                else
                {
                    userHandler.addUser(user.getText().toString(), pass.getText().toString());
                    Intent i = new Intent(this, LoginPage.class);
                    Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show();
                    startActivity(i);
                }
            }catch (Exception e){
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}