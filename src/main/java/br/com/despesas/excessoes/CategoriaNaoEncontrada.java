package br.com.despesas.excessoes;

public class CategoriaNaoEncontrada extends RuntimeException {
    public CategoriaNaoEncontrada(String mensagem) {
        super(mensagem);
    }

    public CategoriaNaoEncontrada(){
        super("Categoria não encontrada ");
    }
}
