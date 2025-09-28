package com.lucas.gerenciadorDePedidos.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "pedido_produto",
            joinColumns = @JoinColumn(name = "pedido_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id", referencedColumnName = "id")
    )

    private List<Produto> produtos = new ArrayList<>();

    //Construtores
    public Pedido(LocalDate data) {
        this.data = data;
    }

    public Pedido(){}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 1;

        for(Produto p : produtos) {
            sb
                    .append(i++)
                    .append(". ")
                    .append(p.getNome())
                    .append(" - R$ ")
                    .append(p.getPreco())
                    .append("\n");
        }
        return "Pedido: " + id + " | " + data + "\nProdutos:\n" + sb;
    }

    //Getters and setters

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
