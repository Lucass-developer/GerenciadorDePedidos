package com.lucas.gerenciadorDePedidos.service;

import com.lucas.gerenciadorDePedidos.model.Categoria;
import com.lucas.gerenciadorDePedidos.repository.CategoriaRepository;

import java.util.List;
import java.util.Scanner;

public class CategoriaService {

    //Repository's
    private final CategoriaRepository categoriaRepository;

    //Constructors
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    //Public
    public void exibirCategorias() {
        System.out.println("\n+ Lista de categorias +");

        if (!categoriaList().isEmpty()) {
            int i = 1;
            for (Categoria c : categoriaList()) {
                System.out.println(i + ". " + c.getNome());
                i++;
            }
        } else {
            System.out.println("+ Lista de categorias vazia! +");
        }
    }

    public List<Categoria> categoriaList(){
        return categoriaRepository.findAll();
    }

    public void criarCategoria(Scanner s) {
        System.out.println("+ Digite o nome para criar a categoria:");
        String nome = s.nextLine();

        if (!existeCategoria(nome)) {
            Categoria categoria = new Categoria(nome);
            categoriaRepository.save(categoria);
            System.out.println("+" + categoria.getNome() + " Salva com sucesso! +");
        } else {
            System.out.println("+ Categoria ja registrada! +\n");
        }
    }

    public boolean existeCategoria(String nomeCategoria) {
        return categoriaList().stream()
                .anyMatch(c -> c.getNome().equalsIgnoreCase(nomeCategoria));
    }
}
