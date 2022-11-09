package br.ufc.quixada.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.ufc.quixada.myapplication.model.Anuncio;
import br.ufc.quixada.myapplication.model.Usuario;
import br.ufc.quixada.myapplication.transactions.Constants;

public class HomeActivity extends AppCompatActivity {

    int selected;
    ArrayList<Usuario> listaAmigos;
    ArrayAdapter adapter;
    Button btn_home_add_imovel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        selected = -1;

        listaAmigos = new ArrayList<Usuario>();
        Anuncio anuncio = new Anuncio("titulo", "Rua teste", 450, 400, 2, 4, 2, 350000);
        ArrayList<Anuncio> anuncios = new ArrayList<>();
        anuncios.add(anuncio);
        anuncios.add(anuncio);
        anuncios.add(anuncio);
        listaAmigos.add(new Usuario("Fernando","88988988989","889889889","fernando@gmail.com","senha123", anuncios));
        listaAmigos.add(new Usuario("Maria","222222222","22222222222","maria@gmail.com","senha123"));
        listaAmigos.add(new Usuario("Marcos","111111111","111111111111","marcos@gmail.com","senha123", anuncios));

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaAmigos);
        ListView listViewAmigos = (ListView) findViewById(R.id.list_view_home);
        listViewAmigos.setAdapter(adapter);
        listViewAmigos.setSelector(android.R.color.holo_blue_light);

        btn_home_add_imovel = findViewById(R.id.btn_home_add_imovel);

        btn_home_add_imovel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, RegisterAnuncioActivity.class);
                startActivity(intent);
            }
        });

//        listViewAmigos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//                Toast.makeText(HomeActivity.this,
//                        "" + listaAmigos.get(position).toString(),
//                        Toast.LENGTH_SHORT).show();
//                selected = position;
//            }
//        });
    }

//    public void apagarItemLista() {
//        if (selected >= 0) {
//            listaAmigos.remove(selected);
//            adapter.notifyDataSetChanged();
//        } else {
//            Toast.makeText(HomeActivity.this, "Selecione um Amigo", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void clicarAdicionar() {
//        Intent intent = new Intent(this, RegisterActivity.class);
//        startActivityForResult(intent, Constants.REQUEST_ADD);
//    }
//
//    public void clicarEditar() {
//        if (selected >= 0) {
//            Intent intent = new Intent(this, RegisterActivity.class);
//
//            Usuario usuario = listaAmigos.get(selected);
//
//            intent.putExtra("id", usuario.getId());
//            intent.putExtra("nome", usuario.getNome());
//            intent.putExtra("cpf", usuario.getCpf());
//            intent.putExtra("telefone", usuario.getTelefone());
//            intent.putExtra("email", usuario.getEmail());
//            intent.putExtra("senha", usuario.getSenha());
//
//            startActivityForResult(intent, Constants.REQUEST_EDIT);
//        } else {
//            Toast.makeText(HomeActivity.this, "Selecione um Amigo", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == Constants.REQUEST_ADD && resultCode == Constants.RESULT_ADD) {
//            String nome = (String) data.getExtras().get("nome");
//            String cpf = (String) data.getExtras().get("cpf");
//            String telefone = (String) data.getExtras().get("telefone");
//            String email = (String) data.getExtras().get("email");
//            String senha = (String) data.getExtras().get("senha");
//            Usuario usuario = new Usuario(nome, cpf, telefone, email, senha);
//            listaAmigos.add(usuario);
//            adapter.notifyDataSetChanged();
//        } else if (requestCode == Constants.REQUEST_EDIT && resultCode == Constants.RESULT_ADD) {
//            String nome = (String) data.getExtras().get("nome");
//            String cpf = (String) data.getExtras().get("cpf");
//            String telefone = (String) data.getExtras().get("telefone");
//            String email = (String) data.getExtras().get("email");
//            String senha = (String) data.getExtras().get("senha");
//
//            int idEditar = (int) data.getExtras().get("id");
//
//            for (Usuario usuario : listaAmigos) {
//                if (usuario.getId() == idEditar) {
//                    usuario.setNome(nome);
//                    usuario.setCpf(cpf);
//                    usuario.setTelefone(telefone);
//                    usuario.setEmail(email);
//                    usuario.setSenha(senha);
//                }
//            }
//            adapter.notifyDataSetChanged();
//        } else if (resultCode == Constants.RESULT_CANCEL) {
//            Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show();
//        }
//    }
}