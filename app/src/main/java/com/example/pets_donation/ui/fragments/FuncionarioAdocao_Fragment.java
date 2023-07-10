package com.example.pets_donation.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pets_donation.AdocaoFuncionario;
import com.example.pets_donation.Adocao_View;
import com.example.pets_donation.AnimalAdocaoListAdapter;
import com.example.pets_donation.Funcionario;
import com.example.pets_donation.Models.ProcessoAdocaoDAO;
import com.example.pets_donation.ProcessoAdocao;
import com.example.pets_donation.R;

import java.util.List;

public class FuncionarioAdocao_Fragment extends Fragment {

    //declaracao das variaveis
    private Funcionario funcionario;
    private ListView listView;
    private ProcessoAdocaoDAO processoAdocaoDAO;
    private List<Adocao_View> processoAdocaoList;
    private AdocaoFuncionario adocaoFuncionario;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Adoção");
        this.funcionario = (Funcionario) getActivity().getIntent().getSerializableExtra("funcionario");
        Log.i("Funcionario Adocao", "NOME: " + funcionario.getNome());
        return inflater.inflate(R.layout.fragment_funcionario_adocao_, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listView = view.findViewById(R.id.lista_adocoes);
        processoAdocaoDAO = new ProcessoAdocaoDAO(getActivity());
        processoAdocaoList = processoAdocaoDAO.retornaAnimaisAdocao();
        adocaoFuncionario = new AdocaoFuncionario(getActivity(), R.layout.list_view_adocoes, processoAdocaoList, funcionario);
        //exibir lista adaptada ou mensagem de aviso
        if(adocaoFuncionario.getCount() != 0)
        {
            listView.setAdapter(adocaoFuncionario);
        }
        else
        {
            textView = view.findViewById(R.id.aviso);
            textView.setVisibility(View.VISIBLE);
            textView.setText("Sem adoções pendentes no momento!");
        }

    }
}