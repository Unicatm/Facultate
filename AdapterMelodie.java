package com.example.recapitulare3_test;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdapterMelodie extends ArrayAdapter<Melodie> {
    private Context context;
    private int layoutId;
    private List<Melodie> listaMelodii;
    private LayoutInflater layoutInflater;

    public AdapterMelodie(@NonNull Context context, int layoutId, List<Melodie> listaMelodii, LayoutInflater layoutInflater) {
        super(context, layoutId ,listaMelodii);
        this.context = context;
        this.layoutId = layoutId;
        this.listaMelodii = listaMelodii;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(layoutId,parent,false);
        Melodie melodie = listaMelodii.get(position);

        TextView tvNume = view.findViewById(R.id.tvNume);
        TextView tvData = view.findViewById(R.id.tvData);
        TextView tvDurata = view.findViewById(R.id.tvDurata);
        TextView tvFeedback = view.findViewById(R.id.tvFeedback);
        TextView tvGen = view.findViewById(R.id.tvGen);

        tvNume.setText(melodie.getNumeMelodie());

        tvData.setText(new SimpleDateFormat("dd.MM.yyyy").format(melodie.getPublicare()));
        tvDurata.setText(String.valueOf(melodie.getDurata()));
        tvFeedback.setText(melodie.getFeedback());
        tvGen.setText(melodie.getGen());

        if(melodie.getFeedback().compareTo("Like") == 0){
            tvFeedback.setTextColor(Color.GREEN);
        }else{
            tvFeedback.setTextColor(Color.RED);
        }

        return view;
    }
}
