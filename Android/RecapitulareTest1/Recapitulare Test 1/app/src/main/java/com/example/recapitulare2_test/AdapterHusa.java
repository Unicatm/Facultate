package com.example.recapitulare2_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdapterHusa extends ArrayAdapter<HusaTelefon> {
    private Context context;
    private int layoutId;
    private List<HusaTelefon> listaHuse;
    private LayoutInflater layoutInflater;

    public AdapterHusa(@NonNull Context context, int layoutId, @NonNull List<HusaTelefon> listaHuse, LayoutInflater layoutInflater) {
        super(context, layoutId,listaHuse);
        this.context=context;
        this.layoutInflater=layoutInflater;
        this.layoutId = layoutId;
        this.listaHuse=listaHuse;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(layoutId,parent,false);
        HusaTelefon husa = listaHuse.get(position);


        TextView tvMaterial = view.findViewById(R.id.tvMaterial);
        TextView tvLungime = view.findViewById(R.id.tvLungime);
        TextView tvCuloare = view.findViewById(R.id.tvCuloare);

        tvMaterial.setText(husa.getMaterial());
        tvLungime.setText(String.valueOf(husa.getLungime()));
        tvCuloare.setText(husa.getCuloare());

        return view;
    }
}
