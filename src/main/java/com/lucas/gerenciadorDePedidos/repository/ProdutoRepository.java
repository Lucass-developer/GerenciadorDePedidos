package com.lucas.gerenciadorDePedidos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lucas.gerenciadorDePedidos.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByNomeContainingIgnoreCase(String nome);

    List<Produto> findByPrecoGreaterThanEqual(double valor);

    List<Produto> findByPrecoLessThanEqual(double valor);

    List<Produto> findByPrecoEquals(double valor);

    List<Produto> findTop3ByOrderByPrecoDesc();

    List<Produto> findTop5ByOrderByPrecoAsc();

    @Query("SELECT p FROM Produto p WHERE p.nome ILIKE %:name%")
    List<Produto> findBYProdutoName(String name);
}
