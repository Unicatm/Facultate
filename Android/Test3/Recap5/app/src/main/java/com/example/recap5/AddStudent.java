package com.example.recap5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddStudent extends AppCompatActivity {

    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etNume = findViewById(R.id.etNume);
        EditText etMedie = findViewById(R.id.etMedie);
        Spinner spn = findViewById(R.id.spnSpecializare);
        String[] specializari = {"Stat","CSIE","Cibe"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,specializari);
        spn.setAdapter(adapter);

        Button btnSave = findViewById(R.id.btnSave);
        Button btnDelete = findViewById(R.id.btnDelete);


        Intent editIntent = getIntent();
        if(editIntent.hasExtra("deEditat")){
            isEdit = true;
            Student stud = (Student) editIntent.getSerializableExtra("deEditat");
            btnDelete.setVisibility(View.VISIBLE);

            etNume.setText(stud.getNume());
            etMedie.setText(String.valueOf(stud.getMedie()));

            for (int i = 0; i < adapter.getCount(); i++) {
                if(stud.getSpecializare().equals(adapter.getItem(i))){
                    spn.setSelection(i);
                }
            }

            AppDB appDB = AppDB.getInstance(getApplicationContext());
            StudentiDAO studentiDAO = appDB.getStudentiDAO();

            btnDelete.setOnClickListener(v->{
                studentiDAO.deleteStud(stud);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("deSters",true);
                setResult(RESULT_OK, intent);
                finish();
            });
        }

        btnSave.setOnClickListener(v->{
            String den = etNume.getText().toString();
            String spec = spn.getSelectedItem().toString();
            float medie = Float.parseFloat(etMedie.getText().toString());

            Student stud = new Student(den,medie,spec);

            Intent intent = getIntent();

            if(isEdit){
                intent.putExtra("deEditat",stud);
                isEdit = false;
            }else{
                intent.putExtra("deAdaugat",stud);
            }

            setResult(RESULT_OK,intent);
            finish();

        });
    }
}