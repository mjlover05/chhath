package com.org.Chhath.service;

import com.org.Chhath.model.User;
import com.org.Chhath.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public String saveUser(User user) {

        // ✅ Trim values
        String email = user.getEmail().trim();
        String phone = user.getPhone().trim();

        // ✅ Validate email format
        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            throw new IllegalArgumentException("❌ Invalid email format!");
        }

        // ✅ Validate phone (starts with 6–9, 10 digits)
        if (!phone.matches("^[6-9][0-9]{9}$")) {
            throw new IllegalArgumentException("❌ Invalid phone number! Must start with 6/7/8/9 and be 10 digits.");
        }

        // ✅ Check for duplicates
        if (userRepo.findByEmail(email) != null) {
            throw new IllegalArgumentException("⚠️ Email already registered!");
        }

        if (userRepo.findByPhone(phone) != null) {
            throw new IllegalArgumentException("⚠️ Phone number already registered!");
        }

        // ✅ Generate password (firstName + day + month)
        String fullName = user.getName().trim();
        String firstName = fullName.contains(" ") ? fullName.split(" ")[0] : fullName;

        int day = user.getDateOfBirth().getDayOfMonth();
        int month = user.getDateOfBirth().getMonthValue();

        String dayStr = String.format("%02d", day);
        String monthStr = String.format("%02d", month);

        String generatedPassword = firstName + dayStr + monthStr;
        user.setPassword(generatedPassword);

        // ✅ Save user
        userRepo.save(user);

        // Return password to controller for success page
        return generatedPassword;
    }

    public User validateUser(String email, String password) {
        User user = userRepo.findByEmail(email);
        if (user == null) return null;
        return user.getPassword().equals(password) ? user : null;
    }
}
