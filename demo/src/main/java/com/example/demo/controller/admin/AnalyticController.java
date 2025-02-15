package com.example.demo.controller.admin;

import com.example.demo.dto.RevenueDTO;
import com.example.demo.entity.ResponseObject;
import com.example.demo.service.AnalyticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin/analytic")
@AllArgsConstructor
public class AnalyticController {
    private final AnalyticService analyticService;

    @Operation(summary = "Get revenue for a specific month or year", description = "Retrieve revenue data for a specific month and year or for the entire year.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Revenue data retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters or error retrieving data")
    })
    @GetMapping("/revenue")
    public ResponseEntity<ResponseObject> getRevenueMonth(@RequestParam(name = "month", defaultValue = "0") Integer month,
                                                          @RequestParam(name = "year") Integer year,
                                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                                          @RequestParam(name = "size", defaultValue = "10") int size) {
        try {
            log.info("Fetching revenue for month: {}, year: {}, page: {}, size: {}", month, year, page, size);
            Page<RevenueDTO> revenueDTOPage;
            if (month == 0) {
                revenueDTOPage = analyticService.getRevenueInYear(year, page, size);
            } else {
                revenueDTOPage = analyticService.getRevenueInMonth(month, year, page, size);
            }
            return ResponseEntity.ok().body(new ResponseObject("ok", "Get revenue month " + month + " successfully!", revenueDTOPage));
        } catch (Exception e) {
            log.error("Error fetching revenue data", e);
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @Operation(summary = "Get revenue between specific days, months, and years", description = "Retrieve revenue data for a range of days, months, and years.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Revenue data retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters or error retrieving data")
    })
    @GetMapping("/revenue/day-month-year-between")
    public ResponseEntity<ResponseObject> getRevenueDayMonthYearBetween(@RequestParam(name = "dayStart") Integer dayStart,
                                                                        @RequestParam(name = "dayEnd") Integer dayEnd,
                                                                        @RequestParam(name = "monthStart") Integer monthStart,
                                                                        @RequestParam(name = "monthEnd") Integer monthEnd,
                                                                        @RequestParam(name = "yearStart") Integer yearStart,
                                                                        @RequestParam(name = "yearEnd") Integer yearEnd,
                                                                        @RequestParam(name = "page", defaultValue = "0") int page,
                                                                        @RequestParam(name = "size", defaultValue = "10") int size) {
        try {
            log.info("Fetching revenue from {}-{}-{} to {}-{}-{}, page: {}, size: {}", dayStart, monthStart, yearStart, dayEnd, monthEnd, yearEnd, page, size);
            Page<RevenueDTO> revenueDTOPage = analyticService.getRevenueInWithDayMonthYearBetween(dayStart, dayEnd, monthStart, monthEnd, yearStart, yearEnd, page, size);
            return ResponseEntity.ok().body(new ResponseObject("ok", "Get revenue from " + dayStart + "/" + monthStart + "/" + yearStart + " to " + dayEnd + "/" + monthEnd + "/" + yearEnd + " successfully!", revenueDTOPage));
        } catch (Exception e) {
            log.error("Error fetching revenue data", e);
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @Operation(summary = "Get revenue between specific months and years", description = "Retrieve revenue data for a range of months and years.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Revenue data retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters or error retrieving data")
    })
    @GetMapping("/revenue/month-year-between")
    public ResponseEntity<ResponseObject> getRevenueMonthYearBetween(@RequestParam(name = "monthStart") Integer monthStart,
                                                                     @RequestParam(name = "monthEnd") Integer monthEnd,
                                                                     @RequestParam(name = "yearStart") Integer yearStart,
                                                                     @RequestParam(name = "yearEnd") Integer yearEnd,
                                                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                                                     @RequestParam(name = "size", defaultValue = "10") int size) {
        try {
            log.info("Fetching revenue from month {} year {} to month {} year {}, page: {}, size: {}", monthStart, yearStart, monthEnd, yearEnd, page, size);
            Page<RevenueDTO> revenueDTOPage = analyticService.getRevenueInWithMonthYearBetween(monthStart, monthEnd, yearStart, yearEnd, page, size);
            return ResponseEntity.ok().body(new ResponseObject("ok", "Get revenue between " + monthStart + "/" + yearStart + " and " + monthEnd + "/" + yearEnd + " successfully!", revenueDTOPage));
        } catch (Exception e) {
            log.error("Error fetching revenue data", e);
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }
}
