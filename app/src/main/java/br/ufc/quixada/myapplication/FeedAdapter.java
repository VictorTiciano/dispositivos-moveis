package br.ufc.quixada.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.ufc.quixada.myapplication.model.Anuncio;
import br.ufc.quixada.myapplication.model.AnuncioFireBase;

public class FeedAdapter extends ArrayAdapter<AnuncioFireBase> {

    private final Context context;
    private final ArrayList<AnuncioFireBase> anuncios;

    public FeedAdapter(Context context, ArrayList<AnuncioFireBase> anuncios) {
        super(context, R.layout.activity_feed, anuncios);
        this.context = context;
        this.anuncios = anuncios;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_feed, parent, false);

        TextView anuncio = (TextView) rowView.findViewById(R.id.nome);
        TextView endereco = (TextView) rowView.findViewById(R.id.endereco);
        TextView preco = (TextView) rowView.findViewById(R.id.preco);
        ImageView imagem = (ImageView) rowView.findViewById(R.id.imagem);

        anuncio.setText(anuncios.get(position).getTitulo());
        endereco.setText(anuncios.get(position).getEndereco());
        preco.setText(anuncios.get(position).getPreco());
        imagem.setImageResource(R.drawable.img5);
        return rowView;
    }
}
