package com.greatlearning.twitter.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.greatlearning.twitter.exception.UserNotFoundException;
import com.greatlearning.twitter.model.Tweet;
import com.greatlearning.twitter.model.User;

public interface UserService {
	void createUser(User user);

	void addFollower(Long userId, Long followerId);

	void deleteUser(Long userId);
	
	void saveTweet(Long userId,Tweet tweet);

	List<Tweet> findAllTweetsByUser(Long userId);
	
	Optional<User> findUserById(Long userId);

	void deleteAllTweetByUserId(Long userId);

	User updateUser(User user, Long userId) throws UserNotFoundException;

	List<User> getAllUsers();

	Set<User> getAllFollowers(Long userId);

	Set<User> getAllFollowing(Long userId);
}
