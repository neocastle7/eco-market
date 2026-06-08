package br.ucb.rpg.eco_market.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private double preco;
    private int estoque;

    // TODO (Lucas e Jackson): Implementar os atributos focados em Sustentabilidade
    // Ex: pontosDeReciclagem, creditosDeCarbono, etc.

    public Produto() {}

    // TODO (Lucas e Jackson): Gerar os Getters e Setters para manter o Encapsulamento da POO
}