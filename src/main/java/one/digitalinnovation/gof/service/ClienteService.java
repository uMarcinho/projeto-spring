package one.digitalinnovation.gof.service;

import one.digitalinnovation.gof.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteService {
    Page<Cliente> buscarTodos(Pageable pageable);
    Cliente buscarPorId(Long id);
    Page<Cliente> buscarPorNome(String nome, Pageable pageable);
    void inserir(Cliente cliente);
    void atualizar(Long id, Cliente cliente);
    void deletar(Long id);
}