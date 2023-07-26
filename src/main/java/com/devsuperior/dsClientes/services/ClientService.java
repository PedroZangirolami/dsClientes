package com.devsuperior.dsClientes.services;

import com.devsuperior.dsClientes.dto.ClientDTO;
import com.devsuperior.dsClientes.entities.Client;
import com.devsuperior.dsClientes.repositories.ClientRepository;
import com.devsuperior.dsClientes.services.exceptions.ResourceNotFoundExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    ClientRepository repository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable){
        Page<Client> result = repository.findAll(pageable);
        return result.map(x -> new ClientDTO(x));
    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id){
        Client result = repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundExeption("Recurso não encontrado"));
        return new ClientDTO(result);
    }

    @Transactional
    public ClientDTO insert(ClientDTO dto){
        Client client = new Client();
        copyDtoToEntity(dto,client);
        client = repository.save(client);
        return new ClientDTO(client);
    }

    @Transactional()
    public ClientDTO update(Long id, ClientDTO dto){
        Client client = repository.getReferenceById(id);
        copyDtoToEntity(dto,client);
        client = repository.save(client);
        return new ClientDTO(client);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundExeption("Recurso não encontrado");
        }

        repository.deleteById(id);
    }

    private void copyDtoToEntity(ClientDTO dto, Client entity) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());
    }
}
