package com.bid.bid_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BidRequest {

    @NotNull(message = "Project ID is required")
    private Integer projectId;

    @NotNull(message = "Freelancer ID is required")
    private Integer freelancerId;

    private String proposalText;

    @NotNull(message = "Bid amount is required")
    @Positive(message = "Bid amount must be positive")
    private Double bidAmount;
}
