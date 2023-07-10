package com.example.pets_donation;

import android.annotation.SuppressLint;
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

public class AdocaoDeferidaAdapter extends BaseAdapter {

    //declaracao das variaveis
    private Context context;
    private int layout;
    private List<Adocoes> adocoesList;
    private Conexao_Banco banco;
    private Funcionario funcionario;

    /**
     * Construtor
     * @param context context
     * @param layout layout da lista
     * @param adocoesList lista de adocoes
     * @param funcionario funcionario
     */
    public AdocaoDeferidaAdapter(Context context, int layout, List<Adocoes> adocoesList, Funcionario funcionario) {
        this.context = context;
        this.layout = layout;
        this.adocoesList = adocoesList;
        this.funcionario = funcionario;
    }


    /**
     * Funcao para retornar o tamanho da lista
     * @return tamanho da lista
     */
    @Override
    public int getCount() {
        return adocoesList.size();
    }

    /**
     * Funcao para retornar o objeto na posicao da lista
     * @param position posicao
     * @return objeto na posicao
     */
    @Override
    public Object getItem(int position) {
        return adocoesList.get(position);
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
        AdocaoDeferidaAdapter.ViewHolder viewHolder = new AdocaoDeferidaAdapter.ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);
            row.setOnClickListener(null);

            viewHolder.textNome = (TextView) row.findViewById(R.id.title);
            viewHolder.textInfo = (TextView) row.findViewById(R.id.subtitle);
            viewHolder.imageView = (ImageView) row.findViewById(R.id.icon);
            row.setTag(viewHolder);
        } else {
            viewHolder = (AdocaoDeferidaAdapter.ViewHolder) row.getTag();
        }

        Adocoes adocoes = adocoesList.get(position);

        Animal animal = banco.obterAnimal(adocoes.getID_Animal());
        Adotante adotante = banco.obterAdotanteAdocao(adocoes.getCPF_Adotante());
        Funcionario funcionario = banco.obterFuncionarioAdocao(adocoes.getCPF_Funcionario());

        viewHolder.textNome.setText(adocoes.getData());
        viewHolder.textInfo.setText("Adotante: " + adotante.getNome() +
                "\nFuncionário: " + funcionario.getNome() +
                "\nAnimal: " + animal.getNome());

        viewHolder.imageView.setImageBitmap(animal.getFoto());

        viewHolder.imageView.setFocusableInTouchMode(false);

        return row;
    }

}
