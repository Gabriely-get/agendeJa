package com.fatec.tcc.agendify.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fatec.tcc.agendify.Builders.JsonResponseBuilder;
import com.fatec.tcc.agendify.Entities.RequestTemplate.*;
import com.fatec.tcc.agendify.Entities.RequestTemplate.ErrorResponseAPI;
import com.fatec.tcc.agendify.Entities.Schedule;
import com.fatec.tcc.agendify.Infra.TokenService;
import com.fatec.tcc.agendify.Services.ScheduleService;
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

    @Autowired
    private TokenService tokenService;

    @GetMapping("/{id}")
    public ResponseEntity<ObjectNode> getScheduleById(@PathVariable("id") Long id) {
        try {
            ScheduleDetailsResponse schedule = this.scheduleService.getById(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withBody(schedule).build(), HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getSchedules() {
        try {
            List<ScheduleDetailsResponse> schedules = this.scheduleService.getAll();

            return ResponseEntity.ok(schedules);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ErrorResponseAPI(e.getMessage()));
        }
    }

    @GetMapping("/by/{id}")
    public ResponseEntity<?> getAlSchedulesByUserId(@PathVariable("id") Long userId) {
        try {
            List<ScheduleClientResponse> schedules = this.scheduleService.getAllFromClientSchedule(userId);

            return ResponseEntity.ok(schedules);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ErrorResponseAPI(e.getMessage()));
        }
    }

    @GetMapping("/portfolio/{id}")
    public ResponseEntity<ObjectNode> getAlSchedulesByPortfolioId(@PathVariable("id") Long id) {
        try {
            List<ScheduleClientResponse> schedules = this.scheduleService.getAllUserEnterpriseSchedulesByPortfolio(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withList(schedules).build(), HttpStatus.OK);
        } catch (RuntimeException | JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/")
    public ResponseEntity<ObjectNode> createSchedule(@RequestBody ScheduleBody scheduleBody) {
        try {

            this.scheduleService.createAppointment(scheduleBody);

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/accept")
    public ResponseEntity<?> acceptSchedule(
            @RequestBody ManageStatusAcceptAppointment appointment,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = this.extractBearerToken(authorizationHeader);
            var idByToken = tokenService.getClaimId(token);
            ScheduleEnterpriseResponse schedule = this.scheduleService.acceptOrDeclineAppointment(appointment, idByToken);

            return ResponseEntity.ok(schedule);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmSchedule(
            @RequestBody ManageStatusConfirmAppointment appointment,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = this.extractBearerToken(authorizationHeader);
            var idByToken = tokenService.getClaimId(token);
            ScheduleEnterpriseResponse schedule = this.scheduleService.confirmOrDeclineAppointment(appointment, idByToken);

            return ResponseEntity.ok(schedule);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reschedule")
    public ResponseEntity<?> rescheduleAppointment(
            @RequestBody ManageStatusConfirmAppointment appointment,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = this.extractBearerToken(authorizationHeader);
            var idByToken = tokenService.getClaimId(token);
            Schedule schedule = this.scheduleService.reschedule(appointment);

            return ResponseEntity.ok(new ScheduleEnterpriseResponse(schedule));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/realized")
    public ResponseEntity<?> appointmentRealized(
            @RequestBody ManageStatusRelizedAppointment appointment,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = this.extractBearerToken(authorizationHeader);
            var idByToken = tokenService.getClaimId(token);
            System.out.println("CHEGUEI");
            ScheduleEnterpriseResponse schedule = this.scheduleService.appointmentRealized(appointment, idByToken);

            return ResponseEntity.ok(schedule);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    private String extractBearerToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // 7 is the length of "Bearer "
        }
        return null;
    }

}
