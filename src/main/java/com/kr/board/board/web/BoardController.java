package com.kr.board.board.web;

import com.kr.board.board.service.BoardService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String boardMain(@RequestParam Map<String, Object> requestMap, Model model) {
        List<Map<String, Object>> rtnList = boardService.boardMain(requestMap);

        model.addAttribute("detail", rtnList);

        return "board/BoardMain";
    }

    @GetMapping("/boardWrite")
    public String boardWrite(Model model) {

        return "board/BoardWrite";
    }

    @PostMapping("/boardInsert")
    public ResponseEntity<Map<String, Object>> insertBoard(@RequestBody Map<String, Object> requestMap) {

        boardService.insertBoard(requestMap);

        return ResponseEntity.ok(requestMap);
    }

    @GetMapping("/boardDetail")
    public String boardDetail(@RequestParam Map<String, Object> requestMap, Model model) {
        List<Map<String, Object>> rtnList = boardService.boardDetail(requestMap);

        model.addAttribute("detail", rtnList);

        return "board/BoardDetail";
    }

    @PostMapping("/boardUpdate")
    public ResponseEntity<Map<String, Object>> boardUpdate(@RequestBody Map<String, Object> requestMap) {

        boardService.boardUpdate(requestMap);

        return ResponseEntity.ok(requestMap);
    }

    @PostMapping("/boardDelete")
    public ResponseEntity<Map<String, Object>> boardDelete(@RequestBody Map<String, Object> requestMap) {

        boardService.boardDelete(requestMap);

        return ResponseEntity.ok(requestMap);
    }


}

