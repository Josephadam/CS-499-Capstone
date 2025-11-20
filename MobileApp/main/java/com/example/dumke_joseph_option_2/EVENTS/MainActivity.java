package com.example.dumke_joseph_option_2.EVENTS;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dumke_joseph_option_2.DATA.DBHelper;
import com.example.dumke_joseph_option_2.R;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    EditText editEmail, editPassword;
    Button buttonLogin;
    TextView textCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);  // This should match your login layout file

        dbHelper = new DBHelper(this);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textCreateAccount = findViewById(R.id.textCreateAccount);

        // Handle login
        buttonLogin.setOnClickListener(v -> {
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean valid = dbHelper.checkUser(email, password);
            if (valid) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, EventsActivity.class));  // Replace with your real target
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle create account
        textCreateAccount.setOnClickListener(v -> {
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dbHelper.userExists(email)) {
                Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
            } else {
                boolean inserted = dbHelper.insertUser(email, password);
                if (inserted) {
                    Toast.makeText(this, "Account created!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to create account", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
