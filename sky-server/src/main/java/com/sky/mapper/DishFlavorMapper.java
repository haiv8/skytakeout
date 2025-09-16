package com.sky.mapper;

import com.sky.entity.DishFlavor;

import java.util.List;

public interface DishFlavorMapper {
    /**
     * 批量插入口味列表数据
     * @param dishFlavorList
     */
    void insertBatch(List<DishFlavor> dishFlavorList);

    void deleteBatch(List<Long> dishIds);

    List <DishFlavor> selectByDishId(Long dishId);

    void deleteByDishId(Long dishId);
}
