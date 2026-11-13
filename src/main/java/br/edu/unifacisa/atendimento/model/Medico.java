package br.edu.unifacisa.atendimento.model;

import java.time.LocalDateTime;

public class Medico extends Usuario {
    private String crm;
    private String especialidade;

    public Medico() { }

    public Medico(int id, String nome, String email, String senha, LocalDateTime criadoEm,
                  String crm, String especialidade) {
        super(id, nome, email, senha, criadoEm);
        setCrm(crm);
        setEspecialidade(especialidade);
    }

    public String getCrm() { return crm; }

    public void setCrm(String crm) {
        this.crm = validarObrigatorio(crm, 20, "CRM");
    }

    public String getEspecialidade() { return especialidade; }

    public void setEspecialidade(String especialidade) {
        this.especialidade = validarObrigatorio(especialidade, 80, "Especialidade");
    }

    private String validarObrigatorio(String valor, int limite, String campo) {
        if (valor == null || valor.isBlank()) throw new IllegalArgumentException(campo + " é obrigatório.");
        if (valor.trim().length() > limite) throw new IllegalArgumentException(campo + " deve ter até " + limite + " caracteres.");
        return valor.trim();
    }

    @Override
    public Perfil getPerfil() { return Perfil.MEDICO; }

    @Override
    public String toString() {
        return "Medico{" + super.toString() + ", crm='" + crm + "', especialidade='" + especialidade + "'}";
    }
}
