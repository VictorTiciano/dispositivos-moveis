package br.ufc.quixada.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.UUID;

import br.ufc.quixada.myapplication.model.Anuncio;
import br.ufc.quixada.myapplication.model.Usuario;

public class HomeActivity extends AppCompatActivity {

    Button btn_home_add_imovel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ListView feed = (ListView) findViewById(R.id.list_view_home);
        ArrayAdapter adapter = new FeedAdapter(this, adicionarAnuncios());
        feed.setAdapter(adapter);

        btn_home_add_imovel = findViewById(R.id.btn_home_add_imovel);

        btn_home_add_imovel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, RegisterAnuncioActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_chat:
                Intent intentH = new Intent(HomeActivity.this, MensagensActivity.class);
                startActivity(intentH);
                break;
            case R.id.item_menu_perfil:
                //ir para a tela de edição mas com os dados do usuario ja preenchidos
                editarUsuario("TdX3WwjisqVyTslt5f4I8vdUJNv2");
                Intent intentE = new Intent(HomeActivity.this, RegisterActivity.class);
                startActivity(intentE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void editarUsuario(String id) {
        Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
        FirebaseFirestore.getInstance().collection("usuarios").whereEqualTo("uuid", id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot document : task.getResult()){
                            String key = "julio@gmail.com";
                            Usuario usuario = (Usuario) document.getData().get(key);
                            System.out.println(usuario);
                        }
                    }
                });
    }

    private ArrayList<Anuncio> adicionarAnuncios() {
        ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>();
        Anuncio e = new Anuncio("Casa 1", "Rua 1", R.drawable.img1, 10, 5, 7, 9, 12, 140000);
        anuncios.add(e);
        e = new Anuncio("Casa 2", "Rua 2", R.drawable.img2, 10, 5, 7, 9, 12, 140000);
        anuncios.add(e);
        e = new Anuncio("Casa 3", "Rua 3", R.drawable.img3, 10, 5, 7, 9, 12, 140000);
        anuncios.add(e);
        e = new Anuncio("Casa 4", "Rua 4", R.drawable.img4, 10, 5, 7, 9, 12, 140000);
        anuncios.add(e);
        e = new Anuncio("Casa 5", "Rua 5", R.drawable.img5, 10, 5, 7, 9, 12, 140000);
        anuncios.add(e);
        return anuncios;
    }
}