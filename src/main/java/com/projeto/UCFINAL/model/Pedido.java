/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.UCFINAL.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ThinkPad
 */
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nome_comprador")
    private String nomeComprador;
    
    private String endereco;
    
    private LocalDate date;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<PedidoProduto> pedidoProduto;

    public List<PedidoProduto> getPedidoProduto() {
        return pedidoProduto;
    }

    public void setPedidoProduto(List<PedidoProduto> pedidoProduto) {
        this.pedidoProduto = pedidoProduto;
    }
    
    
    public Long getId(){
        return id;
    }
    public void setId(){
        this.id = id;
    }
    public String getNomeComprador(){
        return nomeComprador;    
    }
    public void setNomeComprador(String nomeComprador){
        this.nomeComprador = nomeComprador;
    }
    
     public String getEndereco(){
        return endereco;    
    }
    public void setEndereco(String endereco){
        this.endereco = endereco;
    }
     public LocalDate getDate(){
        return date;    
    
    }
    public void setDate(LocalDate date){
        this.date = date;
    }

   
    
    
}
