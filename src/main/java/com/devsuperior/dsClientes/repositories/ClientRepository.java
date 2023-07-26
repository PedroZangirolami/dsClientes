package com.devsuperior.dsClientes.repositories;

import com.devsuperior.dsClientes.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
}
