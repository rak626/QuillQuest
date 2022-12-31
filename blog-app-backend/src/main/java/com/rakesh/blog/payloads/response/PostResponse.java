package com.rakesh.blog.payloads.response;

import java.util.List;

import com.rakesh.blog.payloads.request.PostDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {

	private List<PostDto> content;
	private int pageNumber;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean firstPage;
	private boolean lastPage;
}
