package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName: OrderTask
 * Package: com.sky.task
 * Description
 *
 * @Author HuanZ
 * @Create 2023/12/13 14:16
 * @Version 1.0
 */
@Component
@Slf4j
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;
    /**
     * 超时订单方法
     */
    //@Scheduled(cron = "1/5 * * * * ?")//每分钟处理一次
    public void processTimeoutOrder(){
        log.info("定时处理超时订单：{}", LocalDateTime.now());
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
         List<Orders> ordersList = orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS,time);
          if(ordersList != null && ordersList.size() > 0){
              for (Orders orders : ordersList) {
                  orders.setStatus(Orders.CANCELLED);
                  orders.setCancelReason("订单超时，自动取消");
                  orders.setCancelTime(LocalDateTime.now());
                  orderMapper.update(orders);
              }

          }
    }

    /**
     * 处理一直处于配送中
     */
   // @Scheduled(cron = "0/5 * * * * ?")//每天凌晨1点出发一次
    public void processDeliveryOrder(){
        log.info("定时处理超时订单：{}", LocalDateTime.now());
        LocalDateTime time = LocalDateTime.now().plusMinutes(-60);
        List<Orders> ordersList = orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS,time);
        if(ordersList != null && ordersList.size() > 0){
            for (Orders orders : ordersList) {
                orders.setStatus(Orders.COMPLETED);
                orderMapper.update(orders);
            }

        }
    }
}
