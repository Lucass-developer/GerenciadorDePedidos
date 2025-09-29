package com.lucas.gerenciadorDePedidos.service;

import com.lucas.gerenciadorDePedidos.model.Pedido;
import com.lucas.gerenciadorDePedidos.model.Produto;
import com.lucas.gerenciadorDePedidos.repository.CategoriaRepository;
import com.lucas.gerenciadorDePedidos.repository.PedidoRepository;
import com.lucas.gerenciadorDePedidos.repository.ProdutoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PedidoService {

    //Repository's
    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;

    //Service
    CategoriaService categoriaService;

    //Constructors
    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.pedidoRepository = pedidoRepository;
        this.categoriaService = new CategoriaService(categoriaRepository);
    }

    //Functions
    public void vizualizarPedidos() {
        System.out.println("+ LISTA DE PEDIDOS +");
        List<Pedido> pedidos = pedidoRepository.findAll();
        pedidos.forEach(System.out::println);
    }

    public void addProdutoPedido(Scanner scanner) {

        Pedido pedido = criarPedido();

        while (true) {
            categoriaService.exibirCategorias();

            if (pedido.getProdutos().isEmpty()) {
                System.out.println("+ Digite o numero da categoria para selecionar os produtos:");
            } else {
                System.out.println("+ Digite o numero da categoria para selecionar os produtos (Digite 0 para fechar o pedido):");
            }

            var categoriaEscolhida = scanner.nextInt();
            scanner.nextLine();

            if (categoriaEscolhida == 0) {
                pedidoRepository.save(pedido);
                System.out.println("+ Pedido fechado com sucesso! +\n");
                break;
            } else {
                categoriaEscolhida -= 1;
            }

            if (categoriaEscolhida <= categoriaService.categoriaList().size()) {

                var categoria = categoriaService.categoriaList().get(categoriaEscolhida);

                System.out.println(categoria);

                System.out.println("+ Digite o numero do produto para adicionar ao pedido:");

                int numeroProduto = scanner.nextInt();
                scanner.nextLine();

                long idProduto = categoria.getProdutos().get(numeroProduto - 1).getId();

                Optional<Produto> produto = produtoRepository.findById(idProduto);
                produto.ifPresent(p -> pedido.getProdutos().add(p));
                produto.ifPresent(p -> System.out.println("+ " + p.getNome() + " no valor de R$" + p.getPreco() + " adicionado ao pedido! +"));

            } else {
                System.out.println("\n+ Categoria nao encontrada! +\n");
            }
        }
    }

    private Pedido criarPedido() {
        Pedido pedido = new Pedido(LocalDate.now());
        return pedidoRepository.save(pedido);
    }
}
