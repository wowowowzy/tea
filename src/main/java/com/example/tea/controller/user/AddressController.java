package com.example.tea.controller.user;

import com.example.tea.entity.dto.Address.AddressDTO;
import com.example.tea.entity.pojo.Address.Address;
import com.example.tea.entity.pojo.Result;
import com.example.tea.service.AddressService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/address")
public class AddressController {

    @Resource
    private AddressService addressService;

    /**
     * 新增地址
     * @return
     */
    @PostMapping("/add")
    public Result addAddress(@RequestBody AddressDTO address) {
        // 简单参数校验
        if ( address.getName() == null || address.getPhone() == null ||
                address.getProvince() == null || address.getCity() == null || address.getDistrict() == null ||
                address.getDetail() == null) {
            return Result.error("必填字段不能为空");
        }
        // 处理默认地址默认值
        if (address.getIsDefault() == null) {
            address.setIsDefault(0);
        }
        boolean success = addressService.addAddress(address);
        return success ? Result.success() : Result.error("新增地址失败");
    }

    /**
     * 删除地址
     * @return
     */
    @DeleteMapping("/delete")
    public Result deleteById(Long id) {
        boolean success = addressService.deleteById(id);
        return success ? Result.success() : Result.error("删除地址失败");
    }

    /**
     * 修改地址
     * @param address
     * @param id
     * @return
     */
    @PutMapping("/update")
    public Result updateAddress(@RequestBody AddressDTO address,
                                @RequestParam(value = "addressId") Long id) {
        // 简单参数校验

        boolean success = addressService.updateAddress(address,id);
        return success ? Result.success() : Result.error("修改地址失败");
    }


    /**
     * 查询用户所有地址
     * @return
     */
    @GetMapping("/list")
    public Result listByUserId() {
        List<Address> addressList = addressService.listByUserId();
        return Result.success(addressList);
    }
}
