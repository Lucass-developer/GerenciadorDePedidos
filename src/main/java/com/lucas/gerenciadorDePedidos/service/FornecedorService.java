package com.lucas.gerenciadorDePedidos.service;

import com.lucas.gerenciadorDePedidos.model.Fornecedor;
import com.lucas.gerenciadorDePedidos.repository.FornecedorRepository;

import java.util.List;
import java.util.Scanner;

public class FornecedorService {
    //Repository
    private final FornecedorRepository fornecedorRepository;

    //Constructors
    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    //Public
    public List<Fornecedor> fornecedorList(){
        return fornecedorRepository.findAll();
    }

    public void exibirFornecedores(){
        System.out.println("\n+ Lista de fornedores +");
        int i = 1;
        if (!fornecedorList().isEmpty()) {
            for (Fornecedor f : fornecedorList()) {
                System.out.println(i + " | " + f.getNome());
                i++;
            }
        } else {
            System.out.println("+ Lista Vazia! +\n");
        }
    }

    public void criarFonecedor(Scanner s) {
        System.out.println("+ Digite o nome do fornecedor:");
        String nome = s.nextLine();

        if (!existeFornecedor(nome)) {
            Fornecedor fornecedor = new Fornecedor(nome);
            fornecedorRepository.save(fornecedor);
            System.out.println("+ " + fornecedor.getNome() + " salvo com sucesso!");
        } else {
            System.out.println("+ Fonecedor ja registrado +");
        }
    }

    public boolean existeFornecedor(String nome){
        return fornecedorRepository.findAll().stream()
                .anyMatch(f -> f.getNome().equalsIgnoreCase(nome));
    }
}
