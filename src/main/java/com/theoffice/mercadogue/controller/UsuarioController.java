package com.theoffice.mercadogue.controller;

import com.theoffice.mercadogue.model.Usuario;
import com.theoffice.mercadogue.model.UsuarioLogin;
import com.theoffice.mercadogue.repository.UsuarioRepository;
import com.theoffice.mercadogue.service.UsuarioService;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
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


    @GetMapping("consultacep/{cep}")
    public ResponseEntity<Boolean> consultaCep(@PathVariable String cep) {

        boolean existeCEP = usuarioService.consultaCep(cep);

        return ResponseEntity.ok().body(existeCEP);
    }


    @GetMapping("consultacpf/{cpf}")
    public ResponseEntity<Boolean> consultaCPF(@CPF @PathVariable String cpf) {

        CPFValidator cpfValidator = new CPFValidator();
        return ResponseEntity.ok().body(usuarioService.validaCPF(cpf));

    }

    @GetMapping("validanome/{nome}")
    public ResponseEntity<Boolean> validanome(@PathVariable String nome) {

        boolean nomeValido = usuarioService.validaNome(nome);
        return ResponseEntity.ok().body(nomeValido);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable int id) {
        return repository.findById(id)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("validemail/{emailUsuario}")
    public ResponseEntity<Boolean> getEmailExistente(@PathVariable String emailUsuario) {
//        Optional<Usuario> usuario = repository.findByEmail(email);
//
//        boolean existeEmail = true;
//        Usuario usuario1 = usuario.;
//
//
//        if (usuario1 == null) {
//            existeEmail = false;
//        }
        System.out.println("entrou" + emailUsuario);
        return null;
    }

    @GetMapping("emailUsuario/{emailUsuario}")
    public ResponseEntity<Optional<Usuario>> getEmail(@PathVariable String emailUsuario) {
        return ResponseEntity.ok(repository.findByEmail(emailUsuario));
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
