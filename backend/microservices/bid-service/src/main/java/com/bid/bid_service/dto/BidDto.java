package com.bid.bid_service.dto;

import com.bid.bid_service.model.BidStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidDto {
    private Integer bidId;
    private Integer projectId;
    private Integer freelancerId;
    private String proposalText;
    private Double bidAmount;
    private BidStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
