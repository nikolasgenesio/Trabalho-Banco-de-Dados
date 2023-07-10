package com.example.pets_donation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.pets_donation.Lib.Conexao_Banco;
import com.example.pets_donation.Models.FuncionarioDAO;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Cadastro_Funcionario extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //declaracao das variaveis
    private EditText nome, nascimento, telefone, salario;
    private EditText cep, estado, cidade, rua, numero, bairro;
    private EditText cpf, senha, confirmasenha;
    private Button btnLimpar, btnCancelar, btnAvancar, btnPesquisar;
    private RadioButton masculino, feminino;
    private RadioButton celular, fixo;
    private RadioButton aceito, naoaceito;
    private String sexo, tipoTelefone;
    private SimpleMaskFormatter telefoneCelular, telefoneFixo;
    private MaskTextWatcher telefone1;

    private FuncionarioDAO funcionarioDAO;
    private Conexao_Banco banco;

    private Funcionario funcionario = null;
    private Funcionario funcionarioADM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_funcionario);
        getSupportActionBar().setTitle("Cadastro - Funcionário");

        funcionarioDAO = new FuncionarioDAO(this);
        banco = new Conexao_Banco(this);

        //Inicializando as variaveis
        nome = findViewById(R.id.nome);
        nascimento = findViewById(R.id.nascimento);
        fixo = (RadioButton) findViewById(R.id.radioButtonTelefoneFixo);
        celular = (RadioButton) findViewById(R.id.radioButtonTelefoneCelular);
        telefone = findViewById(R.id.telefone);
        telefone.setFocusable(false);
        masculino = (RadioButton) findViewById(R.id.radioButtonMasculino);
        feminino = (RadioButton) findViewById(R.id.radioButtonFeminino);
        salario = findViewById(R.id.salario);

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

        aceito = (RadioButton) findViewById(R.id.radioButtonAceito);
        naoaceito = (RadioButton) findViewById(R.id.radioButtonNaoAceito);

        btnAvancar = findViewById(R.id.btnConfirma);
        btnCancelar = findViewById(R.id.btnCancela);
        btnLimpar = findViewById(R.id.btnLimpa);

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

        this.funcionarioADM = (Funcionario) getIntent().getSerializableExtra("adm");

        //caso tenha funcionario
        Intent intent = getIntent();
        if (intent.hasExtra("funcionario")) {
            funcionario = (Funcionario) intent.getSerializableExtra("funcionario");
            nome.setText(funcionario.getNome());
            nascimento.setText(funcionario.getDataNascimento());
            telefone.setText(funcionario.getTelefone());

            Double salario1 = funcionario.getSalario();
            salario.setText(String.valueOf(salario1));

            cep.setText(funcionario.getCep());
            estado.setText(funcionario.getEstado());
            cidade.setText(funcionario.getCidade());
            bairro.setText(funcionario.getBairro());
            rua.setText(funcionario.getRua());
            numero.setText(funcionario.getNumero());
            cpf.setText(funcionario.getCpf());
            senha.setText(funcionario.getSenha());

            cpf.setFocusable(false);

            if (funcionario.getSexo().equals("Masculino"))
                masculino.setChecked(true);
            else
                feminino.setChecked(true);

            if (funcionario.getTipoTelefone().equals("Celular"))
                celular.setChecked(true);
            else
                fixo.setChecked(true);
        }

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
                if (funcionario == null) {
                    Intent intent = new Intent(Cadastro_Funcionario.this, Tela_Administrador.class);
                    intent.putExtra("funcionario", funcionarioADM);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Cadastro_Funcionario.this, Tela_Administrador.class);
                    intent.putExtra("funcionario", funcionario);
                    startActivity(intent);
                }

            }
        });

        //botao de limpar
        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nome.setText("");
                nascimento.setText("");
                telefone.setText("");
                salario.setText("");

                estado.setText("");
                cidade.setText("");
                bairro.setText("");
                rua.setText("");
                numero.setText("");
                cpf.setText("");
                senha.setText("");
                confirmasenha.setText("");
            }
        });

        //botao de adicionar
        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionar_Adotante();
            }
        });

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

    /**
     * Funcao para limpar dados
     */
    public void limparDados() {
        nome.setText("");
        nascimento.setText("");
        telefone.setText("");
        salario.setText("");

        estado.setText("");
        cidade.setText("");
        bairro.setText("");
        rua.setText("");
        numero.setText("");
        cpf.setText("");
        senha.setText("");
        confirmasenha.setText("");
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
     * Funcao para verificar se as informacoes estao corretas e adicionar, caso positivo
     */
    public void adicionar_Adotante() {
        String nome1 = nome.getText().toString();
        if (nome1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou o nome", Toast.LENGTH_SHORT).show();
            return;
        }

        String nascimento1 = nascimento.getText().toString();
        if (nascimento1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou a data de nascimento", Toast.LENGTH_SHORT).show();
            return;
        }

        Date data = null;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            format.setLenient(false);
            data = format.parse(nascimento1);
        } catch (ParseException e) {
            Toast.makeText(getApplicationContext(), "Data inválida. Tente novamente!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (masculino.isChecked()) {
            sexo = "Masculino";
        }
        if (feminino.isChecked()) {
            sexo = "Feminino";
        }

        if (!(masculino.isChecked() || feminino.isChecked())) {
            Toast.makeText(getApplicationContext(), "Por favor, selecione um sexo", Toast.LENGTH_SHORT).show();
            return;
        }

        if (fixo.isChecked()) {
            tipoTelefone = "Fixo";
        }
        if (celular.isChecked()) {
            tipoTelefone = "Celular";
        }

        if (!(fixo.isChecked() || celular.isChecked())) {
            Toast.makeText(getApplicationContext(), "Por favor, selecione um tipo de telefone", Toast.LENGTH_SHORT).show();
            return;
        }

        String telefone1 = telefone.getText().toString();
        if (telefone1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou o telefone", Toast.LENGTH_SHORT).show();
            return;
        }

        if (telefone1.length() != 13 && telefone1.length() != 14) {
            Toast.makeText(getApplicationContext(), "Telefone inválido", Toast.LENGTH_SHORT).show();
            return;
        }


        String salario1 = salario.getText().toString();
        if (salario1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou o salario", Toast.LENGTH_SHORT).show();
            return;
        }

        String cep1 = cep.getText().toString();
        if (cep1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou o CEP", Toast.LENGTH_SHORT).show();
            return;
        }

        String estado1 = estado.getText().toString();
        if (estado1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou o estado", Toast.LENGTH_SHORT).show();
            return;
        }

        /*
        if (estado1.length() != 2) {
            Toast.makeText(getApplicationContext(), "Estado: somente a sigla", Toast.LENGTH_SHORT).show();
            return;
        }
         */

        String cidade1 = cidade.getText().toString();
        if (cidade1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou a cidade", Toast.LENGTH_SHORT).show();
            return;
        }

        String bairro1 = bairro.getText().toString();
        if (bairro1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou o bairro", Toast.LENGTH_SHORT).show();
            return;
        }

        String rua1 = rua.getText().toString();
        if (rua1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou a rua", Toast.LENGTH_SHORT).show();
            return;
        }

        String numero1 = numero.getText().toString();
        if (numero1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou o número", Toast.LENGTH_SHORT).show();
            return;
        }

        String cpf1 = cpf.getText().toString();
        if (cpf1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou o cpf", Toast.LENGTH_SHORT).show();
            return;
        }

        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (cpf1.equals("000.000.000-00") ||
                cpf1.equals("111.111.111-11") ||
                cpf1.equals("222.222.222-22") || cpf1.equals("333.333.333-33") ||
                cpf1.equals("444.444.444-44") || cpf1.equals("555.555.555-55") ||
                cpf1.equals("666.666.666-66") || cpf1.equals("777.777.777-77") ||
                cpf1.equals("888.888.888-88") || cpf1.equals("999.999.999-99") ||
                (cpf1.length() != 14)) {
            Toast.makeText(getApplicationContext(), "CPF incorreto", Toast.LENGTH_SHORT).show();
            return;
        }

        String senha1 = senha.getText().toString();
        if (senha1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou a senha", Toast.LENGTH_SHORT).show();
            return;
        }
        if (senha1.length() < 8) {
            Toast.makeText(getApplicationContext(), "Senha: mínimo 8 digitos", Toast.LENGTH_SHORT).show();
            return;
        }
        String confirmaSenha1 = confirmasenha.getText().toString();
        if (confirmaSenha1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou a senha para confirmar", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!senha1.equals(confirmaSenha1)) {
            Toast.makeText(getApplicationContext(), "Senhas não batem", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!aceito.isChecked() || naoaceito.isChecked()) {
            Toast.makeText(getApplicationContext(), "Aceitar termo de uso", Toast.LENGTH_SHORT).show();
            return;
        }


        Boolean verificarFucnionario = banco.checkSenhaUsuario(nome1, senha1);
        if (verificarFucnionario) {
            Toast.makeText(getApplicationContext(), "Funcionário Existente!", Toast.LENGTH_SHORT).show();
        } else {
            if (funcionario == null) {
                funcionario = new Funcionario();

                //dados pessoais
                funcionario.setNome(nome1);
                funcionario.setDataNascimento(nascimento1);
                funcionario.setSexo(sexo);
                funcionario.setTipoTelefone(tipoTelefone);
                funcionario.setTelefone(telefone1);

                //renda mensal
                double salario = Double.parseDouble(salario1);
                funcionario.setSalario(salario);

                //endereco
                funcionario.setCep(cep1);
                funcionario.setEstado(estado1);
                funcionario.setCidade(cidade1);
                funcionario.setBairro(bairro1);
                funcionario.setRua(rua1);
                funcionario.setNumero(numero1);
                funcionario.setCpf(cpf1);
                funcionario.setSenha(senha1);

                funcionario.setCPF_Administrador(funcionarioADM.getCPF_Administrador());

                Boolean inserir = funcionarioDAO.inserir(funcionario, funcionarioADM);
                if (inserir) {
                    Toast.makeText(getApplicationContext(), "CADASTRO COM SUCESSO!", Toast.LENGTH_SHORT).show();
                    limparDados();
                    Intent intent = new Intent(Cadastro_Funcionario.this, Tela_Administrador.class);
                    intent.putExtra("funcionario", funcionarioADM);
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "ERRO NO CADASTRO!", Toast.LENGTH_SHORT).show();
            } else {
                //dados pessoais
                funcionario.setNome(nome1);
                funcionario.setDataNascimento(nascimento1);
                funcionario.setSexo(sexo);
                funcionario.setTipoTelefone(tipoTelefone);
                funcionario.setTelefone(telefone1);

                //renda mensal
                double salario = Double.parseDouble(salario1);
                funcionario.setSalario(salario);

                //endereco
                funcionario.setCep(cep1);
                funcionario.setEstado(estado1);
                funcionario.setCidade(cidade1);
                funcionario.setBairro(bairro1);
                funcionario.setRua(rua1);
                funcionario.setNumero(numero1);
                funcionario.setCpf(cpf1);
                funcionario.setSenha(senha1);
                funcionario.setCPF_Administrador(funcionarioADM.getCPF_Administrador());

                Boolean inserir = funcionarioDAO.alterar(funcionario);
                if (inserir) {
                    Toast.makeText(getApplicationContext(), "ALTERAÇÃO COM SUCESSO!", Toast.LENGTH_SHORT).show();
                    limparDados();
                    Intent intent = new Intent(Cadastro_Funcionario.this, Tela_Funcionario.class);
                    intent.putExtra("funcionario", funcionario);
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "ERRO NA ALTERAÇÃO!", Toast.LENGTH_SHORT).show();
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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