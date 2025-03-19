package com.kr.board.web;

import com.kr.board.service.BoardService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /* 메인 */
    @GetMapping("/")
    public String boardMain(@RequestParam Map<String, Object> requestMap, Model model) {
        List<Map<String, Object>> rtnList = boardService.boardMain(requestMap);
        int boardCount = boardService.boardMainCount(requestMap);

        log.warn("Converted selectSrch111:", requestMap);

        // 검색조건 값 String으로 변환
        String selectSrch = String.valueOf(requestMap.get("selectSrch"));
        requestMap.put("selectSrch", selectSrch);

        log.warn("Converted selectSrch: {}", selectSrch);
        log.warn("Converted selectSrch:", requestMap);

        // 게시판 조회
        model.addAttribute("detail", rtnList);
        // 게시판 조회 카운트
        model.addAttribute("boardCount", boardCount);

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

        return ResponseEntity.ok(requestMap);
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

/*
    // 게시글 검색 조건 AJAX
    @PostMapping("/boardSrch")
    public ResponseEntity<Map<String, Object>> boardSrch(@RequestBody Map<String, Object> requestMap) {
        boardService.boardMain(requestMap);


        return ResponseEntity.ok(requestMap);
    }
*/


}

