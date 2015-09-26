package karan.findmyphone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button button;
    TextView textView;
    String data, temp;
    LinearLayout linearLayout;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        Log.i("text", editText.getText().toString());
        if (textView.getText().toString().equalsIgnoreCase("no code set")) {

            FileInputStream fin = null;
            try {
                fin = openFileInput("test");
                InputStreamReader inputStreamReader = new InputStreamReader(fin);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                temp = bufferedReader.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (temp != null)
            textView.setText(temp);


        View.OnClickListener myListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(editText.getText().toString());
                data = textView.getText().toString();

                if (TextUtils.isEmpty(editText.getText().toString())) {
                    flag = 0;
                } else flag = 1;
                Intent pop = new Intent(MainActivity.this, popup2.class);
                pop.putExtra("flag", flag);
                pop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(pop);


                Intent intent = new Intent("my.action.string");
                intent.putExtra("data", data);

                sendBroadcast(intent);


            }
        };
        button.setOnClickListener(myListener);

    }
}


