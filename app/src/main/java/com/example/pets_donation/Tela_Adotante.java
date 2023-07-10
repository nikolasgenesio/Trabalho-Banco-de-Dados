package com.example.pets_donation;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.pets_donation.Lib.Conexao_Banco;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import com.example.pets_donation.databinding.ActivityTelaAdotanteBinding;

public class Tela_Adotante extends AppCompatActivity {

    //declaracao das variaveis
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityTelaAdotanteBinding binding;
    private Adotante adotante;
    private TextView nomedoAdotante;
    private CircleImageView imageView;
    private Conexao_Banco banco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.adotante = (Adotante) getIntent().getSerializableExtra("adotante");
        banco = new Conexao_Banco(this);
        Log.i("Tarefa 1 - status Tela", "NOME: " + adotante.getNome());

        //inicializando variveis
        binding = ActivityTelaAdotanteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarTelaAdotante.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        View headerView = navigationView.getHeaderView(0);
        nomedoAdotante = headerView.findViewById(R.id.nomeAdotante);
        imageView = headerView.findViewById(R.id.imageView);
        nomedoAdotante.setText("Seja Bem-Vindo " + adotante.getNome());

        //verifica se existe foto
        Bitmap imagem = banco.retornaFotoAdotante(adotante.getCpf(), adotante.getSenha());
        if (imagem != null) {
            imageView.setImageBitmap(imagem);
        }


        navigationView.getMenu().findItem(R.id.sair).setOnMenuItemClickListener(MenuItem ->
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Tela_Adotante.this);
            builder.setTitle("Realmente deseja sair?");

            builder
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Tela_Adotante.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return true;
        });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.tela_inicial, R.id.perfil, R.id.adocao, R.id.statusAdocao, R.id.abrigos, R.id.informacoes)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_tela_adotante);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tela__adotante, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_tela_adotante);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * Opcoes
     * @param item item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.duvidas) {
            //Passar outra tela
            Intent intent = new Intent(Tela_Adotante.this, Adotante_Duvidas.class);
            intent.putExtra("adotante", adotante);
            startActivity(intent);
        } else if (id == R.id.sairApp) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Tela_Adotante.this);
            builder.setTitle("Realmente deseja sair?");

            // set dialog message
            builder
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            //Menu_Alergia.this.finish();
                            Intent intent = new Intent(Tela_Adotante.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = builder.create();

            // show it
            alertDialog.show();
            //openDialog();
        }

        return super.onOptionsItemSelected(item);
    }
}