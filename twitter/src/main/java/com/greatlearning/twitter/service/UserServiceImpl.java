package com.greatlearning.twitter.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greatlearning.twitter.exception.UserNotFoundException;
import com.greatlearning.twitter.model.Tweet;
import com.greatlearning.twitter.model.User;
import com.greatlearning.twitter.repository.TweetRepository;
import com.greatlearning.twitter.repository.UserRepository;
import com.greatlearning.twitter.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TweetRepository tweetRepository;

	@Override
	public void createUser(User user) {
		System.out.println("In Service class");
		userRepository.save(user);
	}

	@Override
	public void addFollower(Long userId, Long followerId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		Optional<User> optionalFollower = userRepository.findById(followerId);
		if (optionalUser.isPresent() && optionalFollower.isPresent()) {

			User user = optionalUser.get();
			User follower = optionalFollower.get();

			user.addFollower(follower);
		}

	}

	@Override
	@JsonIgnore
	public void deleteUser(Long userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.removeFollower();
			userRepository.delete(user);
		}
	}

	@Override
	public void saveTweet(Long userId, Tweet tweet) {
		Optional<User> userOptional = this.userRepository.findById(userId);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			tweet.setUser(user);
			this.tweetRepository.save(tweet);
		}
	}

	@Override
	public List<Tweet> findAllTweetsByUser(Long userId) {
		Optional<User> optionalUser = this.userRepository.findById(userId);
		User user = optionalUser.get();
		return this.tweetRepository.findAllTweetsByUserId(user);
	}

	@Override
	public Optional<User> findUserById(Long userId) {
		return this.userRepository.findById(userId);
	}

	@Override
	public void deleteAllTweetByUserId(Long userId) {
		Optional<User> optionalUser = this.userRepository.findById(userId);
		User user = optionalUser.get();
		this.tweetRepository.deleteAllTweetByUserId(user);
	}

	@Override
	public User updateUser(User user, Long userId) throws UserNotFoundException {
		Optional<User> optionalUser = this.userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User updatedUser = optionalUser.get();
			if (user.getUserId() != null && userId != user.getUserId()) {
				throw new IllegalArgumentException("Cannot change the userId");
			}
			if (user.getAge() != null) {
				updatedUser.setAge(user.getAge());
			}
			if (user.getName() != null) {
				updatedUser.setName(user.getName());
			}
			if (user.getProfilePicURL() != null) {
				updatedUser.setProfilePicURL(user.getProfilePicURL());
			}
			this.userRepository.save(updatedUser);
			Optional<User> optionalUpdatedUser = this.userRepository.findById(userId);
			User usr = optionalUpdatedUser.get();
			return usr;
		} else {
			throw new UserNotFoundException("User Not Found");
		}
	}

	@Override
	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}

	@Override
	public Set<User> getAllFollowers(Long userId) {
		Optional<User> optionalUser = this.userRepository.findById(userId);
		Set<User> followersList = new HashSet<User>();
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			followersList.addAll(user.getFollowers());

		}
		if (!followersList.isEmpty()) {
			return followersList;
		} else {
			return null;
		}

	}

	@Override
	public Set<User> getAllFollowing(Long userId) {
		Optional<User> optionalUser = this.userRepository.findById(userId);
		Set<User> followingList = new HashSet<User>();
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			followingList.addAll(user.getFollowing());
		}
		if (!followingList.isEmpty()) {
			return followingList;
		} else {
			return null;
		}
	}
}
