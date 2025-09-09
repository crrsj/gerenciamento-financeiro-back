package br.com.despesas.entidade;

import br.com.despesas.enums.FormaPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "despesas")
@Data
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private BigDecimal valorTotal;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy")
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;
    private int parcelas;
    @ManyToOne
    @JoinColumn(name="categoria_id")
    private Categoria categoria;

}
