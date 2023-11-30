package com.fatec.tcc.agendify.Services;

import com.fatec.tcc.agendify.CustomExceptions.NotFoundException;
//import com.fatec.tcc.agendeja.Database.Seeders.Seed;
import com.fatec.tcc.agendify.Entities.*;
import com.fatec.tcc.agendify.Entities.RequestTemplate.ScheduleBody;
import com.fatec.tcc.agendify.Repositories.PortfolioJobRepository;
import com.fatec.tcc.agendify.Repositories.ScheduleRepository;
import com.fatec.tcc.agendify.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class ScheduleService {
    Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private PortfolioJobRepository portfolioJobRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Schedule> getAll(){
        Iterable<Schedule> iterableUsers = this.scheduleRepository.findAll();
        List<Schedule> userList = new ArrayList<>();
        iterableUsers.forEach(userList::add);

        return userList;
    }

    public List<Schedule> getAllValidSchedules(){
        Iterable<Schedule> iterableUsers = this.scheduleRepository.findAll();
        List<Schedule> userList = new ArrayList<>();
        iterableUsers.forEach(userList::add);

        return userList;
    }

    public Schedule getById(Long id) {
        Optional<Schedule> optionalSchedule = this.scheduleRepository.findById(id);

        if (optionalSchedule.isPresent()) {
            return optionalSchedule.get();
        }

        throw new NotFoundException("Company branch does not exists");
    }

    public List<Schedule> getByUserId(Long id) {
        return this.scheduleRepository.findAllByUserId(id);
    }

    public void createSchedule(ScheduleBody scheduleBody) {
        try {
            if (Objects.isNull(scheduleBody.date())) throw new IllegalArgumentException("Can not create schedule! Invalid date!");
            if (Objects.isNull(scheduleBody.time())) throw new IllegalArgumentException("Can not create schedule! Invalid time!");
            if (Objects.isNull(scheduleBody.portfolioJobId())) throw new IllegalArgumentException("Can not create schedule! Invalid portfolio job id!");


            Optional<PortfolioJob> optionalPortfolioJob = this.portfolioJobRepository.findById(scheduleBody.portfolioJobId());

            if (optionalPortfolioJob.isEmpty())
                throw new NotFoundException("Can not create schedule! Portfolio job does not exists!");

            Optional<User> optionalUser = this.userRepository.findById(scheduleBody.userId());
            if (optionalUser.isEmpty())
                throw new NotFoundException("Can not create schedule! Client does not exists!");

            PortfolioJob portfolioJob = optionalPortfolioJob.get();
            User user = optionalUser.get();

            if (!this.roleExists(
                    portfolioJob.getPortfolio().getCompanyBranch().getUser().getRole() )
                    || portfolioJob.getPortfolio().getCompanyBranch().getUser().getRole() != Role.ENTERPRISE
            )
                throw new IllegalArgumentException("Can not create schedule! Invalid user role!");

            if (scheduleBody.date().isBefore(LocalDate.now()))
                throw new IllegalArgumentException("Can not create a schedule for a past day");

            if (scheduleBody.date().equals(LocalDate.now()) && scheduleBody.time().isBefore(LocalTime.now()))
                throw new IllegalArgumentException("Can not create a schedule for a past time");

            if (this.scheduleRepository
                    .existsByDateAndTimeAndPortfolioJob_Id(
                            scheduleBody.date(), scheduleBody.time(), scheduleBody.portfolioJobId()
                    )
            )
                throw new IllegalArgumentException("This schedule already exists!");



            Schedule schedule =
                    new Schedule(scheduleBody.date(), scheduleBody.time(), portfolioJob, user, SCHEDULE_STATUS.PENDENTE);

            this.scheduleRepository.save(
                    schedule
            );

        } catch (Exception e) {
            logger.error("Error on create schedule: " + e.getMessage());
            throw  e;
        }

    }

//    public void scheduleASchedule(Long userId, Long scheduleId) {
//        Optional<Schedule> optionalSchedule = this.scheduleRepository.findById(scheduleId);
////        Optional<User> optionalUser = this.userRepository.findById(userId);
//
//        if (optionalSchedule.isEmpty()) throw new RuntimeException("Schedule does not exists! Can not schedule");
//
//        Schedule schedule = optionalSchedule.get();
//        if (schedule.getIsScheduled() && Objects.nonNull(schedule.getUser().getId()))
//            throw new RuntimeException("Can not schedule! Schedule is not available!");
//
//        if (!this.userRepository.existsById(userId)) throw new RuntimeException("Invalid user! Can not schedule");
//
//        if (this.scheduleRepository
//                .existsByDateAndTimeAndUserIdAndIsScheduledIsTrue(schedule.getDate(), schedule.getTime(), userId)
//        ) throw new RuntimeException("Can not schedule! User already have a schedule for this date and time!");
//
//        schedule.setIsScheduled(true);
//        schedule.setUserId(userId);
//        this.scheduleRepository.save(schedule);
//
//    }

    public void updateSchedule(Long scheduleId, Long userId, LocalDate date, LocalTime time) {
        Optional<Schedule> optionalSchedule = this.scheduleRepository.findById(scheduleId);
        Optional<User> optionalUser = this.userRepository.findById(userId);

        if (Objects.isNull(date)) throw new IllegalArgumentException("Can not create schedule! Invalid date!");
        if (Objects.isNull(time)) throw new IllegalArgumentException("Can not create schedule! Invalid time!");

        if (optionalSchedule.isEmpty())
            throw new NotFoundException("Can not update schedule! Schedule does not exists!");

        if (optionalUser.isEmpty())
            throw new NotFoundException("Can not update schedule! User enterprise does not exists!");

        Schedule schedule = optionalSchedule.get();
        User user = optionalUser.get();
        if (!this.roleExists(user.getRole()) || user.getRole() != Role.ENTERPRISE)
            throw new IllegalArgumentException("Can not update schedule! Invalid user role!");

        if (schedule.getIsScheduled())
            throw new IllegalArgumentException("Schedule is scheduled! Can not update");
        if (date.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Can not update a schedule for a past day");
        if (time.isBefore(LocalTime.now()))
            throw new IllegalArgumentException("Can not update a schedule for a past time");
        if (this.scheduleRepository.existsByDateAndTime(date, time))
            throw new IllegalArgumentException("Can not update! The schedule already is registered!");

        schedule.setDate(date);
        schedule.setTime(time);
        schedule.setDateAsString(date.toString());
        schedule.setTimeAsString(time.toString());

        this.scheduleRepository.save(
                schedule
        );

    }

    public void deleteSchedule(Long scheduleId) {
        boolean existsById = this.scheduleRepository.existsById(scheduleId);

        if (existsById) {
            Schedule schedule = this.scheduleRepository.findById(scheduleId).get();
            if (!schedule.getIsScheduled())
                this.scheduleRepository.deleteById(scheduleId);
        }

    }

    public boolean roleExists(Role role) {
        for (Role roleValue : Role.values()) {
            if (role.name().equals(roleValue.name())) {
                return true;
            }
        }
        return false;
    }

    //TODO criar, alterar e agendar. 3 coisas diferentes
    // invalidar os schedules que ja passaram da data, ex: tudo que foi anterior a hoje, e mandar pro histórico
    // get all só com as datas que ainda estão por vir
    // error if user try schedule and already has a schedule for that day DONE
}
