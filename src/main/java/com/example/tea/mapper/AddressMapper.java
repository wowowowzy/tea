package com.example.tea.mapper;

import com.example.tea.entity.pojo.Address.Address;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 地址数据访问层
 */
@Mapper
public interface AddressMapper {

    /**
     * 新增地址
     */
    @Insert("INSERT INTO address (user_id, name, phone, province, city, district, detail, postal_code, is_default, create_time, update_time) " +
            "VALUES (#{userId}, #{name}, #{phone}, #{province}, #{city}, #{district}, #{detail}, #{postalCode}, #{isDefault}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id") // 回填自增主键
    int addAddress(Address address);

    /**
     * 删除地址
     */
    @Delete("DELETE FROM address WHERE id = #{id} AND user_id = #{userId}")
    int deleteById(@Param("id") Long id, @Param("userId") Long userId);

    /**
     * 修改地址
     */
    @Update("UPDATE address SET name = #{name}, phone = #{phone}, province = #{province}, city = #{city}, " +
            "district = #{district}, detail = #{detail}, postal_code = #{postalCode}, is_default = #{isDefault}, update_time = NOW() " +
            "WHERE id = #{id} AND user_id = #{userId}")
    int updateAddress(Address address);


    /**
     * 根据用户ID查询所有地址
     */
    @Select("SELECT * FROM address WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<Address> listByUserId(@Param("userId") Long userId);

    /**
     * 取消用户原有默认地址
     */
    @Update("UPDATE address SET is_default = 0, update_time = NOW() WHERE user_id = #{userId} AND is_default = 1")
    int cancelDefault(@Param("userId") Long userId);
}
