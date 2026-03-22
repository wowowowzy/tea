package com.example.tea.entity.pojo.Address;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户地址实体类（纯MyBatis适配）
 * 对应数据库表：address
 * 字段名与数据库列名通过驼峰命名自动映射（如user_id ↔ userId）
 */
@Data // Lombok注解，自动生成get/set/toString等方法（无Lombok则手动添加）
@Builder
public class Address {

    /**
     * 主键ID（自增）
     * 数据库列：id
     */
    private Long id;

    /**
     * 关联用户ID
     * 数据库列：user_id（MyBatis默认驼峰转换可匹配userId）
     */
    private Long userId;

    /**
     * 收件人姓名
     * 数据库列：name
     */
    private String name;

    /**
     * 收件人手机号
     * 数据库列：phone
     */
    private String phone;

    /**
     * 省份
     * 数据库列：province
     */
    private String province;

    /**
     * 城市
     * 数据库列：city
     */
    private String city;

    /**
     * 区县
     * 数据库列：district
     */
    private String district;

    /**
     * 详细地址
     * 数据库列：detail
     */
    private String detail;

    /**
     * 邮政编码
     * 数据库列：postal_code（驼峰转换匹配postalCode）
     */
    private String postalCode;

    /**
     * 是否默认地址：0-非默认，1-默认
     * 数据库列：is_default（驼峰转换匹配isDefault）
     */
    private Integer isDefault;

    /**
     * 创建时间
     * 数据库列：create_time（驼峰转换匹配createTime）
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     * 数据库列：update_time（驼峰转换匹配updateTime）
     */
    private LocalDateTime updateTime;

}
