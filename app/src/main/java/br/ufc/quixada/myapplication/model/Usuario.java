package br.ufc.quixada.myapplication.model;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Usuario {

    private int id;

    private String nome;

    private String cpf;

    private String telefone;

    private String email;

    private String senha;

    private ArrayList<Anuncio> anuncios;

    public Usuario(){}

    public Usuario(String nome, String cpf, String telefone, String email, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String nome, String cpf, String telefone, String email, String senha, ArrayList<Anuncio> anuncios) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.anuncios = anuncios;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public ArrayList<Anuncio> getAnuncios() {
        return anuncios;
    }

    public void setAnuncios(ArrayList<Anuncio> anuncios) {
        this.anuncios = anuncios;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + " - CPF: " + cpf + " - Telefone: " + telefone + " - Email: " + email
                + "\n" + anuncios;
    }
}
