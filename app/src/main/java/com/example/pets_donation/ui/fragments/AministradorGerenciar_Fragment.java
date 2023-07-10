package com.example.pets_donation.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pets_donation.Cadastro_Funcionario;
import com.example.pets_donation.Funcionario;
import com.example.pets_donation.Funcionario_ListCadastros;
import com.example.pets_donation.Listar_Abrigos;
import com.example.pets_donation.Listar_Adotantes;
import com.example.pets_donation.Listar_Animais;
import com.example.pets_donation.Listar_AnimaisAdocoes;
import com.example.pets_donation.Listar_Funcionarios;
import com.example.pets_donation.Listar_ProcessosAdocoes;
import com.example.pets_donation.R;

public class AministradorGerenciar_Fragment extends Fragment {

    //declaracao das variaveis
    private Funcionario funcionario;
    private ListView listView;

    String[] maintitle = {
            "Cadastro",
            "Informação",
    };

    String[] subtitle = {
            "Efetivar registro do funcionário", "Editar informações de funcionários",
    };

    Integer[] imgid = {
            R.drawable.adicionar_funcionario_vector, R.drawable.gerenciar_vector,
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        this.funcionario = (Funcionario) getActivity().getIntent().getSerializableExtra("funcionario");
        Log.i("Funcionario Gerenciar", "NOME: " + funcionario.getNome());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Gerenciar");
        return inflater.inflate(R.layout.fragment_aministrador_gerenciar_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listView = view.findViewById(R.id.listViewGerencia1);

        //exibir lista adaptada
        Funcionario_ListCadastros adapter = new Funcionario_ListCadastros(getActivity(), maintitle, subtitle, imgid);
        listView.setAdapter(adapter);

        //abre novas telas de acordo com a selecao
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(getActivity(), Cadastro_Funcionario.class);
                    intent.putExtra("adm", funcionario);
                    startActivity(intent);
                }
            }
        });

        super.onViewCreated(view, savedInstanceState);

    }
}