package com.lucas.gerenciadorDePedidos.main;

import com.lucas.gerenciadorDePedidos.repository.CategoriaRepository;
import com.lucas.gerenciadorDePedidos.repository.PedidoRepository;
import com.lucas.gerenciadorDePedidos.repository.ProdutoRepository;
import com.lucas.gerenciadorDePedidos.service.PedidoService;
import com.lucas.gerenciadorDePedidos.service.ProdutoService;

import java.util.Scanner;

public class Main {
    //Services2
    private final Scanner scanner = new Scanner(System.in);
    private final ProdutoService produtoService;
    private final PedidoService pedidoService;

    //Repository's
    public Main(ProdutoRepository produtoRepository, PedidoRepository pedidoRepository, CategoriaRepository categoriaRepository) {
        //Services
        this.produtoService = new ProdutoService(produtoRepository, categoriaRepository);
        this.pedidoService = new PedidoService(pedidoRepository, produtoRepository, categoriaRepository);
    }

    public void gerenciador() {
        int opcao;

        do {
            System.out.println("""
                    \n+ -*-*-  MENU  -*-*- +
                    1 - Adicionar Produtos
                    2 - Vizualizar Produtos
                    3 - Adicionar Pedidos
                    4 - Ver Pedidos
                    0 - Sair
                    + Digite uma Opção:
                    """);

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> produtoService.adicionarProduto(scanner);
                case 2 -> produtoService.vizualizarProdutos();
                case 3 -> pedidoService.addProdutoPedido(scanner);
                case 4 -> pedidoService.vizualizarPedidos();
                case 0 -> System.out.println("+ Saindo.....");
                default -> System.out.println("+ Opção invalida, tente novamente.");
            }

        } while (opcao != 0);

        scanner.close();
    }
}
