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
    public String boardMain(
            @RequestParam Map<String, Object> requestMap,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            Model model) {

        int totalCount = boardService.boardMainCount(requestMap);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        int offset = (page - 1) * pageSize;

        requestMap.put("offset", offset);
        requestMap.put("limit", pageSize);

        List<Map<String, Object>> rtnList = boardService.boardMain(requestMap);

        model.addAttribute("detail", rtnList);
        model.addAttribute("boardCount", totalCount);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", pageSize);

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

