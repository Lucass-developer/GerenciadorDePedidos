package com.lucas.gerenciadorDePedidos.repository;

import com.lucas.gerenciadorDePedidos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
