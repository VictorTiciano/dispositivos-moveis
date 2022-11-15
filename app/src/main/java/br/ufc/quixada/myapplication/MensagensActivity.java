package br.ufc.quixada.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import br.ufc.quixada.myapplication.model.Usuario;

public class MensagensActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagens);
        verificaAutenticacao();
        ArrayList<Usuario> listaAmigos = new ArrayList<>();
        Usuario u1 = new Usuario("Fulano", "5454", "2121", "f@gmail.com", "senha123");
        Usuario u2 = new Usuario("Maria Julia", "5454", "2121", "maria.ju@gmail.com", "senha123");
        Usuario u3 = new Usuario("Patricia", "5454", "2121", "patricia@gmail.com", "senha123");
        listaAmigos.add(u1);
        listaAmigos.add(u2);
        listaAmigos.add(u3);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaAmigos);
        ListView listView = (ListView) findViewById(R.id.list_view_mensagens);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(MensagensActivity.this, ChatActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_act:
                Intent intentH = new Intent(MensagensActivity.this, HomeActivity.class);
                startActivity(intentH);
                break;
            case R.id.contatos:
                Intent intent = new Intent(MensagensActivity.this, AmigosActivity.class);
                startActivity(intent);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                verificaAutenticacao();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void verificaAutenticacao() {
        if (FirebaseAuth.getInstance().getUid() == null) {
            Intent intent = new Intent(MensagensActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}