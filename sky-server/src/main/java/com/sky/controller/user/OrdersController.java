package com.sky.controller.user;

import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.Result;
import com.sky.service.OrdersService;
import com.sky.vo.OrderSubmitVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("user/order")
@Api(tags = "下单相关接口")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO dto){
        log.info("用户下单：dto={}",dto);
        OrderSubmitVO vo = ordersService.submit(dto);
        return  Result.success(vo);
    }
}
