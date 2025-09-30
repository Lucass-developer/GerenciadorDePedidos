package com.lucas.gerenciadorDePedidos.repository;

import com.lucas.gerenciadorDePedidos.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
}
