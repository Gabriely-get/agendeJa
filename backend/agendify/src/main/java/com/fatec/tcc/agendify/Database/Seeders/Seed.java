package com.fatec.tcc.agendify.Database.Seeders;

import com.fatec.tcc.agendify.Entities.*;
import com.fatec.tcc.agendify.Entities.Address.Address;
import com.fatec.tcc.agendify.Entities.Address.City;
import com.fatec.tcc.agendify.Entities.Address.Neighborhood;
import com.fatec.tcc.agendify.Entities.Address.State;
import com.fatec.tcc.agendify.Repositories.*;
import com.fatec.tcc.agendify.Repositories.Address.AddressRepository;
import com.fatec.tcc.agendify.Repositories.Address.CityRepository;
import com.fatec.tcc.agendify.Repositories.Address.NeighborhoodRepository;
import com.fatec.tcc.agendify.Repositories.Address.StateRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Component
public class Seed {
    Logger logger = LoggerFactory.getLogger(Seed.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private  BusinessHourRepository businessHourRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private JobCategoryRepository jobCategoryRepository;

    @Autowired
    private CompanyBranchRepository companyRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private NeighborhoodRepository neighborhoodRepository;

    @Autowired
    private PortfolioJobRepository portfolioJobRepository;

    @EventListener
    public void seed(ContextRefreshedEvent event) throws ParseException, InterruptedException {

        try {
            this.seedUser();
            this.seedRCategory();
            this.seedRSubCategory();
            this.seedJobCategory();
            this.seedAddressAndOthersNecessariesTables();
            this.seedCompany();
            this.seedPortfolio();
            this.seedBusinessHour();
            this.seedPortfolioJob();
            this.seedSchedule();

            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
//        this.seedRoles();

    }

    /*
    private void seedRoles() {
        int qt = this.roleRepository.findAll().size();

        if (qt < 1) {
            Role role1 = new Role("USER");
            Role role2 = new Role("ENTERPRISE");
            Role role3 = new Role("ADMIN");

            this.roleRepository.saveAll(List.of(role1, role2, role3));
            logger.info("Role seeded");
        }
    }
     */

    @Transactional
    private void seedRCategory() {
        int qt = ((List<Category>) this.categoryRepository.findAll()).size();

        if (qt < 1) {
            Category category1 = new Category("Beleza");
            Category category2 = new Category("Saúde/Bem-estar");
            Category category3 = new Category("Aulas Particulares");

            this.categoryRepository.saveAll(List.of(category1, category2, category3));
            logger.info("Category seeded");
        }
    }

    @Transactional
    private void seedRSubCategory() {
        int qt = ((List<SubCategory>) this.subCategoryRepository.findAll()).size();

        if (qt < 1) {
            SubCategory subCategory1 =
                    new SubCategory("Design de Sobrancelha", this.categoryRepository.findByName("Beleza").get());
            SubCategory subCategory2 =
                    new SubCategory("Salão de Beleza", this.categoryRepository.findByName("Beleza").get());
            SubCategory subCategory3 =
                    new SubCategory("Esteticista", this.categoryRepository.findByName("Saúde/Bem-estar").get());
            SubCategory subCategory4 =
                    new SubCategory("Aulas de idiomas", this.categoryRepository.findByName("Aulas Particulares").get());

            this.subCategoryRepository.saveAll(List.of(subCategory1, subCategory2, subCategory3, subCategory4));
            logger.info("Sub category seeded");
        }
    }

    @Transactional
    private void seedJobCategory() throws InterruptedException {
//        Thread.sleep(500);
        try {
            int qt = ((List<JobCategory>) this.jobCategoryRepository.findAll()).size();

            if (qt < 1) {
                Optional<SubCategory> optionalSubCategory = this.subCategoryRepository.findById(1L);
                Optional<SubCategory> optionalSubCategory2 = this.subCategoryRepository.findById(3L);
                Optional<SubCategory> optionalSubCategory3 = this.subCategoryRepository.findById(4L);

                if (
                        optionalSubCategory3.isPresent()
                        && optionalSubCategory2.isPresent()
                        && optionalSubCategory.isPresent()
                ) {
                    SubCategory subCategory = optionalSubCategory.get();
                    SubCategory subCategory2 = optionalSubCategory2.get();
                    SubCategory subCategory3 = optionalSubCategory3.get();

                    JobCategory jobCategory1 =
                            new JobCategory("Micropigmentação", subCategory);
                    JobCategory jobCategory2 =
                            new JobCategory("Massagem Transversal", subCategory2);
                    JobCategory jobCategory3 =
                            new JobCategory("Aula de Inglês", subCategory3);
                    this.jobCategoryRepository.saveAll(List.of(jobCategory1, jobCategory2, jobCategory3));
                    logger.info("Job seeded");
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error on seed job: " + e);
        }

    }

    @Transactional
    private void seedAddressAndOthersNecessariesTables() {
        int qt = ((List<Address>) this.addressRepository.findAll()).size();

        if (qt < 1) {
            State state = new State("SP");
            this.stateRepository.save(state);

            City city = new City("Praia Grande", state);
            this.cityRepository.save(city);

            Neighborhood neighborhood = new Neighborhood("Melvi", city);
            this.neighborhoodRepository.save(neighborhood);

            Address address1 =
                    new Address("11712280", "Rua do Equilíbrio", "147", null, neighborhood);
            Address address2 =
                    new Address("11712380", "Rua Euclides", "15975", null, neighborhood);

            this.addressRepository.saveAll(List.of(address1, address2));

            logger.info("State, City, Neighborhood and Address seeded");
        }
    }

    @Transactional
    private void seedCompany() {
        int qt = ((List<CompanyBranch>) this.companyRepository.findAll()).size();

        if (qt < 1) {

            CompanyBranch company1 =
                    new CompanyBranch(
                            "Meu espaço de Cuidado",
                            this.addressRepository.findById(1L).get(),
                            this.userRepository.findById(1L).get(),
                            null,
                            true);
            CompanyBranch company2 =
                    new CompanyBranch(
                            "Salão Raízes",
                            this.addressRepository.findById(1L).get(),
                            this.userRepository.findById(3L).get(),
                            "Desde 1974 etc etc",
                            false);
            CompanyBranch company3 =
                    new CompanyBranch(
                            "Apice Learning",
                            this.addressRepository.findById(2L).get(),
                            this.userRepository.findById(5L).get(),
                            "Formada nisso, tals, etc",
                            false);

            this.companyRepository.saveAll(List.of(company1, company2, company3));
            logger.info("Company seeded");
        }
    }

    @Transactional
    private void seedPortfolio() {
        int qt = ((List<Portfolio>) this.portfolioRepository.findAll()).size();

        if (qt < 1) {

            Optional<CompanyBranch> ocb = this.companyRepository.findById(1L);
            Optional<CompanyBranch> ocb2 = this.companyRepository.findById(3L);
            Optional<CompanyBranch> ocb3 = this.companyRepository.findById(2L);

            Optional<Category> category = this.categoryRepository.findById(2L);
            Optional<Category> category2 = this.categoryRepository.findById(1L);
            Optional<Category> category3 = this.categoryRepository.findById(3L);

            Optional<SubCategory> subCategory = this.subCategoryRepository.findById(3L);
            Optional<SubCategory> subCategory1 = this.subCategoryRepository.findById(1L);
            Optional<SubCategory> subCategory2 = this.subCategoryRepository.findById(2L);
            Optional<SubCategory> subCategory3 = this.subCategoryRepository.findById(4L);

            if (
                ocb.isPresent()
                        && ocb2.isPresent()
                        && ocb3.isPresent()
                && category.isPresent()
                        && category2.isPresent()
                        && category3.isPresent()
                && subCategory.isPresent()
                        && subCategory1.isPresent()
                        && subCategory2.isPresent()
                        && subCategory3.isPresent()
            ) {

                Portfolio portfolio1 =
                        new Portfolio(
                                ocb.get(),
                                category.get(),
                                new HashSet<>(
                                        Set.of(subCategory.get()))
                        );

                Portfolio portfolio2 =
                        new Portfolio(
                                ocb2.get(),
                                category2.get(),
                                Set.of(
                                        subCategory1.get(),
                                       subCategory2.get()
                                ));

                Portfolio portfolio3 =
                        new Portfolio(
                                ocb3.get(),
                                category3.get(),
                                new HashSet<>(
                                        Set.of(subCategory3.get()))
                        );

                this.portfolioRepository.saveAll(List.of(portfolio1, portfolio2, portfolio3));

                logger.info("Portfolio seeded");
            }
        }
    }

    @Transactional
    private void seedBusinessHour() {
        Iterable<BussinessHour> businessHourIterable = this.businessHourRepository.findAll();
        List<BussinessHour> businessHours = new ArrayList<>();
        businessHourIterable.forEach(businessHours::add);

        if (businessHours.size() < 3) {

            //seed as 24h for company 1 that is 24h
            this.businessHourRepository.saveAll(
                    List.of(
                            new BussinessHour(
                                    DaysOfWeek.MONDAY,
                                    LocalTime.of(0, 0),
                                    LocalTime.of(23, 0),
                                    this.portfolioRepository.findById(1L).get()
                            ),
                            new BussinessHour(
                                    DaysOfWeek.WEDNESDAY,
                                    LocalTime.of(0, 0),
                                    LocalTime.of(23, 0),
                                    this.portfolioRepository.findById(1L).get()
                            ),
                            new BussinessHour(
                                    DaysOfWeek.FRIDAY,
                                    LocalTime.of(0, 0),
                                    LocalTime.of(23, 0),
                                    this.portfolioRepository.findById(1L).get()
                            )
                    )
            );

            //seed not as 24h for company 2
            this.businessHourRepository.saveAll(
                    List.of(
                            new BussinessHour(
                                    DaysOfWeek.MONDAY,
                                    LocalTime.of(8, 0),
                                    LocalTime.of(17, 0),
                                    this.portfolioRepository.findById(2L).get()
                            ),
                            new BussinessHour(
                                    DaysOfWeek.TUESDAY,
                                    LocalTime.of(8, 0),
                                    LocalTime.of(17, 0),
                                    this.portfolioRepository.findById(2L).get()
                            ),
                            new BussinessHour(
                                    DaysOfWeek.THURSDAY,
                                    LocalTime.of(10, 0),
                                    LocalTime.of(19, 0),
                                    this.portfolioRepository.findById(2L).get()
                            ),
                            new BussinessHour(
                                    DaysOfWeek.FRIDAY,
                                    LocalTime.of(9, 0),
                                    LocalTime.of(19, 0),
                                    this.portfolioRepository.findById(2L).get()
                            )
                    )
            );

            //seed not as 24h for company 3
            this.businessHourRepository.saveAll(
                    List.of(
                            new BussinessHour(
                                    DaysOfWeek.MONDAY,
                                    LocalTime.of(8, 0),
                                    LocalTime.of(17, 0),
                                    this.portfolioRepository.findById(3L).get()
                            ),
                            new BussinessHour(
                                    DaysOfWeek.TUESDAY,
                                    LocalTime.of(8, 0),
                                    LocalTime.of(17, 0),
                                    this.portfolioRepository.findById(3L).get()
                            ),
                            new BussinessHour(
                                    DaysOfWeek.WEDNESDAY,
                                    LocalTime.of(8, 0),
                                    LocalTime.of(17, 0),
                                    this.portfolioRepository.findById(3L).get()
                            ),
                            new BussinessHour(
                                    DaysOfWeek.THURSDAY,
                                    LocalTime.of(10, 0),
                                    LocalTime.of(19, 0),
                                    this.portfolioRepository.findById(3L).get()
                            ),
                            new BussinessHour(
                                    DaysOfWeek.FRIDAY,
                                    LocalTime.of(9, 0),
                                    LocalTime.of(19, 0),
                                    this.portfolioRepository.findById(3L).get()
                            ),
                            new BussinessHour(
                                    DaysOfWeek.SUNDAY,
                                    LocalTime.of(8, 0),
                                    LocalTime.of(14, 0),
                                    this.portfolioRepository.findById(3L).get()
                            )
                    )
            );

            logger.info("Bussiness seeded");
        }
    }

    @Transactional
    private void seedPortfolioJob() {
        int qt = ((List<PortfolioJob>) this.portfolioJobRepository.findAll()).size();

        if (qt < 1) {
            PortfolioJob portfolioJob1 =
                    new PortfolioJob(
                            "Micropigmentação Fio a Fio",
                            50.00,
                            "Esta é a descrição do portfolio job micropigmentação fio a fio",
                            6L,
                            this.portfolioRepository.findById(2L).get(),
                            this.jobCategoryRepository.findById(1L).get(),
                            LocalTime.of(1,0).toString(),
                            false
                    );

            PortfolioJob portfolioJob2 =
                    new PortfolioJob(
                            "Massagem Transversal Superior",
                            70.00,
                            "Esta é a descrição do portfolio job massagem transversal",
                            null,
                            this.portfolioRepository.findById(1L).get(),
                            this.jobCategoryRepository.findById(2L).get(),
                            LocalTime.of(2,0).toString(),
                            false
                    );

            PortfolioJob portfolioJob3 =
                    new PortfolioJob(
                            "",
                            30.00,
                            "Esta é a descrição do portfolio aula de inglês",
                            null,
                            this.portfolioRepository.findById(3L).get(),
                            this.jobCategoryRepository.findById(3L).get(),
                            LocalTime.of(1,0).toString(),
                            false
                    );

            this.portfolioJobRepository.saveAll(List.of(portfolioJob1, portfolioJob2, portfolioJob3));
            logger.info("Portfolio Job seeded");
        }
    }

    @Transactional
    private void seedSchedule() {

        Iterable<Schedule> scheduleIterable = this.scheduleRepository.findAll();
        List<Schedule> schedules1 = new ArrayList<>();
        scheduleIterable.forEach(schedules1::add);

        if (schedules1.size() < 3) {

            //schedule for company 1
            this.scheduleRepository.saveAll(
                    List.of(
                            //agenda segunda
                            new Schedule(
                                    LocalDate.of(2023, 12, 11),
                                    LocalTime.of(9, 0),
                                    this.portfolioJobRepository.findById(1L).get(),
                                    this.userRepository.findById(1L).get(),
                                    SCHEDULE_STATUS.PENDENTE,
                                    false,
                                    null
                            ),
                            //agenda quinta
                            new Schedule(
                                    LocalDate.of(2023, 12, 21),
                                    LocalTime.of(9, 0),
                                    this.portfolioJobRepository.findById(1L).get(),
                                    this.userRepository.findById(1L).get(),
                                    SCHEDULE_STATUS.PENDENTE,
                                    false,
                                    null
                            ),
                            //agenda sexta
                            new Schedule(
                                    LocalDate.of(2023, 12, 15),
                                    LocalTime.of(9, 0),
                                    this.portfolioJobRepository.findById(1L).get(),
                                    this.userRepository.findById(1L).get(),
                                    SCHEDULE_STATUS.PENDENTE,
                                    false,
                                    null
                            )
                    )
            );

            //schedule for company 2
            this.scheduleRepository.saveAll(
                    List.of(
                            //agenda segunda
                            new Schedule(
                                    LocalDate.of(2023, 12, 11),
                                    LocalTime.of(22, 0),
                                    this.portfolioJobRepository.findById(2L).get(),
                                    this.userRepository.findById(2L).get(),
                                    SCHEDULE_STATUS.PENDENTE,
                                    false,
                                    null
                            ),
                            //agenda quarta
                            new Schedule(
                                    LocalDate.of(2023, 12, 13),
                                    LocalTime.of(20, 0),
                                    this.portfolioJobRepository.findById(2L).get(),
                                    this.userRepository.findById(2L).get(),
                                    SCHEDULE_STATUS.PENDENTE,
                                    false,
                                    null
                            ),
                            //agenda quarta
                            new Schedule(
                                    LocalDate.of(2023, 12, 20),
                                    LocalTime.of(5, 0),
                                    this.portfolioJobRepository.findById(2L).get(),
                                    this.userRepository.findById(2L).get(),
                                    SCHEDULE_STATUS.PENDENTE,
                                    false,
                                    null
                            )
                    )
            );

            //schedule for company 3
            this.scheduleRepository.saveAll(
                    List.of(
                            //agenda segunda
                            new Schedule(
                                    LocalDate.of(2023, 12, 25),
                                    LocalTime.of(12, 0),
                                    this.portfolioJobRepository.findById(3L).get(),
                                    this.userRepository.findById(3L).get(),
                                    SCHEDULE_STATUS.PENDENTE,
                                    false,
                                    null
                            ),
                            //agenda terça
                            new Schedule(
                                    LocalDate.of(2023, 12, 5),
                                    LocalTime.of(17, 0),
                                    this.portfolioJobRepository.findById(3L).get(),
                                    this.userRepository.findById(3L).get(),
                                    SCHEDULE_STATUS.PENDENTE,
                                    false,
                                    null
                            ),
                            //agenda quinta
                            new Schedule(
                                    LocalDate.of(2024, 1, 4),
                                    LocalTime.of(11, 0),
                                    this.portfolioJobRepository.findById(3L).get(),
                                    this.userRepository.findById(3L).get(),
                                    SCHEDULE_STATUS.PENDENTE,
                                    false,
                                    null
                            ),
                            //agenda domingo
                            new Schedule(
                                    LocalDate.of(2023, 12, 10),
                                    LocalTime.of(8, 0),
                                    this.portfolioJobRepository.findById(3L).get(),
                                    this.userRepository.findById(3L).get(),
                                    SCHEDULE_STATUS.PENDENTE,
                                    false,
                                    null
                            )
                    )
            );

            logger.info("Schedules seeded");
        }
    }

    @Transactional
    private void seedUser() throws ParseException {
        int qt = this.userRepository.findAll().size();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        if (qt < 6) {
            boolean change = false;

            //3 cliente
            for (int i = 1; i < 4; i++) {
                String email = "teste" + i + "@gmail.com";
                String cpf = "1234567898" + i;
                String dateInString = "2002-07-0" + i;

                User user = new User();
                user.setCpf(cpf);
                user.setEmail(email);
                user.setFirstName("Gabriely");
                user.setLastName(" Santos " + i);
                user.setIsActive(true);
                user.setPhone(cpf);
                user.setBirthday(dateInString);
                user.setPassword(new BCryptPasswordEncoder().encode("test123"));
                user.setRole(Role.USER);
                user.setIsJobProvider(false);

                user.setImageProfileId((long) i);

                this.userRepository.save(user);
            }



            //3 prestador de serviço
            for (int i = 1; i < 3; i++) {
                String email = "gab"+i+"@gmail.com";
                String cpf = i+"266567898" + i;
                String dateInString = "200"+i+"-08-0" + i;

                User user = new User();
                user.setCpf(cpf);
                user.setEmail(email);
                user.setFirstName("Rodrigo");
                user.setLastName(" Ablu " + i);
                user.setIsActive(true);
                user.setPhone(cpf);
                user.setBirthday(dateInString);
                user.setPassword(new BCryptPasswordEncoder().encode("test123"));
                user.setIsJobProvider(true);
                user.setRole(Role.ENTERPRISE);
                user.setImageProfileId((long) i);
                user.setImageCoverId((long) i + 3);

                this.userRepository.save(user);
            }

            //3 admin
            for (int i = 1; i < 3; i++) {
                String email = "mandinha"+i+"@gmail.com";
                String cpf = i+"360278988" + i;
                String dateInString = "200"+i+"-04-0" + i;

                User user = new User();
                user.setCpf(cpf);
                user.setEmail(email);
                user.setFirstName("Amanda ");
                user.setLastName(" Suit" + i);
                user.setIsActive(true);
                user.setPhone(cpf);
                user.setBirthday(dateInString);
                user.setPassword(new BCryptPasswordEncoder().encode("test123"));
                user.setIsJobProvider(false);
                user.setRole(Role.ADMIN);
                user.setImageProfileId((long) i);

                this.userRepository.save(user);
            }

            logger.info("User with image seeded");
        }
    }

}
