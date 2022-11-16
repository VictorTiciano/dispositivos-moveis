package br.ufc.quixada.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        //tenho que chamar a tela de 'edição' passando os dados do usuario
    }
}