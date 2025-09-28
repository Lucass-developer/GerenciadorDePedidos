package com.lucas.gerenciadorDePedidos.model;

import com.lucas.gerenciadorDePedidos.repository.ProdutoRepository;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Produto> produtos;

    @Override
    public String toString() {

        StringBuilder listaProdutos = new StringBuilder();

        int i = 1;

        for (Produto p : produtos) {
            listaProdutos
                    .append(i++)
                    .append(" | ")
                    .append(p.getNome())
                    .append(" - R$ ")
                    .append(p.getPreco())
                    .append("\n");
        }

        return "Categoria: " + nome +
                "\nLista de Produtos:\n" + listaProdutos;
    }

    //Construtor padrao
    public Categoria(){}

    //Getters and setters
    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        produtos.forEach(p -> p.setCategoria(this));
        this.produtos = produtos;
    }

    public Categoria(String nome) {
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
}
