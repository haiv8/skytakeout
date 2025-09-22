package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.anno.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    @AutoFill(OperationType.INSERT)
    @Options(useGeneratedKeys = true,keyProperty = "id") //获取主键值赋值给id属性
    @Insert("insert into dish values (null,#{name},#{categoryId},#{price},#{image},#{description}," +
            "#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void insert(Dish dish);


    /**
     * 条件查询菜品列表
     * @param dto
     * @return
     */
    Page<DishVO> list(DishPageQueryDTO dto);

    Dish selectById(Long id);

    void deleteBatch(List<Long> ids);

    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    List<Dish> listBy(Dish dish);
}
