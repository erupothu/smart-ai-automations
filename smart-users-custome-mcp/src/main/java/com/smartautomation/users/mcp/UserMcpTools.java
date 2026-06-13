package com.smartautomation.users.mcp;

import com.smartautomation.users.dto.UserRequest;
import com.smartautomation.users.service.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class UserMcpTools {

    private final UserService userService;

    public UserMcpTools(UserService userService) {
        this.userService = userService;
    }

    @Tool(name = "list_users", description = "List all users from the users microservice")
    public List<Map<String, Object>> listUsers() {
        return userService.getAllUsersAsMaps();
    }

    @Tool(name = "get_user_by_id", description = "Get a single user by numeric id")
    public Map<String, Object> getUserById(
            @ToolParam(description = "Numeric id of the user", required = true) Long id) {
        return userService.getUserByIdAsMap(id);
    }

    @Tool(name = "create_user", description = "Create a new user with first name, last name, email, mobile number, and active status")
    public Map<String, Object> createUser(
            @ToolParam(description = "User first name", required = true) String firstName,
            @ToolParam(description = "User last name", required = true) String lastName,
            @ToolParam(description = "Unique email address", required = true) String email,
            @ToolParam(description = "Unique mobile number with 10 to 15 digits", required = true) String mobileNumber,
            @ToolParam(description = "Whether the user is active", required = false) Boolean active) {
        return userService.createUserAsMap(toRequest(firstName, lastName, email, mobileNumber, active));
    }

    @Tool(name = "update_user", description = "Update an existing user by id")
    public Map<String, Object> updateUser(
            @ToolParam(description = "Numeric id of the user", required = true) Long id,
            @ToolParam(description = "User first name", required = true) String firstName,
            @ToolParam(description = "User last name", required = true) String lastName,
            @ToolParam(description = "Unique email address", required = true) String email,
            @ToolParam(description = "Unique mobile number with 10 to 15 digits", required = true) String mobileNumber,
            @ToolParam(description = "Whether the user is active", required = false) Boolean active) {
        return userService.updateUserAsMap(id, toRequest(firstName, lastName, email, mobileNumber, active));
    }

    @Tool(name = "delete_user", description = "Delete a user by numeric id")
    public Map<String, Object> deleteUser(
            @ToolParam(description = "Numeric id of the user", required = true) Long id) {
        return userService.deleteUserAsMap(id);
    }

    private UserRequest toRequest(String firstName, String lastName, String email, String mobileNumber, Boolean active) {
        UserRequest request = new UserRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setEmail(email);
        request.setMobileNumber(mobileNumber);
        request.setActive(active == null || active);
        return request;
    }
}