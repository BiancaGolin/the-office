package com.theoffice.mercadogue.controller;

import com.theoffice.mercadogue.model.Compra;
import com.theoffice.mercadogue.model.Produto;
import com.theoffice.mercadogue.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/compra")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CompraController {

    @Autowired
    private CompraRepository repository;

    @GetMapping
    public ResponseEntity<List<Compra>> getAll() {
        List<Compra> listcompra = repository.findAll();
        return ResponseEntity.ok(listcompra);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Compra> getById(@PathVariable int id){
        return repository.findById(id)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<Compra> put(@RequestBody Compra compra) {
        return ResponseEntity.ok(repository.save(compra));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Compra> postCompra(@RequestBody Compra compra){
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(compra));
    }
}
