package br.ufc.quixada.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.List;

import br.ufc.quixada.myapplication.model.AnuncioFireBase;
import br.ufc.quixada.myapplication.model.Usuario;

public class EditAnuncioActivity extends AppCompatActivity {

    EditText edit_im_titulo;
    EditText edit_im_endereco;
    EditText edit_im_MQTerreno;
    EditText edit_im_MQConstruidos;
    EditText edit_im_qtdQuartos;
    EditText edit_im_qtdBanheiros;
    EditText edit_im_qtdVagasGaragem;
    EditText edit_im_preco;
    Button btn_edit_im_cadastrar;
    Button btn_edit_im_foto;
    Uri selectedUri;
    ImageView image_foto;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    AnuncioFireBase anuncioFireBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_anuncio);

        edit_im_titulo = findViewById(R.id.edit_im_titulo);
        edit_im_endereco = findViewById(R.id.edit_im_endereco);
        edit_im_MQTerreno = findViewById(R.id.edit_im_metros);
        edit_im_MQConstruidos = findViewById(R.id.edit_im_metros_contruidos);
        edit_im_qtdQuartos = findViewById(R.id.edit_im_quartos);
        edit_im_qtdBanheiros = findViewById(R.id.edit_im_banheiros);
        edit_im_qtdVagasGaragem = findViewById(R.id.edit_im_garagem);
        edit_im_preco = findViewById(R.id.edit_im_preco);
        btn_edit_im_cadastrar = findViewById(R.id.btn_edit_im_cadastrar);
        btn_edit_im_foto = findViewById(R.id.btn_edit_im_foto);
        image_foto = findViewById(R.id.image_edit_view_foto);

        btn_edit_im_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarFoto();
            }
        });

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
                            if (anuncioFireBase.getId().equals(getIntent().getExtras().get("id"))) {

                                getIntent().getExtras().get("id");
                                edit_im_titulo.setText(anuncioFireBase.getTitulo());
                                edit_im_endereco.setText(anuncioFireBase.getEndereco());
                                edit_im_MQTerreno.setText(anuncioFireBase.getMetrosQuadradosTerreno());
                                edit_im_MQConstruidos.setText(anuncioFireBase.getMetrosQuadradosConstruidos());
                                edit_im_qtdQuartos.setText(anuncioFireBase.getQuantidadeQuartos());
                                edit_im_qtdBanheiros.setText(anuncioFireBase.getQuantidadeBanheiros());
                                edit_im_qtdVagasGaragem.setText(anuncioFireBase.getQuantidadeVagasGaragem());
                                edit_im_preco.setText(anuncioFireBase.getPreco());

                            }
                        }
                    }
                });

        btn_edit_im_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnuncioFireBase anuncioFireBase = new AnuncioFireBase(

                        edit_im_titulo.getText().toString(),
                        edit_im_endereco.getText().toString(),
                        edit_im_MQTerreno.getText().toString(),
                        edit_im_MQConstruidos.getText().toString(),
                        edit_im_qtdQuartos.getText().toString(),
                        edit_im_qtdBanheiros.getText().toString(),
                        edit_im_qtdVagasGaragem.getText().toString(),
                        edit_im_preco.getText().toString()

                );
                updateAnuncio(getIntent().getExtras().get("id").toString(), anuncioFireBase);
            }
        });


    }

    private void updateAnuncio(String srt, AnuncioFireBase anuncioFireBase) {
        DocumentReference docRef = firestore.collection("anuncios").document(srt);
        docRef.update("titulo", anuncioFireBase.getTitulo(),
                "endereco", anuncioFireBase.getEndereco(),
                "metrosQuadradosTerreno",anuncioFireBase.getMetrosQuadradosTerreno(),
                "metrosQuadradosConstruidos", anuncioFireBase.getMetrosQuadradosConstruidos(),
                "quantidadeQuartos",anuncioFireBase.getQuantidadeQuartos(),
                "quantidadeBanheiros", anuncioFireBase.getQuantidadeBanheiros(),
                "quantidadeVagasGaragem", anuncioFireBase.getQuantidadeVagasGaragem(),
                "preco", anuncioFireBase.getPreco() );
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        Intent intent = new Intent(EditAnuncioActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            selectedUri = data.getData();

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedUri);
                image_foto.setImageDrawable(new BitmapDrawable(bitmap));
                btn_edit_im_foto.setAlpha(0);
            } catch (IOException e) {

            }

        }
    }

    private void selecionarFoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

}