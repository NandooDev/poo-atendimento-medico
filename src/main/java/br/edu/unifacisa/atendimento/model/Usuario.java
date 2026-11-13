package br.edu.unifacisa.atendimento.model;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.regex.Pattern;

public abstract class Usuario {
    private static final Pattern EMAIL_VALIDO = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    private int id;
    private String nome;
    private String email;
    private String senha;
    private LocalDateTime criadoEm;

    protected Usuario() {
    }

    protected Usuario(int id, String nome, String email, String senha, LocalDateTime criadoEm) {
        setId(id);
        setNome(nome);
        setEmail(email);
        setSenha(senha);
        setCriadoEm(criadoEm);
    }

    public abstract Perfil getPerfil();

    public int getId() { return id; }

    public void setId(int id) {
        if (id < 0) throw new IllegalArgumentException("O id não pode ser negativo.");
        this.id = id;
    }

    public String getNome() { return nome; }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("O nome é obrigatório.");
        if (nome.trim().length() > 120) throw new IllegalArgumentException("O nome deve ter até 120 caracteres.");
        this.nome = nome.trim();
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) throw new IllegalArgumentException("O e-mail é obrigatório.");
        String normalizado = email.trim().toLowerCase(Locale.ROOT);
        if (normalizado.length() > 160 || !EMAIL_VALIDO.matcher(normalizado).matches()) {
            throw new IllegalArgumentException("Informe um e-mail válido com até 160 caracteres.");
        }
        this.email = normalizado;
    }

    public String getSenha() { return senha; }

    public void setSenha(String senha) {
        if (senha == null || senha.length() < 6) throw new IllegalArgumentException("A senha deve ter no mínimo 6 caracteres.");
        if (senha.length() > 255) throw new IllegalArgumentException("A senha deve ter até 255 caracteres.");
        this.senha = senha;
    }

    public LocalDateTime getCriadoEm() { return criadoEm; }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    @Override
    public String toString() {
        return "id=" + id + ", nome='" + nome + "', email='" + email + "', perfil=" + getPerfil();
    }
}
