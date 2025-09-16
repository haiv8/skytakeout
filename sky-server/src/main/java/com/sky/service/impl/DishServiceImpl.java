package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFLavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {


    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFLavorMapper dishFLavorMapper;
    @Override
    @Transactional//开启事务，涉及到多张表的增删改操作需要事务管理
    public void addDish(DishDTO dto) {
        //1.构造菜品基本信息数据，将其存入dish表
        Dish dish = new Dish();
        //把dto的值拷贝给dish
        BeanUtils.copyProperties(dto,dish);

        dishMapper.insert(dish);
        log.info("dishId = {}",dish.getId());
        List<DishFlavor> dishFlavorList = dto.getFlavors();

        //关联菜品id
        dishFlavorList.forEach(flavor ->{
            flavor.setDishId(dish.getId());
        });
        dishFLavorMapper.insertBatch(dishFlavorList);


        //2.构造菜品口味道信息数据，将其存入dish_flvor表中
    }

    @Override
    public PageResult page(DishPageQueryDTO dto) {
        //设置分页参数
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        Page<DishVO> page =  dishMapper.list(dto);
        return new PageResult(page.getTotal(),page.getResult());
    }
}
