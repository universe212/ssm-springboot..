package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName: OrderDetailMapper
 * Package: com.sky.mapper
 * Description
 *
 * @Author HuanZ
 * @Create 2023/12/12 11:05
 * @Version 1.0
 */
@Mapper
public interface OrderDetailMapper {
    /**
     * 批量插入
     * @param orderDetailList
     */
    void insertBatch(List<OrderDetail> orderDetailList);

    /**
     * 根据订单id查询订单详情
     * @param orderId
     * @return
     */
    @Select("select * from order_detail where order_id = #{orderId}")
    List<OrderDetail> getByOrderId(Long orderId);
}
