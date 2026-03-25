package com.example.tea.service.impl;

import com.example.tea.entity.dto.Address.AddressDTO;
import com.example.tea.entity.pojo.Address.Address;
import com.example.tea.mapper.AddressMapper;
import com.example.tea.service.AddressService;
import com.example.tea.utils.ThreadLocalUserIdUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 地址业务层实现类
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Resource
    private AddressMapper addressMapper;

    /**
     * 新增地址：若设为默认，先取消原有默认
     */
    @Override
    @Transactional(rollbackFor = Exception.class) // 事务保证操作原子性
    public boolean addAddress(AddressDTO addressDTO) {
        // 1. 如果设为默认地址，先取消该用户原有默认
        if (1 == addressDTO.getIsDefault()) {
            addressMapper.cancelDefault(ThreadLocalUserIdUtil.getCurrentId());
        }
        Address address = Address.builder().build();
        BeanUtils.copyProperties(addressDTO, address);
        address.setUserId(ThreadLocalUserIdUtil.getCurrentId());
        // 2. 新增地址
        return addressMapper.addAddress(address) > 0;
    }

    /**
     * 删除地址：仅允许删除自己的地址
     */
    @Override
    public boolean deleteById(Long id) {
        return addressMapper.deleteById(id, ThreadLocalUserIdUtil.getCurrentId()) > 0;
    }

    /**
     * 修改地址：若设为默认，先取消原有默认
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAddress(AddressDTO addressDTO, Long id) {
        // 1. 如果设为默认地址，先取消该用户原有默认
        if (1 == addressDTO.getIsDefault()) {
            addressMapper.cancelDefault(ThreadLocalUserIdUtil.getCurrentId());
        }
        Address address = Address.builder().build();
        BeanUtils.copyProperties(addressDTO, address);
        address.setUserId(ThreadLocalUserIdUtil.getCurrentId());
        address.setId(id);
        // 2. 修改地址
        return addressMapper.updateAddress(address) > 0;
    }


    /**
     * 查询用户所有地址
     */
    @Override
    public List<Address> listByUserId() {
        return addressMapper.listByUserId(ThreadLocalUserIdUtil.getCurrentId());
    }
}
