package br.com.despesas.dto;

import br.com.despesas.enums.FormaPagamento;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DespesaDto(
        Long id,
        String descricao,
        BigDecimal valorTotal,
        LocalDate data,
        String formaPagamento,
        int parcelas,
        String categoria


) {
}
