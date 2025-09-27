package com.lucas.gerenciadorDePedidos.main;

import com.lucas.gerenciadorDePedidos.model.Categoria;
import com.lucas.gerenciadorDePedidos.model.Pedido;
import com.lucas.gerenciadorDePedidos.model.Produto;
import com.lucas.gerenciadorDePedidos.repository.CategoriaRepository;
import com.lucas.gerenciadorDePedidos.repository.PedidoRepository;
import com.lucas.gerenciadorDePedidos.repository.ProdutoRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private final Scanner scanner = new Scanner(System.in);

    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;
    private final CategoriaRepository  categoriaRepository;

    public Main(ProdutoRepository produtoRepository,
                PedidoRepository pedidoRepository,
                CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.pedidoRepository = pedidoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    private List<Categoria> categorias = new ArrayList<>();


    public void gerenciador() {
        boolean loop = true;
        categorias = categoriaRepository.findAll();

        do {
            System.out.println("""
                    \nDIGITE A OPÇÃO:
                    1 - Adicionar Produtosr
                    2 - Vizualizar Produtos
                    3 - Adicionar Pedidos
                    4 - Ver Pedidos
                    5 - sair
                    """);

            var opcaoEscolhida = scanner.nextInt();
            scanner.nextLine();

            switch (opcaoEscolhida) {
                case 1: {
                    adicionarProduto();
                    break;
                }
                case 2: {
                    vizualizarProdutos();
                    break;
                }
                case 3: {
                    adicionarPedido();
                    break;
                }
                case 4: {
                    System.out.println("4");
                    break;
                }
                case 5: {
                    System.out.println("Saindo....");
                    loop = false;
                    break;
                }
                default: {
                    System.out.println("Opção invalida :(");
                }
            }
        } while (loop);
    }

    private void adicionarPedido() {
        var loop = true;
        do {
            exibirCategorias();

            System.out.println("Digite o numero da categoria para selecionar os produtos:");
            var categoriaEscolhida = scanner.nextInt();
            scanner.nextLine();

            if (categoriaEscolhida <= categorias.size()) {
                Pedido pedido = new Pedido(LocalDate.now());

                var num = -1;
                int numeroProduto;
                var categoria = categorias.get(categoriaEscolhida - 1);

                do {
                    System.out.println(categoria);

                    if (num == -1) {
                        System.out.println("Digite o numero do produto para adicionar ao pedido:");
                    } else {
                        System.out.println("Digite o numero do produto para adicionar ao pedido ou 0 para fechar o pedido:");

                    }
                    numeroProduto = scanner.nextInt();
                    scanner.nextLine();

                    num = numeroProduto;

                    if (numeroProduto != 0) {
                        if (numeroProduto <= categoria.getProdutos().size()) {
                            var idproduto = categoria.getProdutos().get(numeroProduto - 1).getId();

                            Optional<Produto> buscaProduto = produtoRepository.findById(idproduto);

                            Produto produto = buscaProduto.get();

                            pedido.getProdutos().add(produto);

                            System.out.println("***** " + produto.getNome() + " Adicionado ao pedido! *****\n");


                        } else {
                            System.out.println("***** Produto nao encontrado! *****\n");
                        }
                    } else {
                        pedidoRepository.save(pedido);
                        System.out.println("\n***** PEDIDO FECHADO! *****");
                        num = numeroProduto;
                    }

                } while (num != 0);

                loop = false;

            } else {
                System.out.println("***** CATEGORIA NÃO ENCONTRADA! *****\n");
            }
        } while (loop);
    }

    private void adicionarProduto() {
        boolean loop = true;
        String nome = "";

        do {
            System.out.println("Nome do porduto:");
            String nomeCheck = scanner.nextLine();

            List<Produto> nomeProdutos = produtoRepository.findAll();

            Optional<Produto> verificaProduto = nomeProdutos.stream()
                    .filter(p -> p.getNome().toLowerCase().contains(nomeCheck.toLowerCase()))
                    .findAny();

            if (verificaProduto.isEmpty()) {
                nome = nomeCheck;
                loop = false;
            } else {
                System.out.println("Este Produto ja esta registrado!");
            }

        } while (loop);

        loop = true;

        System.out.println("Digite o Valor:");
        double valor = scanner.nextDouble();

        do {
            exibirCategorias();


            System.out.println("Digite o numero da categoria (digite 0 para adicionar uma categoria):");
            var numeroDaCategoria = scanner.nextInt();
            scanner.nextLine();

            if (numeroDaCategoria == 0) {
                System.out.println("Digite o nome da Categoria para cadastrar:");
                String nomeCategoria = scanner.nextLine();

                Optional<Categoria> verficaCategoria = categorias.stream()
                        .filter(c -> c.getNome().toLowerCase().contains(nomeCategoria.toLowerCase()))
                        .findFirst();

                if (verficaCategoria.isEmpty()){
                    Categoria categoria = new Categoria(nomeCategoria);
                    categoriaRepository.save(categoria);
                    System.out.println("**** Nova Categoria salva com sucesso! ****");
                } else {
                    System.out.println("**** Categoria Ja existente! ****");
                }

            } else if (numeroDaCategoria <= categorias.size()) {
                Produto produto = new Produto(valor, nome);

                var categoria = categorias.get(numeroDaCategoria - 1);

                List<Produto> novosProdutos = categoria.getProdutos();

                novosProdutos.add(produto);
                categoria.setProdutos(novosProdutos);
                produtoRepository.save(produto);

                System.out.println("\n****** " + nome + " no valor de R$ " + valor + " adicionado a lista de produtos! ******\n");

                loop =  false;
            } else {
                System.out.println("Esta Categoria nao existe!");
            }
        } while (loop);
    }

    private void exibirCategorias() {
        System.out.println("LISTA DE CATEGORIAS:");

        categorias = categoriaRepository.findAll();

        if (!categorias.isEmpty()){
            for (int i = 0; i < categorias.size(); i++) {
                System.out.println(i + 1 + " - " +categorias.get(i).getNome());
            }
        } else {
            System.out.println("**** Lista de categorias vazia! ****");
        }

    }

    private void vizualizarProdutos(){
        List<Categoria> produtosRegistrados = categoriaRepository.findAll();

        produtosRegistrados.forEach(System.out::println);

    }

}
