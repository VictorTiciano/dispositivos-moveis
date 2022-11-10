package br.ufc.quixada.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    ArrayAdapter adapter;
    ArrayList<Usuario> listaAmigos;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigos);

        listaAmigos = new ArrayList<>();
        Usuario u = new Usuario("Fulano", "5454", "2121", "f@gmail.com", "senha123");
        listaAmigos.add(u);
        listaAmigos.add(u);
        listaAmigos.add(u);
//        FirebaseFirestore.getInstance().collection("usuarios")
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        if(error != null){
//                            Log.e("Teste", error.getMessage(), error);
//                            return;
//                        }
//                        List<DocumentSnapshot> documents = value.getDocuments();
//                        for (DocumentSnapshot doc: documents) {
//                            Usuario usuario = doc.toObject(Usuario.class);
//                            listaAmigos.add(usuario);
//                        }
//                    }
//                });

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaAmigos);
//        RecyclerView recyclerView = findViewById(R.id.recycler);
//        recyclerView.setAdapter(adapter);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
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