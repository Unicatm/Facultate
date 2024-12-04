package com.example.recapitulare2_test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class AdaugaHusa extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adauga_husa);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String[] culori = {"Babyblue","Coral","Midnight blue"};

        EditText etMaterial = findViewById(R.id.etMaterial);
        EditText etLungime = findViewById(R.id.etLungime);
        Spinner spnCulori = findViewById(R.id.spnCuloare);
        ArrayAdapter<String> adapterSpn = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,culori);
        spnCulori.setAdapter(adapterSpn);

        Button btnAdauga =findViewById(R.id.btnAdaugare);

        btnAdauga.setOnClickListener(view->{

            String material = etMaterial.getText().toString();
            float lungime = Float.parseFloat(etLungime.getText().toString());
            String culoare = (String) spnCulori.getSelectedItem();

            HusaTelefon husa = new HusaTelefon(material,lungime,culoare);

            Intent intent = getIntent();
            intent.putExtra("addHusa",husa);

            setResult(RESULT_OK, intent);
            finish();
        });

    }
}