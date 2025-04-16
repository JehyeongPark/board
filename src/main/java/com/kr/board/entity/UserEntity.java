package com.kr.board.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="login")
public class UserEntity {

    // JPA에서 테이블과 매핑되는 객체
    @Id
    private String uid;
    private String pass;

    private int grade;

}
