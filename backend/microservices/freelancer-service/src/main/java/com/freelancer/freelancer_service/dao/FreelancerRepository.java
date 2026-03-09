package com.freelancer.freelancer_service.dao;

import com.freelancer.freelancer_service.model.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer, Integer> {
    Optional<Freelancer> findByUserId(Integer userId);

    @Query("SELECT f FROM freelancers_table f WHERE LOWER(f.skills) LIKE LOWER(CONCAT('%', :skill, '%'))")
    List<Freelancer> findBySkillContaining(@Param("skill") String skill);
}
