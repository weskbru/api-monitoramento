package com.monitoramento.api.service;


import com.monitoramento.api.model.MonitoramentoLog;
import com.monitoramento.api.repository.MonitoramentoLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonitoramentoLogService {


    private final  MonitoramentoLogRepository monitoramentoLogRepository;


    public MonitoramentoLog salvar(MonitoramentoLog log){
        return monitoramentoLogRepository.save(log);
    }



}
