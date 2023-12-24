package com.springSecurity.LoginSystem.repository;

import com.springSecurity.LoginSystem.model.OurUser;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<OurUser,Integer> {
    @Query(value = "select * from our_user where mail=?1",nativeQuery = true)
    Optional<OurUser>findByMail(String mail);
}
