package com.monitoramento.api.repository;

import com.monitoramento.api.model.MonitoramentoLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoramentoLogRepository extends JpaRepository<MonitoramentoLog, Long> {
}
