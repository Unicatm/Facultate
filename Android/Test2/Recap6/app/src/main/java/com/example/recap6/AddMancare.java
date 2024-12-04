package com.example.recap6;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddMancare extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_mancare);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etDen = findViewById(R.id.etDen);
        EditText etKcal = findViewById(R.id.etKcal);
        EditText etDataExp = findViewById(R.id.etDataExp);
        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v->{
            String den = etDen.getText().toString();
            double kcal = Double.parseDouble(etKcal.getText().toString());
            Date data;

            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            try {
                data = sdf.parse(etDataExp.getText().toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            Mancare m = new Mancare(den,kcal,data);

            Intent intent= getIntent();
            intent.putExtra("mancareDeSalvat",m);
            setResult(RESULT_OK,intent);
            finish();
        });
    }
}