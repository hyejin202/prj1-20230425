package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface BoardMapper {

	@Select("""
			SELECT 
				id, title, writer, inserted
			FROM Board 
			ORDER BY id DESC
			""")
	List<Board> selectAll();
	
	//sevice에서 받은 해당 id의 전체 내용 가져오기
	@Select("""
			SELECT * 
			FROM Board
			WHERE id = #{id}
			""")
	Board selectById(Integer id);

	//service에서 받은 해당 보드 수정하기
	@Update("""
			UPDATE Board
			SET 
				title = #{title},
				body = #{body},
				writer = #{writer}
			WHERE 
				id = #{id}
			""")
	int update(Board board);
	
	// 파라미터로 받은 해당 id 게시물 삭제하기
	@Delete("""
			DELETE FROM Board
			WHERE id = #{id}
			""")
	int deleteById(Integer id);
	
	
}
