package br.ufc.quixada.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.ufc.quixada.myapplication.model.Mensagem;

public class MensagemAdapter extends ArrayAdapter<Mensagem> {

    private final Context context;
    private final ArrayList<Mensagem> mensagens;

    public MensagemAdapter(Context context, ArrayList<Mensagem> mensagens) {
        super(context, R.layout.activity_mensagem_chat, mensagens);
        this.context = context;
        this.mensagens = mensagens;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_mensagem_chat, parent, false);

        TextView mensagem = (TextView) rowView.findViewById(R.id.nome_user);
        ImageView imagem = (ImageView) rowView.findViewById(R.id.imagem_user);

        mensagem.setText(mensagens.get(position).getMsg());
        imagem.setImageResource(mensagens.get(position).getImg());
        return rowView;
    }
}
