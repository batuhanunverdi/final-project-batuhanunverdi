package org.patikadev.finalprojectbatuhanunverdi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.patikadev.finalprojectbatuhanunverdi.model.request.UserRequest;
import org.patikadev.finalprojectbatuhanunverdi.service.UserService;
import org.patikadev.finalprojectbatuhanunverdi.validator.UserRequestValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mert Batuhan UNVERDI
 * @since 18.05.2022
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
@Api(value = "User Documentation")
public class UserController {

    private final UserRequestValidator userRequestValidator;
    private final UserService userService;

    @PutMapping(path = "/update-user/{id}")
    @ApiOperation(value = "Update User method")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        userRequestValidator.validate(userRequest);
        userService.updateUser(id, userRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/delete-user/{id}")
    @ApiOperation(value = "Delete User Method")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
