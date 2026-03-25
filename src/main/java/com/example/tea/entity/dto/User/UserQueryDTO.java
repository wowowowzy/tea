package com.example.tea.entity.dto.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserQueryDTO {
    private Integer page;

    private Integer pageSize;

    private String username;
}
