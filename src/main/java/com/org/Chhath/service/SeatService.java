package com.org.Chhath.service;

import com.org.Chhath.model.Seat;
import com.org.Chhath.model.User;
import com.org.Chhath.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

    private SeatRepository seatRepository;
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }


    public List<Seat> findAll() {
        return seatRepository.findAll();
    }

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    public void bookSeat(int id) {
        Seat seat = seatRepository.findById(id).orElseThrow();
        if (!seat.isBooked()) {
            seat.setBooked(true);
            seatRepository.save(seat);
        }
    }

    public boolean bookSeat(int seatId, User user) {
        // 1️⃣ Check if user already booked a seat
        Seat existing = seatRepository.findByBookedBy(user);
        if (existing != null) {
            return false; // already booked one
        }

        // 2️⃣ Find seat by ID
        Seat seat = seatRepository.findById(seatId).orElse(null);
        if (seat == null || seat.isBooked()) {
            return false; // seat not found or already booked
        }

        // 3️⃣ Assign user and mark booked
        seat.setBooked(true);
        seat.setBookedBy(user);
        seatRepository.save(seat);
        return true;
    }


    public void cancelSeat(User user) {
        Seat seat = seatRepository.findByBookedBy(user);
        if (seat != null) {
            seat.setBooked(false);
            seat.setBookedBy(null);
            seatRepository.save(seat);
        }
    }

    public Seat getSeatBy(User user) {
        return seatRepository.findByBookedBy(user);
    }

}


//    public boolean bookSeat(int seatId, User user) {
//        // Check if user already booked
//        if (seatRepository.existsByBookedBy(user)) {
//            return false; // already booked one seat
//        }
//
//        Seat seat = seatRepository.findById(seatId).orElse(null);
//        if (seat != null && !seat.isBooked()) {
//            seat.setBooked(true);
//            seat.setBookedBy(user);
//            seatRepository.save(seat);
//            return true;
//        }
//        return false;
//    }
