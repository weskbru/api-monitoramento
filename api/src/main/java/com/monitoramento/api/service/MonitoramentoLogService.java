package com.monitoramento.api.service;


import com.monitoramento.api.model.MonitoramentoLog;
import com.monitoramento.api.repository.MonitoramentoLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MonitoramentoLogService {

    @Autowired
    private MonitoramentoLogRepository monitoramentoLogRepository;


    public MonitoramentoLog salvarLog(MonitoramentoLog log){
        return monitoramentoLogRepository.save(log);
    }


    public List<MonitoramentoLog> listarTodos(){
        return monitoramentoLogRepository.findAll();
    }

    public Optional<MonitoramentoLog> buscarPorId(Long id){
        return monitoramentoLogRepository.findById(id);
    }

}
