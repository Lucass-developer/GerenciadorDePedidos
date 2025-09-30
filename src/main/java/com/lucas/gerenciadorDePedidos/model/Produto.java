package com.lucas.gerenciadorDePedidos.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    @Column(name = "Valor")
    private Double preco;

    //Maps
    @ManyToOne
    private Categoria categoria;

    @ManyToMany(mappedBy = "produtos")
    private List<Pedido> pedidos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    //Constructor
    public Produto(Double preco, String nome) {
        this.preco = preco;
        this.nome = nome;
    }

    public Produto(){}

    @Override
    public String toString() {
        return "Nome: " + nome + " - R$ " + preco + "Fornecedor: " + fornecedor.getNome() + "\n";
    }

    //Getters and Setters
    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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
