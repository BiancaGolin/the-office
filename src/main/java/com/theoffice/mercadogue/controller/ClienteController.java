package com.theoffice.mercadogue.controller;

import com.theoffice.mercadogue.model.Cliente;
import com.theoffice.mercadogue.model.ClienteLogin;
import com.theoffice.mercadogue.repository.ClienteRepository;
import com.theoffice.mercadogue.service.ClienteService;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository repositoryCliente;

    @GetMapping
    public ResponseEntity<List<Cliente>> getAll() {

        return ResponseEntity.ok(repositoryCliente.findAll());
    }

    @GetMapping("consultacep/{cep}")
    public ResponseEntity<Boolean> consultaCep(@PathVariable String cep) {

        boolean existeCEP = clienteService.consultaCep(cep);

        return ResponseEntity.ok().body(existeCEP);
    }

    @GetMapping("consultacpf/{cpf}")
    public ResponseEntity<Boolean> consultaCPF(@CPF @PathVariable String cpf) {

        CPFValidator cpfValidator = new CPFValidator();
        return ResponseEntity.ok().body(clienteService.validaCPF(cpf));

    }

    @GetMapping("id/{id}")
    public ResponseEntity<Cliente> getById(@PathVariable int id){
        return repositoryCliente.findById(id)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("validanome/{nome}")
    public ResponseEntity<Boolean> validaNome(@PathVariable String nome) {

        boolean nomeValido = clienteService.validaNome(nome);
        return ResponseEntity.ok().body(nomeValido);
    }

    @GetMapping("validemail/{emailCliente}")
    public ResponseEntity<Boolean> getEmailExistente(@PathVariable String emailCliente) {
//        Optional<Usuario> usuario = repository.findByEmail(email);
//
//        boolean existeEmail = true;
//        Usuario usuario1 = usuario.;
//
//
//        if (usuario1 == null) {
//            existeEmail = false;
//        }
        System.out.println("entrou" + emailCliente);
        return null;
    }

    @GetMapping("emailCliente/{emailCliente}")
    public ResponseEntity<Optional<Cliente>> getEmailCliente(@PathVariable String emailCliente) {
        return ResponseEntity.ok(repositoryCliente.findByEmailCliente(emailCliente));
    }

    @GetMapping("nomeCliente/{nomeCliente}")
    public ResponseEntity<List<Cliente>> getByNomeCliente(@PathVariable String nomeCliente) {
        return ResponseEntity.ok(repositoryCliente.findAllByNomeClienteContainingIgnoreCase(nomeCliente));
    }

    @PutMapping("/alterar")
    public ResponseEntity<Cliente> put(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(repositoryCliente.save(clienteService.alterarCliente(cliente)));
    }

    @PostMapping("/logar")
    public ResponseEntity<ClienteLogin> authentication(@RequestBody Optional<ClienteLogin> user) {
        System.out.println("deu ruim ou deu bom?");
        return clienteService.logarCliente(user).map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clienteService.cadastrarCliente(cliente));
    }
}
