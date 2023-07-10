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
import android.widget.Toast;

import com.example.pets_donation.Lib.Conexao_Banco;

import java.util.List;

public class AnimalAdocaoListAdapter extends BaseAdapter {

    //declaracao das variaveis
    private Context context;
    private int layout;
    private List<Adocao_View> processoAdocaoList;
    private Conexao_Banco banco;

    /**
     * Construtor
     * @param context context
     * @param layout layout da lista
     * @param processoAdocaoList lista de processos de adocoes
     */
    public AnimalAdocaoListAdapter(Context context, int layout, List<Adocao_View> processoAdocaoList) {
        this.context = context;
        this.layout = layout;
        this.processoAdocaoList = processoAdocaoList;
    }

    /**
     * Funcao para retornar o tamanho da lista
     * @return tamanho da lista
     */
    @Override
    public int getCount() {
        return processoAdocaoList.size();
    }

    /**
     * Funcao para retornar o objeto na posicao da lista
     * @param position posicao
     * @return objeto na posicao
     */
    @Override
    public Object getItem(int position) {
        return processoAdocaoList.get(position);
    }

    /**
     * Funcao para retonar posicao do objeto
     * @param position posicao
     * @return posicao do objeto
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Classe para a lista adaptada
     */
    private class ViewHolder {
        ImageView imageView;
        TextView textNome, textInfo;
    }

    /**
     * Funcao para exibir a lista adaptada
     * @param position posicao
     * @param convertView auxiliar
     * @param parent auxiliar
     * @return lista adaptada
     */
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        banco = new Conexao_Banco(context);
        View row = convertView;
        AnimalAdocaoListAdapter.ViewHolder viewHolder = new AnimalAdocaoListAdapter.ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);
            row.setOnClickListener(null);

            viewHolder.textNome = (TextView) row.findViewById(R.id.title);
            viewHolder.textInfo = (TextView) row.findViewById(R.id.subtitle);
            viewHolder.imageView = (ImageView) row.findViewById(R.id.icon);
            row.setTag(viewHolder);
        } else {
            viewHolder = (AnimalAdocaoListAdapter.ViewHolder) row.getTag();
        }

        Adocao_View processoAdocao = processoAdocaoList.get(position);

        Animal animal = banco.obterAnimal(processoAdocao.getId_animal());
        viewHolder.textNome.setText(animal.getNome());
        viewHolder.imageView.setImageBitmap(animal.getFoto());

        viewHolder.imageView.setFocusableInTouchMode(false);

        if (processoAdocao.getResposta().equals("Finalizado! Buscar animal!")) {
            Abrigo abrigo = banco.obterAbrigo(animal.getId_abrigo());
            viewHolder.textInfo.setText("Status: " + processoAdocao.getResposta() +
                    "\nAbrigo: " + abrigo.getNome() +
                    "\nRua: " + abrigo.getRua() + ", " + abrigo.getNumero() +
                    "\nBairro: " + abrigo.getBairro() +
                    "\nCidade: " + abrigo.getCidade() +
                    "\nCEP: " + abrigo.getCep() + " | " + abrigo.getEstado());
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Adoção já Confirmada!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            viewHolder.textInfo.setText("Status: " + processoAdocao.getResposta());
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("LISTA", "Nome: " + animal.getNome());
                    Intent intent = new Intent(context, ProcessoAdocao_Visualizar.class);
                    intent.putExtra("adocao", processoAdocao);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
            });
        }
        return row;
    }

}
