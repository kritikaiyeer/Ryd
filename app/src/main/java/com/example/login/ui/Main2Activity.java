package com.example.login.ui;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.login.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main2Activity extends AppCompatActivity {

    private Button mButtonChoice0;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;
    private TextView mQuestionView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final int pay_amt=0;
        String userId="csddfgfghyj";

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/" + userId);
        myRef.child("pay_amt").setValue(pay_amt);

        mQuestionView = (TextView)findViewById(R.id.textView);
        mButtonChoice0=(Button)findViewById(R.id.COD);
        mButtonChoice1 = (Button)findViewById(R.id.Paytm);
        mButtonChoice2 = (Button)findViewById(R.id.GPay);
        mButtonChoice0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, cod.class);
                intent= intent.putExtra("key", pay_amt);
                startActivity(intent);

            }
        });

//        @Override
        mButtonChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, webview1.class);
                startActivity(intent);


            }
        });


        mButtonChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.nbu.paisa.user&hl=en_IN"));
                startActivity(intent);


            }
        });

    }
}
