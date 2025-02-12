package com.wzx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ManagementVO {
    private List<AdminVO> adminVOList;
    private List<OrdinaryVO> ordinaryVOList;
}
