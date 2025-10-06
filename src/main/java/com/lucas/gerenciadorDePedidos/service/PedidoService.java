package com.lucas.gerenciadorDePedidos.service;

import com.lucas.gerenciadorDePedidos.model.Pedido;
import com.lucas.gerenciadorDePedidos.repository.CategoriaRepository;
import com.lucas.gerenciadorDePedidos.repository.PedidoRepository;
import com.lucas.gerenciadorDePedidos.repository.ProdutoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class PedidoService {

    //Repository's
    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;

    //Services
    CategoriaService categoriaService;

    //Constructors
    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.pedidoRepository = pedidoRepository;
        this.categoriaService = new CategoriaService(categoriaRepository);
    }

    //Public
    public void vizualizarPedidos() {
        System.out.println("\n+ Lista de pedidos +");
        List<Pedido> pedidos = pedidoRepository.findAll();

        if(!pedidos.isEmpty()) {
            pedidos.forEach(System.out::println);
        } else {
            System.out.println("+ Lista de pedidos vazia! +");
        }
    }

    public void adiconarNovoPedido(Scanner scanner) {
    }

    //Private
    private Pedido salvarPedido() {
        Pedido pedido = new Pedido(LocalDate.now());
        return pedidoRepository.save(pedido);
    }
}
