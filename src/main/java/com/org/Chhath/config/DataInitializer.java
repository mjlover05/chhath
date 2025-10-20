package com.org.Chhath.config;

import com.org.Chhath.model.Seat;
import com.org.Chhath.repository.SeatRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initSeats(SeatRepository seatRepository) {
        return args -> {
            // Check if seats are already initialized
            if (seatRepository.count() == 0) {
                for (int i = 1; i <= 172; i++) {
                    Seat seat = new Seat();
                    seat.setSeatNumber(i);
                    seat.setBooked(false);
                    seatRepository.save(seat);
                }
                System.out.println("Initialized 176 seats!");
            }
        };
    }
}
