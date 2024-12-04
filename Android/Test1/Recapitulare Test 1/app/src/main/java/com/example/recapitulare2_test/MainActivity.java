package com.example.recapitulare2_test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvHuse;
    private List<HusaTelefon> listaHuse = new ArrayList<>();
    private ActivityResultLauncher<Intent> launcher;

    private int selectedHusa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvHuse = findViewById(R.id.lvHuse);
        Button btnAdaugare = findViewById(R.id.btnAdaugaHusa);

        lvHuse.setOnItemClickListener((adapterView,view ,position,l)->{
            selectedHusa = position;

            HusaTelefon husa = listaHuse.get(position);

            Toast.makeText(this, husa.toString(), Toast.LENGTH_SHORT).show();
        });

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result->{
           Intent intent =result.getData();

           HusaTelefon husaPreluata = (HusaTelefon) intent.getSerializableExtra("addHusa");

           if(husaPreluata!=null) {
               listaHuse.add(husaPreluata);
               //ArrayAdapter<HusaTelefon> adapterLv = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,listaHuse);

               AdapterHusa adapterLv = new AdapterHusa(getApplicationContext(),R.layout.view_huse,listaHuse,getLayoutInflater());
               lvHuse.setAdapter(adapterLv);
           }
        });


        btnAdaugare.setOnClickListener(view->{
            Intent intent = new Intent(getApplicationContext(),AdaugaHusa.class);
            launcher.launch(intent);
        });
    }
}