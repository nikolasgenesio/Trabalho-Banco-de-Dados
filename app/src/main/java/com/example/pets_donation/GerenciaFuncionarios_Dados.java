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
import com.example.pets_donation.Models.FuncionarioDAO;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GerenciaFuncionarios_Dados extends AppCompatActivity {

    //declaracao das variaveis
    private EditText nome, nascimento, telefone, email, salario;
    private EditText cep, estado, cidade, rua, numero, bairro;
    private EditText cpf, senha, confirmasenha;
    private Button btnCancelar, btnAlterar, btnDeletar, btnPesquisar;
    private RadioButton masculino, feminino;
    private RadioButton celular, fixo;
    private String sexo, tipoTelefone;
    private SimpleMaskFormatter telefoneCelular, telefoneFixo;
    private MaskTextWatcher telefone1;

    //Funcionario
    private FuncionarioDAO funcionarioDAO;
    private Conexao_Banco banco;

    private Funcionario funcionarioADM;
    private Funcionario funcionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario_gerencia_funcionarios);

        funcionarioDAO = new FuncionarioDAO(this);
        banco = new Conexao_Banco(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Dados Pessoais");
        this.funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");


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
                alterar_Funcionario();
            }
        });

        //botao de deletar
        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //deletar_Funcionario();
            }
        });
    }

    /**
     * Funcao para preencher informacoes
     */
    public void preencheInformacoes() {
        nome.setText(funcionario.getNome());
        nascimento.setText(funcionario.getDataNascimento());

        sexo = funcionario.getSexo();
        tipoTelefone = funcionario.getTipoTelefone();

        if (sexo.equals("Masculino"))
            masculino.setChecked(true);
        else if (sexo.equals("Feminino"))
            feminino.setChecked(true);


        if (tipoTelefone.equals("Fixo"))
            fixo.setChecked(true);
        else if (tipoTelefone.equals("Celular"))
            celular.setChecked(true);

        telefone.setText(funcionario.getTelefone());

        String salario1 = String.valueOf(funcionario.getSalario());
        salario.setText(salario1);

        cep.setText(funcionario.getCep());
        estado.setText(funcionario.getEstado());
        cidade.setText(funcionario.getCidade());
        rua.setText(funcionario.getRua());
        numero.setText(funcionario.getNumero());
        bairro.setText(funcionario.getBairro());


        cpf.setText(funcionario.getCpf());
        cpf.setFocusable(false);

        senha.setText(funcionario.getSenha());
    }

    /**
     * Funcao para alterar funcionario
     */
    public void alterar_Funcionario() {
        String nome1 = nome.getText().toString();
        if (nome1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou seu nome", Toast.LENGTH_SHORT).show();
            return;
        }

        String nascimento1 = nascimento.getText().toString();
        if (nascimento1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou sua data de nascimento", Toast.LENGTH_SHORT).show();
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

        //Log.i("Tarefa 1 - status", "Teste = " + dia + "/" + mes + "/" + ano);

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
            Toast.makeText(getApplicationContext(), "Você não digitou seu telefone", Toast.LENGTH_SHORT).show();
            return;
        }

        if (telefone1.length() != 13 && telefone1.length() != 14) {
            Toast.makeText(getApplicationContext(), "Telefone inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        String email1 = email.getText().toString();
        if (email1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou seu email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isEmailValid(email1)) {
            Toast.makeText(getApplicationContext(), "Email inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        String salario1 = salario.getText().toString();
        if (salario1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou o salario", Toast.LENGTH_SHORT).show();
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

        String cpf1 = cpf.getText().toString();

        String senha1 = senha.getText().toString();
        if (senha1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou sua senha", Toast.LENGTH_SHORT).show();
            return;
        }
        if (senha1.length() < 8) {
            Toast.makeText(getApplicationContext(), "Senha: mínimo 8 digitos", Toast.LENGTH_SHORT).show();
            return;
        }
        String confirmaSenha1 = confirmasenha.getText().toString();
        if (confirmaSenha1.matches("")) {
            Toast.makeText(getApplicationContext(), "Você não digitou sua senha para confirmar", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!senha1.equals(confirmaSenha1)) {
            Toast.makeText(getApplicationContext(), "Senhas não batem", Toast.LENGTH_SHORT).show();
            return;
        }


        //dados pessoais
        funcionario.setNome(nome1);
        funcionario.setDataNascimento(nascimento1);
        funcionario.setSexo(sexo);
        funcionario.setTipoTelefone(tipoTelefone);
        funcionario.setTelefone(telefone1);

        //salario
        double salarioDouble = Double.parseDouble(salario1);
        funcionario.setSalario(salarioDouble);

        //endereco
        funcionario.setCep(cep1);
        funcionario.setEstado(estado1);
        funcionario.setCidade(cidade1);
        funcionario.setBairro(bairro1);
        funcionario.setRua(rua1);
        funcionario.setNumero(numero1);
        funcionario.setCpf(cpf1);
        funcionario.setSenha(senha1);

        boolean alterar = funcionarioDAO.alterar(funcionario);
        if (alterar) {
            Intent intent = getIntent();
            if (intent.hasExtra("adm")) {
                this.funcionarioADM = (Funcionario) getIntent().getSerializableExtra("adm");
                Toast.makeText(getApplicationContext(), "Funcionário alterado", Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), Listar_Adotantes.class);
                intent.putExtra("funcionario", funcionarioADM);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Informações alteradas", Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), Tela_Funcionario.class);
                intent.putExtra("funcionario", funcionario);
                startActivity(intent);
                finish();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Informações não alteradas", Toast.LENGTH_SHORT).show();
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