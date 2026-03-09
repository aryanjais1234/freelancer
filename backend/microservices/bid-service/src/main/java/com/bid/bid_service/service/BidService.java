package com.bid.bid_service.service;

import com.bid.bid_service.dao.BidRepository;
import com.bid.bid_service.dto.BidDto;
import com.bid.bid_service.dto.BidRequest;
import com.bid.bid_service.feign.ProjectInterface;
import com.bid.bid_service.model.Bid;
import com.bid.bid_service.model.BidStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BidService {

    private static final Logger log = LoggerFactory.getLogger(BidService.class);

    private final BidRepository bidRepository;
    private final ProjectInterface projectInterface;

    public BidDto submitBid(BidRequest request) {
        if (bidRepository.existsByProjectIdAndFreelancerId(request.getProjectId(), request.getFreelancerId())) {
            throw new RuntimeException("You have already submitted a bid for this project");
        }
        Bid bid = new Bid();
        bid.setProjectId(request.getProjectId());
        bid.setFreelancerId(request.getFreelancerId());
        bid.setProposalText(request.getProposalText());
        bid.setBidAmount(request.getBidAmount());
        bid.setStatus(BidStatus.PENDING);
        Bid saved = bidRepository.save(bid);
        return mapToDto(saved);
    }

    public List<BidDto> getBidsByProject(Integer projectId) {
        return bidRepository.findByProjectId(projectId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<BidDto> getBidsByFreelancer(Integer freelancerId) {
        return bidRepository.findByFreelancerId(freelancerId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public BidDto acceptBid(Integer bidId) {
        Bid bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new RuntimeException("Bid not found with id: " + bidId));

        if (bid.getStatus() != BidStatus.PENDING) {
            throw new RuntimeException("Only pending bids can be accepted");
        }

        bid.setStatus(BidStatus.ACCEPTED);
        bidRepository.save(bid);

        // Reject all other pending bids for this project
        List<Bid> otherBids = bidRepository.findByProjectId(bid.getProjectId());
        for (Bid other : otherBids) {
            if (!other.getBidId().equals(bidId) && other.getStatus() == BidStatus.PENDING) {
                other.setStatus(BidStatus.REJECTED);
                bidRepository.save(other);
            }
        }

        // Update project status to IN_PROGRESS and set assignedFreelancerId via Feign
        projectInterface.updateProjectStatus(bid.getProjectId(), "IN_PROGRESS", bid.getFreelancerId());
        log.info("Project {} status updated to IN_PROGRESS, assigned to freelancer {}",
                bid.getProjectId(), bid.getFreelancerId());

        return mapToDto(bid);
    }

    public BidDto rejectBid(Integer bidId) {
        Bid bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new RuntimeException("Bid not found with id: " + bidId));

        if (bid.getStatus() != BidStatus.PENDING) {
            throw new RuntimeException("Only pending bids can be rejected");
        }

        bid.setStatus(BidStatus.REJECTED);
        return mapToDto(bidRepository.save(bid));
    }

    public BidDto getBidById(Integer bidId) {
        Bid bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new RuntimeException("Bid not found with id: " + bidId));
        return mapToDto(bid);
    }

    private BidDto mapToDto(Bid bid) {
        BidDto dto = new BidDto();
        dto.setBidId(bid.getBidId());
        dto.setProjectId(bid.getProjectId());
        dto.setFreelancerId(bid.getFreelancerId());
        dto.setProposalText(bid.getProposalText());
        dto.setBidAmount(bid.getBidAmount());
        dto.setStatus(bid.getStatus());
        dto.setCreatedAt(bid.getCreatedAt());
        dto.setUpdatedAt(bid.getUpdatedAt());
        return dto;
    }
}
