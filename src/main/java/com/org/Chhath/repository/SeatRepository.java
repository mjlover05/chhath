package com.org.Chhath.repository;

import com.org.Chhath.model.Seat;
import com.org.Chhath.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
    boolean existsByBookedBy(User user);

    Seat findByBookedBy(User user);


}
