package com.masterwork.simpledocumentmanagmentapp.security.service;

import com.masterwork.simpledocumentmanagmentapp.exception.RequestCauseConflictException;
import com.masterwork.simpledocumentmanagmentapp.exception.RequestedResourceNotFoundException;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.RoleDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.UserReqDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.UserResDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.UsersDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.entity.Role;
import com.masterwork.simpledocumentmanagmentapp.security.model.entity.User;
import com.masterwork.simpledocumentmanagmentapp.security.model.entity.UserDetailsImpl;
import com.masterwork.simpledocumentmanagmentapp.security.model.entity.UserRole;
import com.masterwork.simpledocumentmanagmentapp.security.repository.UserRepository;
import com.masterwork.simpledocumentmanagmentapp.security.repository.UserRoleRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

  private final UserRepository userRepository;
  private final UserRoleRepository userRoleRepository;
  private final RoleService roleService;
  private final PasswordEncoder passwordEncoder;

  public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository,
                         RoleService roleService, @Lazy PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.userRoleRepository = userRoleRepository;
    this.roleService = roleService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public User findByName(String userName) {
    return userRepository.findByUserName(userName)
        .orElseThrow(() -> new RequestedResourceNotFoundException("Not exisiting username: " + userName));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return new UserDetailsImpl(findByName(username));
  }

  @Override
  public UsersDto findAll() {
    List<User> users = userRepository.findAll();
    if(users.isEmpty()) {
      throw new RequestedResourceNotFoundException("Requested resource is not found");
    } else {
      return convertToDtoList(users);
    }
  }

  @Override
  public UserResDto convertToResDto(User user) {
    return new UserResDto(
        user.getUserName(),
        user.getFirstName(),
        user.getLastName(),
        user.getEnabled(),
        user.getUserRoles().stream().map(ur -> ur.getRole().getName()).collect(Collectors.toList())
    );
  }

  @Override
  public UsersDto convertToDtoList(List<User> users) {
    UsersDto usersDto = new UsersDto();
    users.forEach(user -> usersDto.getUsers().add(convertToResDto(user)));
    return usersDto;
  }

  @Override
  public User createUser(UserReqDto userReqDto) {
    if(userRepository.existsByUserName(userReqDto.getUserName())) {
      throw new RequestCauseConflictException("User is (" + userReqDto.getUserName() + ") already exist.");
    } else {
      return userRepository.save(
          new User(
              userReqDto.getUserName(),
              userReqDto.getFirstName(),
              userReqDto.getLastName(),
              passwordEncoder.encode(userReqDto.getPassword()),
              userReqDto.getEnabled()
          )
      );
    }
  }

  @Override
  public UserResDto modifyBaseUserData(UserReqDto userReqDto) {
    User user = findByName(userReqDto.getUserName());
    user.setUserName(userReqDto.getUserName());
    user.setFirstName(userReqDto.getFirstName());
    user.setLastName(userReqDto.getLastName());
    user.setPassword(passwordEncoder.encode(userReqDto.getPassword()));
    if(userReqDto.getEnabled() != null) {
      user.setEnabled(userReqDto.getEnabled());
    }
    return convertToResDto(userRepository.save(user));
  }

  @Override
  public void deleteUser(String userName) {
    userRepository.delete(findByName(userName));
  }

  @Override
  public UserResDto addRole(String userName, RoleDto roleDto) {
    User user = findByName(userName);
    Role role = roleService.findByName(roleDto.getName());
    user.getUserRoles().add(new UserRole(null, user, role, true));
    return convertToResDto(userRepository.save(user));
  }

  @Override
  public void deleteRole(String userName, String roleName) {
    User user = findByName(userName);
    Role role = roleService.findByName(roleName);
    UserRole userRole = user.getUserRoles().stream().filter(ur -> ur.getRole().getName().equals(role.getName())).findFirst()
        .orElseThrow(() -> new RequestedResourceNotFoundException("The user does not have the required role: " + roleName));
    user.getUserRoles().remove(userRole);
    role.getUserRoles().remove(userRole);
    userRoleRepository.delete(userRole);
  }

}
