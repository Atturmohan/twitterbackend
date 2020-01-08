package com.greatlearning.twitter.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greatlearning.twitter.model.Tweet;
import com.greatlearning.twitter.model.User;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
	@Query(value = "SELECT t FROM Tweet t WHERE t.user IN :user")
	List<Tweet> findAllTweetsByUserId(@Param("user") User user);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM Tweet t WHERE t.user =:user")
	void deleteAllTweetByUserId(@Param("user") User user);
}
