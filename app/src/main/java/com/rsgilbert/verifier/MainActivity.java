package com.rsgilbert.verifier;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Button btn_scan;
    TextView text_result;
    ImageView status;

    String[] companies = {"Safi", "Tecno", "Cocacola"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_result = findViewById(R.id.text_result);
        btn_scan = findViewById(R.id.btn_scan);
        status = findViewById(R.id.status);

        btn_scan.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                        integrator.setPrompt("Scanning barcode");
                        integrator.setCameraId(0);
                        integrator.setBeepEnabled(true);
                        integrator.setBarcodeImageEnabled(true);
                        integrator.initiateScan();

                    }
                }
        );
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Changing to sign in", Toast.LENGTH_LONG);
                Intent intent = new Intent(MainActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned " + result.getContents(), Toast.LENGTH_LONG).show();
                String scanText = result.getContents();

                boolean found = false;
                for(String companyName : companies){
                    if (companyName.equals(scanText)) {
                        found = true;
                        text_result.setText("Success, The product was manufactured by " + scanText);
                        status.setImageResource(R.drawable.tick);
                        return;
                    }
                }
                if(found){
                    text_result.setText("Success, The product was manufactured by " + scanText);
                    status.setImageResource(R.drawable.tick);
                }
                else {
                    text_result.setText("Failed, This product is not authentic");
                    status.setImageResource(R.drawable.cross);
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


}
