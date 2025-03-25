package com.monitoramento.api.repository;

import com.monitoramento.api.model.MonitoramentoLog;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface MonitoramentoLogRepository extends JpaRepository<MonitoramentoLog, Long>, JpaSpecificationExecutor<MonitoramentoLog> {

    static Specification<MonitoramentoLog> filtroDinamico(Integer statusHttp, String nomeServico) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por Status HTTP
            if (statusHttp != null) {
                predicates.add(criteriaBuilder.equal(root.get("statusHttp"), statusHttp));
            }

            // Filtro por Nome do Serviço
            if (nomeServico != null && !nomeServico.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nomeServico")), "%" + nomeServico.toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
