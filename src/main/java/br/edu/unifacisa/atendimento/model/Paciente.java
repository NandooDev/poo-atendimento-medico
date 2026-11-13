package br.edu.unifacisa.atendimento.model;

import java.time.LocalDateTime;

public class Paciente extends Usuario {
    private String cpf;
    private String telefone;

    public Paciente() { }

    public Paciente(int id, String nome, String email, String senha, LocalDateTime criadoEm,
                    String cpf, String telefone) {
        super(id, nome, email, senha, criadoEm);
        setCpf(cpf);
        setTelefone(telefone);
    }

    public String getCpf() { return cpf; }

    public void setCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) throw new IllegalArgumentException("O CPF deve conter 11 números.");
        this.cpf = cpf;
    }

    public String getTelefone() { return telefone; }

    public void setTelefone(String telefone) {
        if (telefone == null || telefone.isBlank()) throw new IllegalArgumentException("O telefone é obrigatório.");
        if (telefone.trim().length() > 20) throw new IllegalArgumentException("O telefone deve ter até 20 caracteres.");
        this.telefone = telefone.trim();
    }

    @Override
    public Perfil getPerfil() { return Perfil.PACIENTE; }

    @Override
    public String toString() {
        return "Paciente{" + super.toString() + ", cpf='" + cpf + "', telefone='" + telefone + "'}";
    }
}
