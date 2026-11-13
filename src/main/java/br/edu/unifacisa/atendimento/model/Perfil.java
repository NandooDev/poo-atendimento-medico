package br.edu.unifacisa.atendimento.model;

import java.util.Arrays;
import java.util.Locale;

public enum Perfil {
    MEDICO("medico"),
    SECRETARIA("secretaria"),
    PACIENTE("paciente");

    private final String valorBanco;

    Perfil(String valorBanco) {
        this.valorBanco = valorBanco;
    }

    public String getValorBanco() {
        return valorBanco;
    }

    public static Perfil deValorBanco(String valor) {
        if (valor == null) {
            throw new IllegalArgumentException("O perfil não pode ser nulo.");
        }
        String normalizado = valor.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(perfil -> perfil.valorBanco.equals(normalizado))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Perfil inválido: " + valor));
    }
}
