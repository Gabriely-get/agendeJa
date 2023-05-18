package com.fatec.tcc.agendeja.Database.Seeders;

import com.fatec.tcc.agendeja.Entities.Address.Address;
import com.fatec.tcc.agendeja.Entities.Address.City;
import com.fatec.tcc.agendeja.Entities.Address.Neighborhood;
import com.fatec.tcc.agendeja.Entities.Address.State;
import com.fatec.tcc.agendeja.Entities.*;
import com.fatec.tcc.agendeja.Repositories.Address.AddressRepository;
import com.fatec.tcc.agendeja.Repositories.Address.CityRepository;
import com.fatec.tcc.agendeja.Repositories.Address.NeighborhoodRepository;
import com.fatec.tcc.agendeja.Repositories.Address.StateRepository;
import com.fatec.tcc.agendeja.Repositories.*;
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
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class Seed {
    Logger logger = LoggerFactory.getLogger(Seed.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

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
            this.seedUser(true);
            this.seedRCategory();
            this.seedRSubCategory();
            this.seedJobCategory();
            this.seedAddressAndOthersNecessariesTables();
            this.seedCompany();
            this.seedPortfolio();
            this.seedPortfolioJob();

            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
//        this.seedRoles();

    }

//    private void seedRoles() {
//        int qt = this.roleRepository.findAll().size();
//
//        if (qt < 1) {
//            Role role1 = new Role("USER");
//            Role role2 = new Role("ENTERPRISE");
//            Role role3 = new Role("ADMIN");
//
//            this.roleRepository.saveAll(List.of(role1, role2, role3));
//            logger.info("Role seeded");
//        }
//    }

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

//    @Transactional
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

    private void seedCompany() {
        int qt = ((List<CompanyBranch>) this.companyRepository.findAll()).size();

        if (qt < 1) {

            CompanyBranch company1 =
                    new CompanyBranch(
                            "Meu espaço de Cuidado",
                            this.addressRepository.findById(1L).get(),
                            this.userRepository.findById(1L).get());
            CompanyBranch company2 =
                    new CompanyBranch(
                            "Salão Raízes",
                            this.addressRepository.findById(1L).get(),
                            this.userRepository.findById(3L).get());
            CompanyBranch company3 =
                    new CompanyBranch(
                            "Apice Learning",
                            this.addressRepository.findById(2L).get(),
                            this.userRepository.findById(5L).get());

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

    private void seedPortfolioJob() {
        int qt = ((List<PortfolioJob>) this.portfolioJobRepository.findAll()).size();

        if (qt < 1) {
            PortfolioJob portfolioJob1 =
                    new PortfolioJob(
                            "Micropigmentação Fio a Fio",
                            50.00,
                            "Esta é a descrição do portfolio job micropigmentação fio a fio",
                            this.portfolioRepository.findById(2L).get(),
                            this.jobCategoryRepository.findById(1L).get()
                    );

            PortfolioJob portfolioJob2 =
                    new PortfolioJob(
                            "Massagem Transversal Superior",
                            70.00,
                            "Esta é a descrição do portfolio job massagem transversal",
                            this.portfolioRepository.findById(1L).get(),
                            this.jobCategoryRepository.findById(2L).get()
                    );

            PortfolioJob portfolioJob3 =
                    new PortfolioJob(
                            "",
                            30.00,
                            "Esta é a descrição do portfolio aula de inglês",
                            this.portfolioRepository.findById(3L).get(),
                            this.jobCategoryRepository.findById(3L).get()
                    );

            this.portfolioJobRepository.saveAll(List.of(portfolioJob1, portfolioJob2, portfolioJob3));
            logger.info("Portfolio Job seeded");
        }
    }

//    @Transactional
    private void seedUser(boolean active) throws ParseException {
        int qt = this.userRepository.findAll().size();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

//        Set<Role> roles = new HashSet<>();
//        roles.add(this.roleRepository.findById(1L).get());

        if (qt < 6) {
            boolean change = false;

            for (int i = 1; i < 9; i++) {
                if (change) {
                    String email = "teste"+i+"@gmail.com";
                    String cpf = "1234567898" + i;
                    String dateInString = "2002-07-0" + i;

                    active = !active;

                    User user = new User();
                    user.setCpf(cpf);
                    user.setEmail(email);
                    user.setFirstName("Gabriely");
                    user.setLastName(" Santos " + i);
                    user.setIsActive(active);
                    user.setPhone(cpf);
                    user.setBirthday(formatter.parse(dateInString));
                    user.setPassword(new BCryptPasswordEncoder().encode("test123"));
//                    user.setRoles(Set.of(this.roleRepository.findById(1L).get()));
                    user.setRole(RoleType.USER);
                    user.setIsJobProvider(false);

                    this.userRepository.save(user);
                    change = false;
                } else {
                    String email = "gab"+i+"@gmail.com";
                    String cpf = i+"266567898" + i;
                    String dateInString = "200"+i+"-08-0" + i;

//TODO validate if older than 17 years old

                    active = !active;

                    User user = new User();
                    user.setCpf(cpf);
                    user.setEmail(email);
                    user.setFirstName("Rodrigo");
                    user.setLastName(" Ablu " + i);
                    user.setIsActive(active);
                    user.setPhone(cpf);
                    user.setBirthday(formatter.parse(dateInString));
                    user.setPassword(new BCryptPasswordEncoder().encode("test123"));
                    user.setIsJobProvider(true);

//                    user.addRole(this.roleRepository.findById(3L).get());
                    user.setRole(RoleType.ENTERPRISE);
                    this.userRepository.save(user);
                    change = true;
                }

            }

            for (int i = 1; i < 3; i++) {
                String email = "mandinha"+i+"@gmail.com";
                String cpf = i+"360278988" + i;
                String dateInString = "200"+i+"-04-0" + i;

                User user = new User();
                user.setCpf(cpf);
                user.setEmail(email);
                user.setFirstName("Amanda ");
                user.setLastName(" Suit" + i);
                user.setIsActive(active);
                user.setPhone(cpf);
                user.setBirthday(formatter.parse(dateInString));
                user.setPassword(new BCryptPasswordEncoder().encode("test123"));
//                user.setRoles(Set.of(this.roleRepository.findById(2L).get()));
                user.setIsJobProvider(false);
                user.setRole(RoleType.ADMIN);
                this.userRepository.save(user);
            }
            logger.info("User seeded");
        }
    }

}
