package com.monitoramento.api.service;


import com.monitoramento.api.model.MonitoramentoLog;
import com.monitoramento.api.repository.MonitoramentoLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonitoramentoLogService {

    @Autowired
    private MonitoramentoLogRepository monitoramentoLogRepository;


    public MonitoramentoLog salvarLog(MonitoramentoLog log){
        return monitoramentoLogRepository.save(log);
    }



}
