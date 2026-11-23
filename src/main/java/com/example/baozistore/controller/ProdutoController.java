package com.example.baozistore.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.baozistore.model.Produto;
import com.example.baozistore.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoRepository repository;

	@PostMapping
	public ResponseEntity<Produto> criar(@RequestBody Produto produto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(produto));
	}

	@GetMapping
	public List<Produto> listarTodos() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
		return repository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto dados) {
		return repository.findById(id).map(produto -> {
			produto.setNome(dados.getNome());
			produto.setPreco(dados.getPreco());
			produto.setEstoque(dados.getEstoque());
			return ResponseEntity.ok(repository.save(produto));
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		if (!repository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
