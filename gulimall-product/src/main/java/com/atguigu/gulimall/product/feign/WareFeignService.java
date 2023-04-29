package com.atguigu.gulimall.product.feign;

import com.atguigu.common.to.SkuHasStockVo;
import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author Yihan
 * @date 4/21/2023 - 12:33 AM
 */
@FeignClient("gulimall-ware")
public interface WareFeignService {
    @PostMapping("/ware/waresku/hasstock")
    List<SkuHasStockVo> skusHasStock(@RequestBody List<Long> skuIds);
//    R skusHasStock(@RequestBody List<Long> skuIds);


}
