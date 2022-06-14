package com.masterwork.simpledocumentmanagmentapp.security.service;

import com.masterwork.simpledocumentmanagmentapp.security.model.dto.RoleDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.UserReqDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.UserResDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.UsersDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.entity.User;

import java.util.List;

public interface UserService {

  UsersDto findAll();

  User findByName(String userName);

  UserResDto convertToResDto(User user);

  UsersDto convertToDtoList(List<User> users);

  User createUser(UserReqDto userReqDto);

  UserResDto modifyBaseUserData(UserReqDto userReqDto);

  void deleteUser(String userName);

  UserResDto addRole(String userName, RoleDto roleDto);

  void deleteRole(String userName, String roleName);
}
