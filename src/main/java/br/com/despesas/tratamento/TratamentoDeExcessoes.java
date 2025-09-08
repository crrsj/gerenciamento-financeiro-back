package br.com.despesas.tratamento;

import br.com.despesas.excessoes.CategoriaNaoEncontrada;
import br.com.despesas.excessoes.DespesaNaoEncontrada;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TratamentoDeExcessoes {

    @ExceptionHandler(DespesaNaoEncontrada.class)
    public ResponseEntity<MensagemDeErro>despesaNaoEncontrada(){
        var msg = new MensagemDeErro(HttpStatus.BAD_REQUEST,"Despesa não encontrada");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
    }
    @ExceptionHandler(CategoriaNaoEncontrada.class)
    public ResponseEntity<MensagemDeErro>categoriaNaoEncontrada(){
        var msg = new MensagemDeErro(HttpStatus.BAD_REQUEST,"Categoria não encontrada");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
    }

}
