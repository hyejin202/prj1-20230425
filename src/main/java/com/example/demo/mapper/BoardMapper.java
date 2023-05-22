package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface BoardMapper {

	@Select("""
			SELECT
				id,
				title,
				writer,
				inserted
			FROM Board 
			ORDER BY id DESC
			""")
	List<Board> selectAll();

	@Select("""
			SELECT 
				b.id,
				b.title,
				b.body,
				b.inserted,
				b.writer,
				f.fileName,
				(SELECT COUNT(*) FROM BoardLike WHERE boardId = b.id) likeCount
			FROM Board b LEFT JOIN FileName f ON b.id = f.boardId
						 LEFT JOIN BoardLike bl ON b.id = bl.boardId
			WHERE b.id = #{id}
			""")
	@ResultMap("boardResultMap")
	Board selectById(Integer id);

	@Update("""
			UPDATE Board
			SET 
				title = #{title},
				body = #{body}
			WHERE
				id = #{id}
			""")
	int update(Board board);

	@Delete("""
			DELETE FROM Board
			WHERE id = #{id}
			""")
	int deleteById(Integer id);

	@Insert("""
			INSERT INTO Board (title, body, writer)
			VALUES (#{title}, #{body}, #{writer})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(Board board);

	@Select("""
			<script>
			<bind name="pattern" value="'%' + search + '%'" />
			SELECT
				b.id,
				b.title,
				b.writer,
				b.inserted,
				COUNT(f.id) fileCount,
				(SELECT COUNT(*) FROM BoardLike WHERE boardId = b.id) likeCount,
			    (SELECT COUNT(*) FROM Comment WHERE boardId = b.id) commentCount
			FROM Board b LEFT JOIN FileName f ON b.id = f.boardId
						LEFT JOIN BoardLike bl ON b.id = bl.boardId
			
			<where>
				<if test="(type eq 'all') or (type eq 'title')">
				   title  LIKE #{pattern}
				</if>
				<if test="(type eq 'all') or (type eq 'body')">
				OR body   LIKE #{pattern}
				</if>
				<if test="(type eq 'all') or (type eq 'writer')">
				OR writer LIKE #{pattern}
				</if>
			</where>
			GROUP BY b.id
			ORDER BY b.id DESC
			LIMIT #{startIndex}, #{rowPerPage}
			</script>
			""")
	List<Board> selectAllPaging(Integer startIndex, Integer rowPerPage, String search, String type);

	@Select("""
			<script>
			<bind name="pattern" value="'%' + search + '%'" />
			SELECT COUNT(*) 
			FROM Board
			
			<where>
				<if test="(type eq 'all') or (type eq 'title')">
				   title  LIKE #{pattern}
				</if>
				<if test="(type eq 'all') or (type eq 'body')">
				OR body   LIKE #{pattern}
				</if>
				<if test="(type eq 'all') or (type eq 'writer')">
				OR writer LIKE #{pattern}
				</if>
			</where>
			
			</script>
			""")
	Integer countAll(String search, String type);

	@Insert("""
			INSERT INTO FileName (boardId, fileName)
			VALUES (#{boardId}, #{fileName})
			""")
	void insertFileName(Integer boardId, String fileName);
	
	@Select("""
			SELECT fileName
			FROM FileName
			WHERE boardId = #{boardId}
			""")
	List<String> selectFileNamesByBoardId(Integer boardId);

	@Delete("""
			DELETE FROM FileName
			WHERE boardId = #{boardId}
			""")
	void deleteFileNameByBoardId(Integer boardId);

	@Delete("""
			DELETE FROM FileName
			WHERE boardId = #{boardId}
				AND fileName = #{fileName}
			""")
	void deleteFileNameByBoardIdAndFileName(Integer boardId, String fileName);

	@Select("""
			SELECT id
			FROM Board
			WHERE writer = #{writer}
			""")
	List<Integer> selectIdByWriter(String writer);

	

	
}
