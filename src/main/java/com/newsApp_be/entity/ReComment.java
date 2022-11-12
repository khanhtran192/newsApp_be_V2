package com.newsApp_be.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="recomment")
public class ReComment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="content")
	private String content;
	
	@Column(name="createdtime")
	private Date createdTime;
	
	@ManyToOne
	@JoinColumn(name="comment_id",nullable = false)
	private Comment comment;
	
	@ManyToOne
	@JoinColumn(name="User",nullable = false)
	private User userCreator;
}