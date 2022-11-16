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

import br.ufc.quixada.myapplication.model.AnuncioFireBase;
import br.ufc.quixada.myapplication.model.Usuario;

public class RegisterActivity extends AppCompatActivity {

    EditText edit_text_rg_nome;
    EditText edit_text_rg_cpf;
    EditText edit_text_rg_telefone;
    EditText edit_text_rg_email;
    EditText edit_text_rg_senha;
    Button btn_rg_salvar;
    Button btn_rg_foto;
    Uri selectedUri;
    ImageView image_foto;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edit_text_rg_nome = findViewById(R.id.edit_text_rg_name);
        edit_text_rg_cpf = findViewById(R.id.edit_text_rg_cpf);
        edit_text_rg_telefone = findViewById(R.id.edit_text_rg_telefone);
        edit_text_rg_email = findViewById(R.id.edit_text_rg_email);
        edit_text_rg_senha = findViewById(R.id.edit_text_rg_senha);
        btn_rg_salvar = findViewById(R.id.btn_im_cadastrar);
        btn_rg_foto = findViewById(R.id.btn_rg_foto);
        image_foto = findViewById(R.id.image_view_foto);


        btn_rg_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarFoto();
            }
        });

        btn_rg_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criarUsuario();
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
                image_foto.setImageDrawable(new BitmapDrawable(bitmap));
                btn_rg_foto.setAlpha(0);
            }catch (IOException e){

            }

        }
    }

    private void selecionarFoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    private void criarUsuario(){
        String nome = edit_text_rg_nome.getText().toString();
        String cpf = edit_text_rg_cpf.getText().toString();
        String telefone = edit_text_rg_telefone.getText().toString();
        String email = edit_text_rg_email.getText().toString();
        String senha = edit_text_rg_senha.getText().toString();

        if(nome == null || nome.isEmpty() || cpf == null || cpf.isEmpty() || telefone == null || telefone.isEmpty() || email == null || email.isEmpty() || senha == null || senha.isEmpty()){
            Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.i("Teste", task.getResult().getUser().getUid());
                            salvarUsuarioNoFirebase();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Teste", e.getMessage());
                    }
                });
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
                        String nome = edit_text_rg_nome.getText().toString();
                        String cpf = edit_text_rg_cpf.getText().toString();
                        String telefone = edit_text_rg_telefone.getText().toString();
                        String email = edit_text_rg_email.getText().toString();
                        String senha = edit_text_rg_senha.getText().toString();
                        String fotoUrl = uri.toString();

                        Usuario usuario = new Usuario(uid,nome, cpf,telefone,email,senha, fotoUrl);

                        FirebaseFirestore.getInstance().collection("usuarios")
                                .add(usuario)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.i("Teste", documentReference.getId());

                                        Intent intent = new Intent(RegisterActivity.this, MensagensActivity.class);

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