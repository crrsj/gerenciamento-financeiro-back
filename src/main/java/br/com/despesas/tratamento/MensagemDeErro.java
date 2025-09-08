package br.com.despesas.tratamento;

import org.springframework.http.HttpStatus;

public record MensagemDeErro(HttpStatus starus,String mensagem) {
}
