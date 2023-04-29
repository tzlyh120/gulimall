package com.atguigu.gulimall.search.service;


import com.atguigu.gulimall.search.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @author Yihan
 * @date 4/21/2023 - 1:06 AM
 */
public interface ProductSaveService {
    boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;
}
