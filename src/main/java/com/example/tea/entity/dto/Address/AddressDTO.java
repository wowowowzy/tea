package com.example.tea.entity.dto.Address;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AddressDTO {


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


}
