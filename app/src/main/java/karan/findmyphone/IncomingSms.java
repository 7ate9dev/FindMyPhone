package karan.findmyphone;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class IncomingSms extends BroadcastReceiver {
    final SmsManager sms = SmsManager.getDefault();
    String message, temp;
    char[] ch=new char[10];
    String data;
    String format;
    public void onReceive(Context context, Intent intent) {
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();
        temp = intent.getAction();
        Log.i("Receiver", "Broadcast received: " + temp);
        data=intent.getExtras().getString("data");
        if(data!=null) {
            try {
                FileOutputStream fOut = context.openFileOutput("test",Context.MODE_PRIVATE);
                fOut.write(data.getBytes());
                fOut.close();
                Log.i("WRITE","done"+data);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try {
            FileInputStream fin=context.openFileInput("test");
            InputStreamReader inputStreamReader = new InputStreamReader(fin);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            data = bufferedReader.readLine();
            sb.append(data);
            Log.i("READ",""+data);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Log.i("DATA",""+ data);

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                format=intent.getStringExtra("format");
                SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[0],format);
                String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                String senderNum = phoneNumber;
                message = currentMessage.getDisplayMessageBody();
                Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
                // Show Alert

                if (message.equalsIgnoreCase(data)) {
                    Intent pop = new Intent(context, PopUp.class);
                    pop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(pop);
                }
            } // bundle is null
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);
        }


    }
}
