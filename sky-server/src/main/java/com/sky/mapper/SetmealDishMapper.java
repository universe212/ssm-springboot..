package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName: SetmealDishMapper
 * Package: com.sky.mapper
 * Description
 *
 * @Author HuanZ
 * @Create 2023/12/6 16:57
 * @Version 1.0
 */
@Mapper
public interface SetmealDishMapper {
    /**
     * 根据套餐id查菜品id
     * @param
     * @return
     */
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);
}
