package com.sky.task; // 假设的包名，你需要根据你的项目结构调整

import com.sky.entity.Orders;
import com.sky.mapper.OrdersMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrdersMapper ordersMapper; // 注入订单 Mapper

    /**
     * 每分钟检查一次是否存在超时未支付的订单
     * Cron 表达式: "0 * * * * ?" => 每分钟的第 0 秒执行
     */
    @Scheduled(cron = "0 * * * * ?")
    public void processOutTime() {
        log.info("定时任务：开始处理超时未支付订单...");

        // 1. 计算出 15 分钟前的时间点作为查询截止时间
        LocalDateTime time = LocalDateTime.now().minusMinutes(15);

        // 2. 查询出所有状态为 '待付款' 且 '下单时间' 早于 time 的订单
        // 这里的 Order.PENDING_PAYMENT 假设是订单类中定义的待付款状态常量
        List<Orders> orderList = ordersMapper.selectByStatusAndOrderTime(Orders.PENDING_PAYMENT, time);

        if (orderList != null && !orderList.isEmpty()) {
            log.info("发现 {} 个超时未支付订单，准备进行自动取消。", orderList.size());

            // 3. 遍历并取消这些订单
            orderList.forEach(order -> {
                // 将状态设置为 '已取消'
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("订单超时，系统自动取消");
                order.setCancelTime(LocalDateTime.now());

                // 更新数据库记录
                ordersMapper.update(order);
            });

            log.info("超时订单处理完成，已成功取消 {} 个订单。", orderList.size());
        } else {
            log.info("当前没有超时未支付的订单需要处理。");
        }
    }
}