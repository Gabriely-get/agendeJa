package com.fatec.tcc.agendify.Controllers;

import com.fatec.tcc.agendify.Entities.RequestTemplate.BusinessHoursByDayOfWeek;
import com.fatec.tcc.agendify.Entities.RequestTemplate.Hour;
import com.fatec.tcc.agendify.Services.BusinessHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/agenda")
public class BusinessHoursController {

    @Autowired
    private BusinessHourService businessHourService;

    @GetMapping("/hours/")
    public ResponseEntity<?> getBusinessHoursByDay(@RequestBody BusinessHoursByDayOfWeek data) {
        try {
            List<String> times = this.businessHourService.getBusinessHoursByDay(data);

            return ResponseEntity.ok(times);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/hours/filter")
    public ResponseEntity<?> getFilteredBusinessHoursByDay(@RequestBody BusinessHoursByDayOfWeek data) {
        try {
            List<Hour> hours = this.businessHourService.getFilteredBusinessHoursByDay(data);

            return ResponseEntity.ok(hours);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
