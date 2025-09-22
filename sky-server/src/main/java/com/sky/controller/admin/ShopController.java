package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 设置店铺的营业状态
 */


import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
@Api(tags = "店铺相关接口-管理端")
public class ShopController {

    private static final String SHOP_STATUS = "SHOP_STATUS"; // Redis 键

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 设置店铺营业状态（1=营业，0=打烊）
     */
    @ApiOperation("设置店铺营业状态")
    @PutMapping("/{status}")
    public Result<Void> setStatus(@PathVariable Integer status) {
        log.info("【Admin】设置店铺的营业状态为：{}", status == 1 ? "营业中" : "打烊中");
        stringRedisTemplate.opsForValue().set(SHOP_STATUS, String.valueOf(status));
        return Result.success();
    }

    /**
     * 获取店铺营业状态
     */
    @ApiOperation("获取店铺营业状态")
    @GetMapping("/status")
    public Result<Integer> getStatus() {
        String v = stringRedisTemplate.opsForValue().get(SHOP_STATUS);
        int status = "1".equals(v) ? 1 : 0;
        log.info("【Admin】获取到店铺的营业状态：{}", status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }
}


