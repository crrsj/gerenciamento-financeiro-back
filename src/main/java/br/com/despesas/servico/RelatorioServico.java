package br.com.despesas.servico;

import br.com.despesas.entidade.Despesa;
import br.com.despesas.repositorio.DespesaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;


import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RelatorioServico {

    private final DespesaRepositorio despesaRepository;

    public ByteArrayInputStream gerarRelatorioMensal(int mes, int ano) {
        List<Despesa> despesas = despesaRepository.findAll()
                .stream()
                .filter(d -> d.getData().getMonthValue() == mes && d.getData().getYear() == ano)
                .toList();

        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Título
            Font titulo = new Font(Font.HELVETICA, 16, Font.BOLD);
            Paragraph p = new Paragraph("Relatório de Despesas - " + mes + "/" + ano, titulo);
            p.setAlignment(Element.ALIGN_CENTER);
            p.setSpacingAfter(20);
            document.add(p);

            // Tabela
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 4, 3, 2, 3, 3, 2});

            // Cabeçalho
            Stream.of("ID", "Descrição", "Categoria", "Valor", "Data", "Forma Pagamento", "Parcelas")
                    .forEach(headerTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(Color.LIGHT_GRAY);
                        header.setPhrase(new Phrase(headerTitle));
                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(header);
                    });

            // Linhas
            for (Despesa d : despesas) {
                table.addCell(String.valueOf(d.getId()));
                table.addCell(d.getDescricao());
                table.addCell(d.getCategoria().getNome());
                table.addCell(String.format("R$ %.2f", d.getValorTotal()));
                table.addCell(d.getData().toString());
                table.addCell(d.getFormaPagamento().name());
                table.addCell(String.valueOf(d.getParcelas()));
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
