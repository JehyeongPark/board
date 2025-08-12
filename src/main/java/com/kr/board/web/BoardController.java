package com.kr.board.web;


import com.kr.board.service.BoardService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// 파일 경로 관련
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

// HTTP 응답 상태 관련
import org.springframework.http.HttpStatus;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /* 메인 */
    @GetMapping("/")
    public String boardMain(
                    @RequestParam Map<String, Object> requestMap,
                    @RequestParam(name = "page", defaultValue = "1") int page,
                    @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                    Model model) {

        int totalCount = boardService.boardMainCount(requestMap);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        int offset = (page - 1) * pageSize;

        // 페이지 블록 설정
        int blockSize = 5; // 한 번에 표시할 페이지 수
        int startPage = ((page - 1) / blockSize) * blockSize + 1;
        int endPage = Math.min(startPage + blockSize - 1, totalPages);

        requestMap.put("offset", offset);
        requestMap.put("limit", pageSize);

        System.out.println("검색 조건: " + requestMap.get("selectSrch"));
        System.out.println("검색어: " + requestMap.get("writeSrch"));


        List<Map<String, Object>> rtnList = boardService.boardMain(requestMap);

        model.addAttribute("detail", rtnList);
        model.addAttribute("boardCount", totalCount);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "board/BoardMain";
    }

    /* 글 쓰기 */
    @GetMapping("/boardWrite")
    public String boardWrite(Model model) {

        return "board/BoardWrite";
    }

    /* 게시글 저장 AJAX */
    @PostMapping("/boardInsert")
    public ResponseEntity<Map<String, Object>> insertBoard(@RequestBody Map<String, Object> requestMap) {
        boardService.insertBoard(requestMap);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("no", requestMap.get("no"));

        return ResponseEntity.ok(response);
    }

    /* 게시글 상세보기 */
    @GetMapping("/boardDetail")
    public String boardDetail(@RequestParam Map<String, Object> requestMap, Model model) {
        List<Map<String, Object>> rtnList = boardService.boardDetail(requestMap);

        model.addAttribute("detail", rtnList);

        return "board/BoardDetail";
    }

    /* 게시글 수정 AJAX */
    @PostMapping("/boardUpdate")
    public ResponseEntity<Map<String, Object>> boardUpdate(@RequestBody Map<String, Object> requestMap) {

        boardService.boardUpdate(requestMap);

        return ResponseEntity.ok(requestMap);
    }

    /* 게시글 삭제 AJAX */
    @PostMapping("/boardDelete")
    public ResponseEntity<Map<String, Object>> boardDelete(@RequestBody Map<String, Object> requestMap) {

        boardService.boardDelete(requestMap);

        return ResponseEntity.ok(requestMap);
    }

    /* 파일 업로드 */
    @PostMapping("/boardUpload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("oriName") String oriName,
            @RequestParam("no") int no) {

        String uploadPath = "C:\\Users\\박제형\\Desktop\\내 파일\\박제형\\workspace\\file"; // 회사
        // String uploadPath = "C:\\Users\\박제형\\Desktop\\내 파일\\박제형\\workspace\\file"; // 집
        String extension = oriName.substring(oriName.lastIndexOf("."));
        String newName = UUID.randomUUID().toString() + extension;

        Path filePath = Paths.get(uploadPath, newName);

        try {
            // 파일 저장
            Files.write(filePath, file.getBytes());

            // 파일 정보 DB 저장을 위한 Map 생성
            Map<String, Object> fileMap = new HashMap<>();
            fileMap.put("oriName", oriName);
            fileMap.put("newName", newName);
            fileMap.put("no", no);

            // 서비스 호출
            boardService.boardUpload(fileMap);

            return ResponseEntity.ok("File uploaded successfully!");
        } catch (IOException e) {
            log.error("Error uploading file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file. Please try again.");
        }
    }

    /* 파일 다운로드 */
    @GetMapping("/boardFileDownload")
    @ResponseBody
    public ResponseEntity<Resource> fileDownload(@RequestParam("fno") String fno) throws IOException {
        int fnoInt = Integer.parseInt(fno);

        Map<String, Object> fileMap = boardService.selectFile(fnoInt);
        String oriName = (String) fileMap.get("oriName");
        String newName = (String) fileMap.get("newName");

        return boardService.fileDownload(oriName, newName);
    }


    /* 엑셀 다운로드 */
    @GetMapping("/boardExcel")
    public void downloadBoardExcel(@RequestParam Map<String, Object> requestMap, HttpServletResponse response) throws IOException {
        boardService.downloadExcelFile(requestMap, response);
    }



}

