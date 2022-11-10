package br.ufc.quixada.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.ufc.quixada.myapplication.model.Anuncio;
import br.ufc.quixada.myapplication.model.Usuario;
import br.ufc.quixada.myapplication.transactions.Constants;

public class HomeActivity extends AppCompatActivity {

    Button btn_home_add_imovel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ListView feed = (ListView) findViewById(R.id.list_view_home);
        ArrayAdapter adapter = new FeedAdapter(this,adicionarAnuncios());
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

    private ArrayList<Anuncio> adicionarAnuncios() {
        ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>();
        Anuncio e = new Anuncio("Casa 1", "Rua 1", R.drawable.img1,10,5,7,9, 12, 140000);
        anuncios.add(e);
        e = new Anuncio("Casa 2", "Rua 2", R.drawable.img2,10,5,7,9, 12, 140000);
        anuncios.add(e);
        e = new Anuncio("Casa 3", "Rua 3", R.drawable.img3,10,5,7,9, 12, 140000);
        anuncios.add(e);
        e = new Anuncio("Casa 4", "Rua 4", R.drawable.img4,10,5,7,9, 12, 140000);
        anuncios.add(e);
        e = new Anuncio("Casa 5", "Rua 5", R.drawable.img5,10,5,7,9, 12, 140000);
        anuncios.add(e);
        return anuncios;
    }

}