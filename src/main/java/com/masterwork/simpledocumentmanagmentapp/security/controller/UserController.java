package com.masterwork.simpledocumentmanagmentapp.security.controller;

import com.masterwork.simpledocumentmanagmentapp.security.model.dto.RoleDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.UserReqDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.UserResDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.UsersDto;
import com.masterwork.simpledocumentmanagmentapp.security.service.UserServiceImpl;
import com.masterwork.simpledocumentmanagmentapp.utility.model.InfoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "User controller provide CRUD operations to User resource", tags = {"Users"})
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/admin/user")
public class UserController {

  private final UserServiceImpl userService;

  public UserController(UserServiceImpl userService) {
    this.userService = userService;
  }

  @ApiOperation(value = "Get all users")
  @GetMapping
  public ResponseEntity<UsersDto> getAll() {
    return ResponseEntity.status(200).body(userService.findAll());
  }

  @ApiOperation(value = "Get a given user by username")
  @GetMapping("/{userName}")
  public ResponseEntity<UserResDto> getByUserName(@PathVariable String userName) {
    return ResponseEntity.status(200).body(userService.convertToResDto(userService.findByName(userName)));
  }

  @ApiOperation(value = "Create new user")
  @PostMapping
  public ResponseEntity<UserResDto> createNewUser(@Valid @RequestBody UserReqDto userReqDto) {
    return ResponseEntity.status(201).body(userService.convertToResDto(userService.createUser(userReqDto)));
  }

  @ApiOperation(value = "Modify a given user by name")
  @PutMapping("/{userName}")
  public ResponseEntity<UserResDto> modifyUser(@PathVariable String userName,
                                               @Valid @RequestBody UserReqDto userReqDto) {
    userReqDto.setUserName(userName);
    return ResponseEntity.status(200).body(userService.modifyBaseUserData(userReqDto));
  }

  @ApiOperation(value = "Delete a given user by name")
  @DeleteMapping("/{userName}")
  public ResponseEntity<InfoDto> deleteUserByUserName(@PathVariable String userName) {
    userService.deleteUser(userName);
    return ResponseEntity.status(200)
        .body(new InfoDto("Result of request", "Successful deletion of the follwing user: " + userName));
  }

  @ApiOperation(value = "Add an existing role to an existing user")
  @PostMapping("/{userName}/role/")
  public ResponseEntity<UserResDto> addRole(@PathVariable String userName, @RequestBody RoleDto roleDto) {
    return ResponseEntity.status(200).body(userService.addRole(userName, roleDto));
  }

  @ApiOperation(value = "Delete a role of a given user")
  @DeleteMapping("/{userName}/role/{roleName}")
  public ResponseEntity<InfoDto> deleteRole(@PathVariable String userName, @PathVariable String roleName) {
    userService.deleteRole(userName, roleName);
    return ResponseEntity.status(200).body(new InfoDto(
        "Result of request", "Successful deletion of the following Role from " + userName +"user: " + roleName));
  }
}
