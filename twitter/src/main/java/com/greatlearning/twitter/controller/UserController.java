package com.greatlearning.twitter.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.greatlearning.twitter.exception.UserNotFoundException;
import com.greatlearning.twitter.model.Tweet;
import com.greatlearning.twitter.model.User;
import com.greatlearning.twitter.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping(value="/user",consumes = { APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.CREATED)
	public void createUser(@RequestBody User user) {
		userService.createUser(user);
	}
	
	//service if conditions needed.
	@PutMapping(value="/{id}/user/update")
	@ResponseStatus(HttpStatus.CREATED)
	public User updateUser(@RequestBody User user,@PathVariable(value = "id") Long userId) throws UserNotFoundException {
		return this.userService.updateUser(user,userId);
	}
 	
	@GetMapping(value = "/")
	@ResponseStatus(HttpStatus.OK)
	public List<User> getAllUsers(){
		return this.userService.getAllUsers();
	}

	//infinite loop
	@PutMapping(value="/{followerId}/follow/{followingId}")
	@ResponseStatus(HttpStatus.CREATED)
	public void addFollower(@PathVariable(value = "followingId")Long user,@PathVariable(value="followerId") Long follower) {
		userService.addFollower(user,follower);
	}

	//test again both users are getting deleted.
	@DeleteMapping(value = "/delete/{id}/user")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable(value = "id")Long userId) {
		this.userService.deleteUser(userId);
	}
	
	@PostMapping(value = "/{id}/tweet")
	@ResponseStatus(HttpStatus.CREATED)
	public void addTweet(@PathVariable(value = "id")Long userId,@RequestBody Tweet tweet) {
		this.userService.saveTweet(userId,tweet);
	}

	@GetMapping("/{id}/tweets")
	@ResponseStatus(HttpStatus.OK)
	public List<Tweet> findAllTweetsByUser(@PathVariable("id")Long userId) {
		return this.userService.findAllTweetsByUser(userId);
	}

	@DeleteMapping(value = "/{id}/delete/tweets/by/user")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAllTweetByUserId(@PathVariable(value = "id")Long userId) {
		this.userService.deleteAllTweetByUserId(userId);
	}
	
	@GetMapping(value = "/{id}/followers")
	@ResponseStatus(HttpStatus.OK)
	public Set<User> getAllFollowers(@PathVariable(value = "id") Long userId){
		return this.userService.getAllFollowers(userId);
	}
	
	@GetMapping(value = "/{id}/following")
	@ResponseStatus(HttpStatus.OK)
	public Set<User> getAllFollowing(@PathVariable(value = "id") Long userId){
		return this.userService.getAllFollowing(userId);
	}
}
