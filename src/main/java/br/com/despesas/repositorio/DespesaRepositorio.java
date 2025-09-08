package br.com.despesas.repositorio;

import br.com.despesas.entidade.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DespesaRepositorio extends JpaRepository<Despesa,Long> {
    List<Despesa> findByDataBetween(LocalDate inicio, LocalDate fim);
}
