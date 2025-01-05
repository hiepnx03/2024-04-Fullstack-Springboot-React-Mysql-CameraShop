package com.example.demo.controller.admin;


import com.example.demo.dto.RevenueDTO;
import com.example.demo.entity.ResponseObject;
import com.example.demo.service.AnalyticService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/analytic")
@AllArgsConstructor
public class AnalyticController {
    private final AnalyticService analyticService;

    @GetMapping("/revenue")
    public ResponseEntity<ResponseObject> getRevenueMonth(@RequestParam(name = "month", defaultValue = "0") Integer month,
                                                          @RequestParam(name = "year") Integer year,
                                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                                          @RequestParam(name = "size", defaultValue = "10") int size) {
        try {
            Page<RevenueDTO> revenueDTOPage;
            if (month == 0) {
                revenueDTOPage = analyticService.getRevenueInYear(year, page, size);
            } else {
                revenueDTOPage = analyticService.getRevenueInMonth(month, year, page, size);
            }
            return ResponseEntity.ok().body(new ResponseObject("ok", "Get revenue month " + month + " successfully!", revenueDTOPage));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

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
            Page<RevenueDTO> revenueDTOPage = analyticService.getRevenueInWithDayMonthYearBetween(dayStart, dayEnd, monthStart, monthEnd, yearStart, yearEnd, page, size);
            return ResponseEntity.ok().body(new ResponseObject("ok", "Get revenue " + dayStart + "/" + monthStart + "/" + yearStart + "--" + dayEnd + "/" + monthEnd + "/" + yearEnd + " successfully!", revenueDTOPage));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }

    @GetMapping("/revenue/month-year-between")
    public ResponseEntity<ResponseObject> getRevenueMonthYearBetween(@RequestParam(name = "monthStart") Integer monthStart,
                                                                     @RequestParam(name = "monthEnd") Integer monthEnd,
                                                                     @RequestParam(name = "yearStart") Integer yearStart,
                                                                     @RequestParam(name = "yearEnd") Integer yearEnd,
                                                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                                                     @RequestParam(name = "size", defaultValue = "10") int size) {
        try {
            Page<RevenueDTO> revenueDTOPage = analyticService.getRevenueInWithMonthYearBetween(monthStart, monthEnd, yearStart, yearEnd, page, size);
            return ResponseEntity.ok().body(new ResponseObject("ok", "Get revenue " + monthStart + " successfully!", revenueDTOPage));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
        }
    }
}
