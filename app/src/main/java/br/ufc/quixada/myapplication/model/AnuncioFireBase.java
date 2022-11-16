package br.ufc.quixada.myapplication.model;

public class AnuncioFireBase {

    private String uuid;

    private String titulo;

    private String endereco;

    private String img;

    private String metrosQuadradosTerreno;

    private String metrosQuadradosConstruidos;

    private String quantidadeQuartos;

    private String quantidadeBanheiros;

    private String quantidadeVagasGaragem;

    private String preco;

    public AnuncioFireBase() {
    }

    public AnuncioFireBase(String uuid, String titulo, String endereco, String img, String metrosQuadradosTerreno, String metrosQuadradosConstruidos, String quantidadeQuartos, String quantidadeBanheiros, String quantidadeVagasGaragem, String preco) {
        this.uuid = uuid;
        this.titulo = titulo;
        this.endereco = endereco;
        this.img = img;
        this.metrosQuadradosTerreno = metrosQuadradosTerreno;
        this.metrosQuadradosConstruidos = metrosQuadradosConstruidos;
        this.quantidadeQuartos = quantidadeQuartos;
        this.quantidadeBanheiros = quantidadeBanheiros;
        this.quantidadeVagasGaragem = quantidadeVagasGaragem;
        this.preco = preco;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMetrosQuadradosTerreno() {
        return metrosQuadradosTerreno;
    }

    public void setMetrosQuadradosTerreno(String metrosQuadradosTerreno) {
        this.metrosQuadradosTerreno = metrosQuadradosTerreno;
    }

    public String getMetrosQuadradosConstruidos() {
        return metrosQuadradosConstruidos;
    }

    public void setMetrosQuadradosConstruidos(String metrosQuadradosConstruidos) {
        this.metrosQuadradosConstruidos = metrosQuadradosConstruidos;
    }

    public String getQuantidadeQuartos() {
        return quantidadeQuartos;
    }

    public void setQuantidadeQuartos(String quantidadeQuartos) {
        this.quantidadeQuartos = quantidadeQuartos;
    }

    public String getQuantidadeBanheiros() {
        return quantidadeBanheiros;
    }

    public void setQuantidadeBanheiros(String quantidadeBanheiros) {
        this.quantidadeBanheiros = quantidadeBanheiros;
    }

    public String getQuantidadeVagasGaragem() {
        return quantidadeVagasGaragem;
    }

    public void setQuantidadeVagasGaragem(String quantidadeVagasGaragem) {
        this.quantidadeVagasGaragem = quantidadeVagasGaragem;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }
}
