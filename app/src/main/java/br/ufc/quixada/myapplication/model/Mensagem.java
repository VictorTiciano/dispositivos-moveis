package br.ufc.quixada.myapplication.model;

public class Mensagem {
    private String msg;
    private int img;

    public Mensagem(String msg, int img) {
        this.msg = msg;
        this.img = img;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
