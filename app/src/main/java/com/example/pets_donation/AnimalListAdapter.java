package com.example.pets_donation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AnimalListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Animal> animals;
    private Funcionario funcionario;

    public AnimalListAdapter(Context context, int layout, List<Animal> animals, Funcionario funcionario) {
        this.context = context;
        this.layout = layout;
        this.animals = animals;
        this.funcionario = funcionario;
    }

    @Override
    public int getCount() {
        return animals.size();
    }

    @Override
    public Object getItem(int position) {
        return animals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textNome, textInfo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder viewHolder = new ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);
            row.setOnClickListener(null);

            viewHolder.textNome = (TextView) row.findViewById(R.id.title);
            viewHolder.textInfo = (TextView) row.findViewById(R.id.subtitle);
            viewHolder.imageView = (ImageView) row.findViewById(R.id.icon);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) row.getTag();
        }

        Animal animal = animals.get(position);

        viewHolder.textNome.setText(animal.getNome());
        viewHolder.textInfo.setText(animal.getTipo());

        viewHolder.imageView.setImageBitmap(animal.getFoto());

        viewHolder.imageView.setFocusableInTouchMode(false);

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("LISTA", "Nome: " + animal.getNome());
                Intent intent = new Intent(context, FuncionarioGerencia_Animal.class);
                intent.putExtra("funcionario", funcionario);
                intent.putExtra("animal", animal);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
        return row;
    }

    public boolean areAllItemsEnabled() {
        return false;
    }

    public boolean isEnabled(int position) {
        return false;
    }

}
