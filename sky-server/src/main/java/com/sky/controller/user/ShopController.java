package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;





@RestController("userShopController")
@RequestMapping("/user/shop")
@Slf4j
@Api(tags = "店铺相关接口-用户端")
public class ShopController {

    private static final String SHOP_STATUS = "SHOP_STATUS";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 用户端获取店铺营业状态
     */
    @ApiOperation("获取店铺营业状态")
    @GetMapping("/status")
    public Result<Integer> getStatus() {
        String v = stringRedisTemplate.opsForValue().get(SHOP_STATUS);
        int status = "1".equals(v) ? 1 : 0;
        log.info("【User】获取到店铺的营业状态：{}", status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }
}


