package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    @Transactional
    public void addCart(ShoppingCartDTO dto) {
        Long userId = BaseContext.getCurrentId();

        ShoppingCart cart = shoppingCartMapper.findByUserAndItem(
                userId,
                dto.getDishId(),
                dto.getSetmealId(),
                dto.getDishFlavor()
        );

        if (cart != null) {
            // 已存在，数量+1
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.updateNumber(cart);
        } else {
            // 不存在，插入新记录
            cart = new ShoppingCart();
            cart.setUserId(userId);
            cart.setDishId(dto.getDishId());
            cart.setSetmealId(dto.getSetmealId());
            cart.setDishFlavor(dto.getDishFlavor());
            cart.setNumber(1);
            cart.setCreateTime(LocalDateTime.now());

            // ⚠️ 这里应该查 Dish/Setmeal 表填充 name/image/amount
            cart.setName("示例菜品/套餐");
            cart.setImage("default.png");
            cart.setAmount(java.math.BigDecimal.valueOf(20.00));

            shoppingCartMapper.insert(cart);
        }
    }

    @Override
    public List<ShoppingCart> list() {
        Long userId = BaseContext.getCurrentId();
        return shoppingCartMapper.findByUserId(userId);
    }

    @Override
    @Transactional
    public void clean() {
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.deleteByUserId(userId);
    }
}
