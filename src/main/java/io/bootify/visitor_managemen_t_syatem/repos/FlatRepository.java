package io.bootify.visitor_managemen_t_syatem.repos;

import io.bootify.visitor_managemen_t_syatem.domain.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlatRepository extends JpaRepository<Flat, Long> {
     Flat findByNumber(String number);
}
