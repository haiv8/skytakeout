package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper // ⚠️ 必须加上这个注解，MyBatis才能识别它
public interface OrdersMapper {

    /**
     * 插入订单
     * 注意：实际项目中，这通常是一个复杂的 INSERT 语句，可能涉及主键回填。
     */
    @Insert("INSERT INTO orders " +
            "(number, status, user_id, address_book_id, order_time, checkout_time, pay_method, " +
            "amount, remark, phone, address, user_name, consignee, estimated_delivery_time, " +
            "delivery_status, pack_amount, tableware_number, tableware_status) " +
            "VALUES " +
            "(#{number}, #{status}, #{userId}, #{addressBookId}, #{orderTime}, #{checkoutTime}, #{payMethod}, " +
            "#{amount}, #{remark}, #{phone}, #{address}, #{userName}, #{consignee}, #{estimatedDeliveryTime}, " +
            "#{deliveryStatus}, #{packAmount}, #{tablewareNumber}, #{tablewareStatus})")
    void insert(Orders orders);

    /**
     * 查询超时未支付订单列表（用于定时任务自动取消）
     * 条件: status = 待付款 且 order_time < 指定时间点
     */
    @Select("SELECT * FROM orders " +
            "WHERE status = #{status} AND order_time < #{time} " +
            "LIMIT 500") // 加上 LIMIT 避免一次性加载过多数据
    List<Orders> selectByStatusAndOrderTime(
            @Param("status") Integer status,
            @Param("time") LocalDateTime time
    );

    // ⚠️ 补充：定时任务中还需要用到的更新方法
    /**
     * 更新订单状态和取消信息
     */
    @Update("<script>" +
            "UPDATE orders " +
            "<set>" +
            "status = #{status}, " +
            "cancel_reason = #{cancelReason}, " +
            "cancel_time = #{cancelTime} " +
            "</set>" +
            "WHERE id = #{id}" +
            "</script>")
    void update(Orders orders);
}