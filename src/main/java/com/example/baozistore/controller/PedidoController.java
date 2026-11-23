package com.example.baozistore.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.baozistore.model.Pedido;
import com.example.baozistore.repository.PedidoRepository;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoRepository repository;

	@PostMapping
	public ResponseEntity<Pedido> criar(@RequestBody Pedido pedido) {
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(pedido));
	}

	@GetMapping
	public List<Pedido> listarTodos() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
		return repository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Pedido> atualizar(@PathVariable Long id, @RequestBody Pedido dados) {
		return repository.findById(id).map(pedido -> {
			pedido.setClienteId(dados.getClienteId());
			pedido.setProdutoId(dados.getProdutoId());
			pedido.setQuantidade(dados.getQuantidade());
			return ResponseEntity.ok(repository.save(pedido));
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
