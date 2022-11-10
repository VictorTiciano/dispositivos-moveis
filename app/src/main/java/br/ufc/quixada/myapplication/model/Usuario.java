package br.ufc.quixada.myapplication.model;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Usuario {

    private String uuid;

    private String nome;

    private String cpf;

    private String telefone;

    private String email;

    private String senha;

    private String foto;

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

    public Usuario(String uuid, String nome, String cpf, String telefone, String email, String senha, String foto, ArrayList<Anuncio> anuncios) {
        this.uuid = uuid;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.foto = foto;
        this.anuncios = anuncios;
    }
    public Usuario(String uuid, String nome, String cpf, String telefone, String email, String senha, String foto) {
        this.uuid = uuid;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.foto = foto;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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

    public String toString2() {
        return "Nome: " + nome + " - Telefone: " + telefone + " - Email: " + email;
    }

}
