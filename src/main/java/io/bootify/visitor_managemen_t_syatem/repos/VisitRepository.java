package io.bootify.visitor_managemen_t_syatem.repos;

import io.bootify.visitor_managemen_t_syatem.domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VisitRepository extends JpaRepository<Visit, Long> {
}
