package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@RestController
public class DishController {
    @Autowired
    private DishService dishService;

    /**
     * 新增菜品
     * @param dto
     * @return
     */
    @PostMapping
    public Result addDish(@RequestBody  DishDTO dto){
        log.info("新增菜品:{}",dto);
        dishService.addDish(dto);
        return Result.success();
    }

    /**
     * 分页查询菜品列表
     * @param dto
     * @return
     */
    @ApiOperation("分页查询菜品列表")
    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dto){
        log.info("分页查询菜品列表：{}",dto);
        PageResult pageResult =  dishService.page(dto);
        return Result.success(pageResult);

    }
}
