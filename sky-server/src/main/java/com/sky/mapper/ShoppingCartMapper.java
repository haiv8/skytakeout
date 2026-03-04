package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    /**
     * 根据 userId + dishId/setmealId/dishFlavor 查询单条购物项（用于判断是否已存在）
     * 注意：XML 中使用的是参数名 userId/dishId/setmealId/dishFlavor
     */
    ShoppingCart findByUserAndItem(@Param("userId") Long userId,
                                   @Param("dishId") Long dishId,
                                   @Param("setmealId") Long setmealId,
                                   @Param("dishFlavor") String dishFlavor);

    /**
     * 插入购物车项（会回填 id 到 shoppingCart.id）
     */
    void insert(ShoppingCart shoppingCart);



    /**
     * 列表查询（支持按条件筛选：userId 必填）
     * parameterType 使用 ShoppingCart，传入对象或只传 userId 时也可用
     */
    List<ShoppingCart> list(Long useId);

    /**
     * 根据用户 id 清空购物车
     */
    void deleteByUserId(@Param("userId") Long userId);

    void updateNumber(ShoppingCart cart);

    List<ShoppingCart> findByUserId(Long userId);
}
