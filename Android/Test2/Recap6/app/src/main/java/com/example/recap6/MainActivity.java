package com.example.recap6;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    private ListView lvMancare;
    private List<Mancare> lista = new ArrayList<>();
    private ActivityResultLauncher<Intent> launcher;
    private ArrayAdapter<Mancare> adapterLv;
    private final static String URL = "https://www.jsonkeeper.com/b/7SK6";

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

        lvMancare = findViewById(R.id.lvMancare);

        MancareDB mancareDB = MancareDB.getInstance(getApplicationContext());
        MancareDAO mancareDAO = mancareDB.getMancareDAO();

        lista.addAll(mancareDAO.getAllMancare());

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result->{
           if(result.getData().hasExtra("mancareDeSalvat")){
               Intent intent = result.getData();

               Mancare mancareDeSalvat = (Mancare) intent.getSerializableExtra("mancareDeSalvat");

               if(mancareDeSalvat!=null) {
                   mancareDAO.addMancare(mancareDeSalvat);
                   lista.clear();
                   lista.addAll(mancareDAO.getAllMancare());
                   adapterLv.notifyDataSetChanged();

               }
           }
        });

        adapterLv = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,lista);
        lvMancare.setAdapter(adapterLv);
        getMancareFromJson();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meniu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.mAdd){
            Intent intent = new Intent(getApplicationContext(),AddMancare.class);
            launcher.launch(intent);
            return true;
        }

        return false;
    }

    public void getMancareFromJson(){
        Thread thread = new Thread(){

            @Override
            public void run() {
                HttpsManager manager = new HttpsManager(URL);
                String json = manager.procesare();

                new Handler(Looper.getMainLooper()).post(()->{
                   lista.addAll(ParserMancare.parseJson(json));
                   adapterLv.notifyDataSetChanged();
                });
            }
        };
        thread.start();
    }
}