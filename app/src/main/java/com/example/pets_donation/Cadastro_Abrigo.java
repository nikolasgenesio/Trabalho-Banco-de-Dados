package com.example.pets_donation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.pets_donation.Lib.Conexao_Banco;
import com.example.pets_donation.Models.AbrigoDAO;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class Cadastro_Abrigo extends AppCompatActivity {

    //declaracao das variaveis
    private EditText nome, telefone;
    private EditText cep, estado, cidade, rua, numero, bairro;
    private Button btnLimpar, btnCancelar, btnConfirmar, btnPesquisar;
    private RadioButton celular, fixo;
    private String tipoTelefone;
    private SimpleMaskFormatter telefoneCelular, telefoneFixo;
    private MaskTextWatcher telefone1;


    private AbrigoDAO abrigoDAO;
    private Conexao_Banco banco;

    private Funcionario funcionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_abrigo);
        getSupportActionBar().setTitle("Cadastro - Abrigo");


        abrigoDAO = new AbrigoDAO(this);
        banco = new Conexao_Banco(this);
        this.funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");


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

        btnConfirmar = findViewById(R.id.btnConfirma);
        btnCancelar = findViewById(R.id.btnCancela);
        btnLimpar = findViewById(R.id.btnLimpa);

        //Criando a máscara para o campo de CEP
        SimpleMaskFormatter cep1 = new SimpleMaskFormatter("NNNNN-NNN");
        MaskTextWatcher cep2 = new MaskTextWatcher(cep, cep1);
        cep.addTextChangedListener(cep2);
        //Fim da máscara

        //obriga o usuario a escrever cep correto
        estado.setFocusable(false);

        //clique no botao de pesquisa
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

        //botao de limpar
        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nome.setText("");
                telefone.setText("");

                estado.setText("");
                cidade.setText("");
                bairro.setText("");
                rua.setText("");
                numero.setText("");
            }
        });

        //botao de adicionar
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionar_Abrigo();
            }
        });
    }

    /**
     * Funcao para limpar os dados
     */
    public void limparDados() {
        nome.setText("");
        telefone.setText("");
        estado.setText("");
        cidade.setText("");
        bairro.setText("");
        rua.setText("");
        numero.setText("");
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
     * Funcao para adicionar abrigo
     */
    public void adicionar_Abrigo() {
        String nome1 = nome.getText().toString();
        if (nome1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou seu nome", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!(fixo.isChecked() || celular.isChecked())) {
            Toast.makeText(getApplicationContext(), "Por favor, selecione um tipo de telefone", Toast.LENGTH_SHORT).show();
            return;
        }

        if (fixo.isChecked()) {
            tipoTelefone = "Fixo";
        }
        if (celular.isChecked()) {
            tipoTelefone = "Celular";
        }

        String telefone1 = telefone.getText().toString();
        if (telefone1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou seu telefone", Toast.LENGTH_SHORT).show();
            return;
        }

        if (telefone1.length() != 13 && telefone1.length() != 14) {
            Toast.makeText(getApplicationContext(), "Telefone inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        String cep1 = cep.getText().toString();
        if (cep1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou seu CEP", Toast.LENGTH_SHORT).show();
            return;
        }

        String estado1 = estado.getText().toString();
        if (estado1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou seu estado", Toast.LENGTH_SHORT).show();
            return;
        }

        String cidade1 = cidade.getText().toString();
        if (cidade1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou sua cidade", Toast.LENGTH_SHORT).show();
            return;
        }

        String bairro1 = bairro.getText().toString();
        if (bairro1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou seu bairro", Toast.LENGTH_SHORT).show();
            return;
        }

        String rua1 = rua.getText().toString();
        if (rua1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou sua rua", Toast.LENGTH_SHORT).show();
            return;
        }

        String numero1 = numero.getText().toString();
        if (numero1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou seu número", Toast.LENGTH_SHORT).show();
            return;
        }

        Abrigo abrigo = new Abrigo();

        abrigo.setNome(nome1);
        abrigo.setTipoTelefone(tipoTelefone);
        abrigo.setTelefone(telefone1);

        //endereco
        abrigo.setCep(cep1);
        abrigo.setEstado(estado1);
        abrigo.setCidade(cidade1);
        abrigo.setBairro(bairro1);
        abrigo.setRua(rua1);
        abrigo.setNumero(numero1);
        abrigo.setCPF_Secretario(funcionario.getCpf());

        Boolean aBoolean = abrigoDAO.inserir(abrigo);
        if (aBoolean)
        {
            Toast.makeText(getApplicationContext(), "CADASTRO COM SUCESSO!", Toast.LENGTH_SHORT).show();
            limparDados();
            Intent intent = new Intent(Cadastro_Abrigo.this, Tela_Funcionario.class);
            intent.putExtra("funcionario", funcionario);
            startActivity(intent);
        }
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

}