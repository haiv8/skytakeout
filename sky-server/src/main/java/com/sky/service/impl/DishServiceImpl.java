package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFLavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
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
    @Autowired
    private SetmealDishMapper setmealDishMapper;
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

    @Override
    @Transactional //开启事务
    public void delete(List<Long> ids) {
        //1.删除菜品之前需要判断菜品是否起售中，起售中的不允许删除
        ids.forEach(id ->{
            Dish dish =dishMapper.selectById(id);
            if(dish.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        });


        //2.需要判断菜品是否被套餐关联，关联了也不允许删除
        Integer count = setmealDishMapper.countByDishId(ids);
        if(count > 0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //3.删除菜品基本信息dish表
        dishMapper.deleteBatch(ids);
        //4.删除菜品口味表信息，dish_flavor
        dishFLavorMapper.deleteBatch(ids);
    }
}
