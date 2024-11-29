/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.UCFINAL.controllers;


import com.projeto.UCFINAL.dto.PedidoDTO;
import com.projeto.UCFINAL.model.Pedido;
import com.projeto.UCFINAL.model.PedidoProduto;
import com.projeto.UCFINAL.repositories.PedidoRepository;
import com.projeto.UCFINAL.repositories.ProdutosRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
/** 
 *
 * @author ThinkPad
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ProdutosRepository produtosRepository;

    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }
    
    @PostMapping
    public ResponseEntity<String> registrarPedido(@RequestBody PedidoDTO pedidoDTO) {
        System.out.println("PedidoDTO recebido: " + pedidoDTO);
        Pedido pedido = new Pedido();
        pedido.setDate(pedidoDTO.getDate());
        pedido.setNomeComprador(pedidoDTO.getNomeComprador());
        pedido.setEndereco(pedidoDTO.getEndereco());

        // Inicializa a lista se estiver nula
        if (pedido.getPedidoProduto() == null) {
            pedido.setPedidoProduto(new ArrayList<>());
        }

        List<PedidoProduto> itensPedido = pedidoDTO.getProdutos().stream().map(prod -> {
            PedidoProduto pedidoProduto = new PedidoProduto();
            pedidoProduto.setProduto(produtosRepository.findById(prod.getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado")));
            pedidoProduto.setQuantidade(prod.getQuantidade());
            pedidoProduto.setPedido(pedido); // Associa o PedidoProduto ao Pedido
            return pedidoProduto;
        }).collect(Collectors.toList());

        // Adiciona os itens na lista de PedidoProduto
        pedido.getPedidoProduto().addAll(itensPedido);

        pedidoRepository.save(pedido); // Salva o pedido no banco

        return ResponseEntity.ok("Pedido registrado com sucesso");
    }

}

