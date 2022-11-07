package br.ufc.quixada.myapplication.model;

public class Anuncio {

    private int id;

    private String titulo;

    private String endereco;

    private double metrosQuadradosTerreno;

    private double metrosQuadradosConstruidos;

    private int quantidadeQuartos;

    private int quantidadeBanheiros;

    private int quantidadeVagasGaragem;

    private double preco;

    public Anuncio(String titulo, String endereco, double metrosQuadradosTerreno, double metrosQuadradosConstruidos, int quantidadeQuartos, int quantidadeBanheiros, int quantidadeVagasGaragem, double preco) {
        this.titulo = titulo;
        this.endereco = endereco;
        this.metrosQuadradosTerreno = metrosQuadradosTerreno;
        this.metrosQuadradosConstruidos = metrosQuadradosConstruidos;
        this.quantidadeQuartos = quantidadeQuartos;
        this.quantidadeBanheiros = quantidadeBanheiros;
        this.quantidadeVagasGaragem = quantidadeVagasGaragem;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public double getMetrosQuadradosTerreno() {
        return metrosQuadradosTerreno;
    }

    public void setMetrosQuadradosTerreno(double metrosQuadradosTerreno) {
        this.metrosQuadradosTerreno = metrosQuadradosTerreno;
    }

    public double getMetrosQuadradosConstruidos() {
        return metrosQuadradosConstruidos;
    }

    public void setMetrosQuadradosConstruidos(double metrosQuadradosConstruidos) {
        this.metrosQuadradosConstruidos = metrosQuadradosConstruidos;
    }

    public int getQuantidadeQuartos() {
        return quantidadeQuartos;
    }

    public void setQuantidadeQuartos(int quantidadeQuartos) {
        this.quantidadeQuartos = quantidadeQuartos;
    }

    public int getQuantidadeBanheiros() {
        return quantidadeBanheiros;
    }

    public void setQuantidadeBanheiros(int quantidadeBanheiros) {
        this.quantidadeBanheiros = quantidadeBanheiros;
    }

    public int getQuantidadeVagasGaragem() {
        return quantidadeVagasGaragem;
    }

    public void setQuantidadeVagasGaragem(int quantidadeVagasGaragem) {
        this.quantidadeVagasGaragem = quantidadeVagasGaragem;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return titulo + " - " + endereco + " - " + metrosQuadradosTerreno +
                " - " + metrosQuadradosConstruidos +
                " - " + quantidadeQuartos +
                " - " + quantidadeBanheiros +
                " - " + quantidadeVagasGaragem +
                " - " + preco+"\n";
    }
}
