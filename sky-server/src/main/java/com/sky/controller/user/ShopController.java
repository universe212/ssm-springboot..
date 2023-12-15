package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: ShopController
 * Package: com.sky.controller.admin
 * Description
 *
 * @Author HuanZ
 * @Create 2023/12/8 14:24
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/shop")
@Api(tags = "店铺相关接口")
@Slf4j
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 设置店铺状态
     */
    @PutMapping("/{status}")
    @ApiOperation("设置店铺得营业状态")
    public Result setStatus(@PathVariable Integer status){
        log.info("设置店铺营业状态为：{}", status == 1?"营业中" : "打烊中");
        redisTemplate.opsForValue().set("SHOP_STATUS",status);
        return Result.success();
    }

    /**
     * 获取店铺营业状态
     */
    @GetMapping("/status")
    @ApiOperation("获取店铺得营业状态")
    public Result<Integer> getStatus(){
       Integer status = (Integer)redisTemplate.opsForValue().get("SHOP_STATUS");
       log.info("获取店铺营业状态：{}",status == 1? "营业中" : "打烊中");
       return  Result.success(status);
    }
}
