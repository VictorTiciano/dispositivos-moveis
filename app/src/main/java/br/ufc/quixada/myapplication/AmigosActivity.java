package br.ufc.quixada.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import br.ufc.quixada.myapplication.model.Usuario;

public class AmigosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigos);

        ArrayList<Usuario> listaAmigos = new ArrayList<>();
        Usuario u1 = new Usuario("Fulano", "5454", "2121", "f@gmail.com", "senha123");
        Usuario u2 = new Usuario("Pedro Henrique", "5454", "2121", "ph@gmail.com", "senha123");
        Usuario u3 = new Usuario("Jonas de Sousa", "5454", "2121", "jonas.s@gmail.com", "senha123");
        Usuario u4 = new Usuario("Bonosalro", "5454", "2121", "bonosalro.patriota@gmail.com", "senha123");
        Usuario u5 = new Usuario("Simone Tablet", "5454", "2121", "tabA7@gmail.com", "senha123");
        Usuario u6 = new Usuario("Maria Julia", "5454", "2121", "maria.ju@gmail.com", "senha123");
        Usuario u7 = new Usuario("Patricia", "5454", "2121", "patricia@gmail.com", "senha123");
        listaAmigos.add(u1);
        listaAmigos.add(u6);
        listaAmigos.add(u7);
        listaAmigos.add(u2);
        listaAmigos.add(u3);
        listaAmigos.add(u4);
        listaAmigos.add(u5);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaAmigos);
        ListView listView = (ListView) findViewById(R.id.list_view_amigos);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(AmigosActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
        buscarUsuarios();
    }

    private void buscarUsuarios() {
        FirebaseFirestore.getInstance().collection("/usuarios")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e("Teste", error.getMessage(), error);
                            return;
                        }
                        List<DocumentSnapshot> documents = value.getDocuments();
                        for (DocumentSnapshot doc: documents) {
                            Usuario usuario = doc.toObject(Usuario.class);
                            Log.d("Teste", usuario.getEmail());
                        }

                    }
                });
    }
}