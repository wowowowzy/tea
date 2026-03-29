package com.example.tea.entity.dto.Community;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RabbitMQDTO implements Serializable {
    private Long postId;
    private Long weight;
    private Long userId;
}
