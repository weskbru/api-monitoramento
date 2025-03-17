package com.monitoramento.api.controller;

import com.monitoramento.api.model.MonitoramentoLog;
import com.monitoramento.api.service.MonitoramentoLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/monitoramento")
public class MonitoramentoLogController {

    @Autowired
    private MonitoramentoLogService monitoramentoLogService;

    @PostMapping("/salvar")
    @ResponseStatus(HttpStatus.CREATED) // 201 Created quando o log for salvo
    public MonitoramentoLog salvar(@RequestBody MonitoramentoLog monitoramentoLog) {
        // Salva o log diretamente e retorna o objeto salvo
        return monitoramentoLogService.salvarLog(monitoramentoLog);
    }

    @GetMapping("/logs")
    public List<MonitoramentoLog> listaTodos() {
        return monitoramentoLogService.listarTodos();
    }


}
