package com.lucas.gerenciadorDePedidos.service;

import com.lucas.gerenciadorDePedidos.model.Categoria;
import com.lucas.gerenciadorDePedidos.model.Pedido;
import com.lucas.gerenciadorDePedidos.model.Produto;
import com.lucas.gerenciadorDePedidos.repository.CategoriaRepository;
import com.lucas.gerenciadorDePedidos.repository.PedidoRepository;
import com.lucas.gerenciadorDePedidos.repository.ProdutoRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PedidoService {

    //Repositories
    private final PedidoRepository pedidoRepository;

    //Services
    CategoriaService categoriaService;

    //Constructors
    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
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

    public void adiconarNovoPedido(Scanner s) {
        System.out.println("+ Pedido iniciado! +");
        Pedido pedido = new Pedido(LocalDate.now());
        List<Produto> produtos = new ArrayList<>();

        do {
            categoriaService.exibirCategorias();
            System.out.println("+ Selecione a categoria para adicionar um produto(digite 0 para fechar o pedido):");
            int numCategoria = s.nextInt();
            s.nextLine();

            if (numCategoria == 0) {
                if(produtos.isEmpty()){
                    System.out.println("+ Pedido cancelado! +");
                    return;
                } else {
                    pedido.setProdutos(produtos);
                    pedidoRepository.save(pedido);
                    System.out.println(pedido);
                    System.out.println("+ Pedido finalizado! +");
                    return;
                }
            } else {
                numCategoria -= 1;
            }

            if (numCategoria <= categoriaService.categoriaList().size()) {
                Categoria categoria = categoriaService.categoriaList().get(numCategoria);
                Produto produto = adiconarProdutoAoPedido(categoria, s);
                produtos.add(produto);
            } else {
                System.out.println("+ Categoria inválida, tente novamente. +");
            }
        
        } while (true);
    }

    //Private
    private Produto adiconarProdutoAoPedido(Categoria categoria, Scanner s) {
        while(true) {
            System.out.println(categoria);
            System.out.println("+ digite o numero do produto para adiconar ao pedido:");
            int numProduto = s.nextInt();
            s.nextLine();

            if (numProduto <= categoria.getProdutos().size()) {
            return categoria.getProdutos().get(numProduto - 1);
            } else {
                System.out.println("+ Produto nao encontrado! +");
            }
        }
    }
}
