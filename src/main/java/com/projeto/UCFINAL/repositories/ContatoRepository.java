/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.UCFINAL.repositories;

import com.projeto.UCFINAL.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ThinkPad
 */
@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {
    
}

