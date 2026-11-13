package br.edu.unifacisa.atendimento.model;

import java.time.LocalDateTime;

public class Secretaria extends Usuario {
    public Secretaria() { }

    public Secretaria(int id, String nome, String email, String senha, LocalDateTime criadoEm) {
        super(id, nome, email, senha, criadoEm);
    }

    @Override
    public Perfil getPerfil() { return Perfil.SECRETARIA; }

    @Override
    public String toString() { return "Secretaria{" + super.toString() + "}"; }
}
