package com.newsApp_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="comment")
public class Comment implements Serializable{
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
	@JoinColumn(name="news_id",nullable = false)
	private News news;
	
	@ManyToOne
	@JoinColumn(name="User",nullable = false)
	private User userCreator;
	
	@JsonIgnore
	@OneToMany(mappedBy = "comment",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private Set<ReComment> listReComment;

	
}
