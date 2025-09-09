package br.com.despesas.repositorio;

import br.com.despesas.entidade.Despesa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DespesaRepositorio extends JpaRepository<Despesa,Long> {
    List<Despesa> findByDataBetween(LocalDate inicio, LocalDate fim);
    Page<Despesa> findByCategoriaNomeContainingIgnoreCase(String categoria, Pageable pageable);

}
