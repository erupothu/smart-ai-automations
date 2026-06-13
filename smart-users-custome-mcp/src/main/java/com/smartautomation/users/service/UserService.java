package com.smartautomation.users.service;

import com.smartautomation.users.dto.UserRequest;
import com.smartautomation.users.dto.UserResponse;
import com.smartautomation.users.entity.User;
import com.smartautomation.users.entity.UserAuditDocument;
import com.smartautomation.users.repository.UserAuditRepository;
import com.smartautomation.users.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserAuditRepository userAuditRepository;

    public UserService(UserRepository userRepository, UserAuditRepository userAuditRepository) {
        this.userRepository = userRepository;
        this.userAuditRepository = userAuditRepository;
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getAllUsersAsMaps() {
        return getAllUsers().stream().map(this::toMap).toList();
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        return toResponse(findUser(id));
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getUserByIdAsMap(Long id) {
        return toMap(getUserById(id));
    }

    @Transactional
    public UserResponse createUser(UserRequest request) {
        validateUniqueFields(request, null);
        User user = new User();
        mapRequest(request, user);
        User savedUser = userRepository.save(user);
        saveAudit(savedUser, "CREATED");
        return toResponse(savedUser);
    }

    @Transactional
    public Map<String, Object> createUserAsMap(UserRequest request) {
        return toMap(createUser(request));
    }

    @Transactional
    public UserResponse updateUser(Long id, UserRequest request) {
        User existingUser = findUser(id);
        validateUniqueFields(request, id);
        mapRequest(request, existingUser);
        User updatedUser = userRepository.save(existingUser);
        saveAudit(updatedUser, "UPDATED");
        return toResponse(updatedUser);
    }

    @Transactional
    public Map<String, Object> updateUserAsMap(Long id, UserRequest request) {
        return toMap(updateUser(id, request));
    }

    @Transactional
    public void deleteUser(Long id) {
        User existingUser = findUser(id);
        userRepository.delete(existingUser);
        saveAudit(existingUser, "DELETED");
    }

    @Transactional
    public Map<String, Object> deleteUserAsMap(Long id) {
        deleteUser(id);
        return Map.of(
                "message", "User deleted successfully",
                "id", id
        );
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    private void validateUniqueFields(UserRequest request, Long userId) {
        userRepository.findByEmail(request.getEmail())
                .filter(user -> !user.getId().equals(userId))
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Email already exists: " + request.getEmail());
                });

        userRepository.findAll().stream()
                .filter(user -> user.getMobileNumber().equals(request.getMobileNumber()))
                .filter(user -> !user.getId().equals(userId))
                .findFirst()
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Mobile number already exists: " + request.getMobileNumber());
                });
    }

    private void mapRequest(UserRequest request, User user) {
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobileNumber());
        user.setActive(request.isActive());
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getMobileNumber(),
                user.isActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    private Map<String, Object> toMap(UserResponse user) {
        return Map.of(
                "id", user.id(),
                "firstName", user.firstName(),
                "lastName", user.lastName(),
                "email", user.email(),
                "mobileNumber", user.mobileNumber(),
                "active", user.active(),
                "createdAt", user.createdAt() == null ? null : user.createdAt().toString(),
                "updatedAt", user.updatedAt() == null ? null : user.updatedAt().toString()
        );
    }

    private void saveAudit(User user, String action) {
        userAuditRepository.save(new UserAuditDocument(
                user.getId(),
                action,
                user.getEmail(),
                LocalDateTime.now()
        ));
    }
}
