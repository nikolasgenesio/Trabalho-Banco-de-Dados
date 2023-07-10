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
import com.example.pets_donation.Models.AdotanteDAO;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GerenciaAdotantes_Dados extends AppCompatActivity {

    //declaracao das variaveis
    private EditText nome, nascimento, telefone, email, rendaMensal;
    private EditText cep, estado, cidade, rua, numero, bairro;
    private EditText cpf, senha, confirmasenha;
    private Button btnCancelar, btnAlterar, btnDeletar, btnPesquisar;
    private RadioButton masculino, feminino;
    private RadioButton celular, fixo;
    private String sexo, tipoTelefone;
    private SimpleMaskFormatter telefoneCelular, telefoneFixo;
    private MaskTextWatcher telefone1;

    //Adotante
    private AdotanteDAO adotanteDAO;
    private Conexao_Banco banco;

    private Funcionario funcionario;
    private Adotante adotante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario_gerencia_adotantes);

        adotanteDAO = new AdotanteDAO(this);
        banco = new Conexao_Banco(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Editar Dados Pessoais");
        this.adotante = (Adotante) getIntent().getSerializableExtra("adotante");

        //Inicializando as variaveis
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

        cep = findViewById(R.id.cep);
        btnPesquisar = findViewById(R.id.btnPesquisa);
        estado = findViewById(R.id.estado);
        cidade = findViewById(R.id.cidade);
        rua = findViewById(R.id.rua);
        numero = findViewById(R.id.numero);
        bairro = findViewById(R.id.bairro);

        cpf = findViewById(R.id.cpf);
        senha = findViewById(R.id.senha);
        confirmasenha = findViewById(R.id.confirmarsenha);

        btnAlterar = findViewById(R.id.btnAltera);
        btnCancelar = findViewById(R.id.btnCancela);
        btnDeletar = findViewById(R.id.btnDeleta);

        //Criando a máscara para o campo de Nascimento
        SimpleMaskFormatter nascimento1 = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher nascimento2 = new MaskTextWatcher(nascimento, nascimento1);
        nascimento.addTextChangedListener(nascimento2);
        //Fim da máscara

        //Criando a máscara para o campo de CPF
        SimpleMaskFormatter cpf1 = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher cpf2 = new MaskTextWatcher(cpf, cpf1);
        cpf.addTextChangedListener(cpf2);
        //Fim da máscara

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

        //botao de alterar
        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alterar_Adotante();
            }
        });

        //botao de deletar
        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //deletar_Adotante();
            }
        });


    }

    /**
     * Funcao para preencher informacoes
     */
    public void preencheInformacoes() {
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


        cpf.setText(adotante.getCpf());
        cpf.setFocusable(false);

        senha.setText(adotante.getSenha());
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
     * Funcao para limpar endereco
     */
    public void limparEndereco() {
        estado.setText("");
        cidade.setText("");
        bairro.setText("");
        rua.setText("");
        numero.setText("");
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

    /**
     * Funcao para calcular se o email eh valido
     *
     * @param email email a ser verificado
     * @return true se for valido; falso se nao for valido
     */
    public boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Funcao para calcular a idade do usuario
     *
     * @param ano ano de nascimento
     * @param mes mes de nascimento
     * @param dia dia do nascimento
     * @return idade
     */
    private String calculaIdade(int ano, int mes, int dia) {

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(ano, mes, dia);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer idadeInt = new Integer(age);
        String idade = idadeInt.toString();

        return idade;
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