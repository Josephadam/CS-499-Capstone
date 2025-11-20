package com.example.dumke_joseph_option_2.SMS;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import com.example.dumke_joseph_option_2.R;

public class SmsSettingsActivity extends AppCompatActivity {

    private TextView textPermissionStatus;
    private Button buttonRequestSmsPermission, buttonSendTestSms;
    private EditText editPhoneNumber;

    // Persist the user’s phone number
    private SharedPreferences prefs;

    private final ActivityResultLauncher<String> requestSmsPermission =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                updateUiForPermission(isGranted);
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_permisssion);

        prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);

        textPermissionStatus = findViewById(R.id.textPermissionStatus);
        buttonRequestSmsPermission = findViewById(R.id.buttonRequestSmsPermission);
        buttonSendTestSms = findViewById(R.id.buttonSendTestSms);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);

        // restore saved phone number
        editPhoneNumber.setText(prefs.getString("phone", ""));

        boolean granted = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PERMISSION_GRANTED;
        updateUiForPermission(granted);

        buttonRequestSmsPermission.setOnClickListener(v ->
                requestSmsPermission.launch(Manifest.permission.SEND_SMS)
        );

        buttonSendTestSms.setOnClickListener(v -> {
            String phone = editPhoneNumber.getText().toString().trim();
            prefs.edit().putString("phone", phone).apply();

            SmsNotifier.trySendSms(this, phone, "Test: SMS alerts are enabled for your reminders.");
        });
    }

    private void updateUiForPermission(boolean granted) {
        if (granted) {
            textPermissionStatus.setText("Permission: GRANTED");
            buttonSendTestSms.setEnabled(true);
        } else {
            textPermissionStatus.setText("Permission: DENIED");
            buttonSendTestSms.setEnabled(false);
        }
    }
}
