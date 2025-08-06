package one.digitalinnovation.gof.model.dto;

import one.digitalinnovation.gof.model.Endereco;

public class ClienteDTO {
    private Long id;
    private String nome;
    private Endereco endereco;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}