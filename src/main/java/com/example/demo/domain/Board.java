package com.example.demo.domain;

import java.time.*;

import lombok.*;

@Data
public class Board {
	private Integer id;
	private String title;
	private String body;
	private String writer;
	private LocalDateTime inserted;
}
