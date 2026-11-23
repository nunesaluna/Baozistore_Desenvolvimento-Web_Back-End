package com.example.baozistore.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.baozistore.model.Cliente;
import com.example.baozistore.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteRepository repository;

	@PostMapping
	public ResponseEntity<Cliente> criar(@RequestBody Cliente cliente) {
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(cliente));
	}

	@GetMapping
	public List<Cliente> listarTodos() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
		return repository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente dados) {
		return repository.findById(id).map(cliente -> {
			cliente.setNome(dados.getNome());
			cliente.setClienteDesde(dados.getClienteDesde());
			return ResponseEntity.ok(repository.save(cliente));
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
