package com.example.recapitulare4_test1;

import android.content.Intent;
import android.os.Bundle;
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

    private List<Energizant> listaEnergizante = new ArrayList<>();
    private ListView lvEnergizante;
    private ActivityResultLauncher<Intent> launcher;

    private int SELECTED_ITEM;

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

        lvEnergizante = findViewById(R.id.lvEnergizante);


        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result->{
            if(result.getData().hasExtra("add")){
                Intent intent = result.getData();

                Energizant enrg = (Energizant) intent.getSerializableExtra("add");

                if(enrg!=null) {
                    listaEnergizante.add(enrg);
                    AdapterEnrg adapterLv = new AdapterEnrg(getApplicationContext(), R.layout.view_enrg, listaEnergizante, getLayoutInflater());
                    lvEnergizante.setAdapter(adapterLv);
                }

            }else if(result.getData().hasExtra("edit")){
                Intent intent = result.getData();

                Energizant enrgLuatEdit = (Energizant) intent.getSerializableExtra("edit");

                Energizant obiectLista = listaEnergizante.get(SELECTED_ITEM);

                if(enrgLuatEdit!=null) {
                    obiectLista.setDen(enrgLuatEdit.getDen());
                    obiectLista.setCantitate(enrgLuatEdit.getCantitate());
                    obiectLista.setAroma(enrgLuatEdit.getAroma());
                    obiectLista.setIndulcit(enrgLuatEdit.getIndulcit());

                    AdapterEnrg adprLv = (AdapterEnrg) lvEnergizante.getAdapter();
                    adprLv.notifyDataSetChanged();
                }
            }
        });

        lvEnergizante.setOnItemClickListener((adapterView, view, position, l)->{
            SELECTED_ITEM = position;

            Intent intent = new Intent(getApplicationContext(), AddEnergizant.class);
            intent.putExtra("edit",listaEnergizante.get(position));
            launcher.launch(intent);
        });

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(view->{
            Intent intent = new Intent(getApplicationContext(),AddEnergizant.class);
            launcher.launch(intent);
        });
    }

}