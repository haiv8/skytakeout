package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    /**
     * 添加购物车
     */
    void addCart(ShoppingCartDTO dto);

    /**
     * 查看购物车（改名 list）
     */
    List<ShoppingCart> list();

    /**
     * 清空购物车
     */
    void clean();
}
