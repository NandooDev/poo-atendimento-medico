package br.edu.unifacisa.atendimento.model;

import java.util.Arrays;
import java.util.Locale;

public enum StatusConsulta {
    AGENDADA("agendada"),
    REALIZADA("realizada"),
    CANCELADA("cancelada");

    private final String valorBanco;

    StatusConsulta(String valorBanco) {
        this.valorBanco = valorBanco;
    }

    public String getValorBanco() {
        return valorBanco;
    }

    public static StatusConsulta deValorBanco(String valor) {
        if (valor == null) {
            throw new IllegalArgumentException("O status não pode ser nulo.");
        }
        String normalizado = valor.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(status -> status.valorBanco.equals(normalizado))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Status inválido: " + valor));
    }
}
