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


    //Constructors
    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository, FornecedorRepository fornecedorRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.categoriaService = new CategoriaService(categoriaRepository);
        this.fornecedorService = new FornecedorService(fornecedorRepository);
    }

    //Public's
    public void vizualizarProdutos() {
        List<Categoria> produtosRegistrados = categoriaRepository.findAll();
        produtosRegistrados.forEach(System.out::println);
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

        System.out.println("\n+ " + nome + " no valor de R$ " + valor + " adicionado a lista de produtos! +\n");
    }

    //Privates
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
                    return fornecedorService.fornecedorList().get(numFornedor);
                } else {
                    System.out.println("Fornecedor Não encontrado!");
                }
            }
        }
    }

    private Categoria adicionarCategoria(Scanner s) {
        while (true) {
            System.out.println("+ Digite o numero da categoria (digite 0 para adicionar uma categoria):");
            categoriaService.exibirCategorias();

            var numeroDaCategoria = s.nextInt();
            s.nextLine();

            if (numeroDaCategoria == 0) {
                categoriaService.criarCategoria(s);
            }  else {
                numeroDaCategoria -= 1;
                if (categoriaService.existeCategoria(categoriaService.categoriaList().get(numeroDaCategoria).getNome())) {
                    return categoriaService.categoriaList().get(numeroDaCategoria);
                } else {
                    System.out.println("+ Esta Categoria nao existe! +");
                }
            }
        }
    }

    private boolean existeProduto(String nome) {
        return produtoRepository.findAll().stream()
                .anyMatch(p -> p.getNome().equalsIgnoreCase(nome));
    }
}
