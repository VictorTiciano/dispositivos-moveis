package br.ufc.quixada.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import br.ufc.quixada.myapplication.model.AnuncioFireBase;

public class AnuncioActivity extends AppCompatActivity {

    TextView textView_im_titulo;
    TextView textView_im_endereco;
    TextView textView_im_MQTerreno;
    TextView textView_im_MQConstruidos;
    TextView textView_im_qtdQuartos;
    TextView textView_im_qtdBanheiros;
    TextView textView_im_qtdVagasGaragem;
    TextView textView_im_preco;

    String anuncioFireBaseId;
    AnuncioFireBase anuncioFireBase;

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
        anuncioFireBaseId= FirebaseAuth.getInstance().getUid();
        Log.i("Teste--", anuncioFireBaseId);
        FirebaseFirestore.getInstance().collection("/anuncios")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Teste", error.getMessage(), error);
                            return;
                        }
                        List<DocumentSnapshot> documents = value.getDocuments();
                        for (DocumentSnapshot doc : documents) {
                            anuncioFireBase = doc.toObject(AnuncioFireBase.class);
                            if (anuncioFireBase.getUuid().equals(anuncioFireBaseId)) {
                                textView_im_titulo.setText(anuncioFireBase.getTitulo());
                                textView_im_endereco.setText(anuncioFireBase.getEndereco());
                                textView_im_MQTerreno.setText(anuncioFireBase.getMetrosQuadradosTerreno());
                                textView_im_MQConstruidos.setText(anuncioFireBase.getMetrosQuadradosConstruidos());
                                textView_im_qtdQuartos.setText(anuncioFireBase.getQuantidadeQuartos());
                                textView_im_qtdBanheiros.setText(anuncioFireBase.getQuantidadeBanheiros());
                                textView_im_qtdVagasGaragem.setText(anuncioFireBase.getQuantidadeVagasGaragem());
                                textView_im_preco.setText(anuncioFireBase.getPreco());
                            }
                        }
                    }
                });

    }
}