package com.sigma.repository;

import com.sigma.model.Role;
import com.sigma.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public List<User> findUsersByRole(Role role);
}
