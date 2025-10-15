package com.lucas.gerenciadorDePedidos.main;

import com.lucas.gerenciadorDePedidos.repository.CategoriaRepository;
import com.lucas.gerenciadorDePedidos.repository.FornecedorRepository;
import com.lucas.gerenciadorDePedidos.repository.PedidoRepository;
import com.lucas.gerenciadorDePedidos.repository.ProdutoRepository;
import com.lucas.gerenciadorDePedidos.service.PedidoService;
import com.lucas.gerenciadorDePedidos.service.ProdutoService;

import java.util.Scanner;

public class Main {
    //Services
    private final Scanner scanner = new Scanner(System.in);
    private final ProdutoService produtoService;
    private final PedidoService pedidoService;

    //Repositories
    public Main(ProdutoRepository produtoRepository, PedidoRepository pedidoRepository, CategoriaRepository categoriaRepository, FornecedorRepository fornecedorRepository) {
        //Services
        this.produtoService = new ProdutoService(produtoRepository, categoriaRepository, fornecedorRepository);
        this.pedidoService = new PedidoService(pedidoRepository, produtoRepository, categoriaRepository);
    }

    public void gerenciador() {
        int opcao;

        do {
            System.out.println("""
                    + -*-*-  MENU  -*-*- +
                    1 - Adicionar Produtos
                    2 - Menu Produtos
                    3 - Adicionar Pedidos
                    4 - Ver Pedidos
                    0 - Sair
                    + Digite uma Opção:
                    """);

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> produtoService.adicionarProduto(scanner);
                case 2 -> produtoService.menuVizualizarProdutos(scanner);
                case 3 -> pedidoService.adiconarNovoPedido(scanner);
                case 4 -> pedidoService.vizualizarPedidos();
                case 0 -> System.out.println("+ Saindo.....");
                default -> System.out.println("+ Opção invalida, tente novamente.");
            }

        } while (opcao != 0);

        scanner.close();
    }
}
