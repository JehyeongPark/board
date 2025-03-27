package com.kr.board.service;

import com.kr.board.mapper.BoardMapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    /* 게시판 조회 카운트 */
    public int boardMainCount(Map<String, Object> requestMap) {
        return boardMapper.boardMainCount(requestMap);
    }

    /* 게시판 전체 조회 */
    public List<Map<String, Object>> boardMain(Map<String, Object> requestMap) {
        return boardMapper.boardMain(requestMap);
    }

    /* 게시글 저장 */
    public void insertBoard(Map<String, Object> requestMap){
        boardMapper.insertBoard(requestMap);
    }

    /* 게시글 상세보기 */
    public List<Map<String, Object>> boardDetail(Map<String, Object> requestMap) {
        return boardMapper.boardDetail(requestMap);
    }

    /* 게시글 수정 */
    public void boardUpdate(Map<String, Object> requestMap){
        boardMapper.boardUpdate(requestMap);
    }

    /* 게시글 삭제 */
    public void boardDelete(Map<String, Object> requestMap){
        boardMapper.boardDelete(requestMap);
    }

    /* 파일 업로드 */
    public void boardUpload(Map<String, Object> requestMap) {

        // 새 파일명 생성
        String oriName = (String) requestMap.get("oriName");
        String tempName = oriName.substring(oriName.lastIndexOf("."));
        String newName = UUID.randomUUID().toString() + tempName;
        int parentNo = (int) requestMap.get("no");

        // 파일 정보 Map 구성
        Map<String, Object> fileMap = new HashMap<>();
        fileMap.put("oriName", oriName);
        fileMap.put("newName", newName);
        fileMap.put("parent", parentNo);

        // Mapper를 사용한 파일 정보 업데이트
        boardMapper.boardUpload(fileMap);
    }

    public ResponseEntity<Resource> fileDownload(String oriName) throws IOException {

        String uploadPath = "C:\\Users\\박제형\\Desktop\\내 파일\\박제형\\workspace\\file"; // 회사
        /* String uploadPath = "C:\\Users\\박제형\\Desktop\\내 파일\\박제형\\workspace\\file"; // 집 */
        Path path = Paths.get(uploadPath, oriName);

        String contentType = Files.probeContentType(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename(oriName)
                .build());

        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);

    }

    /* 파일 선택 */
    public FileVO selectFile(int fno){

        return boardMapper.selectFile(fno);
    }

}