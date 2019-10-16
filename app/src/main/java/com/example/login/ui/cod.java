package com.example.login.ui;

import android.content.Intent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.login.R;

public class cod extends AppCompatActivity {

    TextView t;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.codentry);
        Intent intent = getIntent();

        t = (TextView) findViewById(R.id.codText1);
        int fName = intent.getIntExtra("key", 0);
        t.setText(Integer.toString(fName));
    }
}
