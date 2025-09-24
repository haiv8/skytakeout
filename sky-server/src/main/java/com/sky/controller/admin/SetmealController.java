package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐管理相关接口")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @ApiOperation("新增套餐")
    @PostMapping
    public Result save(@RequestBody SetmealDTO dto) {
        setmealService.saveWithDish(dto);
        return Result.success();
    }

    @ApiOperation("分页查询套餐")
    @GetMapping("/page")
    public Result<PageResult> page(SetmealPageQueryDTO queryDTO) {
        PageResult pageResult = setmealService.pageQuery(queryDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("根据id查询套餐")
    @GetMapping("/{id}")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        SetmealVO vo = setmealService.getByIdWithDish(id);
        return Result.success(vo);
    }

    @ApiOperation("修改套餐")
    @PutMapping
    public Result update(@RequestBody SetmealDTO dto) {
        setmealService.updateWithDish(dto);
        return Result.success();
    }

    @ApiOperation("删除套餐")
    @DeleteMapping
    public Result delete(@RequestParam Long id) {
        setmealService.deleteById(id);
        return Result.success();
    }
}
