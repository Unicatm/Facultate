package com.example.recap4;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Bursa> lista = new ArrayList<>();
    private ListView lvBurse;
    private ArrayAdapter<Bursa> adapter;
    private int ItemSelectat;
    private ActivityResultLauncher<Intent> launcher;
    private final String url = "https://www.jsonkeeper.com/b/4XBJ";

    private AppDB appDB;
    private BurseDAO burseDAO;

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

        Button btnCalc = findViewById(R.id.btnCalc);
        Button btnStergereSociale = findViewById(R.id.btnStergereSociale);
        Button btnInsereaza = findViewById(R.id.btnInsereaza);

        appDB = AppDB.getInstance(getApplicationContext());
        burseDAO = appDB.getBurseDAO();
        lista.clear();
        lista.addAll(burseDAO.getAllBurse());

        lvBurse = findViewById(R.id.lvBurse);
        adapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,lista);
        lvBurse.setAdapter(adapter);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result->{
           if(result.getData().hasExtra("deAdaugat")){
               Intent intent = result.getData();

               Bursa bursa = (Bursa) intent.getSerializableExtra("deAdaugat");

                burseDAO.addBursa(bursa);
                lista.clear();
                lista.addAll(burseDAO.getAllBurse());
                adapter.notifyDataSetChanged();
//               lista.add(bursa);
//               adapter.notifyDataSetChanged();
           }else if(result.getData().hasExtra("deModificat")){
               Intent intent = result.getData();

               Bursa bEditata = (Bursa) intent.getSerializableExtra("deModificat");

               if(bEditata!=null){
                   Bursa bDeModificat = lista.get(ItemSelectat);

                   bDeModificat.setDen(bEditata.getDen());
                   bDeModificat.setSuma(bEditata.getSuma());
                   bDeModificat.setTip(bEditata.getTip());

                   burseDAO.updateBursa(bDeModificat);
                   lista.clear();
                   lista.addAll(burseDAO.getAllBurse());
                   adapter.notifyDataSetChanged();
               }
           }else if(result.getData().hasExtra("deSters")){
               lista.clear();
               lista.addAll(burseDAO.getAllBurse());
               adapter.notifyDataSetChanged();
           }
        });

        lvBurse.setOnItemClickListener((editView, view, position, l)->{
            ItemSelectat = position;

            Bursa bDeEditat = lista.get(position);

            Intent intent = new Intent(getApplicationContext(), AddBursa.class);
            intent.putExtra("deModificat",bDeEditat);
            launcher.launch(intent);
        });

        btnCalc.setOnClickListener(v->{
            int suma=0;
            for (int i = 0; i < lista.size(); i++) {
                suma+= lista.get(i).getSuma();
            }

            Toast.makeText(this, "Suma burselor este de "+suma+ " lei", Toast.LENGTH_SHORT).show();
        });

        btnStergereSociale.setOnClickListener(v->{
            burseDAO.stergeByTip("Sociala");
            lista.clear();
            lista.addAll(burseDAO.getAllBurse());
            adapter.notifyDataSetChanged();
        });

        btnInsereaza.setOnClickListener(v->{
            getBurseFromJson();
        });

        SharedPreferences shp = getSharedPreferences("local",MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putString("token","ceva");
        editor.apply();

        String token = shp.getString("token","ceva");
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meniu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.mAdd){
            Intent intent = new Intent(getApplicationContext(), AddBursa.class);
//            startActivity(intent);
            launcher.launch(intent);
            return true;
        }
        return false;
    }

    void getBurseFromJson(){
        Thread thread = new Thread(){

            @Override
            public void run() {
                HttpsManager manager = new HttpsManager(url);
                String json = manager.procesare();

                new Handler(Looper.getMainLooper()).post(()->{

//                    lista.addAll(BurseParser.parseJson(json));
//                    adapter.notifyDataSetChanged();

                    List<Bursa> listaNoua = new ArrayList<>();

                    listaNoua.addAll(BurseParser.parseJson(json));

                    for (int i = 0; i < listaNoua.size(); i++) {
                        burseDAO.addBursa(listaNoua.get(i));
                    }

                    lista.clear();
                    lista.addAll(burseDAO.getAllBurse());
                    adapter.notifyDataSetChanged();
                });
            }
        };
        thread.start();
    }
}
