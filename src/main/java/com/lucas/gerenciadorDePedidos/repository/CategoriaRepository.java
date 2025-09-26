package com.lucas.gerenciadorDePedidos.repository;

import com.lucas.gerenciadorDePedidos.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
