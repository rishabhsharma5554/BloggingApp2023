package com.blog.app.payloads;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class PostResponse {

	private List<PostDTO> content;
	private Integer pageNo;
	private Integer pageSize;
	private Long totalRecords;
	private Integer totalPages;
	private boolean lastPage;
}
