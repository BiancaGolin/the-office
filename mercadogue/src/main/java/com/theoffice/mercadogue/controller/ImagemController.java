package com.theoffice.mercadogue.controller;

import com.theoffice.mercadogue.model.Imagem;
import com.theoffice.mercadogue.repository.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/imagem")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class ImagemController {

    @Autowired
    private ImagemRepository repository;

    @GetMapping
    public ResponseEntity<List<Imagem>> getAllImg() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Imagem> getByIdImg(@PathVariable int id) {
        return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<Imagem> putImg (@RequestBody Imagem imagem) {
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(imagem));
    }

    @PostMapping
    public ResponseEntity<Imagem> postImg(@RequestBody Imagem imagem) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(imagem));
    }

    @DeleteMapping("/{id}")
    public void deleteImg (@PathVariable int id) {
        repository.deleteById(id);
    }
}
