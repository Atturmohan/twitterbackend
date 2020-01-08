package com.greatlearning.twitter.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.greatlearning.twitter.model.User;

import lombok.Data;

@Entity
@Table(name="tweet")
@Data
public class Tweet {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tweetId;

	private String message;

	private String tweetMediaURL;

	private LocalDate createdDate;
	
	@ManyToOne
	@JoinColumn(name="masterUserId",nullable = false)
	private User user;
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public Long getTweetId() {
		return tweetId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public String getTweetMediaURL() {
		return tweetMediaURL;
	}

	public void setTweetMediaURL(String tweetMediaURL) {
		this.tweetMediaURL = tweetMediaURL;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tweetId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tweet other = (Tweet) obj;
		return tweetId == other.tweetId;
	}

	@Override
	public String toString() {
		return "Tweet [tweetId=" + tweetId + ", message=" + message + ", createdDate=" + createdDate + "]";
	}

}
