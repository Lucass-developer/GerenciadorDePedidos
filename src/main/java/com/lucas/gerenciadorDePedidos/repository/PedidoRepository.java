package com.lucas.gerenciadorDePedidos.repository;

import com.lucas.gerenciadorDePedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
