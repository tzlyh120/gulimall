package com.atguigu.gulimall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Yihan
 * @date 4/13/2023 - 12:28 AM
 */
@Data
public class MergeVo {
    private Long purchasedId;
    private List<Long> items;

}
