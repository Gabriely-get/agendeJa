package com.fatec.tcc.agendeja.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fatec.tcc.agendeja.Builders.JsonResponseBuilder;
import com.fatec.tcc.agendeja.Entities.RequestTemplate.ScheduleBody;
import com.fatec.tcc.agendeja.Entities.Schedule;
import com.fatec.tcc.agendeja.Services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/agenda/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private JsonResponseBuilder jsonResponseBuilder;

    @GetMapping("/{id}")
    public ResponseEntity<ObjectNode> getScheduleById(@PathVariable("id") Long id) {
        try {
            Schedule schedule = this.scheduleService.getById(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withBody(schedule).build(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    public ResponseEntity<ObjectNode> getSchedules() {
        try {
            List<Schedule> schedules = this.scheduleService.getAll();

            return new ResponseEntity<>(this.jsonResponseBuilder.withList(schedules).build(), HttpStatus.OK);
        } catch (RuntimeException | JsonProcessingException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/by/{id}")
    public ResponseEntity<ObjectNode> getAlSchedulesByUserId(@PathVariable("id") Long id) {
        try {
            List<Schedule> schedules = this.scheduleService.getByUserId(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withList(schedules).build(), HttpStatus.OK);
        } catch (RuntimeException | JsonProcessingException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/")
    public ResponseEntity<ObjectNode> createSchedule(@RequestBody ScheduleBody scheduleBody) {
        try {

            this.scheduleService.createSchedule(
                    scheduleBody.getPortfolioJobId(),
                    scheduleBody.getDate(),
                    scheduleBody.getTime());

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<ObjectNode> scheduleSchedule(@PathVariable("id") Long scheduleId, @RequestBody ScheduleBody scheduleBody) {
        try {

            this.scheduleService.scheduleASchedule(
                    scheduleBody.getUserId(),
                    scheduleId
            );

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjectNode> updateSchedule(@PathVariable("id") Long scheduleId, @RequestBody ScheduleBody scheduleBody) {
        try {

            this.scheduleService.updateSchedule(
                    scheduleId,
                    scheduleBody.getUserId(),
                    scheduleBody.getDate(),
                    scheduleBody.getTime()
            );

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

}
