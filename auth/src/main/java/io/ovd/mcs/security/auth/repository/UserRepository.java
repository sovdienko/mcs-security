package io.ovd.mcs.security.auth.repository;

import io.ovd.mcs.security.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sergey.Ovdienko on 28.07.2016.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

}
