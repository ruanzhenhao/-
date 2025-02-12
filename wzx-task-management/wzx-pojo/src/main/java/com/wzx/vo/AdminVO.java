package com.wzx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminVO {
    private Long id;
    private String userName;
    private String img;
    private int role;
}
