package io.bootify.visitor_managemen_t_syatem.repos;

import io.bootify.visitor_managemen_t_syatem.domain.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    boolean existsByPhone(Long phone);

    boolean existsByIDNumberIgnoreCase(String iDNumber);

}
