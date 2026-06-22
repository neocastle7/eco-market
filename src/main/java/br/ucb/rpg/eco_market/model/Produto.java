package br.ucb.rpg.eco_market.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
    private Double preco;
    private Integer estoque;
    private Integer pontosDeReciclagem;
    private Double creditosDeCarbono;

    public Produto() {}

    public Produto(String nome, Double preco, Integer estoque, Integer pontosDeReciclagem, Double creditosDeCarbono) {
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.pontosDeReciclagem = pontosDeReciclagem;
        this.creditosDeCarbono = creditosDeCarbono;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }

    public Integer getEstoque() { return estoque; }
    public void setEstoque(Integer estoque) { this.estoque = estoque; }

    public Integer getPontosDeReciclagem() { return pontosDeReciclagem; }
    public void setPontosDeReciclagem(Integer pontosDeReciclagem) { this.pontosDeReciclagem = pontosDeReciclagem; }

    public Double getCreditosDeCarbono() { return creditosDeCarbono; }
    public void setCreditosDeCarbono(Double creditosDeCarbono) { this.creditosDeCarbono = creditosDeCarbono; }
}