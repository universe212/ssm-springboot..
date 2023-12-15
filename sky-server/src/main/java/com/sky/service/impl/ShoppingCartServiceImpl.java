package com.sky.service.impl;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName: ShoppingCartServiceImpl
 * Package: com.sky.service.impl
 * Description
 *
 * @Author HuanZ
 * @Create 2023/12/11 15:27
 * @Version 1.0
 */
@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    public void addShopingCart(ShoppingCartDTO shoppingCartDTO) {
        //判断当前加入购物车中商品是否已经存在
       ShoppingCart shoppingCart = new ShoppingCart();
       BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
       Long userId = BaseContext.getCurrentId();
       shoppingCart.setUserId(userId);

        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        //如果已经存在了，只需要数量加1
       if(list != null && list.size() > 0){
            ShoppingCart  cart = list.get(0);
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.updateNumberById(cart);
       }else {
           //如果不存在，需要插入一条购物车数据
           Long dishId = shoppingCartDTO.getDishId();
           //判断本次是菜品还是套餐
           if(dishId != null){
               Dish dish = dishMapper.getById(dishId);
               shoppingCart.setName(dish.getName());
               shoppingCart.setImage(dish.getImage());
               shoppingCart.setAmount(dish.getPrice());
           }
           else {
               //本次是套餐
               Long setmealId = shoppingCartDTO.getSetmealId();
               Setmeal setmeal = setmealMapper.getById(setmealId);
               shoppingCart.setName(setmeal.getName());
               shoppingCart.setImage(setmeal.getImage());
               shoppingCart.setAmount(setmeal.getPrice());
           }
           shoppingCart.setNumber(1);
           shoppingCart.setCreateTime(LocalDateTime.now());
           shoppingCartMapper.insert(shoppingCart);

       }
    }

    /**
     * 查看购物车
     * @return
     */
    public List<ShoppingCart> showShoppingCart() {
        //获取到当前微信用户得Id
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(userId)
                .build();
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        return list;
    }

    /**
     * 清空购物车
     */
    public void cleanShoppingCart() {
        //获取当前微信用户Id
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.deleteByUserId(userId);
    }

    /**
     * 删除购物车里面得东西
     * @param shoppingCartDTO
     */
    @Override
    public void subShopingCart(ShoppingCartDTO shoppingCartDTO) {
        //判断当前加入购物车中商品是否已经存在
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        //设置查询条件
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
            if(list != null && list.size() > 0){
                shoppingCart = list.get(0);
                Integer number = shoppingCart.getNumber();
                if(number == 1){
                    //当前分数为1直接删除
                    shoppingCartMapper.deleteById(shoppingCart.getId());
                }else {
                    shoppingCart.setNumber(shoppingCart.getNumber() - 1);
                    shoppingCartMapper.updateNumberById(shoppingCart);
                }
            }
    }
}
