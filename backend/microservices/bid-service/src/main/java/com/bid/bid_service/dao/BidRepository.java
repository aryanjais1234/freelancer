package com.bid.bid_service.dao;

import com.bid.bid_service.model.Bid;
import com.bid.bid_service.model.BidStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Integer> {
    List<Bid> findByProjectId(Integer projectId);
    List<Bid> findByFreelancerId(Integer freelancerId);
    List<Bid> findByProjectIdAndStatus(Integer projectId, BidStatus status);
    boolean existsByProjectIdAndFreelancerId(Integer projectId, Integer freelancerId);
}
