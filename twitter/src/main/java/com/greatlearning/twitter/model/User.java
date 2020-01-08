package com.greatlearning.twitter.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greatlearning.twitter.model.Tweet;

import lombok.Data;

@Entity
@Table(name="user")
@Data
public class User implements Comparable<User>,Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	private String name;

	private Integer age;

	private String profilePicURL;

	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Tweet> tweets;
	
	 @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.EAGER)
	 @JoinTable(name="users_followers",joinColumns = @JoinColumn(name = "followers"),inverseJoinColumns = @JoinColumn(name = "following"))
	 private Set<User> followers = new HashSet<>();


	 @ManyToMany(mappedBy = "followers", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.EAGER)
	 private Set<User> following = new HashSet<>();
	 
	@JsonIgnore
	public Set<User> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}

	@JsonIgnore
	public Set<User> getFollowing() {
		return following;
	}

	public void setFollowing(Set<User> following) {
		this.following = following;
	}

	public Long getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getProfilePicURL() {
		return profilePicURL;
	}

	public void setProfilePicURL(String profilePicURL) {
		this.profilePicURL = profilePicURL;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return userId == other.userId;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", age=" + age + ", profilePicURL=" + profilePicURL + "]";
	}

	@Override
	public int compareTo(User usr) {
		return (int)(this.userId-usr.userId);
	}

	public void addFollower(User follower) {
		follower.getFollowing().add(this);
		this.getFollowers().add(follower);
	}
	
	public void removeFollower() {
		for (User user : followers) {
			followers.remove(user);
		}
	}

	public Set<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(Set<Tweet> tweets) {
		this.tweets = tweets;
	}
	
	public void addTweet(Tweet tweet) {
		this.getTweets().add(tweet);
	}
	
	
}
