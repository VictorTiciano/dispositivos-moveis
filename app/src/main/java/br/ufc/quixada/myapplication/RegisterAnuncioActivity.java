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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import br.ufc.quixada.myapplication.model.Anuncio;
import br.ufc.quixada.myapplication.model.AnuncioFireBase;
import br.ufc.quixada.myapplication.model.Usuario;

public class RegisterAnuncioActivity extends AppCompatActivity {

    EditText edit_text_im_titulo;
    EditText edit_text_im_endereco;
    EditText edit_text_im_MQTerreno;
    EditText edit_text_im_MQConstruidos;
    EditText edit_text_im_qtdQuartos;
    EditText edit_text_im_qtdBanheiros;
    EditText edit_text_im_qtdVagasGaragem;
    EditText edit_text_im_preco;
    Button btn_im_cadastrar;
    Button btn_im_foto;
    Uri selectedUri;
    ImageView image_foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_anuncio);

        edit_text_im_titulo = findViewById(R.id.edit_text_im_titulo);
        edit_text_im_endereco = findViewById(R.id.edit_text_im_endereco);
        edit_text_im_MQTerreno = findViewById(R.id.edit_text_im_metros);
        edit_text_im_MQConstruidos = findViewById(R.id.edit_text_im_metros_contruidos);
        edit_text_im_qtdQuartos = findViewById(R.id.edit_text_im_quartos);
        edit_text_im_qtdBanheiros = findViewById(R.id.edit_text_im_banheiros);
        edit_text_im_qtdVagasGaragem = findViewById(R.id.edit_text_im_garagem);
        edit_text_im_preco = findViewById(R.id.edit_text_im_preco);
        btn_im_cadastrar = findViewById(R.id.btn_im_cadastrar);
        btn_im_foto = findViewById(R.id.btn_im_foto);
        image_foto = findViewById(R.id.image_view_foto);

        btn_im_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarFoto();
            }
        });

        btn_im_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarAnuncioNoFirebase();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            selectedUri = data.getData();

            Bitmap bitmap = null;
            try {
                bitmap =  MediaStore.Images.Media.getBitmap(getContentResolver(), selectedUri);
                image_foto.setImageDrawable(new BitmapDrawable(bitmap));
                btn_im_foto.setAlpha(0);
            }catch (IOException e){

            }

        }
    }

    private void selecionarFoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    private void salvarAnuncioNoFirebase(){
        String filename = UUID.randomUUID().toString();
        final StorageReference referencia = FirebaseStorage.getInstance().getReference("/images_imoveis/"+filename);
        referencia.putFile(selectedUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                referencia.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String uid = FirebaseAuth.getInstance().getUid();
                        String titulo = edit_text_im_titulo.getText().toString();
                        String endereco = edit_text_im_endereco.getText().toString();
                        String mtQuadradosTerreno = edit_text_im_MQTerreno.getText().toString();
                        String mtQuadradosContruido = edit_text_im_MQConstruidos.getText().toString();
                        String qtdQuartos = edit_text_im_qtdQuartos.getText().toString();
                        String qtdBanheiros = edit_text_im_qtdBanheiros.getText().toString();
                        String qtdGaragem = edit_text_im_qtdVagasGaragem.getText().toString();
                        String preco = edit_text_im_preco.getText().toString();
                        String fotoUrl = uri.toString();

                        AnuncioFireBase anuncio = new AnuncioFireBase(uid,titulo, endereco,fotoUrl,mtQuadradosTerreno,mtQuadradosContruido,qtdQuartos,qtdBanheiros,qtdGaragem,preco);

                        FirebaseFirestore.getInstance().collection("anuncios")
                                .add(anuncio)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {

                                        Intent intent = new Intent(RegisterAnuncioActivity.this, HomeActivity.class);

                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                        startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.i("Teste", e.getMessage());
                                    }
                                });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Teste", e.getMessage(), e);
            }
        });
    }
}