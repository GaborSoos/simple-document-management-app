package com.masterwork.simpledocumentmanagmentapp.security.controller;

import com.masterwork.simpledocumentmanagmentapp.document.model.dto.DocumentResDto;
import com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto.DocTagReqDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.RoleDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.UserReqDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.UserResDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.UsersDto;
import com.masterwork.simpledocumentmanagmentapp.security.service.UserServiceImpl;
import com.masterwork.simpledocumentmanagmentapp.utility.model.InfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/user")
public class UserController {

  private final UserServiceImpl userService;

  public UserController(UserServiceImpl userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<UsersDto> getAll() {
    return ResponseEntity.status(200).body(userService.findAll());
  }

  @GetMapping("/{userName}")
  public ResponseEntity<UserResDto> getByUserName(@PathVariable String userName) {
    return ResponseEntity.status(200).body(userService.convertToResDto(userService.findByName(userName)));
  }

  @PostMapping
  public ResponseEntity<UserResDto> createNewUser(@Valid @RequestBody UserReqDto userReqDto) {
    return ResponseEntity.status(201).body(userService.convertToResDto(userService.createUser(userReqDto)));
  }

  @PutMapping("/{userName}")
  public ResponseEntity<UserResDto> modifyUser(@PathVariable String userName,
                                               @Valid @RequestBody UserReqDto userReqDto) {
    userReqDto.setUserName(userName);
    return ResponseEntity.status(200).body(userService.modifyBaseUserData(userReqDto));
  }

  // TODO:
  //Put: modify role of a given user -> user/{username}/role

  @DeleteMapping("/{userName}")
  public ResponseEntity<InfoDto> deleteUserByUserName(@PathVariable String userName) {
    userService.deleteUser(userName);
    return ResponseEntity.status(200)
        .body(new InfoDto("Result of request", "Successful deletion of the follwing user: " + userName));
  }


  @PostMapping("/{userName}/role/")
  public ResponseEntity<UserResDto> addRole(@PathVariable String userName, @RequestBody RoleDto roleDto) {
    return ResponseEntity.status(200).body(userService.addRole(userName, roleDto));
  }

  @DeleteMapping("/{userName}/role/{roleName}")
  public ResponseEntity<InfoDto> deleteRole(@PathVariable String userName, @PathVariable String roleName) {
    userService.deleteRole(userName, roleName);
    return ResponseEntity.status(200).body(new InfoDto(
        "Result of request", "Successful deletion of the following Role from " + userName +"user: " + roleName));
  }
}
