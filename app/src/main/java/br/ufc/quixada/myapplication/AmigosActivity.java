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
    ArrayList<Usuario> listaAmigos = new ArrayList<>();
    ArrayAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigos);

        buscarUsuarios();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaAmigos);
        listView = (ListView) findViewById(R.id.list_view_amigos);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(AmigosActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

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
                            listaAmigos.add(doc.toObject(Usuario.class));
                            Usuario usuario = doc.toObject(Usuario.class);
                            adapter.notifyDataSetChanged();
                            Log.d("Teste", usuario.getEmail());
                        }
                    }
                });
    }
}