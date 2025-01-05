package com.example.demo.service;

import com.example.demo.dto.RevenueDTO;
import org.springframework.data.domain.Page;

public interface AnalyticService {
    Page<RevenueDTO> getRevenueInMonth(Integer month, Integer year, Integer page, Integer size);
    Page<RevenueDTO> getRevenueInWithDayMonthYearBetween(Integer dayStart,Integer dayEnd,Integer monthStart,Integer monthEnd,Integer yearStart,Integer yearEnd,Integer page,Integer size);
    Page<RevenueDTO> getRevenueInWithMonthYearBetween(Integer monthStart,Integer monthEnd,Integer yearStart,Integer yearEnd,Integer page,Integer size);
    Page<RevenueDTO> getRevenueInYear(Integer year,Integer page,Integer size);
}
