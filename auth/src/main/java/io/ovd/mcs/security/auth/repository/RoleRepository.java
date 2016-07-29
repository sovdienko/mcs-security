package io.ovd.mcs.security.auth.repository;

import io.ovd.mcs.security.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sergey.Ovdienko on 29.07.2016.
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>
 {
    Role findByName(String name);
}
