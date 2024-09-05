package com.photography.lithuanian_press_photography.fakedata;

import com.github.javafaker.DateAndTime;
import com.github.javafaker.Faker;
import com.photography.lithuanian_press_photography.entity.Contest;
import com.photography.lithuanian_press_photography.entity.Participation;
import com.photography.lithuanian_press_photography.entity.User;
import com.photography.lithuanian_press_photography.enums.ParticipationStatus;
import com.photography.lithuanian_press_photography.enums.Role;
import com.photography.lithuanian_press_photography.repository.ContestRepository;
import com.photography.lithuanian_press_photography.repository.ParticipationRepository;
import com.photography.lithuanian_press_photography.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class GenerateData implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ContestRepository contestRepository;
    private final ParticipationRepository participationRepository;
    private final Faker faker = new Faker();



    @Override
    public void run(String... args) throws Exception {
        generateUsers();
        generateContests(10);
        generateParticipation(10);
    }

    private void generateUsers() {
        User user = User.builder()
                .email("admin@admin.com")
                .password("1234")
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
                .password("1234")
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
                .password("1234")
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
            Participation participation = Participation.builder()
                    .status(ParticipationStatus.PENDING)
                    .user(userRepository.findAll().get(faker.number().numberBetween(0,3)))
                    .contest(contestRepository.findAll().get(faker.number().numberBetween(0,10)))
                    .build();
            participationRepository.save(participation);
        }
    }
}
