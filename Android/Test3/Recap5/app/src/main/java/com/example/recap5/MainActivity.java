package com.example.recap5;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Student> lista = new ArrayList<>();
    private ListView lvStud;
    private ArrayAdapter<Student> adapter;
    private int SelectedStud;
    private ActivityResultLauncher<Intent> launcher;
    private AppDB appDB;
    private StudentiDAO studentiDAO;
    private String url = "https://www.jsonkeeper.com/b/7B4Z";

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

        Button btnMedie = findViewById(R.id.btnMedie);
        Button btnStud = findViewById(R.id.btnStud);
        Button btnJson = findViewById(R.id.btnJson);

        LinearLayout linear = findViewById(R.id.linearLayout);

        appDB = AppDB.getInstance(getApplicationContext());
        studentiDAO = appDB.getStudentiDAO();

        lista.clear();
        lista.addAll(studentiDAO.getAllStud());

        lvStud = findViewById(R.id.lvStud);
        adapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,lista);
        lvStud.setAdapter(adapter);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result->{
           if(result.getData().hasExtra("deAdaugat")){
               Intent intent = result.getData();

               Student stud = (Student) intent.getSerializableExtra("deAdaugat");

               studentiDAO.addStud(stud);
               lista.clear();
               lista.addAll(studentiDAO.getAllStud());
               adapter.notifyDataSetChanged();
           }else if(result.getData().hasExtra("deEditat")){
               Intent intent = result.getData();

               Student studModificat = (Student) intent.getSerializableExtra("deEditat");

               if(studModificat!=null){
                   Student studDeEditat = lista.get(SelectedStud);

                   studDeEditat.setNume(studModificat.getNume());
                   studDeEditat.setMedie(studModificat.getMedie());
                   studDeEditat.setSpecializare(studModificat.getSpecializare());

                   studentiDAO.updateStud(studDeEditat);
                   lista.clear();
                   lista.addAll(studentiDAO.getAllStud());
                   adapter.notifyDataSetChanged();
               }
           } else if (result.getData().hasExtra("deSters")) {
               lista.clear();
               lista.addAll(studentiDAO.getAllStud());
               adapter.notifyDataSetChanged();
           }
        });

        lvStud.setOnItemClickListener((adapterView, view,position,l)->{
            SelectedStud = position;

            Student deEditat = lista.get(position);

            Intent intent = new Intent(getApplicationContext(), AddStudent.class);
            intent.putExtra("deEditat",deEditat);
            launcher.launch(intent);
        });

        btnMedie.setOnClickListener(v->{
            String spec = "Stat";
            float medie= studentiDAO.afisareMedie(spec);
            Toast.makeText(this, "Media specializarii "+spec+" este de "+medie, Toast.LENGTH_SHORT).show();
        });

        btnStud.setOnClickListener(v->{
            String spec = "Stat";
            List<String> listaNume = studentiDAO.getNamesBySpec(spec);

            for (int i = 0; i < listaNume.size(); i++) {
                TextView textView = new TextView(getApplicationContext());
                textView.setText(listaNume.get(i));
                linear.addView(textView);
            }
        });

        btnJson.setOnClickListener(v->{
            getFromHttps();
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meniu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.mAdd){
            Intent intent = new Intent(getApplicationContext(), AddStudent.class);
            launcher.launch(intent);
            return true;
        }

        return false;
    }

    public void getFromHttps(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                HttpsManager manager = new HttpsManager(url);
                String json = manager.procesare();

                new Handler(Looper.getMainLooper()).post(()->{
                    List<Student> listaNoua = new ArrayList<>();
                    listaNoua.addAll(StudParser.parseJson(json));

                    for (int i = 0; i < listaNoua.size(); i++) {
                        studentiDAO.addStud(listaNoua.get(i));
                    }

                    lista.clear();
                    lista.addAll(studentiDAO.getAllStud());
                    adapter.notifyDataSetChanged();
                });
            }
        };
        thread.start();
    }
}