package com.sky.mapper;

import com.sky.entity.DishFlavor;

import java.util.List;

public interface DishFLavorMapper {
    /**
     * 批量插入口味列表数据
     * @param dishFlavorList
     */
    void insertBatch(List<DishFlavor> dishFlavorList);

    void deleteBatch(List<Long> ids);
}
