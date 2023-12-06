package com.fatec.tcc.agendify.Services;

import com.fatec.tcc.agendify.CustomExceptions.NotFoundException;
//import com.fatec.tcc.agendeja.Database.Seeders.Seed;
import com.fatec.tcc.agendify.Entities.*;
import com.fatec.tcc.agendify.Entities.RequestTemplate.*;
import com.fatec.tcc.agendify.Repositories.PortfolioJobRepository;
import com.fatec.tcc.agendify.Repositories.ScheduleRepository;
import com.fatec.tcc.agendify.Repositories.UserRepository;
import com.fatec.tcc.agendify.Utils.GetHoursBetween;
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

    public List<ScheduleDetailsResponse> getAll(){
        Iterable<Schedule> scheduleIterable = this.scheduleRepository.findAll();
        List<ScheduleDetailsResponse> schedule = new ArrayList<>();

        scheduleIterable.forEach(schedule1 -> {
            schedule.add(new ScheduleDetailsResponse(schedule1));
        });

        return schedule;
    }

    public ScheduleDetailsResponse getById(Long id) {
        Optional<Schedule> optionalSchedule = this.scheduleRepository.findById(id);

        if (optionalSchedule.isPresent()) {
            return new ScheduleDetailsResponse(optionalSchedule.get());
        }

        throw new NotFoundException("Agendamento não encontrado");
    }

    public List<ScheduleClientResponse> getAllFromClientSchedule(Long id) {
        List<Schedule> schedules = this.scheduleRepository.findAllByUserId(id);
        List<ScheduleClientResponse> schedule = new ArrayList<>();

        schedules.forEach(schedule1 -> {
            schedule.add(new ScheduleClientResponse(schedule1));
        });

        return schedule;
    }

    public List<ScheduleClientResponse> getAllUserEnterpriseSchedulesByPortfolio(Long portfolioId) {
        List<Schedule> schedules = this.scheduleRepository.findAllByPortfolioJob_Portfolio_Id(portfolioId);
        List<ScheduleClientResponse> scheduleClientResponseList = new ArrayList<>();

        schedules.forEach(schedule -> {
            scheduleClientResponseList.add(new ScheduleClientResponse(schedule));
        });

        return scheduleClientResponseList;
    }

    public void createAppointment(ScheduleBody scheduleBody) {
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

            LocalTime finalHour =
                    LocalTime.of(scheduleBody.time().getHour() + portfolioJob.getDuration().getHour(),0);

//            List<Hour> hours = GetHoursBetween.
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

//    public void cancelSchedule(ManageStatusConfirmAppointment statusAppointment) {
//        Optional<Schedule> optionalSchedule = this.scheduleRepository.findById(statusAppointment.scheduleId());
//
//        if (!statusAppointment.reschedule() && Objects.equals(schedule.getStatus(), SCHEDULE_STATUS.PENDENTE.name())) {
//            schedule.setStatus(SCHEDULE_STATUS.CANCELADO.name());
//            schedule.setNoteByDecline(statusAppointment.note());
//            this.scheduleRepository.save(schedule);
//
//            return new ScheduleEnterpriseResponse(schedule);
//        }
//    }

    public ScheduleEnterpriseResponse acceptOrDeclineAppointment(ManageStatusAcceptAppointment statusAppointment, Long idByToken) {
        Optional<Schedule> optionalSchedule = this.scheduleRepository.findById(statusAppointment.scheduleId());
        Optional<User> optionalUser = this.userRepository.findById(idByToken);

        if (optionalUser.isEmpty()) throw new RuntimeException("Usuário não existe. Impossível recuperar agendamento.");

        if (optionalSchedule.isEmpty()) throw new RuntimeException("Agenda inválida!");
        else {
            if (this.scheduleRepository.existsByIdAndPortfolioJob_Portfolio_CompanyBranch_User_Id(
                            statusAppointment.scheduleId(),
                            idByToken)
            ) {
                Schedule schedule = optionalSchedule.get();

                if (statusAppointment.accept() && Objects.equals(schedule.getStatus(), SCHEDULE_STATUS.PENDENTE.name())) {
                    schedule.setStatus(SCHEDULE_STATUS.ACEITO.name());
                    this.scheduleRepository.save(schedule);

                    return new ScheduleEnterpriseResponse(schedule);
                }

                if (!statusAppointment.accept() && Objects.equals(schedule.getStatus(), SCHEDULE_STATUS.PENDENTE.name())) {
                    schedule.setStatus(SCHEDULE_STATUS.CANCELADO.name());
                    schedule.setNoteByDecline(statusAppointment.note());
                    this.scheduleRepository.save(schedule);

                    return new ScheduleEnterpriseResponse(schedule);
                }

                throw new RuntimeException("Operação inválida! Verifique status do agendamento. Deveria ser PENDENTE");
            }
            else throw new RuntimeException("Esse agendamento não pertence a esse prestador de serviço. Verificar");


        }
    }

    //TODO remarcar cliente também
    public ScheduleEnterpriseResponse confirmOrDeclineAppointment(
            ManageStatusConfirmAppointment statusAppointment,
            Long idByToken
    ) {
        Optional<Schedule> optionalSchedule = this.scheduleRepository.findById(statusAppointment.scheduleId());
        Optional<User> optionalUser = this.userRepository.findById(idByToken);

        if (optionalUser.isEmpty()) throw new RuntimeException("Usuário não existe. Impossível recuperar agendamento.");

        if (optionalSchedule.isEmpty()) throw new RuntimeException("Agenda inválida!");
        else {
            if (this.scheduleRepository.existsByIdAndPortfolioJob_Portfolio_CompanyBranch_User_Id(
                    statusAppointment.scheduleId(),
                    idByToken)
            ) {
                Schedule schedule = optionalSchedule.get();

                if (statusAppointment.reschedule() && Objects.equals(schedule.getStatus(), SCHEDULE_STATUS.ACEITO.name())) {
                    Schedule newSchedule = this.reschedule(statusAppointment);

                    return new ScheduleEnterpriseResponse(newSchedule);
                }

                if (statusAppointment.confirm() && Objects.equals(schedule.getStatus(), SCHEDULE_STATUS.ACEITO.name())) {
                    schedule.setStatus(SCHEDULE_STATUS.CONFIRMADO.name());
                    this.scheduleRepository.save(schedule);

                    return new ScheduleEnterpriseResponse(schedule);
                }

                if (!statusAppointment.confirm() && Objects.equals(schedule.getStatus(), SCHEDULE_STATUS.ACEITO.name())) {
                    schedule.setStatus(SCHEDULE_STATUS.CANCELADO.name());
                    schedule.setNoteByDecline(statusAppointment.note());
                    this.scheduleRepository.save(schedule);

                    return new ScheduleEnterpriseResponse(schedule);
                }

                throw new RuntimeException("Operação inválida! Verifique status do agendamento. Deveria ser ACEITO");
            }
            else throw new RuntimeException("Esse agendamento não pertence a esse prestador de serviço. Verificar");

        }
    }

    public ScheduleEnterpriseResponse appointmentRealized(
            ManageStatusRelizedAppointment statusAppointment,
            Long idByToken
    ) {
        try {
            System.out.println("CHEGUEII");
            Optional<Schedule> optionalSchedule = this.scheduleRepository.findById(statusAppointment.scheduleId());
            Optional<User> optionalUser = this.userRepository.findById(idByToken);

            if (optionalUser.isEmpty())
                throw new RuntimeException("Usuário não existe. Impossível recuperar agendamento.");

            if (optionalSchedule.isEmpty()) throw new RuntimeException("Agenda inválida!");
            else {
                if (this.scheduleRepository.existsByIdAndPortfolioJob_Portfolio_CompanyBranch_User_Id(
                        statusAppointment.scheduleId(),
                        idByToken)
                ) {
                    Schedule schedule = optionalSchedule.get();

                    if (statusAppointment.reschedule() && Objects.equals(schedule.getStatus(), SCHEDULE_STATUS.CONFIRMADO.name())) {
                        Schedule newSchedule = this.reschedule(statusAppointment);

                        return new ScheduleEnterpriseResponse(newSchedule);
                    }

                    if (statusAppointment.realized() && Objects.equals(schedule.getStatus(), SCHEDULE_STATUS.CONFIRMADO.name())) {
                        schedule.setStatus(SCHEDULE_STATUS.REALIZADO.name());
                        this.scheduleRepository.save(schedule);

                        return new ScheduleEnterpriseResponse(schedule);
                    }

                    if (!statusAppointment.realized() && Objects.equals(schedule.getStatus(), SCHEDULE_STATUS.CONFIRMADO.name())) {
                        schedule.setStatus(SCHEDULE_STATUS.CANCELADO.name());
                        schedule.setNoteByDecline(statusAppointment.note());
                        this.scheduleRepository.save(schedule);

                        return new ScheduleEnterpriseResponse(schedule);
                    }

                    throw new RuntimeException("Operação inválida! Verifique status do agendamento. Deveria ser CONFIRMADO");
                } else
                    throw new RuntimeException("Esse agendamento não pertence a esse prestador de serviço. Verificar");

            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw  e;
        }
    }

//    public List<ScheduleDetailsResponse> getAllRealizesByUserRole(Long idByToken) {
//        Optional<User> optionalUser = this.userRepository.findById(idByToken);
//
//        if (optionalUser.isEmpty()) throw new RuntimeException("Usuairo nao existe")
//
//        return this.scheduleRepository.
//    }

    public Schedule reschedule(ManageStatusConfirmAppointment schedule) {
        try {

            Optional<Schedule> optionalSchedule = this.scheduleRepository.findById(schedule.scheduleId());

            if (optionalSchedule.isEmpty()) throw new RuntimeException("Agenda inválida!");
            Schedule schedule1 = optionalSchedule.get();

            if (Objects.isNull(schedule.newDate()))
                throw new IllegalArgumentException("Can not update schedule! Invalid date!");

            if (Objects.isNull(schedule.newTime()))
                throw new IllegalArgumentException("Can not update schedule! Invalid time!");

            if (LocalDate.parse(schedule.newDate()).isBefore(LocalDate.now()))
                throw new IllegalArgumentException("Can not update a schedule for a past day");

            if (this.scheduleRepository.existsByDateAndTimeAndPortfolioJob_Portfolio_Id(
                    LocalDate.parse(schedule.newDate()),
                    LocalTime.parse(schedule.newTime()),
                    schedule1.getPortfolioJob().getPortfolio().getId()
            )
            )
                throw new IllegalArgumentException("Não foi possível reagendar! Já existe um agendamento nesse horário!");

            schedule1.setDate(LocalDate.parse(schedule.newDate()));
            schedule1.setTime(LocalTime.parse(schedule.newTime()));

            this.scheduleRepository.save(
                    schedule1
            );

            return schedule1;
//        }
//        else throw new RuntimeException("Esse agendamento não pertence a esse prestador de serviço. Verificar");

        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Schedule reschedule(ManageStatusRelizedAppointment schedule) {
        try {

            Optional<Schedule> optionalSchedule = this.scheduleRepository.findById(schedule.scheduleId());

            if (optionalSchedule.isEmpty()) throw new RuntimeException("Agenda inválida!");
            Schedule schedule1 = optionalSchedule.get();

            if (Objects.isNull(schedule.newDate()))
                throw new IllegalArgumentException("Can not update schedule! Invalid date!");

            if (Objects.isNull(schedule.newTime()))
                throw new IllegalArgumentException("Can not update schedule! Invalid time!");

            if (LocalDate.parse(schedule.newDate()).isBefore(LocalDate.now()))
                throw new IllegalArgumentException("Can not update a schedule for a past day");

            if (this.scheduleRepository.existsByDateAndTimeAndPortfolioJob_Portfolio_Id(
                    LocalDate.parse(schedule.newDate()),
                    LocalTime.parse(schedule.newTime()),
                    schedule1.getPortfolioJob().getPortfolio().getId()
            )
            )
                throw new IllegalArgumentException("Não foi possível reagendar! Já existe um agendamento nesse horário!");

            schedule1.setDate(LocalDate.parse(schedule.newDate()));
            schedule1.setTime(LocalTime.parse(schedule.newTime()));

            this.scheduleRepository.save(
                    schedule1
            );

            return schedule1;
//        }
//        else throw new RuntimeException("Esse agendamento não pertence a esse prestador de serviço. Verificar");

        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
    }

    //update with any date and time; without validation; trazer apenas dias e horarios de acordo com o horário comercial da empresa
//    public void updateSchedule(Long scheduleId, Long idByToken, ScheduleBody scheduleBody) {
//        Optional<Schedule> optionalSchedule = this.scheduleRepository.findById(scheduleId);
//        Optional<User> optionalUser = this.userRepository.findById(scheduleBody.userId());
//
//        if (Objects.isNull(scheduleBody.date()))
//            throw new IllegalArgumentException("Can not update schedule! Invalid date!");
//
//        if (Objects.isNull(scheduleBody.time()))
//            throw new IllegalArgumentException("Can not update schedule! Invalid time!");
//
//        if (optionalSchedule.isEmpty())
//            throw new NotFoundException("Can not update schedule! Schedule does not exists!");
//
//        if (optionalUser.isEmpty())
//            throw new NotFoundException("Can not update schedule! User enterprise does not exists!");
//
//        if (this.scheduleRepository.existsByIdAndPortfolioJob_Portfolio_CompanyBranch_User_Id(
//                scheduleId,
//                idByToken)
//        ) {
//
//            Schedule schedule = optionalSchedule.get();
//            User user = optionalUser.get();
//
//            if (user.getRole() != Role.ENTERPRISE)
//                throw new IllegalArgumentException("Can not update schedule! Invalid user role!");
//            if (schedule.getIsScheduled())
//                throw new IllegalArgumentException("Schedule is scheduled! Can not update");
//            if (scheduleBody.date().isBefore(LocalDate.now()))
//                throw new IllegalArgumentException("Can not update a schedule for a past day");
//            if (this.scheduleRepository.existsByDateAndTimeAndPortfolioJob_Portfolio_Id(scheduleBody.date(), scheduleBody.time(), p))
//                throw new IllegalArgumentException("Can not update! The schedule already is registered!");
//
//            schedule.setDate(scheduleBody.date());
//            schedule.setTime(scheduleBody.time());
//
//            this.scheduleRepository.save(
//                    schedule
//            );
//        }
//        else throw new RuntimeException("Esse agendamento não pertence a esse prestador de serviço. Verificar");
//
//    }

    public void deleteSchedule(Long scheduleId) {
        boolean existsById = this.scheduleRepository.existsById(scheduleId);

        if (existsById) {
            Schedule schedule = this.scheduleRepository.findById(scheduleId).get();
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
