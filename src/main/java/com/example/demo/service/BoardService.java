package com.example.demo.service;

import java.io.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.multipart.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

import software.amazon.awssdk.core.sync.*;
import software.amazon.awssdk.services.s3.*;
import software.amazon.awssdk.services.s3.model.*;

@Service
@Transactional(rollbackFor = Exception.class)
//@Component - Service 어노테이션 안에 Component 어노테이션 있음
public class BoardService {

	@Autowired
	private S3Client s3;

	@Value("${aws.s3.bucketName}")
	private String bucketName;

	@Autowired
	private BoardMapper mapper;
	
	@Autowired
	private BoardLikeMapper likeMapper;
	
	@Autowired
	private CommentMapper commentMapper;

	public List<Board> listBoard() {
		List<Board> list = mapper.selectAll();
		return list;
	}

	public Map<String, Object> listBoard(Integer page, String search, String type) {
		// 페이지당 행의 수
		Integer rowPerPage = 15;

		// 쿼리 LIMIT 절에 사용할 시작 인덱스
		Integer startIndex = (page - 1) * rowPerPage;

		// 페이지네이션이 필요한 정보
		// 전체 레코드 수
		Integer numOfRecords = mapper.countAll(search, type);
		// 마지막 페이지 번호
		Integer lastPageNumber = (numOfRecords - 1) / rowPerPage + 1;
		// 페이지네이션 왼쪽번호
		Integer leftPageNum = page - 5;
		// 1보다 작을 수 없음
		leftPageNum = Math.max(leftPageNum, 1);

		// 페이지네이션 오른쪽번호
		Integer rightPageNum = leftPageNum + 9;
		// 마지막페이지보다 클 수 없음
		rightPageNum = Math.min(rightPageNum, lastPageNumber);

		Map<String, Object> pageInfo = new HashMap<>();
		pageInfo.put("rightPageNum", rightPageNum);
		pageInfo.put("leftPageNum", leftPageNum);
		pageInfo.put("currentPageNum", page);
		pageInfo.put("lastPageNum", lastPageNumber);

		// 게시물 목록
		List<Board> list = mapper.selectAllPaging(startIndex, rowPerPage, search, type);

		return Map.of("pageInfo", pageInfo,
				"boardList", list);
	}

	// id 받아서 mapper에 건네주면 mapper는 데이터를 가져와 해당 id의 본문을 보여줌
	public Board getBoard(Integer id, Authentication authentication) {
		Board board = mapper.selectById(id);
		
		//현재 로그인한 사람이 이 게시물에 좋아요 했는지?
		if(authentication != null) {
			Like like = likeMapper.select(id, authentication.getName());
			if(like != null) {
				board.setLiked(true);
			}
		}
		return board; 
	}

	public Board getBoard(Integer id) {
		return getBoard(id, null);
	}
	// 수정 메소드, 수정되면 true(1) 안되면 false(0)
	public boolean modify(Board board, List<String> removeFileNames, MultipartFile[] addFiles) throws Exception {
		// FileName 테이블 삭제
		if (removeFileNames != null && !removeFileNames.isEmpty()) {
			for (String fileName : removeFileNames) {
				
				// 하드디스크에서 삭제
//				String path = "C:\\study\\upload\\" + board.getId() + "\\" + fileName;
//				File file = new File(path);
//				if (file.exists()) {
//					file.delete();
//				}
				
				String objectKey = "board/" + board.getId() + "/" + fileName; 
				// S3에서 파일(객체) 삭제
				DeleteObjectRequest dor = DeleteObjectRequest.builder()
						.bucket(bucketName)
						.key(objectKey)
						.build();
				s3.deleteObject(dor);

				// 테이블에서 삭제
				mapper.deleteFileNameByBoardIdAndFileName(board.getId(), fileName);
			}
		}

		// 새 파일 추가
		for (MultipartFile newFile : addFiles) {
			if (newFile.getSize() > 0) {
				// 테이블에 파일명 추가
				mapper.insertFileName(board.getId(), newFile.getOriginalFilename());
				
				//s3에 파일(객체) 업로드
				String objectKey = "board/" + board.getId() + "/" + newFile.getOriginalFilename(); 
				PutObjectRequest por = PutObjectRequest.builder()
						.acl(ObjectCannedACL.PUBLIC_READ)
						.bucket(bucketName)
						.key(objectKey)
						.build();
				RequestBody rb = RequestBody.fromInputStream(newFile.getInputStream(), newFile.getSize());
				s3.putObject(por, rb);

//				String fileName = newFile.getOriginalFilename();
//				String folder = "C:\\study\\upload\\" + board.getId();
//				String path = folder + "\\" + fileName;
				// 디렉토리 없으면 만들기
//				File dir = new File(folder);
//				if (!dir.exists()) {
//					dir.mkdirs();
//				}
				// 파일을 하드디스크에 저장
//				File file = new File(path);
//				newFile.transferTo(file);
				
			}
		}

		// 게시물(Board) 테이블 수정
		int cnt = mapper.update(board); // board파라미터 받아서 mapper한테 update 시킴

		return cnt == 1; // 레코드 1 행이 수정되었다면 true(1)리턴
	}

	// 삭제 메소드
	public boolean remove(Integer id) {
		//댓글 테이블 데이터 지우기
		commentMapper.deleteByBoardId(id);
		
		// 좋아요 테이블 데이터 지우기
		likeMapper.deleteByBoardId(id);
	
		// 파일명 조회
		List<String> fileNames = mapper.selectFileNamesByBoardId(id);

		// fileName 테이블의 데이터 지우기
		mapper.deleteFileNameByBoardId(id);

		// s3 bucket의 파일(객체) 지우기
		for (String fileName : fileNames) { // 여러파일일 수 있으니까
			String objectKey = "board/" + id + "/" + fileName;
			DeleteObjectRequest dor = DeleteObjectRequest.builder()
					.bucket(bucketName)
					.key(objectKey)
					.build();
			s3.deleteObject(dor);
		}

		// 게시물 테이블의 데이터 지우기
		int cnt = mapper.deleteById(id);
		
		
		return cnt == 1;
	}

	// 추가 메소드
	public boolean addBoard(Board board, MultipartFile[] files) throws Exception {

		// 게시물 insert
		int cnt = mapper.insert(board);


		for (MultipartFile file : files) {
			if (file.getSize() > 0) {
				String objectKey = "board/" + board.getId() + "/" + file.getOriginalFilename();

				PutObjectRequest por = PutObjectRequest.builder()
						.bucket(bucketName)
						.key(objectKey)
						.acl(ObjectCannedACL.PUBLIC_READ)
						.build();
				RequestBody rb = RequestBody.fromInputStream(file.getInputStream(), file.getSize());

				s3.putObject(por, rb);


				// db에 관련된 정보 저장(insert)
				mapper.insertFileName(board.getId(), file.getOriginalFilename());
			}
		}

//		int cnt = 0;   //추가 실패
		return cnt == 1;
	}

	public void removeByWriter(String writer) {
		List<Integer> idList = mapper.selectIdByWriter(writer);
		
		for(Integer id : idList) {
			remove(id);
		}
	}

	public Map<String, Object> like(Like like, Authentication authentication) {
		Map<String, Object> result = new HashMap<>();
		
		result.put("like", false);
		
		like.setMemberId(authentication.getName());
		Integer deleteCnt = likeMapper.delete(like);
		
		if(deleteCnt != 1) {
			Integer insertCnt = likeMapper.insert(like);
			result.put("like", true);
		} 
		
		Integer count = likeMapper.countByBoardId(like.getBoardId());
		result.put("count",  count);
		
		return result;
	}


}
