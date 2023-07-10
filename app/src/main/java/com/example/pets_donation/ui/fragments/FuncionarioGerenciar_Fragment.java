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

import com.example.pets_donation.Cadastro_Abrigo;
import com.example.pets_donation.Cadastro_Animal;
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

public class FuncionarioGerenciar_Fragment extends Fragment {

    //declaracao das variaveis
    private Funcionario funcionario;
    private ListView listView;

    String[] maintitle = {
            "Abrigo",
            "Adotante",
            "Animal",
            "Veterinário",
            "Relatório de adoções",
            "Relatório de animais",
    };

    String[] subtitle = {
            "Editar informações de abrigos", "Editar informações de adotantes",
            "Editar informações de animais", "Editar informações de veterinários",
            "Gerar relatório dos processos de adoções", "Gerar relatório dos processos dos animais",
    };

    Integer[] imgid = {
            R.drawable.editar_abrigo_vector, R.drawable.adotante_vector,
            R.drawable.pets_vector, R.drawable.gerenciar_vector,
            R.drawable.processo_adocao_vector, R.drawable.relatorio_animal_vector
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.funcionario = (Funcionario) getActivity().getIntent().getSerializableExtra("funcionario");
        Log.i("Funcionario Gerenciar", "NOME: " + funcionario.getNome());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Gerenciar");
        return inflater.inflate(R.layout.fragment_funcionario_gerenciar_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listView = view.findViewById(R.id.listViewGerencia);

        //exibir lista adaptada
        Funcionario_ListCadastros adapter = new Funcionario_ListCadastros(getActivity(), maintitle, subtitle, imgid);
        listView.setAdapter(adapter);

        //abre novas telas de acordo com a selecao
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {
                        Intent intent = new Intent(getActivity(), Listar_Abrigos.class);
                        intent.putExtra("funcionario", funcionario);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(getActivity(), Listar_Adotantes.class);
                        intent.putExtra("funcionario", funcionario);
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        Intent intent = new Intent(getActivity(), Listar_Animais.class);
                        intent.putExtra("funcionario", funcionario);

                        startActivity(intent);
                        break;
                    }
                    case 3: {
                        break;
                    }

                    case 4: {
                        Intent intent = new Intent(getActivity(), Listar_ProcessosAdocoes.class);
                        intent.putExtra("funcionario", funcionario);
                        startActivity(intent);
                        break;
                    }

                    default: {
                        break;
                    }
                }
            }
        });

        super.onViewCreated(view, savedInstanceState);

    }
}