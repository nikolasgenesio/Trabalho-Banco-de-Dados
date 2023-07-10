package com.example.pets_donation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.pets_donation.Lib.Conexao_Banco;
import com.example.pets_donation.Models.AbrigoDAO;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class FuncionarioGerencia_Abrigos extends AppCompatActivity {

    //declaracao das variaveis
    private EditText nome, telefone;
    private EditText cep, estado, cidade, rua, numero, bairro;
    private Button btnAlterar, btnCancelar, btnDeletar, btnPesquisar;
    private RadioButton celular, fixo;
    private String tipoTelefone;
    private SimpleMaskFormatter telefoneCelular, telefoneFixo;
    private MaskTextWatcher telefone1;

    private AbrigoDAO abrigoDAO;
    private Conexao_Banco banco;
    private Funcionario funcionario;
    private Abrigo abrigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario_gerencia_abrigos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Alterar Abrigo");
        abrigoDAO = new AbrigoDAO(this);
        banco = new Conexao_Banco(this);
        this.funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");
        this.abrigo = (Abrigo) getIntent().getSerializableExtra("abrigo");

        //Inicializando as variaveis
        nome = findViewById(R.id.nome);

        fixo = (RadioButton) findViewById(R.id.radioButtonTelefoneFixo);
        celular = (RadioButton) findViewById(R.id.radioButtonTelefoneCelular);
        telefone = findViewById(R.id.telefone);
        telefone.setFocusable(false);

        cep = findViewById(R.id.cep);
        btnPesquisar = findViewById(R.id.btnPesquisa);
        estado = findViewById(R.id.estado);
        cidade = findViewById(R.id.cidade);
        rua = findViewById(R.id.rua);
        numero = findViewById(R.id.numero);
        bairro = findViewById(R.id.bairro);

        btnAlterar = findViewById(R.id.btnAltera);
        btnDeletar = findViewById(R.id.btnDeleta);
        btnCancelar = findViewById(R.id.btnCancela);


        //Criando a máscara para o campo de CEP
        SimpleMaskFormatter cep1 = new SimpleMaskFormatter("NNNNN-NNN");
        MaskTextWatcher cep2 = new MaskTextWatcher(cep, cep1);
        cep.addTextChangedListener(cep2);
        //Fim da máscara

        //obriga o usuario a escrever cep correto
        estado.setFocusable(false);

        preencheInformacoes();

        //botao de pesquisa
        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    String cep1 = cep.getText().toString();
                    if (cep1.length() != 9) {
                        Toast.makeText(getApplicationContext(), "CEP INVÁLIDO", Toast.LENGTH_SHORT).show();
                        limparEndereco();
                    } else {
                        new DownloadCEPTask().execute(cep1);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //botao de cancelamento
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //botao para alterar
        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alterar_Abrigo();
            }
        });

        //botao para deletar
        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //deletar_Abrigo();
            }
        });
    }

    /**
     * Funcao para limpar o endereco
     */
    public void limparEndereco() {
        estado.setText("");
        cidade.setText("");
        bairro.setText("");
        rua.setText("");
        numero.setText("");
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {

            //Celular selecionado
            case R.id.radioButtonTelefoneCelular:
                if (checked) {
                    telefone.removeTextChangedListener(telefone1);
                    telefone.setText("");
                    //Criando a máscara para o campo de telefone celular - mascara personalizada
                    telefoneCelular = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
                    telefone.setFocusableInTouchMode(true);
                    telefone1 = new MaskTextWatcher(telefone, telefoneCelular);
                    telefone.addTextChangedListener(telefone1);
                    //Fim da máscara
                }
                break;

            //Fixo selecionado
            case R.id.radioButtonTelefoneFixo:
                if (checked) {
                    telefone.removeTextChangedListener(telefone1);
                    telefone.setText("");
                    //Criando a máscara para o campo de telefone fixo - mascara personalizada
                    telefoneFixo = new SimpleMaskFormatter("(NN)NNNN-NNNN");
                    telefone.setFocusableInTouchMode(true);
                    //Criando a máscara para o campo de Telefone
                    telefone1 = new MaskTextWatcher(telefone, telefoneFixo);
                    telefone.addTextChangedListener(telefone1);
                    //Fim da máscara
                }
                break;
        }
    }

    /**
     * Funcao para preencher as informacoes
     */
    public void preencheInformacoes() {
        nome.setText(abrigo.getNome());
        telefone.setText(abrigo.getTelefone());

        Linhas_Telefonicas linhas_telefonicas = banco.retornaLinha(abrigo.getID());

        if (linhas_telefonicas.getTipo_Telefone().equals("Fixo")) {
            fixo.setChecked(true);
        } else if (linhas_telefonicas.getTipo_Telefone().equals("Celular")) {
            celular.setChecked(true);
        }

        cep.setText(abrigo.getCep());
        estado.setText(abrigo.getEstado());
        cidade.setText(abrigo.getCidade());
        rua.setText(abrigo.getRua());
        numero.setText(abrigo.getNumero());
        bairro.setText(abrigo.getBairro());
        telefone.setText(linhas_telefonicas.getNumero());
    }

    private class DownloadCEPTask extends AsyncTask<String, Void, BuscaCep> {
        @Override
        protected BuscaCep doInBackground(String... ceps) {
            BuscaCep vCep = null;

            try {
                vCep = new BuscaCep(ceps[0]);
            } finally {
                return vCep;
            }
        }

        @Override
        protected void onPostExecute(BuscaCep result) {
            if (result != null) {
                bairro.setText(result.getBairro());
                cidade.setText(result.getLocalidade());
                rua.setText(result.getLogradouro());
                estado.setText(result.getUf());
            } else {
                Toast.makeText(getApplicationContext(), "CEP INVÁLIDO", Toast.LENGTH_SHORT).show();
                limparEndereco();
            }
        }
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