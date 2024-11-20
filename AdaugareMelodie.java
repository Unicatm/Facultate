package com.example.recapitulare3_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdaugareMelodie extends AppCompatActivity {

    private boolean isEditing=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adaugare_melodie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String[] genuri = {"Rock", "Alternative", "Pop", "Manele"};
        ArrayAdapter<String> spnAdapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,genuri);
        Spinner spnGen = findViewById(R.id.spnGen);
        spnGen.setAdapter(spnAdapter);

        EditText etNume = findViewById(R.id.etNume);
        EditText etDurata = findViewById(R.id.etDurata);
        EditText etData = findViewById(R.id.etData);
        RadioGroup rgFeedback = findViewById(R.id.rgFeedback);
        Button btnSalvare = findViewById(R.id.btnSalvare);

        Intent editIntent = getIntent();

        if(editIntent.hasExtra("editMelodie")){
            isEditing = true;
            Melodie melodieApasata = (Melodie) editIntent.getSerializableExtra("editMelodie");

            etNume.setText(melodieApasata.getNumeMelodie());
            etDurata.setText(String.valueOf(melodieApasata.getDurata()));
            etData.setText(new SimpleDateFormat("dd.MM.yyyy").format(melodieApasata.getPublicare()));
            for(int i=0;i<spnAdapter.getCount();i++){
                if(melodieApasata.getGen().equals(spnAdapter.getItem(i))){
                    spnGen.setSelection(i);
                }
            }

            switch (melodieApasata.getFeedback()){
                case "Like": {
                    rgFeedback.check(R.id.rbLike);
                    break;
                }
                case "Dislike": {
                    rgFeedback.check(R.id.rbDislike);
                    break;
                }
            }
        }

        SharedPreferences sharedPreferences = getSharedPreferences("local",MODE_PRIVATE);

        String token = sharedPreferences.getString("token","ceva2");
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();

        btnSalvare.setOnClickListener(view->{

            String nume = etNume.getText().toString();
            float durata = Float.parseFloat(etDurata.getText().toString());
            Date data = null;

            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            try {
                data = sdf.parse(etData.getText().toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            RadioButton rb = findViewById(rgFeedback.getCheckedRadioButtonId());
            String feedback = rb.getText().toString();

            String gen = spnGen.getSelectedItem().toString();

            Melodie melodie = new Melodie(nume, gen,durata,data, feedback);

            Intent intent = getIntent();

            if(isEditing){
                intent.putExtra("editMelodie", melodie);
                isEditing = false;
            }else{
                intent.putExtra("addMelodie", melodie);
            }

            setResult(RESULT_OK, intent);
            finish();

        });
    }
}