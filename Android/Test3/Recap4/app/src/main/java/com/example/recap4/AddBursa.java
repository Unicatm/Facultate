package com.example.recap4;

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

public class AddBursa extends AppCompatActivity {

    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_bursa);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AppDB appDB = AppDB.getInstance(getApplicationContext());
        BurseDAO burseDAO = appDB.getBurseDAO();

        EditText etDen = findViewById(R.id.etDen);
        EditText etSuma = findViewById(R.id.etSuma);
        Spinner spn = findViewById(R.id.spnTip);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnDelete = findViewById(R.id.btnDelete);


        String[] tipuri = {"Merit", "Sociala","Performanta"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,tipuri);
        spn.setAdapter(adapter);

        Intent editIntent = getIntent();
        if(editIntent.hasExtra("deModificat")){
            isEdit = true;

            btnDelete.setVisibility(View.VISIBLE);

            Bursa bDeEditat = (Bursa) editIntent.getSerializableExtra("deModificat");

            etDen.setText(bDeEditat.getDen().toString());
            etSuma.setText(String.valueOf(bDeEditat.getSuma()));

            for (int i = 0; i < spn.getCount(); i++) {
                if(bDeEditat.getTip().equals(adapter.getItem(i))){
                    spn.setSelection(i);
                }
            }

            btnDelete.setOnClickListener(v->{
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                burseDAO.deleteBursa(bDeEditat);
                intent.putExtra("deSters",bDeEditat);
                setResult(RESULT_OK,intent);
                finish();
            });

        }

        btnSave.setOnClickListener(v->{
            String den = etDen.getText().toString();
            int suma = Integer.parseInt(etSuma.getText().toString());
            String tip = spn.getSelectedItem().toString();

            Bursa bursa = new Bursa(den,suma,tip);

            Intent intent = getIntent();

            if(isEdit){
                intent.putExtra("deModificat",bursa);
                isEdit = false;
            }else{
                intent.putExtra("deAdaugat",bursa);
            }

            setResult(RESULT_OK,intent);
            finish();

        });
        
        SharedPreferences shp = getSharedPreferences("local",MODE_PRIVATE);
        String token = shp.getString("token","ceva");
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();

    }
}
