package com.example.pets_donation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pets_donation.Lib.Conexao_Banco;
import com.example.pets_donation.Models.ProcessoAdocaoDAO;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.List;

public class FuncionarioFinalizar_Adocao extends AppCompatActivity {

    //atores
    private Adotante adotante;
    private Animal animal;
    private Adocao_View processoAdocao;
    private Funcionario funcionario;

    //variaveis adotante

    private EditText nome, nascimento, telefone, email, rendaMensal;
    private EditText cep, estado, cidade, rua, numero, bairro;
    private RadioButton masculino, feminino;
    private RadioButton celular, fixo;
    private String sexo, tipoTelefone;

    //variaveis questionario
    private TextView textView;

    //auxiliares
    private String morada, imovel, veterinario;

    private Button btnDeletar, btnCancelar, btnConfirmar;
    private ProcessoAdocaoDAO processoAdocaoDAO;
    private Conexao_Banco banco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario_finalizar_adocao);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Informações");

        this.adotante = (Adotante) getIntent().getSerializableExtra("adotante");
        this.animal = (Animal) getIntent().getSerializableExtra("animal");
        this.processoAdocao = (Adocao_View) getIntent().getSerializableExtra("adocao");
        this.funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");

        banco = new Conexao_Banco(this);
        processoAdocaoDAO = new ProcessoAdocaoDAO(this);

        //adotante
        nome = findViewById(R.id.nome);
        nascimento = findViewById(R.id.nascimento);
        fixo = (RadioButton) findViewById(R.id.radioButtonTelefoneFixo);
        celular = (RadioButton) findViewById(R.id.radioButtonTelefoneCelular);
        telefone = findViewById(R.id.telefone);
        telefone.setFocusable(false);
        email = findViewById(R.id.email);
        masculino = (RadioButton) findViewById(R.id.radioButtonMasculino);
        feminino = (RadioButton) findViewById(R.id.radioButtonFeminino);
        rendaMensal = findViewById(R.id.rendaMensal);

        nome.setFocusable(false);
        nascimento.setFocusable(false);
        fixo.setEnabled(false);
        celular.setEnabled(false);
        telefone.setFocusable(false);
        email.setFocusable(false);
        masculino.setEnabled(false);
        feminino.setEnabled(false);
        rendaMensal.setFocusable(false);

        cep = findViewById(R.id.cep);
        estado = findViewById(R.id.estado);
        cidade = findViewById(R.id.cidade);
        rua = findViewById(R.id.rua);
        numero = findViewById(R.id.numero);
        bairro = findViewById(R.id.bairro);

        cep.setFocusable(false);
        estado.setFocusable(false);
        cidade.setFocusable(false);
        rua.setFocusable(false);
        numero.setFocusable(false);
        bairro.setFocusable(false);

        textView = (TextView) findViewById(R.id.respostasQuestionario);

        btnDeletar = findViewById(R.id.btnDeleta);
        btnCancelar = findViewById(R.id.btnCancela);
        btnConfirmar = findViewById(R.id.btnAltera);

        preencheInformacoes();

        //botao de cancelamento
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //botao de confirmacao
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmar_Adocao();
            }
        });

        //botao para deletar
        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletar_Adocao();
            }
        });
    }

    /**
     * Funcao para preencher as informacoes
     */
    public void preencheInformacoes() {
        //adotante
        nome.setText(adotante.getNome());
        nascimento.setText(adotante.getDataNascimento());

        sexo = adotante.getSexo();
        tipoTelefone = adotante.getTipoTelefone();

        if (sexo.equals("Masculino"))
            masculino.setChecked(true);
        else if (sexo.equals("Feminino"))
            feminino.setChecked(true);


        if (tipoTelefone.equals("Fixo"))
            fixo.setChecked(true);
        else if (tipoTelefone.equals("Celular"))
            celular.setChecked(true);

        telefone.setText(adotante.getTelefone());
        email.setText(adotante.getEmail());

        String rendamensal1 = String.valueOf(adotante.getRendaMensal());
        rendaMensal.setText(rendamensal1);

        cep.setText(adotante.getCep());
        estado.setText(adotante.getEstado());
        cidade.setText(adotante.getCidade());
        rua.setText(adotante.getRua());
        numero.setText(adotante.getNumero());
        bairro.setText(adotante.getBairro());

        textView.setText(processoAdocao.getQuestionario());

    }

    /**
     * Funcao para confirmar a adocao
     */
    public void confirmar_Adocao() {
        processoAdocao.setResposta("Finalizado! Buscar animal!");
        boolean alterar = processoAdocaoDAO.alterarStatus(processoAdocao);
        if (alterar) {
            List<Adocao_View> exclusao = processoAdocaoDAO.automatizaSistemaAdocoes(processoAdocao.getId_animal());
            for (Adocao_View processoAdocao1 : exclusao) {
                processoAdocao1.setResposta("Indeferido! Motivo: animal adotado!");
                alterar = processoAdocaoDAO.alterarStatus(processoAdocao1);
            }

            Toast.makeText(getApplicationContext(), "Adoção Confirmada", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Tela_Funcionario.class);
            intent.putExtra("funcionario", funcionario);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Adoção não confirmada", Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * Funcao para deletar adocao
     */
    public void deletar_Adocao() {
        /*
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog_adocao_indeferida, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText motivoAdocao = (EditText) promptsView
                .findViewById(R.id.motivo);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Confirmar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                processoAdocao.setStatus("Indeferido! Motivo: " + motivoAdocao.getText());
                                boolean alterar = processoAdocaoDAO.alterarStatus(processoAdocao);
                                boolean inserir = processoAdocaoDAO.inserirAdocaoIndeferida(processoAdocao, funcionario);
                                if (alterar && inserir) {
                                    Toast.makeText(getApplicationContext(), "Adoção Inderida!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Tela_Funcionario.class);
                                    intent.putExtra("funcionario", funcionario);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    if (!alterar) {
                                        Toast.makeText(getApplicationContext(), "Adoção não indeferida", Toast.LENGTH_SHORT).show();
                                    } else if (!inserir) {
                                        Toast.makeText(getApplicationContext(), "Inserção não confirmada", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
        */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                finish();
                break;
            default:
                break;
        }
        return true;
    }

}