package com.photography.lithuanian_press_photography.fakedata;

import com.github.javafaker.Faker;
import com.photography.lithuanian_press_photography.entity.Category;
import com.photography.lithuanian_press_photography.entity.Contest;
import com.photography.lithuanian_press_photography.entity.UserParticipation;
import com.photography.lithuanian_press_photography.entity.User;
import com.photography.lithuanian_press_photography.enums.ParticipationStatus;
import com.photography.lithuanian_press_photography.enums.PhotoSubmissionType;
import com.photography.lithuanian_press_photography.enums.Role;
import com.photography.lithuanian_press_photography.repository.CategoryRepository;
import com.photography.lithuanian_press_photography.repository.ContestRepository;
import com.photography.lithuanian_press_photography.repository.UserParticipationRepository;
import com.photography.lithuanian_press_photography.repository.UserRepository;
import com.photography.lithuanian_press_photography.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class GenerateData implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ContestRepository contestRepository;
    private final UserParticipationRepository userParticipationRepository;
    private final CategoryRepository categoryRepository;
    private final PhotoService photoService;
    private final Faker faker = new Faker();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



    @Override
    public void run(String... args) throws Exception {
        photoService.deleteAll();
        generateUsers();
        generateContests(2);
        generateParticipation(10);
        generateCategory(3);
    }

    private void generateUsers() {
        User user = User.builder()
                .email("admin@admin.com")
                .password(passwordEncoder.encode("1234"))
                .role(Role.ADMIN)
                .firstName("Adminas")
                .lastName("Adminaitis")
                .institution("Administration")
                .isFreelance(false)
                .isNonLocked(true)
                .phoneNumber("911")
                .isEnabled(true)
                .birthYear(1990)
                .build();
        User user2 = User.builder()
                .email("jury@jury.com")
                .password(passwordEncoder.encode("1234"))
                .role(Role.JURY)
                .firstName("Jury")
                .lastName("Juryte")
                .institution("Jurystai")
                .isFreelance(false)
                .isNonLocked(true)
                .phoneNumber("112")
                .isEnabled(true)
                .birthYear(1990)
                .build();
        User user3 = User.builder()
                .email("user@user.com")
                .password(passwordEncoder.encode("1234"))
                .role(Role.USER)
                .firstName("User")
                .lastName("Useris")
                .institution("Useriai")
                .isFreelance(false)
                .isNonLocked(true)
                .phoneNumber("777")
                .isEnabled(true)
                .birthYear(1990)
                .build();
        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);
    }

    private void generateContests(int contestQuantity) {
        for (int i = 0; i < contestQuantity; i++) {
            Contest contest = Contest.builder()
                    .name(faker.commerce().productName())
                    .description(faker.lorem().sentence(70, 150))
                    .startDate(ZonedDateTime.now())
                    .maxUserSubmissions(1 + i)
                    .build();
            contestRepository.save(contest);
        }

    }
    private void generateParticipation(int participationQuantity){
        for (int i = 0; i < participationQuantity; i++) {
            UserParticipation userParticipation = UserParticipation.builder()
                    .status(ParticipationStatus.PENDING)
                    .user(userRepository.findAll().get(faker.number().numberBetween(0,3)))
                    .contest(contestRepository.findAll().get(faker.number().numberBetween(0,1)))
                    .build();
            userParticipationRepository.save(userParticipation);
        }
    }

    private void generateCategory (int categoryQuantity) {
        for (int i = 0; i < categoryQuantity; i++) {
            Category category = Category.builder()
                    .type(PhotoSubmissionType.SINGLE)
                    .description(faker.lorem().sentence(15))
                    .name(faker.commerce().productName())
                    .maxUserSubmissions(5)
                    .contest(contestRepository.findAll().get(faker.number().numberBetween(0,1)))
                    .maxUserSubmissions(22)
                    .build();

            categoryRepository.save(category);
            photoService.init(category.getId());
        }
    }
}
