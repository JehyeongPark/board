package com.kr.board.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {

    /* 게시판 조회 카운트 */
    public int boardMainCount(Map<String, Object> requestMap);

    /* 게시판 전체 조회 */
    List<Map<String, Object>> boardMain(Map<String, Object> requestMap);

    /* 게시글 저장 */
    public void insertBoard(Map<String, Object> requestMap);

    /* 게시글 상세보기 */
    List<Map<String, Object>> boardDetail(Map<String, Object> requestMap);

    /* 게시글 수정 */
    public void boardUpdate(Map<String, Object> requestMap);

    /* 게시글 삭제 */
    public void boardDelete(Map<String, Object> requestMap);


}
