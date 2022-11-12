package com.newsApp_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="news")
public class News implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@Column(name="time")
	private Date time;
	
	@Column(name="image")
	private String image;

	@Column(name="content")
	@Type(type ="text")
	private String content;

	@Column(name="slug")
	private String slug;

	@Column(name="author_id")
	private String authorId;

	@Column(name="created_at")
	private Timestamp createdAt;

	@Column(name="updated_at")
	private Timestamp updateAt;
	
	@ManyToOne
	@JoinColumn(name="category_id",nullable = false)
	private NewsCategory category;
	
//	@ManyToOne
//	@JoinColumn(name="classify_id",nullable = true)
//	private NewsClassification classify;
	
	@Column(name="views")
	private int views;
	
	@Column(name="status")
	private String status;
	
	@JsonIgnore
	@OneToMany(mappedBy = "news",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private Set<Comment> listComments;
	

	
}
