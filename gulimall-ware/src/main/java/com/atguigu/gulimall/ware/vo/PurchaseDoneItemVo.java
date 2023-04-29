package com.atguigu.gulimall.ware.vo;

import lombok.Data;

/**
 * @author Yihan
 * @date 4/13/2023 - 2:15 AM
 */
@Data
public class PurchaseDoneItemVo {
    private Long itemId;
    private Integer status;
    private String reason;
}
