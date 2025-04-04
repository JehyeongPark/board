package com.kr.board.service;


import com.kr.board.mapper.BoardMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
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
        String newName = (String) requestMap.get("newName");
        int parentNo = (int) requestMap.get("no");

        // 파일 정보 Map 구성
        Map<String, Object> fileMap = new HashMap<>();
        fileMap.put("oriName", oriName);
        fileMap.put("newName", newName);
        fileMap.put("parent", parentNo);

        // Mapper를 사용한 파일 정보 업데이트
        boardMapper.boardUpload(fileMap);
    }

    /* 파일 다운로드 */
    public ResponseEntity<Resource> fileDownload(String oriName, String newName) throws IOException {
        String uploadPath = "C:\\Users\\박제형\\Desktop\\내 파일\\박제형\\workspace\\file";  // 회사
        // String uploadPath = "C:\\Users\\박제형\\Desktop\\내 파일\\박제형\\workspace\\file"; // 집
        Path path = Paths.get(uploadPath, newName);

        String contentType = Files.probeContentType(path);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename(URLEncoder.encode(oriName, "UTF-8"))  // 한글 파일명 인코딩 처리
                .build());
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    /* 파일 선택 */
    public Map<String, Object> selectFile(int fnoInt) {
        List<Map<String, Object>> list = boardMapper.selectFile(fnoInt);
        return list.isEmpty() ? null : list.get(0);
    }

    /* 엑셀 다운로드 */
    public void downloadExcelFile(Map<String, Object> requestMap, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> boardList = boardMapper.boardMain(requestMap);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("게시판 목록");

        // 헤더
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("번호");
        header.createCell(1).setCellValue("제목");
        header.createCell(2).setCellValue("내용");
        header.createCell(3).setCellValue("등록일");
        header.createCell(4).setCellValue("수정일");

        // 데이터 행
        int rowNum = 1;
        for (Map<String, Object> rowData : boardList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(String.valueOf(rowData.get("no")));
            row.createCell(1).setCellValue(String.valueOf(rowData.get("title")));
            row.createCell(2).setCellValue(String.valueOf(rowData.get("comment")));
            row.createCell(3).setCellValue(String.valueOf(rowData.get("regDtm")));
            row.createCell(4).setCellValue(String.valueOf(rowData.get("chgDtm")));
        }

        // 응답 설정
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=board_list.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

}