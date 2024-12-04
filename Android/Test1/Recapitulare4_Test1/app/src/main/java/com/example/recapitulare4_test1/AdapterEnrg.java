package com.example.recapitulare4_test1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdapterEnrg extends ArrayAdapter<Energizant> {
    private Context context;
    private int layoutId;
    private List<Energizant> lista;
    private LayoutInflater layoutInflater;

    public AdapterEnrg(@NonNull Context context, int layoutId, List<Energizant> lista, LayoutInflater layoutInflater) {
        super(context, layoutId, lista);
        this.context = context;
        this.layoutId = layoutId;
        this.lista = lista;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(layoutId,parent,false);
        Energizant enrg = lista.get(position);

        TextView tvDen = view.findViewById(R.id.tvDen);
        TextView tvAroma = view.findViewById(R.id.tvAroma);
        TextView tvCant = view.findViewById(R.id.tvCant);
        TextView tvZahar = view.findViewById(R.id.tvZahar);

        tvDen.setText(enrg.getDen());
        tvAroma.setText(enrg.getAroma());
        tvCant.setText(String.valueOf(enrg.getCantitate()));
        tvZahar.setText(enrg.getIndulcit());

        return view;
    }
}
