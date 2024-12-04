package com.example.recapitulare5_test1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Zbor> listaZboruri = new ArrayList<>();
    private ListView lvZboruri;
    private ActivityResultLauncher<Intent> launcher;
    private int ItemSelectat;
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

        lvZboruri = findViewById(R.id.lvZboruri);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result->{
            if(result.getData().hasExtra("add")){
                Intent intent = result.getData();
                Zbor zborLuat = (Zbor) intent.getSerializableExtra("add");

                if(zborLuat!=null) {
                    listaZboruri.add(zborLuat);
                    ArrayAdapter<Zbor> adapterLv = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaZboruri);
                    lvZboruri.setAdapter(adapterLv);
                }

            }else if(result.getData().hasExtra("edit")){
                Intent intent = result.getData();
                Zbor zborLuatEditat = (Zbor) intent.getSerializableExtra("edit");


                if(zborLuatEditat!=null){
                    Zbor zborLista = listaZboruri.get(ItemSelectat);
                    zborLista.setCod(zborLuatEditat.getCod());
                    zborLista.setDataZbor(zborLuatEditat.getDataZbor());
                    zborLista.setTipZbor(zborLuatEditat.getTipZbor());
                    zborLista.setTara(zborLuatEditat.getTara());

                    ArrayAdapter<Zbor> adapterLvPreluat = (ArrayAdapter<Zbor>) lvZboruri.getAdapter();
                    adapterLvPreluat.notifyDataSetChanged();
                }
            }
        });

        lvZboruri.setOnItemClickListener((adapterView,view,position,l)->{
            ItemSelectat = position;

            Intent intent = new Intent(getApplicationContext(),AddZbor.class);
            intent.putExtra("edit",listaZboruri.get(position));
            launcher.launch(intent);
        });

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(view->{
            Intent intent = new Intent(getApplicationContext(), AddZbor.class);
            launcher.launch(intent);

        });
    }
}