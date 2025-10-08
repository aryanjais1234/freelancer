package com.freelancer.freelancer_service.service;

import com.freelancer.freelancer_service.dao.FreelancerRepository;
import com.freelancer.freelancer_service.dto.FreelancerDto;
import com.freelancer.freelancer_service.dto.ProjectDto;
import com.freelancer.freelancer_service.feign.ProjectInterface;
import com.freelancer.freelancer_service.model.Freelancer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FreelancerService {
    private final FreelancerRepository freelancerRepository;
    private final ProjectInterface projectInterface;
    public FreelancerDto createFreelancer(FreelancerDto freelancerDto) {
        Freelancer freelancer = new Freelancer();
        mapDtoToModel(freelancer, freelancerDto);
        Freelancer savedFreelancer = freelancerRepository.save(freelancer);
        return freelancerDto;

    }

    public void mapDtoToModel(Freelancer freelancer, FreelancerDto freelancerDto) {
        freelancer.setUserId(freelancerDto.getUserId());
        freelancer.setName(freelancerDto.getName());
        freelancer.setEmail(freelancerDto.getEmail());
        freelancer.setPassword(freelancerDto.getPassword());
    }

    public ProjectDto getProjectById(Integer id) {
        return projectInterface.getProjectById(id);
    }
}
