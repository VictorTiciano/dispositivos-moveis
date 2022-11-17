package br.ufc.quixada.myapplication;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import br.ufc.quixada.myapplication.model.Usuario;

public class EditPerfilActivity extends AppCompatActivity {

    String usuarioId;
    Usuario usuario;
    EditText edit_text_edp_nome;
    EditText edit_text_edp_cpf;
    EditText edit_text_edp_telefone;
    EditText edit_text_edp_email;
    EditText edit_text_edp_senha;
    ImageView image_edp_foto;
    Button btn_edp_edit;
    Uri selectedUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_perfil);

        edit_text_edp_nome = findViewById(R.id.edit_text_edp_nome);
        edit_text_edp_cpf = findViewById(R.id.edit_text_edp_cpf);
        edit_text_edp_telefone = findViewById(R.id.edit_text_edp_telefone);
        edit_text_edp_email = findViewById(R.id.edit_text_edp_email);
        edit_text_edp_senha = findViewById(R.id.edit_text_edp_senha);
        image_edp_foto = findViewById(R.id.image_view_edp_foto);
        btn_edp_edit = findViewById(R.id.btn_edp_edit);
        usuarioId = FirebaseAuth.getInstance().getUid();
        Log.i("Teste--", usuarioId);

        btn_edp_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarFoto();
            }
        });


        FirebaseFirestore.getInstance().collection("/usuarios")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Teste", error.getMessage(), error);
                            return;
                        }
                        List<DocumentSnapshot> documents = value.getDocuments();
                        for (DocumentSnapshot doc : documents) {
                            usuario = doc.toObject(Usuario.class);
                            if (usuario.getUuid().equals(usuarioId)) {
                                edit_text_edp_nome.setText(usuario.getNome());
                                edit_text_edp_cpf.setText(usuario.getCpf());
                                edit_text_edp_telefone.setText(usuario.getTelefone());
                                edit_text_edp_email.setText(usuario.getEmail());
                                edit_text_edp_senha.setText(usuario.getSenha());

                                //edit_text_pf_senha.setText(usuario.getFoto());
                                //image_pf_foto.setImageURI(usuario.getFoto().);
                            }
                        }
                    }
                });

        btn_edp_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarUsuarioNoFirebase();
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
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedUri);
                image_edp_foto.setImageDrawable(new BitmapDrawable(bitmap));
                btn_edp_edit.setAlpha(0);
            }catch (IOException e){

            }
        }
    }

    private void selecionarFoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    private void salvarUsuarioNoFirebase(){
        String filename = UUID.randomUUID().toString();
        final StorageReference referencia = FirebaseStorage.getInstance().getReference("/images/"+filename);
        referencia.putFile(selectedUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                referencia.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.i("Teste", uri.toString());
                        String uid = FirebaseAuth.getInstance().getUid();
                        String nome = edit_text_edp_nome.getText().toString();
                        String cpf = edit_text_edp_cpf.getText().toString();
                        String telefone = edit_text_edp_telefone.getText().toString();
                        String email = edit_text_edp_email.getText().toString();
                        String senha = edit_text_edp_senha.getText().toString();
                        String fotoUrl = uri.toString();

                        Usuario usuario = new Usuario(uid,nome, cpf,telefone,email,senha, fotoUrl);

                        FirebaseFirestore.getInstance().collection("usuarios")
                                .add(usuario)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.i("Teste", documentReference.getId());
                                        Intent intent = new Intent(EditPerfilActivity.this, PerfilActivity.class);
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