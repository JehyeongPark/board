package com.kr.board.service;

import com.kr.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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



}