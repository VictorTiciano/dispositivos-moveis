package br.ufc.quixada.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.List;

import br.ufc.quixada.myapplication.model.Usuario;

public class PerfilActivity extends AppCompatActivity {
    String usuarioId;
    Usuario usuario;
    TextView edit_text_pf_nome;
    TextView edit_text_pf_cpf;
    TextView edit_text_pf_telefone;
    TextView edit_text_pf_email;
    TextView edit_text_pf_senha;
    ImageView image_pf_foto;
    Button btn_pf_edit;
    Button btn_pf_logout;
    Button btn_pf_delete_conta;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        edit_text_pf_nome = findViewById(R.id.edit_text_pf_nome);
        edit_text_pf_cpf = findViewById(R.id.edit_text_pf_cpf);
        edit_text_pf_telefone = findViewById(R.id.edit_text_pf_telefone);
        edit_text_pf_email = findViewById(R.id.edit_text_pf_email);
        edit_text_pf_senha = findViewById(R.id.edit_text_pf_senha);
        image_pf_foto = findViewById(R.id.image_view_pf_foto);
        btn_pf_edit = findViewById(R.id.btn_pf_edit);
        btn_pf_logout = findViewById(R.id.btn_pf_logout);
        btn_pf_delete_conta = findViewById(R.id.btn_pf_delete_conta);
        usuarioId = FirebaseAuth.getInstance().getUid();

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
                                edit_text_pf_nome.setText(usuario.getNome());
                                edit_text_pf_cpf.setText(usuario.getCpf());
                                edit_text_pf_telefone.setText(usuario.getTelefone());
                                edit_text_pf_email.setText(usuario.getEmail());
                                edit_text_pf_senha.setText(usuario.getSenha());
                            }
                        }
                    }
                });

        btn_pf_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PerfilActivity.this, EditPerfilActivity.class);
                startActivity(intent);
            }
        });

        btn_pf_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                verificaAutenticacao();
            }
        });

        btn_pf_delete_conta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apagarUsuario(FirebaseAuth.getInstance().getUid());
            }
        });
    }

    private void verificaAutenticacao() {
        if (FirebaseAuth.getInstance().getUid() == null) {
            Intent intent = new Intent(PerfilActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void apagarUsuario(String srt) {
        DocumentReference docRef = firestore.collection("usuarios").document(srt);
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

}

