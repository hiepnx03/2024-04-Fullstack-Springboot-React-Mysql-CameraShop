package com.example.demo.service.impl;

import com.example.demo.dto.RevenueDTO;
import com.example.demo.repository.RevenueRepository;
import com.example.demo.service.AnalyticService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnalyticServiceImpl implements AnalyticService {
    private final RevenueRepository revenueRepository;
    @Override
    public Page<RevenueDTO> getRevenueInMonth(Integer month,Integer year,Integer page,Integer size) {
        Pageable pageable = PageRequest.of(page,size);
        return revenueRepository.getRevenueInMonth(month,year,pageable);
    }

    @Override
    public Page<RevenueDTO> getRevenueInWithDayMonthYearBetween(Integer dayStart, Integer dayEnd, Integer monthStart, Integer monthEnd, Integer yearStart, Integer yearEnd, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page,size);
        return revenueRepository.getRevenueWithDayMonthYear(dayStart,dayEnd,monthStart,monthEnd,yearStart,yearEnd,pageable);

    }

    @Override
    public Page<RevenueDTO> getRevenueInWithMonthYearBetween(Integer monthStart, Integer monthEnd, Integer yearStart, Integer yearEnd, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page,size);
        return revenueRepository.getRevenueWithMonthYear(monthStart,monthEnd,yearStart,yearEnd,pageable);
    }

    @Override
    public Page<RevenueDTO> getRevenueInYear(Integer year, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page,size);
        return revenueRepository.getRevenueInYear(year,pageable);
    }
}
