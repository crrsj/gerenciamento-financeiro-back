package br.com.despesas.excessoes;

public class DespesaNaoEncontrada extends RuntimeException {
   public DespesaNaoEncontrada(String mensagem) {
       super(mensagem);
   }
    public DespesaNaoEncontrada(){
       super("Despesa não encontrada ");
    }
}
