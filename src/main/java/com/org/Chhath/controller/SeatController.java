package com.org.Chhath.controller;

import com.org.Chhath.model.Seat;
import com.org.Chhath.model.User;
import com.org.Chhath.service.SeatService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class SeatController {

    private SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

//    @GetMapping("/dashboard")
//    public String viewDashboard(Model model) {
//        List<Seat> seats = seatService.getAllSeats(); // Make sure this returns a non-null list
//        model.addAttribute("seats", seats != null ? seats : new ArrayList<Seat>());
//        return "dashboard";
//    }


//    @PostMapping("/dashboard/book/{id}")
//    public String bookSeat(@PathVariable int id) {
//        seatService.bookSeat(id);
//        return "redirect:/dashboard";
//    }


    @GetMapping("/dashboard")
    public String viewDashboard(Model model, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        List<Seat> seats = seatService.getAllSeats();

        Seat bookedSeat = seatService.getSeatBy(loggedUser);

        model.addAttribute("seats", seats);
        model.addAttribute("bookedSeat", bookedSeat);
        return "dashboard";
    }

    @PostMapping("/dashboard/book/{id}")
    public String bookSeat(@PathVariable int id, HttpSession session, RedirectAttributes redirectAttributes) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        boolean success = seatService.bookSeat(id, loggedUser);

        if (!success) {
            redirectAttributes.addFlashAttribute("error", "You can only book one seat!");
        }

        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard/cancel")
    public String cancelSeat(HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        seatService.cancelSeat(loggedUser);
        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // ✅ Invalidate session — removes all user data
        session.invalidate();

        // ✅ Redirect back to home (login) page
        return "redirect:/";
    }
}
