/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.UCFINAL.controllers;

import com.projeto.UCFINAL.dto.ContatoDTO;
import com.projeto.UCFINAL.model.Contato;
import com.projeto.UCFINAL.repositories.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ThinkPad
 */
@RestController
@RequestMapping("/api/contatos")
@CrossOrigin(origins = "*")
public class ContatoController {
     @Autowired
    private ContatoRepository contatoRepository;

    @PostMapping
    public ResponseEntity<String> cadastrarContato(@RequestBody ContatoDTO contatoDTO) {
    Contato contato = new Contato();
    contato.setNome(contatoDTO.getNome());
    contato.setEmail(contatoDTO.getEmail());
    contato.setObservacoes(contatoDTO.getObservacoes());

    contatoRepository.save(contato);
    return ResponseEntity.ok("Menságem enviada com sucesso!");
}

}
