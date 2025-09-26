package com.lucas.gerenciadorDePedidos.model;

import jakarta.persistence.*;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Categoria categoria;

    @Column(unique = true)
    private String nome;
    @Column(name = "Valor")
    private Double preco;

    //construtor padrão;
    public Produto(){}

    @Override
    public String toString() {
        return "Nome: " + nome + " - R$ " + preco + "\n";
    }

    //get and setters
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Produto(Double preco, String nome) {
        this.preco = preco;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
