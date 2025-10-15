package com.lucas.gerenciadorDePedidos;

import com.lucas.gerenciadorDePedidos.main.Main;
import com.lucas.gerenciadorDePedidos.repository.CategoriaRepository;
import com.lucas.gerenciadorDePedidos.repository.FornecedorRepository;
import com.lucas.gerenciadorDePedidos.repository.PedidoRepository;
import com.lucas.gerenciadorDePedidos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GerenciadorDePedidosApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GerenciadorDePedidosApplication.class, args);
	}

	//Repositories
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private FornecedorRepository fornecedorRepository;

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(produtoRepository, pedidoRepository, categoriaRepository, fornecedorRepository);
		main.gerenciador();
	}
}
