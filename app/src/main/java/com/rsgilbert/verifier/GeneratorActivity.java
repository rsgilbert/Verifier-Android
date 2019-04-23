package com.rsgilbert.verifier;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;

public class GeneratorActivity extends AppCompatActivity {

    Button generator;
    ArrayList<String> companies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);

        generator = findViewById(R.id.generator);
        companies.add("Safi");
        companies.add("Tecno");
        companies.add("Cocacola");

        generator.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                            Bitmap bitmap = barcodeEncoder.encodeBitmap("Cocacola", BarcodeFormat.CODE_128, 200, 150);
                            ImageView barCode = findViewById(R.id.barCode);
                            barCode.setImageBitmap(bitmap);
                        } catch (Exception e) {
                            Toast.makeText(GeneratorActivity.this, "Unable to generate image", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

}
