package com.example.todolistapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolistapp.R;
import com.example.todolistapp.database.UserDatabaseHandler;
import com.example.todolistapp.helper.AnimateView;
import com.example.todolistapp.models.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        AnimateView.startAnimation(R.id.login_bg , this , 1350);
        Button login = findViewById(R.id.LoginButton);
        TextView signup = findViewById(R.id.sign_up);
        signup.setPaintFlags(signup.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        TextInputEditText user =  findViewById(R.id.Username);
        TextInputEditText pass =  findViewById(R.id.password);
        UserDatabaseHandler userHandler = new UserDatabaseHandler(this);
        login.setOnClickListener(view -> {

            User logged = userHandler
                    .checkUserNamePassword(Objects.requireNonNull(user.getText()).toString() ,
                            Objects.requireNonNull(pass.getText()).toString());

            try {
                if(logged.getUser_id() == -1){
                    Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(this , MainActivity.class);
                    i.putExtra("name" , logged.getName());
                    i.putExtra("id" , logged.getUser_id());
                    startActivity(i);
                }
            }catch (Exception e){
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            }



        });
        signup.setOnClickListener(view -> {
            Intent i = new Intent(this , SignUp.class);
            startActivity(i);
        });
    }
}