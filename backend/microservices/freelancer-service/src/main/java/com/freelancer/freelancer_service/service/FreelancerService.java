package com.freelancer.freelancer_service.service;

import com.freelancer.freelancer_service.dao.FreelancerRepository;
import com.freelancer.freelancer_service.dto.FreelancerDto;
import com.freelancer.freelancer_service.dto.ProjectDto;
import com.freelancer.freelancer_service.feign.ProjectInterface;
import com.freelancer.freelancer_service.model.Freelancer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FreelancerService {
    private final FreelancerRepository freelancerRepository;
    private final ProjectInterface projectInterface;

    public FreelancerDto createFreelancer(FreelancerDto freelancerDto) {
        Freelancer freelancer = new Freelancer();
        mapDtoToModel(freelancer, freelancerDto);
        freelancerRepository.save(freelancer);
        return freelancerDto;
    }

    public FreelancerDto getFreelancerByUserId(Integer userId) {
        Optional<Freelancer> freelancer = freelancerRepository.findByUserId(userId);
        return freelancer.map(this::mapModelToDto).orElse(null);
    }

    public FreelancerDto updateFreelancerProfile(Integer userId, FreelancerDto dto) {
        Optional<Freelancer> optFreelancer = freelancerRepository.findByUserId(userId);
        if (optFreelancer.isPresent()) {
            Freelancer f = optFreelancer.get();
            if (dto.getSkills() != null) f.setSkills(dto.getSkills());
            if (dto.getAvailabilityStatus() != null) f.setAvailabilityStatus(dto.getAvailabilityStatus());
            if (dto.getProfileSummary() != null) f.setProfileSummary(dto.getProfileSummary());
            if (dto.getPortfolioLink() != null) f.setPortfolioLink(dto.getPortfolioLink());
            if (dto.getLocation() != null) f.setLocation(dto.getLocation());
            if (dto.getProfilePictureUrl() != null) f.setProfilePictureUrl(dto.getProfilePictureUrl());
            if (dto.getPhoneNumber() != null) f.setPhoneNumber(dto.getPhoneNumber());
            if (dto.getSocialLinks() != null) f.setSocialLinks(dto.getSocialLinks());
            if (dto.getCertifications() != null) f.setCertifications(dto.getCertifications());
            if (dto.getLanguages() != null) f.setLanguages(dto.getLanguages());
            if (dto.getHourlyRate() != null) f.setHourlyRate(dto.getHourlyRate());
            if (dto.getEducation() != null) f.setEducation(dto.getEducation());
            if (dto.getWorkExperience() != null) f.setWorkExperience(dto.getWorkExperience());
            if (dto.getAdditionalInfo() != null) f.setAdditionalInfo(dto.getAdditionalInfo());
            freelancerRepository.save(f);
            return mapModelToDto(f);
        }
        return null;
    }

    public List<FreelancerDto> searchBySkill(String skill) {
        return freelancerRepository.findBySkillContaining(skill)
                .stream()
                .map(this::mapModelToDto)
                .collect(Collectors.toList());
    }

    public List<FreelancerDto> getAllFreelancers() {
        return freelancerRepository.findAll()
                .stream()
                .map(this::mapModelToDto)
                .collect(Collectors.toList());
    }

    public void mapDtoToModel(Freelancer freelancer, FreelancerDto freelancerDto) {
        freelancer.setUserId(freelancerDto.getUserId());
        freelancer.setName(freelancerDto.getName());
        freelancer.setEmail(freelancerDto.getEmail());
        freelancer.setPassword(freelancerDto.getPassword());
        if (freelancerDto.getSkills() != null) freelancer.setSkills(freelancerDto.getSkills());
        if (freelancerDto.getAvailabilityStatus() != null) freelancer.setAvailabilityStatus(freelancerDto.getAvailabilityStatus());
        if (freelancerDto.getProfileSummary() != null) freelancer.setProfileSummary(freelancerDto.getProfileSummary());
        if (freelancerDto.getPortfolioLink() != null) freelancer.setPortfolioLink(freelancerDto.getPortfolioLink());
        if (freelancerDto.getLocation() != null) freelancer.setLocation(freelancerDto.getLocation());
        if (freelancerDto.getHourlyRate() != null) freelancer.setHourlyRate(freelancerDto.getHourlyRate());
    }

    private FreelancerDto mapModelToDto(Freelancer f) {
        FreelancerDto dto = new FreelancerDto();
        dto.setFreelancerId(f.getFreelancerId());
        dto.setUserId(f.getUserId());
        dto.setName(f.getName());
        dto.setEmail(f.getEmail());
        dto.setSkills(f.getSkills());
        dto.setRating(f.getRating());
        dto.setCompletedProjects(f.getCompletedProjects());
        dto.setAvailabilityStatus(f.getAvailabilityStatus());
        dto.setProfileSummary(f.getProfileSummary());
        dto.setPortfolioLink(f.getPortfolioLink());
        dto.setLocation(f.getLocation());
        dto.setProfilePictureUrl(f.getProfilePictureUrl());
        dto.setPhoneNumber(f.getPhoneNumber());
        dto.setSocialLinks(f.getSocialLinks());
        dto.setCertifications(f.getCertifications());
        dto.setLanguages(f.getLanguages());
        dto.setHourlyRate(f.getHourlyRate());
        dto.setEducation(f.getEducation());
        dto.setWorkExperience(f.getWorkExperience());
        dto.setAdditionalInfo(f.getAdditionalInfo());
        dto.setCreatedAt(f.getCreatedAt());
        dto.setUpdatedAt(f.getUpdatedAt());
        return dto;
    }

    public ProjectDto getProjectById(Integer id) {
        return projectInterface.getProjectById(id);
    }

    public List<ProjectDto> getOpenProjects() {
        return projectInterface.getOpenProjects();
    }
}
