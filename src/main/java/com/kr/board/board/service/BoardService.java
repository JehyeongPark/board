package com.kr.board.board.service;

import com.kr.board.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    public List<Map<String, Object>> boardMain(Map<String, Object> requestMap) {
        return boardMapper.boardMain(requestMap);
    }

    public void insertBoard(Map<String, Object> requestMap){
        boardMapper.insertBoard(requestMap);
    }

    public List<Map<String, Object>> boardDetail(Map<String, Object> requestMap) {
        return boardMapper.boardDetail(requestMap);
    }

    public void boardUpdate(Map<String, Object> requestMap){
        boardMapper.boardUpdate(requestMap);
    }

    public void boardDelete(Map<String, Object> requestMap){
        boardMapper.boardDelete(requestMap);
    }

    public void boardSrch(Map<String, Object> requestMap){
        boardMapper.boardSrch(requestMap);
    }



}