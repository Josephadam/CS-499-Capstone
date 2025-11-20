package com.example.dumke_joseph_option_2.SMS;

import android.Manifest;
import android.content.Context;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class SmsNotifier {

    public static void trySendSms(Context context, String phone, String message) {
        if (phone == null || phone.trim().isEmpty()) {
            Toast.makeText(context, "No phone number set", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean granted = ContextCompat.checkSelfPermission(
                context, Manifest.permission.SEND_SMS) == PERMISSION_GRANTED;

        if (!granted) {
            // Permission denied → app continues normally
            Toast.makeText(context, "SMS permission denied. Skipping text.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phone, null, message, null, null);
            Toast.makeText(context, "SMS sent", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "SMS failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
