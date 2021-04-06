package com.theoffice.mercadogue.controller;

import com.theoffice.mercadogue.model.Usuario;
import com.theoffice.mercadogue.model.UsuarioLogin;
import com.theoffice.mercadogue.repository.UsuarioRepository;
import com.theoffice.mercadogue.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository repository;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable int id) {
        return repository.findById(id)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("validemail")
    public ResponseEntity<Boolean> getEmailExistente(@PathVariable String email) {
        List<Usuario> usuario = repository.findAllByNomeUsuarioContainingIgnoreCase(email);

        boolean existeEmail = true;

        if (usuario.isEmpty()) {
            existeEmail = false;
        }

        return ResponseEntity.ok(existeEmail);
    }

    @GetMapping("nomeUsuario/{nomeUsuario}")
    public ResponseEntity<List<Usuario>> getByNomeUsuario(@PathVariable String nomeUsuario) {
        return ResponseEntity.ok(repository.findAllByNomeUsuarioContainingIgnoreCase(nomeUsuario));
    }

    @PutMapping("/alterar")
    public ResponseEntity<Usuario> put(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(repository.save(usuario));
    }

    @PostMapping("/logar")
    public ResponseEntity<UsuarioLogin> authentication(@RequestBody Optional<UsuarioLogin> user) {
        System.out.println("deu ruim ou deu bom?");
        return usuarioService.logar(user).map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.cadastrarUsuario(usuario));
    }
}
