package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DishService {

    /**
     * 新增菜品
     * @param dto
     */
    void addDish(DishDTO dto);

    PageResult page(DishPageQueryDTO dto);

    void delete(List<Long> ids);

    DishVO getById(Long id);

    /**
     * 修改菜品
     * @param dto
     */
    void update(DishDTO dto);

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);
}


