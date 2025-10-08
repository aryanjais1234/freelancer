package com.freelancer.freelancer_service.dao;

import com.freelancer.freelancer_service.model.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer,Integer> {
}
