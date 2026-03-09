package com.bid.bid_service.controller;

import com.bid.bid_service.dto.BidDto;
import com.bid.bid_service.dto.BidRequest;
import com.bid.bid_service.service.BidService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bids")
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;

    /**
     * Submit a new bid/proposal for a project.
     * Freelancer ID is passed via the header (set by API gateway from JWT).
     */
    @PostMapping("/submit")
    public ResponseEntity<BidDto> submitBid(@Valid @RequestBody BidRequest request,
                                            @RequestHeader(value = "userId", required = false) String userId) {
        if (userId != null && request.getFreelancerId() == null) {
            request.setFreelancerId(Integer.parseInt(userId));
        }
        return ResponseEntity.ok(bidService.submitBid(request));
    }

    /**
     * Get all bids for a specific project (used by client to review proposals).
     */
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<BidDto>> getBidsByProject(@PathVariable Integer projectId) {
        return ResponseEntity.ok(bidService.getBidsByProject(projectId));
    }

    /**
     * Get all bids submitted by a specific freelancer.
     */
    @GetMapping("/freelancer/{freelancerId}")
    public ResponseEntity<List<BidDto>> getBidsByFreelancer(@PathVariable Integer freelancerId) {
        return ResponseEntity.ok(bidService.getBidsByFreelancer(freelancerId));
    }

    /**
     * Get a specific bid by ID.
     */
    @GetMapping("/{bidId}")
    public ResponseEntity<BidDto> getBidById(@PathVariable Integer bidId) {
        return ResponseEntity.ok(bidService.getBidById(bidId));
    }

    /**
     * Accept a bid (client action). This also rejects all other pending bids for the same project
     * and updates the project status to IN_PROGRESS.
     */
    @PutMapping("/{bidId}/accept")
    public ResponseEntity<BidDto> acceptBid(@PathVariable Integer bidId) {
        return ResponseEntity.ok(bidService.acceptBid(bidId));
    }

    /**
     * Reject a bid (client action).
     */
    @PutMapping("/{bidId}/reject")
    public ResponseEntity<BidDto> rejectBid(@PathVariable Integer bidId) {
        return ResponseEntity.ok(bidService.rejectBid(bidId));
    }
}
