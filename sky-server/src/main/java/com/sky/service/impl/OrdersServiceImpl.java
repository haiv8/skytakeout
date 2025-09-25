package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.AddressBook;
import com.sky.entity.Orders;
import com.sky.entity.User;
import com.sky.exception.OrderBusinessException;
import com.sky.mapper.AddressBookMapper;
import com.sky.mapper.OrdersMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.OrdersService;
import com.sky.vo.OrderSubmitVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 用户下单  -- 将订单数据存入表中（orders、order_detail）
 */
@Slf4j
@Transactional
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public OrderSubmitVO submit(OrdersSubmitDTO dto) {


        AddressBook addressBook = addressBookMapper.getById(dto.getAddressBookId());
        if(addressBook == null){
            throw new OrderBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }

        Long userId = BaseContext.getCurrentId();
        //根据id查用户表的用户信息
        User user =userMapper.selectById(userId);



        //1.构造订单数据，存入Orders中
        Orders orders = new Orders();
        BeanUtils.copyProperties(dto,orders);

        orders.setNumber(System.currentTimeMillis() + "");
        //订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setUserId(userId);
        orders.setPhone(addressBook.getPhone());
        orders.setOrderTime(LocalDateTime.now());
        orders.setStatus(Orders.UN_PAID);
        orders.setAddress(addressBook.getDetail());
        orders.setConsignee(addressBook.getConsignee());
        orders.setUserName(user.getName());//下单人姓名


        ordersMapper.insert(orders);

        return null;
    }
}
