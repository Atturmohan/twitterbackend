package com.greatlearning.twitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greatlearning.twitter.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
