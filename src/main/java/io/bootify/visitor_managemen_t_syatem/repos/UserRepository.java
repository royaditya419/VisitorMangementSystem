package io.bootify.visitor_managemen_t_syatem.repos;

import io.bootify.visitor_managemen_t_syatem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
