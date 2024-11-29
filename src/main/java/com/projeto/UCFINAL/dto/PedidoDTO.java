/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.UCFINAL.dto;

/**
 *
 * @author ThinkPad
 */
import java.time.LocalDate;
import java.util.List;

public class PedidoDTO {
    private String nomeComprador; // Nome do cliente
    private String endereco; // Endereço do cliente
    private LocalDate date;
    private List<ProdutoPedidoDTO> produtos; // Lista de produtos do pedido

    // Getters e Setters
    public String getNomeComprador() {
        return nomeComprador;
    }

    public void setNomeComprador(String nomeComprador) {
        this.nomeComprador = nomeComprador;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<ProdutoPedidoDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoPedidoDTO> produtos) {
        this.produtos = produtos;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
}
