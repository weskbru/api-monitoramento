package com.monitoramento.api.service;


import com.monitoramento.api.model.MonitoramentoLog;
import com.monitoramento.api.repository.MonitoramentoLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.monitoramento.api.exceptions.RecursoNaoEncontradoException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MonitoramentoLogService {

    @Autowired
    private MonitoramentoLogRepository monitoramentoLogRepository;

    public MonitoramentoLog salvarLog(MonitoramentoLog log) {
        return monitoramentoLogRepository.save(log);
    }

    public List<MonitoramentoLog> listarTodos() {
        return monitoramentoLogRepository.findAll();
    }

    public MonitoramentoLog buscarPorId(Long id) {
        Optional<MonitoramentoLog> log = monitoramentoLogRepository.findById(id);

        if (log.isEmpty()) {
            throw new RecursoNaoEncontradoException("Log com o ID " + id + " não encontrado");
        }
        return log.get();
    }

    public void deletarPorId(Long id){
        Optional<MonitoramentoLog> log = monitoramentoLogRepository.findById(id);

        if(log.isEmpty()){
            throw new RecursoNaoEncontradoException("Log com o Id " + id + "não encontrado");
        }

        monitoramentoLogRepository.deleteById(id);

    }

    // Metodo para aplicar os filtros
    public List<MonitoramentoLog> buscarDinamica(Integer statusHttp, String nomeServico) {
        return monitoramentoLogRepository.findAll(MonitoramentoLogRepository.filtroDinamico(statusHttp, nomeServico));
    }

}
