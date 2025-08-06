package one.digitalinnovation.gof.service.impl;

import one.digitalinnovation.gof.model.Cliente;
import one.digitalinnovation.gof.model.ClienteRepository;
import one.digitalinnovation.gof.model.Endereco;
import one.digitalinnovation.gof.model.EnderecoRepository;
import one.digitalinnovation.gof.service.ClienteService;
import one.digitalinnovation.gof.service.ViaCepService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;

    @Override
    public Page<Cliente> buscarTodos(Pageable pageable) {
        logger.info("Buscando todos os clientes paginados");
        return clienteRepository.findAll(pageable);
    }

    @Cacheable(value = "clientes", key = "#id")
    @Override
    public Cliente buscarPorId(Long id) {
        logger.info("Buscando cliente por ID: {}", id);
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    @Override
    public Page<Cliente> buscarPorNome(String nome, Pageable pageable) {
        logger.info("Buscando clientes por nome: {}", nome);
        return clienteRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    @CacheEvict(value = "clientes", allEntries = true)
    @Override
    public void inserir(Cliente cliente) {
        logger.info("Inserindo novo cliente: {}", cliente.getNome());
        salvarClienteComCep(cliente);
    }

    @CacheEvict(value = "clientes", allEntries = true)
    @Override
    public void atualizar(Long id, Cliente cliente) {
        logger.info("Atualizando cliente ID: {}", id);
        if (clienteRepository.existsById(id)) {
            salvarClienteComCep(cliente);
        }
    }

    @CacheEvict(value = "clientes", allEntries = true)
    @Override
    public void deletar(Long id) {
        logger.info("Deletando cliente ID: {}", id);
        clienteRepository.deleteById(id);
    }

    private void salvarClienteComCep(Cliente cliente) {
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }
}