package com.blog.app.entites;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="post")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	
	@Column(name = "title",length = 100,nullable = false)
	private String title;
	
	@Column(length = 10000)
	private String content;
	private String imageName;
	private Date addedDate;
	
	//relations
	@JoinColumn(name="category_id")
	@ManyToOne
	private Category category;
	
	@JoinColumn(name="user_id")
	@ManyToOne
	private User user;
	
	//post = name of ref in Comment Entity
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Comment> comments;
}
