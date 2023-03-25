package com.atguigu.gulimall.product;

import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.service.BrandService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes=GulimallProductApplication.class)
@SpringBootTest
@Slf4j
public class GulimallProductApplicationTests {

    @Resource
    BrandService brandService;

    @Test
    public void contextLoads() {
//        BrandEntity brandEntity= new BrandEntity();
//        brandEntity.setName("mtmb");
//        brandService.save(brandEntity);
//        System.out.println("save successful");
        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1));
        list.forEach((item)->{
            System.out.println(item);
        });
        log.info("############## 到底怎么了啊");
    }

}
