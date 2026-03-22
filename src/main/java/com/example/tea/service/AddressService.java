package com.example.tea.service;


import com.example.tea.entity.dto.Address.AddressDTO;
import com.example.tea.entity.pojo.Address.Address;

import java.util.List;

/**
 * 地址业务层接口
 */
public interface AddressService {

    /**
     * 新增地址（自动处理默认地址逻辑）
     */
    boolean addAddress(AddressDTO addressDTO);

    /**
     * 删除地址
     */
    boolean deleteById(Long id);

    /**
     * 修改地址（自动处理默认地址逻辑）
     */
    boolean updateAddress(AddressDTO addressDTO, Long id);

    /**
     * 查询用户所有地址
     */
    List<Address> listByUserId();
}
