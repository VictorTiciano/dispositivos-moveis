package br.ufc.quixada.myapplication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.ufc.quixada.myapplication.model.Anuncio;
import br.ufc.quixada.myapplication.model.AnuncioFireBase;
import br.ufc.quixada.myapplication.model.Usuario;
import br.ufc.quixada.myapplication.transactions.Constants;

public class HomeActivity extends AppCompatActivity {

    Button btn_home_add_imovel;
    ArrayList<AnuncioFireBase> anuncios = new ArrayList<AnuncioFireBase>();
    ArrayAdapter adapter;
    int selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        selected = -1;

        ListView feed = (ListView) findViewById(R.id.list_view_home);
        adapter = new FeedAdapter(this, adicionarAnuncios());
        feed.setAdapter(adapter);

        btn_home_add_imovel = findViewById(R.id.btn_home_add_imovel);

        btn_home_add_imovel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, RegisterAnuncioActivity.class);
                startActivity(intent);
            }
        });

        feed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selected = i;
                clicarEditar();
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
                Intent intentE = new Intent(HomeActivity.this, PerfilActivity.class);
                startActivity(intentE);
                break;
        }
        return super.onOptionsItemSelected(item);
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
                for (DocumentSnapshot doc: documents) {
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