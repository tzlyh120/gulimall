package com.atguigu.gulimall.product.exception;

import com.atguigu.common.exception.BizCodeEnume;
import io.renren.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yihan
 * @date 4/4/2023 - 1:40 AM
 */

@Slf4j
@RestControllerAdvice(basePackages = "com.atguigu.gulimall.product.controller")
public class GulimallExceptionControllerAdvice {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handlerValidException(MethodArgumentNotValidException e) {
        log.error("数据校验有误", e.getMessage(), e.getClass());
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach((fieldError) -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        log.info(errorMap.toString());
        return R.error(BizCodeEnume.VALID_EXCEPTION.getCode(), BizCodeEnume.VALID_EXCEPTION.getMsg()).put("data", errorMap);
    }

    //    @ExceptionHandler(value= Throwable.class)
//    public R handleException(){
//        return R.error(BizCodeEnume.UNKNOWN_EXCEPTION.getCode(),BizCodeEnume.UNKNOWN_EXCEPTION.getMsg());
//    }
    @ExceptionHandler(value = Throwable.class)
    public R handleException(Exception e) {
//        log.error("其他错误", e.getMessage(), e.getClass());
        return R.error(BizCodeEnume.UNKNOWN_EXCEPTION.getCode(), BizCodeEnume.UNKNOWN_EXCEPTION.getMsg());
    }
}
