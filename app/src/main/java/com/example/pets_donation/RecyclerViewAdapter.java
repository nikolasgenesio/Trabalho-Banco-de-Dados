package com.example.pets_donation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pets_donation.Lib.Conexao_Banco;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    //declaracao das variaveis

    private static final String TAG = "RecyclerViewAdapter";

    private Context context;
    private List<Animal> animals;
    private Conexao_Banco banco;
    private Adotante adotante;

    public RecyclerViewAdapter(Context context, List<Animal> animals, Adotante adotante) {
        this.context = context;
        this.animals = animals;
        this.adotante = adotante;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_horizontal, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onCreateViewHolder: called.");

        banco = new Conexao_Banco(context);

        Animal animal = animals.get(position);

        Glide.with(context)
                .asBitmap()
                .load(animal.getFoto())
                .into(holder.imageView);

        holder.nome.setText(animal.getNome() + ", " + animal.getIdade());
        holder.raca.setText(animal.getRaca());
        holder.genero.setText(animal.getGenero());
        holder.porteFisico.setText(animal.getPorteFisico());

        String cidade1 = banco.retornaEnderecoAbrigo(animal.getId_abrigo());
        holder.cidade.setText(cidade1);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Tarefa 1 - status Adocao", "NOME: " + animal.getNome());
                Intent intent = new Intent(context, AdotanteVisualiza_Animal.class);
                intent.putExtra("adotante", adotante);
                intent.putExtra("animal", animal);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
    }

    /**
     * Funcao para retornar o tamanho da lista
     * @return tamanho da lista
     */
    @Override
    public int getItemCount() {
        return animals.size();
    }

    /**
     * Classe para a lista adaptada
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView nome, raca, genero, porteFisico, cidade;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagem);
            nome = itemView.findViewById(R.id.texto);
            raca = itemView.findViewById(R.id.txtRaca);
            genero = itemView.findViewById(R.id.txtGenero);
            porteFisico = itemView.findViewById(R.id.txtPorte);
            cidade = itemView.findViewById(R.id.txtCidade);
        }
    }
}
