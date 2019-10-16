package com.example.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login.R;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity {


    public Button search, find;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        search = (Button) findViewById(R.id.search);
        find = (Button) findViewById(R.id.find);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
           if(bundle.getString("some") != null){
               Toast.makeText(getApplicationContext(),
                       "data:"+bundle.getString("some"),
                       Toast.LENGTH_SHORT).show();
            }
        }
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, PassengerMapsActivity.class);
                startActivity(intent);
                finish();
                return;

            }
        });

        find.setOnClickListener(new View.OnClickListener() {
            // call OwnerMap for Owner Details
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, OwnerMapsActivity.class);
                startActivity(intent);
                finish();
                return;

            }
        });



    }
}
