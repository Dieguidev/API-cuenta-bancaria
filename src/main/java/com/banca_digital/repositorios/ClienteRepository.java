package com.banca_digital.repositorios;

import com.banca_digital.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query("SELECT c FROM Cliente c where c.nombre LIKE :kw")
    List<Cliente> searchClientes(@Param(value = "kw") String keyword);
}
