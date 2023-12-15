package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

/**
 * ClassName: ShoppingCartService
 * Package: com.sky.service
 * Description
 *
 * @Author HuanZ
 * @Create 2023/12/11 15:25
 * @Version 1.0
 */
public interface ShoppingCartService {
    void addShopingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * 返回购物车
     * @return
     */
    List<ShoppingCart> showShoppingCart();

    /**
     * 清空购物车
     */
    void cleanShoppingCart();

    void subShopingCart(ShoppingCartDTO shoppingCartDTO);
}
