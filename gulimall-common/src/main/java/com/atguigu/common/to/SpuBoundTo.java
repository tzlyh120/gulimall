package com.atguigu.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Yihan
 * @date 4/11/2023 - 10:36 AM
 */
@Data
public class SpuBoundTo {
    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
