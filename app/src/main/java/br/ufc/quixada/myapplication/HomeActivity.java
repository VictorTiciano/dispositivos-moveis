package br.ufc.quixada.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import br.ufc.quixada.myapplication.model.AnuncioFireBase;
import br.ufc.quixada.myapplication.transactions.Constants;

public class HomeActivity extends AppCompatActivity {

    ArrayList<AnuncioFireBase> anuncios = new ArrayList<AnuncioFireBase>();
    ArrayAdapter adapter;
    int selected;
    BottomNavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        selected = -1;
        ListView feed = (ListView) findViewById(R.id.list_view_home);
        adapter = new FeedAdapter(this, adicionarAnuncios());
        feed.setAdapter(adapter);

        feed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selected = i;
                clicarEditar();
            }
        });

        nav = findViewById(R.id.bottom_navigation);
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_menu_mensagens:
                        Intent intentM = new Intent(HomeActivity.this, MensagensActivity.class);
                        startActivity(intentM);
                        break;
                    case R.id.home_menu_perfil:
                        Intent intentE = new Intent(HomeActivity.this, PerfilActivity.class);
                        startActivity(intentE);
                        break;
                    case R.id.home_menu_adicionar_imovel:
                        Intent intentI = new Intent(HomeActivity.this, RegisterAnuncioActivity.class);
                        startActivity(intentI);
                        break;
                    case R.id.home_menu_contatos:
                        Intent intentA = new Intent(HomeActivity.this, AmigosActivity.class);
                        startActivity(intentA);
                        break;
                }
                return false;
            }
        });

    }

    public void clicarEditar() {

        if (selected >= 0) {
            Intent intent = new Intent(this, AnuncioActivity.class);

            AnuncioFireBase anuncioFireBase = anuncios.get(selected);

            intent.putExtra("id", anuncioFireBase.getId());

            startActivityForResult(intent, Constants.REQUEST_EDIT);
        } else {
            Toast.makeText(HomeActivity.this, "Selecione um Item", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<AnuncioFireBase> adicionarAnuncios() {

        FirebaseFirestore.getInstance().collection("anuncios").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null)
                    return;

                List<DocumentSnapshot> documents = value.getDocuments();
                for (DocumentSnapshot doc : documents) {
                    //if(!doc.toObject(AnuncioFireBase.class).getUuid().equals(FirebaseAuth.getInstance().getUid())){
                    anuncios.add(doc.toObject(AnuncioFireBase.class));
                    AnuncioFireBase anuncioFireBase = doc.toObject(AnuncioFireBase.class);
                    adapter.notifyDataSetChanged();
                    //}

                }
            }
        });
        return anuncios;
    }
}