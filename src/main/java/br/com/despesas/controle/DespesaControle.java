package br.com.despesas.controle;

import br.com.despesas.dto.DespesaDto;
import br.com.despesas.servico.DespesaServico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/despesas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DespesaControle {

    private final DespesaServico despesaServico;

    @PostMapping
    @Operation(summary = "Endpoint responsável pelo cadastro de despesas.")
    @ApiResponse(responseCode = "201", description = " sucesso", content = {
            @Content(mediaType = "application.json", schema = @Schema(implementation = ResponseEntity.class))
    })
    public ResponseEntity<DespesaDto> salvarDespesa(@RequestBody DespesaDto despesaDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(despesaServico.salvarDespesa(despesaDto));
    }

    @GetMapping("/mes")
    @Operation(summary = "Endpoint responsável pela busca de despesas pelo mês trazendo calculada todas as despesas.")
    @ApiResponse(responseCode = "200", description = " sucesso", content = {
            @Content(mediaType = "application.json", schema = @Schema(implementation = ResponseEntity.class))
    })
    public ResponseEntity<List<DespesaDto>> listarDespesasDoMes(
            @RequestParam int mes,
            @RequestParam int ano

    ) {
        return ResponseEntity.status(HttpStatus.OK).body(despesaServico.listarDespesasDoMes(mes, ano));


    }

    @GetMapping("/{id}")
    @Operation(summary = "Endpoint responsável por buscar despesa pelo id.")
    @ApiResponse(responseCode = "200", description = " sucesso", content = {
            @Content(mediaType = "application.json", schema = @Schema(implementation = ResponseEntity.class))
    })
    public ResponseEntity<DespesaDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(despesaServico.buscarPorId(id));
    }

    @PutMapping("{id}")
    @Operation(summary = "Endpoint responsável por atualizar despesa pelo id.")
    @ApiResponse(responseCode = "200", description = " sucesso", content = {
            @Content(mediaType = "application.json", schema = @Schema(implementation = ResponseEntity.class))
    })
    public ResponseEntity<DespesaDto> atualizarDSespesas(@PathVariable Long id, @RequestBody DespesaDto despesaDto) {
        return ResponseEntity.status(HttpStatus.OK).body(despesaServico.atualizarDespesa(id, despesaDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Endpoint responsável por deletar despesa.")
    @ApiResponse(responseCode = "204", description = " sucesso", content = {
            @Content(mediaType = "application.json", schema = @Schema(implementation = ResponseEntity.class))
    })
    public ResponseEntity<Void> excluirDespesa(@PathVariable Long id) {
        despesaServico.excluirDespesa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Endpoint responsável por buscar todas as despesas.")
    @ApiResponse(responseCode = "200", description = " sucesso", content = {
            @Content(mediaType = "application.json", schema = @Schema(implementation = ResponseEntity.class))
    })
    public ResponseEntity<Page<DespesaDto>> listarDespesas(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(despesaServico.listarDespesas(pageable));
    }

    @GetMapping("/categoria")
    @Operation(summary = "Endpoint responsável por buscar de despesas pela categoria.")
    @ApiResponse(responseCode = "200", description = " sucesso", content = {
            @Content(mediaType = "application.json", schema = @Schema(implementation = ResponseEntity.class))
    })
    public ResponseEntity<Page<DespesaDto>> buscarPorCategoria(
            @RequestParam String categoria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("data").descending());
        Page<DespesaDto> despesas = despesaServico.buscarPorCategoria(categoria, pageable);
        return ResponseEntity.ok(despesas);
    }
}