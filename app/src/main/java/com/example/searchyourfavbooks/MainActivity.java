package com.example.searchyourfavbooks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        Button button = (Button) findViewById (R.id.button);
        button.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById (R.id.edit_text);
                try {
                    num = Integer.parseInt (editText.getText ().toString ());
                    Intent intent = new Intent (getApplicationContext (), BookActivity.class);
                    startActivity (intent);
                } catch (NumberFormatException e) {
                    Toast.makeText (MainActivity.this, "Invalid Input", Toast.LENGTH_SHORT).show ();
                }
            }
        });
    }
}