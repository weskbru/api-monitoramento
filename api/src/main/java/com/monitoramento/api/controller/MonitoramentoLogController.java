package com.monitoramento.api.controller;

import com.monitoramento.api.model.MonitoramentoLog;
import com.monitoramento.api.service.MonitoramentoLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

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
            @ApiResponse(responseCode = "400", description = "Nenhum log encontrado")
    })
    @GetMapping("/logs")
    public List<MonitoramentoLog> listaTodos() {
        return monitoramentoLogService.listarTodos();
    }


}
