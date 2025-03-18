package com.kr.board.board.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {

    List<Map<String, Object>> boardMain(Map<String, Object> requestMap);

    public void insertBoard(Map<String, Object> requestMap);

    List<Map<String, Object>> boardDetail(Map<String, Object> requestMap);

    public void boardUpdate(Map<String, Object> requestMap);

    public void boardDelete(Map<String, Object> requestMap);


}
