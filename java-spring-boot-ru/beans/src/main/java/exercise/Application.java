package exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import exercise.daytime.Daytime;
import exercise.daytime.Day;
import exercise.daytime.Night;
import org.springframework.context.annotation.Bean;

// BEGIN

// END

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @Bean
    public Daytime getDateTime(){
        var time = LocalDateTime.now();
        var localDate = LocalDate.of(time.getYear(), time.getMonth(), time.getDayOfMonth());
        if(time.isBefore(LocalDateTime.of(localDate, LocalTime.of(6, 0))) || time.isAfter(LocalDateTime.of(localDate, LocalTime.of(22, 0))))
            return new Night();
        return new Day();
    }
    // END
}
