package com.example.recapitulare3_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Melodie> listaMelodii = new ArrayList<>();
    private ListView lvMelodii;
    private ActivityResultLauncher<Intent> launcher;

    private int selectedPosition;

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

        lvMelodii = findViewById(R.id.lvMelodii);


        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result->{

            if(result.getData().hasExtra("addMelodie")){

                Intent intent = result.getData();
                Melodie melodie = (Melodie) intent.getSerializableExtra("addMelodie");

                if(melodie!=null) {
                    listaMelodii.add(melodie);
                    AdapterMelodie adapterLv = new AdapterMelodie(getApplicationContext(),R.layout.view_melodii,listaMelodii,getLayoutInflater());
                    //ArrayAdapter<Melodie> adapterLv = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaMelodii);
                    lvMelodii.setAdapter(adapterLv);
                }
            }else if(result.getData().hasExtra("editMelodie")){
                Intent intent = result.getData();
                Melodie melodie = (Melodie) intent.getSerializableExtra("editMelodie");

                if(melodie!=null){
                    Melodie melodieDeActualizat = listaMelodii.get(selectedPosition);

                    melodieDeActualizat.setNumeMelodie(melodie.getNumeMelodie());
                    melodieDeActualizat.setDurata(melodie.getDurata());
                    melodieDeActualizat.setFeedback(melodie.getFeedback());
                    melodieDeActualizat.setGen(melodie.getGen());
                    melodieDeActualizat.setPublicare(melodie.getPublicare());

                    AdapterMelodie adapterNou = (AdapterMelodie) lvMelodii.getAdapter();
                    adapterNou.notifyDataSetChanged();
                }
            }

        });

        lvMelodii.setOnItemClickListener((adapterView, view,position,l)->{
            selectedPosition = position;
            Intent intent = new Intent(getApplicationContext(), AdaugareMelodie.class);
            intent.putExtra("editMelodie",listaMelodii.get(position));
            launcher.launch(intent);
        });

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        fabAdd.setOnClickListener(view->{
            Intent intent = new Intent(getApplicationContext(),AdaugareMelodie.class);
            launcher.launch(intent);
        });

        SharedPreferences sharedPreferences = getSharedPreferences("local",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", "blalblabla");
        editor.apply();

        String token = sharedPreferences.getString("token","ceva1");
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meniu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.menuAdd){
            Intent intent = new Intent(getApplicationContext(),AdaugareMelodie.class);
            launcher.launch(intent);
            return true;
        }

        return false;
    }
}