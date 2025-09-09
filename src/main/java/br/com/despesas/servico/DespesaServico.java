package br.com.despesas.servico;

import br.com.despesas.dto.DespesaDto;
import br.com.despesas.entidade.Categoria;
import br.com.despesas.entidade.Despesa;
import br.com.despesas.enums.FormaPagamento;
import br.com.despesas.excessoes.CategoriaNaoEncontrada;
import br.com.despesas.excessoes.DespesaNaoEncontrada;
import br.com.despesas.repositorio.CategoriaRepositorio;
import br.com.despesas.repositorio.DespesaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DespesaServico {

    private final DespesaRepositorio despesaRepositorio;
    private final CategoriaRepositorio categoriaRepositorio;

    public DespesaDto salvarDespesa(DespesaDto dto) {
        Categoria categoria = categoriaRepositorio.findByNome(dto.categoria())
                .orElseGet(() -> {
                    Categoria nova = new Categoria();
                    nova.setNome(dto.categoria());
                    return categoriaRepositorio.save(nova);
                });
        Despesa despesa = new Despesa();
        despesa.setDescricao(dto.descricao());
        despesa.setValorTotal(dto.valorTotal());
        despesa.setData(dto.data());
        despesa.setFormaPagamento(FormaPagamento.valueOf(dto.formaPagamento()));
        despesa.setParcelas(dto.parcelas());
        despesa.setCategoria(categoria);

        Despesa salva = despesaRepositorio.save(despesa);
        return new DespesaDto(salva.getId(), salva.getDescricao(), salva.getValorTotal(),
                salva.getData(), salva.getFormaPagamento().name(),
                salva.getParcelas(), salva.getCategoria().getNome());
    }

    public List<DespesaDto> listarDespesasDoMes(int mes, int ano) {
        LocalDate inicio = LocalDate.of(ano, mes, 1);
        LocalDate fim = inicio.withDayOfMonth(inicio.lengthOfMonth());
        return despesaRepositorio.findByDataBetween(inicio, fim)
                .stream()
                .map(d -> new DespesaDto(d.getId(), d.getDescricao(), d.getValorTotal(),
                        d.getData(), d.getFormaPagamento().name(),
                        d.getParcelas(), d.getCategoria().getNome()))
                .toList();
    }


    public DespesaDto buscarPorId(Long id) {
        Despesa despesa = despesaRepositorio.findById(id)
                .orElseThrow(() -> new DespesaNaoEncontrada("Despesa não encontrada"));
        return new DespesaDto(despesa.getId(), despesa.getDescricao(), despesa.getValorTotal(),
                despesa.getData(), despesa.getFormaPagamento().name(),
                despesa.getParcelas(), despesa.getCategoria().getNome());
    }

    public DespesaDto atualizarDespesa(Long id, DespesaDto dto) {
        Despesa despesa = despesaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));

        Categoria categoria = categoriaRepositorio.findByNome(dto.categoria())
                .orElseThrow(() -> new CategoriaNaoEncontrada("Categoria não encontrada"));

        despesa.setDescricao(dto.descricao());
        despesa.setValorTotal(dto.valorTotal());
        despesa.setData(dto.data());
        despesa.setFormaPagamento(FormaPagamento.valueOf(dto.formaPagamento()));
        despesa.setParcelas(dto.parcelas());
        despesa.setCategoria(categoria);

        Despesa atualizada = despesaRepositorio.save(despesa);

        return new DespesaDto(atualizada.getId(), atualizada.getDescricao(), atualizada.getValorTotal(),
                atualizada.getData(), atualizada.getFormaPagamento().name(),
                atualizada.getParcelas(), atualizada.getCategoria().getNome());
    }


    public void excluirDespesa(Long id) {
        if (!despesaRepositorio.existsById(id)) {
            throw new DespesaNaoEncontrada("Despesa não encontrada");
        }
        despesaRepositorio.deleteById(id);
    }


        public Page<DespesaDto> listarDespesas(Pageable pageable) {
            return despesaRepositorio.findAll(pageable)
                    .map(despesa -> new DespesaDto(
                            despesa.getId(),
                            despesa.getDescricao(),
                            despesa.getValorTotal(),
                            despesa.getData(),
                            despesa.getFormaPagamento().name(),
                            despesa.getParcelas(),
                            despesa.getCategoria().getNome()
                    ));
        }

    public Page<DespesaDto> buscarPorCategoria(String categoria, Pageable pageable) {
        return despesaRepositorio.findByCategoriaNomeContainingIgnoreCase(categoria, pageable)
                .map(despesa -> new DespesaDto(
                        despesa.getId(),
                        despesa.getDescricao(),
                        despesa.getValorTotal(),
                        despesa.getData(),
                        despesa.getFormaPagamento().name(),
                        despesa.getParcelas(),
                        despesa.getCategoria().getNome()
                ));
    }



}