package com.atguigu.gulimall.ware.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Yihan
 * @date 4/13/2023 - 2:14 AM
 */
@Data
public class PurchaseDoneVo {
    @NotNull
    private Long id;
    private List<PurchaseDoneItemVo> items;
}
