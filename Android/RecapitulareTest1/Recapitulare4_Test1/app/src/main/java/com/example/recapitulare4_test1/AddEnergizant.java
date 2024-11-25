package com.example.recapitulare4_test1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddEnergizant extends AppCompatActivity {

    private boolean isEditing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_energizant);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String[] arome = {"Pepene", "Capsuni", "Blueberry"};
        Spinner spnAroma = findViewById(R.id.spnAroma);
        ArrayAdapter<String> spnAdapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,arome);
        spnAroma.setAdapter(spnAdapter);


        EditText etDen = findViewById(R.id.etDen);
        EditText etCant = findViewById(R.id.etCantitate);
        RadioGroup rgZahar = findViewById(R.id.rgZahar);

        Button btnSalveaza = findViewById(R.id.btnSalveaza);

        Intent editIntent = getIntent();
        if(editIntent.hasExtra("edit")){
            isEditing = true;

            Energizant enrgLuat = (Energizant) editIntent.getSerializableExtra("edit");

            etDen.setText(enrgLuat.getDen());
            etCant.setText(String.valueOf(enrgLuat.getCantitate()));

            for(int i=0;i<spnAroma.getCount();i++){
                if(enrgLuat.getAroma().equals(spnAdapter.getItem(i))){
                    spnAroma.setSelection(i);
                }
            }

            switch (enrgLuat.getIndulcit()){
                case "Cu zahar":{
                    rgZahar.check(R.id.rbZahar);
                }
                case "Fara zahar":{
                    rgZahar.check(R.id.rbFaraZahar);
                }
            }
        }

        btnSalveaza.setOnClickListener(view->{

            String den = etDen.getText().toString();
            int cant = Integer.parseInt(etCant.getText().toString());
            String aroma = spnAroma.getSelectedItem().toString();

            RadioButton rb = findViewById(rgZahar.getCheckedRadioButtonId());
            String zahar = rb.getText().toString();

            Energizant enrg = new Energizant(den, cant, aroma, zahar);

            Intent intent = getIntent();

            if(isEditing){
                intent.putExtra("edit",enrg);
                isEditing=false;
            }else{
                intent.putExtra("add",enrg);
            }

            setResult(RESULT_OK, intent);
            finish();
        });
    }
}