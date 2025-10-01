package com.lucas.gerenciadorDePedidos.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL)
    private List<Produto> produtos = new ArrayList<>();

    //Constructors
    public Fornecedor(String nome) {
        this.nome = nome;
    }
    public Fornecedor(){}

    @Override
    public String toString() {
        StringBuilder listaProdutos = new StringBuilder();

        for (Produto p : produtos) {
            listaProdutos
                    .append(p.getCategoria())
                    .append(" | ")
                    .append(p.getNome())
                    .append(" - R$ ")
                    .append(p.getPreco())
                    .append("\n");
        }
        return "Fornecedor: " + nome +
                "\nLista de Produtos:\n" + listaProdutos;
    }

    //Getters & Setters
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

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}
