package br.ufc.quixada.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

import br.ufc.quixada.myapplication.model.AnuncioFireBase;
import br.ufc.quixada.myapplication.transactions.Constants;

public class AnuncioActivity extends AppCompatActivity {

    String selected;

    TextView textView_im_titulo;
    TextView textView_im_endereco;
    TextView textView_im_MQTerreno;
    TextView textView_im_MQConstruidos;
    TextView textView_im_qtdQuartos;
    TextView textView_im_qtdBanheiros;
    TextView textView_im_qtdVagasGaragem;
    TextView textView_im_preco;
    Button btn_edit_anuncio;
    Button btn_delete_anuncio;
    ImageView image_AN_foto;
    Button btn_foto;
    Button btn_map;

    AnuncioFireBase anuncioFireBase;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio);

        textView_im_titulo = findViewById(R.id.textview_im_titulo);
        textView_im_endereco = findViewById(R.id.textview_im_endereco);
        textView_im_MQTerreno = findViewById(R.id.textview_im_metros);
        textView_im_MQConstruidos = findViewById(R.id.textview_im_metros_contruidos);
        textView_im_qtdQuartos = findViewById(R.id.textview_im_quartos);
        textView_im_qtdBanheiros = findViewById(R.id.textview_im_banheiros);
        textView_im_qtdVagasGaragem = findViewById(R.id.textview_im_garagem);
        textView_im_preco = findViewById(R.id.textview_im_preco);
        btn_edit_anuncio = findViewById(R.id.btn_edit_im_anuncio);
        btn_delete_anuncio = findViewById(R.id.btn_delete_anuncio);
        image_AN_foto = findViewById(R.id.image_view_foto);
        btn_foto = findViewById(R.id.btn_im_foto);
        btn_map = findViewById(R.id.btn_map);


        FirebaseFirestore.getInstance().collection("/anuncios")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            return;
                        }
                        List<DocumentSnapshot> documents = value.getDocuments();
                        for (DocumentSnapshot doc : documents) {
                            anuncioFireBase = doc.toObject(AnuncioFireBase.class);
                            if (anuncioFireBase.getId().equals(getIntent().getExtras().get("id"))) {

                                selected = anuncioFireBase.getId();

                                textView_im_titulo.setText(anuncioFireBase.getTitulo());
                                textView_im_endereco.setText(anuncioFireBase.getEndereco());
                                textView_im_MQTerreno.setText(anuncioFireBase.getMetrosQuadradosTerreno());
                                textView_im_MQConstruidos.setText(anuncioFireBase.getMetrosQuadradosConstruidos());
                                textView_im_qtdQuartos.setText(anuncioFireBase.getQuantidadeQuartos());
                                textView_im_qtdBanheiros.setText(anuncioFireBase.getQuantidadeBanheiros());
                                textView_im_qtdVagasGaragem.setText(anuncioFireBase.getQuantidadeVagasGaragem());
                                textView_im_preco.setText(anuncioFireBase.getPreco());
                                Picasso.get().load(anuncioFireBase.getImg()).into(image_AN_foto);
                                btn_foto.setAlpha(0);

                            }
                        }
                    }
                });

        btn_delete_anuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apagarAnuncio();
                Intent intent = new Intent(AnuncioActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btn_edit_anuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicarEditar();
            }
        });

        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnuncioActivity.this, MapsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    private void apagarAnuncio() {
        DocumentReference docRef = firestore.collection("anuncios").document(selected);
        docRef.delete();

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                }
            }
        });
    }

    public void clicarEditar() {

        Intent intent = new Intent(AnuncioActivity.this, EditAnuncioActivity.class);

        intent.putExtra("id", selected);

        startActivityForResult(intent, Constants.REQUEST_EDIT);
    }
}