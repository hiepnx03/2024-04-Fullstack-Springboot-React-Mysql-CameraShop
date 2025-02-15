package com.example.camerashop_be.service.impl;


import com.example.camerashop_be.dto.RevenueDTO;
import com.example.camerashop_be.repository.RevenueRepo;
import com.example.camerashop_be.service.IAnalyticService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AnalyticService implements IAnalyticService {
	private final RevenueRepo revenueRepo;
	@Override
	public Page<RevenueDTO> getRevenueInMonth(Integer month, Integer year, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page,size);
		return revenueRepo.getRevenueInMonth(month,year,pageable);
	}

	@Override
	public Page<RevenueDTO> getRevenueInWithDayMonthYearBetween(Integer dayStart, Integer dayEnd, Integer monthStart, Integer monthEnd, Integer yearStart, Integer yearEnd, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page,size);
		return revenueRepo.getRevenueWithDayMonthYear(dayStart,dayEnd,monthStart,monthEnd,yearStart,yearEnd,pageable);

	}

	@Override
	public Page<RevenueDTO> getRevenueInWithMonthYearBetween(Integer monthStart, Integer monthEnd, Integer yearStart, Integer yearEnd, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page,size);
		return revenueRepo.getRevenueWithMonthYear(monthStart,monthEnd,yearStart,yearEnd,pageable);
	}

	@Override
	public Page<RevenueDTO> getRevenueInYear(Integer year, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page,size);
		return revenueRepo.getRevenueInYear(year,pageable);
	}
}
