package com.example.recapitulare5_test1;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddZbor extends AppCompatActivity {

    private boolean isEditing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_zbor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String[] tari = {"Spania","Italia","Franta","Germania"};
        Spinner spnTara = findViewById(R.id.spnTara);
        ArrayAdapter<String> adapterSpn = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,tari);
        spnTara.setAdapter(adapterSpn);

        EditText etCod = findViewById(R.id.etCod);
        EditText etData = findViewById(R.id.etData);
        RadioGroup rgTip = findViewById(R.id.rgTip);
        Button btnSalveaza = findViewById(R.id.btnSalveaza);

        Intent editIntent = getIntent();

        if(editIntent.hasExtra("edit")){
            isEditing = true;

            Zbor zbor = (Zbor) editIntent.getSerializableExtra("edit");

            etCod.setText(zbor.getCod());
            etData.setText(new SimpleDateFormat("dd.MM.yyyy").format(zbor.getDataZbor()));

            for(int i=0;i<spnTara.getCount();i++){
                if(zbor.getTara().equals(adapterSpn.getItem(i))){
                    spnTara.setSelection(i);
                }
            }

            switch(zbor.getTipZbor()){
                case "Economy":{
                    rgTip.check(R.id.rbEconomy);
                    break;
                }
                case "Priority":{
                    rgTip.check(R.id.rbPriority);
                    break;
                }
            }

        }


        btnSalveaza.setOnClickListener(view->{
            String cod = etCod.getText().toString();
            Date data = null;
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            try {
                data = sdf.parse(etData.getText().toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            String tara = spnTara.getSelectedItem().toString();

            RadioButton rb = findViewById(rgTip.getCheckedRadioButtonId());
            String tip = rb.getText().toString();

            Zbor zbor = new Zbor(cod,data,tara,tip);

            Intent intent = getIntent();

            if(isEditing){
                intent.putExtra("edit",zbor);
                isEditing=false;
            }else{
                intent.putExtra("add",zbor);
            }

            setResult(RESULT_OK, intent);
            finish();
        });

    }
}