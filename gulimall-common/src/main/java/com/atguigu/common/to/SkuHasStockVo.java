package com.atguigu.common.to;

import lombok.Data;

/**
 * @author Yihan
 * @date 4/21/2023 - 12:09 AM
 */
@Data
public class SkuHasStockVo {
    private Long skuId;
    private Boolean hasStock;
}
