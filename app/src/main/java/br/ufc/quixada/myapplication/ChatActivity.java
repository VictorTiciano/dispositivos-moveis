package br.ufc.quixada.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import br.ufc.quixada.myapplication.model.Anuncio;
import br.ufc.quixada.myapplication.model.Mensagem;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ListView msg = (ListView) findViewById(R.id.list_view_chat);
        ArrayAdapter adapter = new MensagemAdapter(this, adicionarMensagem());
        msg.setAdapter(adapter);
    }

    private ArrayList<Anuncio> adicionarAnuncio() {
        ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>();
        Anuncio e = new Anuncio("Casa 1", "Rua 1", R.drawable.img1, 10, 5, 7, 9, 12, 140000);
        anuncios.add(e);
        e = new Anuncio("Casa 2", "Rua 2", R.drawable.img2, 10, 5, 7, 9, 12, 140000);
        anuncios.add(e);
        e = new Anuncio("Casa 3", "Rua 3", R.drawable.img3, 10, 5, 7, 9, 12, 140000);
        anuncios.add(e);
        e = new Anuncio("Casa 4", "Rua 4", R.drawable.img4, 10, 5, 7, 9, 12, 140000);
        anuncios.add(e);
        e = new Anuncio("Casa 5", "Rua 5", R.drawable.img5, 10, 5, 7, 9, 12, 140000);
        anuncios.add(e);
        return anuncios;
    }

    private ArrayList<Mensagem> adicionarMensagem() {
        ArrayList<Mensagem> mensagens = new ArrayList<>();
        mensagens.add(new Mensagem("Mensagem teste", R.drawable.img1));
        mensagens.add(new Mensagem("Mensagem teste", R.drawable.img1));
        mensagens.add(new Mensagem("Mensagem teste", R.drawable.img1));
        mensagens.add(new Mensagem("Mensagem teste", R.drawable.img1));
        return mensagens;
    }
}