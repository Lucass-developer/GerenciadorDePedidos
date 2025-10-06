package com.lucas.gerenciadorDePedidos.service;

import com.lucas.gerenciadorDePedidos.model.Categoria;
import com.lucas.gerenciadorDePedidos.model.Fornecedor;
import com.lucas.gerenciadorDePedidos.model.Produto;
import com.lucas.gerenciadorDePedidos.repository.CategoriaRepository;
import com.lucas.gerenciadorDePedidos.repository.FornecedorRepository;
import com.lucas.gerenciadorDePedidos.repository.ProdutoRepository;

import java.util.List;
import java.util.Scanner;

public class ProdutoService {
    //Services
    CategoriaService categoriaService;
    FornecedorService fornecedorService;

    //Repository's
    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final FornecedorRepository fornecedorRepository;

    //Constructors
    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository, FornecedorRepository fornecedorRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.categoriaService = new CategoriaService(categoriaRepository);
        this.fornecedorService = new FornecedorService(fornecedorRepository);
    }

    //Public
    public void menuVizualizarProdutos(Scanner s) {
        int opcao;
        do {
            System.out.println("""
                    + Filtros de busca +
                    1. Buscar por Nome
                    2. Buscar por Categoria
                    3. Buscar por Fornecedor
                    4. Buscar por Preço
                    5. Vizualizar Todos
                    0. Menu Principal
                    
                    + Escolha uma opção:
                    """);
            opcao = s.nextInt();
            s.nextLine();

            switch (opcao) {
                case 0 -> System.out.println("Retornando ao menu principal......");
                case 1 -> buscaPorNome(s);
                case 2 -> buscaPorCategoria(s);
                case 3 -> buscaPorFornecedor(s);
                case 4 -> buscaPorPreco(s);
                case 5 -> vizualizarProdutos();
                default -> System.out.println("+ Opção invalida!");
            }
        } while (opcao != 0);
    }

    public void adicionarProduto(Scanner s) {
        System.out.println("+ Nome do porduto:");
        String nome = s.nextLine();

        if (existeProduto(nome)) {
            System.out.println("+ Produdo ja registrado!");
            return;
        }

        System.out.println("+ Digite o Valor:");
        double valor = s.nextDouble();

        var fornecedor = adicionarFornecedor(s);
        var categoria = adicionarCategoria(s);

        Produto produto = new Produto(valor, nome);
        produto.setCategoria(categoria);
        produto.setFornecedor(fornecedor);
        categoria.getProdutos().add(produto);
        produtoRepository.save(produto);

        System.out.println("+ " + nome + " no valor de R$ " + valor + " adicionado a lista de produtos! +");
    }

    //Private
    private void buscaPorCategoria(Scanner s){
        categoriaService.exibirCategorias();
        System.out.println("+ Escolha a Categoria: ");
        var numCategoria = s.nextInt();
        s.nextLine();

        Long id = categoriaService.categoriaList().get(numCategoria - 1).getId();
        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        System.out.println(categoria);
    }

    private void buscaPorFornecedor(Scanner s) {
        fornecedorService.exibirFornecedores();
        System.out.println("+ Escolha o fornecedor: ");
        var numFornecedor = s.nextInt();
        s.nextLine();

        Long id = fornecedorService.fornecedorList().get(numFornecedor - 1).getId();
        Fornecedor fornecedor = fornecedorRepository.findById(id).orElse(null);

        System.out.println(fornecedor);
    }

    private void buscaPorPreco(Scanner s) {

    }

    private void buscaPorNome(Scanner s) {
        System.out.println("+ Digite o nome do produto:");
        String nomeDoProduto = s.nextLine();

        try {
            List<Produto> produtosPorNome = produtoRepository.findByNomeContainingIgnoreCase(nomeDoProduto);
            produtosPorNome.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("+ Produto nao encontrado! +");
        }

    }

    private Fornecedor adicionarFornecedor(Scanner s) {
        while (true){
            fornecedorService.exibirFornecedores();
            System.out.println("\n+ Digite o numero do Fornecedor (0 para adicionar novo fornecedor):");
            int numFornedor= s.nextInt();
            s.nextLine();

            if (numFornedor == 0) {
                fornecedorService.criarFonecedor(s);
            } else {
                numFornedor -= 1;
                if (fornecedorService.existeFornecedor(fornecedorService.fornecedorList().get(numFornedor).getNome())) {
                    System.out.println("\n+ " + fornecedorService.fornecedorList().get(numFornedor).getNome() + " selecionado! +\n");
                    return fornecedorService.fornecedorList().get(numFornedor);
                } else {
                    System.out.println("+ Fornecedor Não encontrado! +\n");
                }
            }
        }
    }

    private Categoria adicionarCategoria(Scanner s) {
        while (true) {
            categoriaService.exibirCategorias();
            System.out.println("\n+ Digite o numero da categoria (digite 0 para adicionar uma categoria):");

            var numeroDaCategoria = s.nextInt();
            s.nextLine();

            if (numeroDaCategoria == 0) {
                categoriaService.criarCategoria(s);
            }  else {
                numeroDaCategoria -= 1;
                if (categoriaService.existeCategoria(categoriaService.categoriaList().get(numeroDaCategoria).getNome())) {
                    System.out.println("\n+ " + categoriaService.categoriaList().get(numeroDaCategoria).getNome() + " selecionada! +\n");
                    return categoriaService.categoriaList().get(numeroDaCategoria);
                } else {
                    System.out.println("+ Esta Categoria nao existe! +");
                }
            }
        }
    }

    private void vizualizarProdutos() {
        System.out.println("\n+ Lista de produtos +\n");
        List<Categoria> produtosRegistrados = categoriaRepository.findAll();
        if (!produtoRepository.findAll().isEmpty()) {
            produtosRegistrados.forEach(System.out::println);
        } else {
            System.out.println("+ Lista de produtos vazia! +"); }
    }

    private boolean existeProduto(String nome) {
        return produtoRepository.findAll().stream()
                .anyMatch(p -> p.getNome().equalsIgnoreCase(nome));
    }
}
