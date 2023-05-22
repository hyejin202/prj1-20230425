package com.example.demo.domain;

import java.time.*;
import java.util.*;

import lombok.*;

@Data
public class Board {
	private Integer id;
	private String title;
	private String body;
	private String writer;
	private LocalDateTime inserted;
	private List<String> fileName;
	
	private Boolean liked;
	
	private Integer fileCount;
	private Integer likeCount;
	private Integer commentCount;
}
