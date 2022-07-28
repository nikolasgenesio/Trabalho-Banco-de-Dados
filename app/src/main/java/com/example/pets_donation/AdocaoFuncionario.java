package com.example.pets_donation;

import android.annotation.SuppressLint;
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

import com.example.pets_donation.Lib.Conexao_Banco;

import java.util.List;

public class AdocaoFuncionario extends BaseAdapter {

    private Context context;
    private int layout;
    private List<ProcessoAdocao> processoAdocaoList;
    private Conexao_Banco banco;
    private Funcionario funcionario;

    public AdocaoFuncionario(Context context, int layout, List<ProcessoAdocao> processoAdocaoList, Funcionario funcionario) {
        this.context = context;
        this.layout = layout;
        this.processoAdocaoList = processoAdocaoList;
        this.funcionario = funcionario;
        banco = new Conexao_Banco(context);
    }


    @Override
    public int getCount() {
        return processoAdocaoList.size();
    }

    @Override
    public Object getItem(int position) {
        return processoAdocaoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textNome, textInfo;
    }

    @SuppressLint("SetTextI18n")
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

        ProcessoAdocao processoAdocao = processoAdocaoList.get(position);

        Adotante adotante = banco.obterAdotanteAdocao(processoAdocao.getCPFAdotante());
        Animal animal = banco.obterAnimal(processoAdocao.getIDAnimal());

        viewHolder.textNome.setText("Adotante: " + adotante.getNome());
        viewHolder.textInfo.setText("Status: " + processoAdocao.getStatus());

        viewHolder.imageView.setImageBitmap(animal.getFoto());

        viewHolder.imageView.setFocusableInTouchMode(false);

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FuncionarioFinalizar_Adocao.class);
                intent.putExtra("adocao", processoAdocao);
                intent.putExtra("adotante", adotante);
                intent.putExtra("animal", animal);
                intent.putExtra("funcionario", funcionario);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
        return row;
    }

}