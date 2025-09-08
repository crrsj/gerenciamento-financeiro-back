package br.com.despesas.controle;


import br.com.despesas.servico.RelatorioServico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/relatorios")
@RequiredArgsConstructor
public class RelatorioControle {

    private final RelatorioServico relatorioService;

    @GetMapping("/mensal")
    @Operation(summary = "Endpoint responsável pela emissão de relatórios.")
    @ApiResponse(responseCode = "201",description = " sucesso",content = {
            @Content(mediaType = "application.json",schema = @Schema(implementation = ResponseEntity.class))
    })
    public ResponseEntity<byte[]> gerarRelatorioMensal(
            @RequestParam int mes,
            @RequestParam int ano) {

        ByteArrayInputStream bis = relatorioService.gerarRelatorioMensal(mes, ano);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=relatorio_despesas_" + mes + "_" + ano + ".pdf");

        try {
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(bis.readAllBytes());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
