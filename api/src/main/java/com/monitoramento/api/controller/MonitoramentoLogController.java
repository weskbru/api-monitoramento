package com.monitoramento.api.controller;

import com.monitoramento.api.exceptions.RecursoNaoEncontradoException;
import com.monitoramento.api.model.MonitoramentoLog;
import com.monitoramento.api.service.MonitoramentoLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

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


    // Configuração da documentação Swagger
    @Operation(summary = "Criar um novo log de monitoramento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Log criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    // Endpoint para criar um log
    @PostMapping("/salvar")
    public MonitoramentoLog salvar(@RequestBody MonitoramentoLog monitoramentoLog) {
        return monitoramentoLogService.salvarLog(monitoramentoLog);
    }

    @Operation(summary = "Lista todos os logs de monitoramento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de logs retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum log encontrado")
    })
    @GetMapping("/logs")
    public List<MonitoramentoLog> listaTodos() {
        return monitoramentoLogService.listarTodos();
    }

    @Operation(summary = "Busca um log específico pelo ID")
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


    @Operation(summary = "Deleta um log específico")
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


}
