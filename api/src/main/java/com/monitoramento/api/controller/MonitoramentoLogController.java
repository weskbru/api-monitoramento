package com.monitoramento.api.controller;

import com.monitoramento.api.exceptions.RecursoNaoEncontradoException;
import com.monitoramento.api.model.MonitoramentoLog;
import com.monitoramento.api.repository.MonitoramentoLogRepository;
import com.monitoramento.api.service.MonitoramentoLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/monitoramento")
public class MonitoramentoLogController {

    @Autowired
    private MonitoramentoLogService monitoramentoLogService;

    @Autowired
    private MonitoramentoLogRepository monitoramentoLogRepository;

    // Configuração da documentação Swagger
    @Operation(summary = "Criar um novo log de monitoramento",
            description = "Cria um novo log de monitoramento com base nas informações fornecidas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Log criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    // Endpoint para criar um log
    @PostMapping("/salvar")
    public MonitoramentoLog salvar(@RequestBody MonitoramentoLog monitoramentoLog) {
        return monitoramentoLogService.salvarLog(monitoramentoLog);
    }

    @Operation(summary = "Lista todos os logs de monitoramento",
            description = "Recupera uma lista de todos os logs de monitoramento existentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de logs retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum log encontrado")
    })
    @GetMapping("/logs")
    public List<MonitoramentoLog> listaTodos() {
        return monitoramentoLogService.listarTodos();
    }

    @Operation(summary = "Busca um log específico pelo ID",
            description = "Recupera um log de monitoramento específico com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o log com sucesso"),
            @ApiResponse(responseCode = "404", description = "Log não encontrado")
    })
    // Endpoint para busca log pelo ID
    @GetMapping("/id/{id}")
    public ResponseEntity<MonitoramentoLog> buscarPorId(@PathVariable Long id){
        MonitoramentoLog log = monitoramentoLogService.buscarPorId(id);
        return new ResponseEntity<>(log, HttpStatus.OK);
    }


    @Operation(summary = "Deleta um log específico",
            description = "Remove um log de monitoramento do sistema com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Log deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Log não encontrado")
    })
    @DeleteMapping("/logs/{id}")
    public ResponseEntity<String> deletarLog(@PathVariable Long id){
        try {
            monitoramentoLogService.deletarPorId(id);
            return  ResponseEntity.ok().build();
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Consultar com filtros Dinamico",
            description = "Filtra logs de monitoramento com base em parâmetros como status HTTP e nome do serviço.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logs encontrados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<Page<MonitoramentoLog>> getLogs(
            @RequestParam(required = false) Integer statusHttp,
            @RequestParam(required = false) String nomeServico,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // Definir a paginação
        Pageable pageable = PageRequest.of(page, size);

        // Usar o metodo de filtro dinâmico no repositório
        Page<MonitoramentoLog> logs = monitoramentoLogRepository.findAll(
                MonitoramentoLogRepository.filtroDinamico(statusHttp, nomeServico), pageable);

        return new ResponseEntity<>(logs, HttpStatus.OK);
    }


}
