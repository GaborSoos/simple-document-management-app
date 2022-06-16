package com.masterwork.simpledocumentmanagmentapp.security.service;

import com.masterwork.simpledocumentmanagmentapp.exception.RequestCauseConflictException;
import com.masterwork.simpledocumentmanagmentapp.exception.RequestedResourceNotFoundException;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.RoleDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.UserReqDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.UserResDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.UsersDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.entity.Role;
import com.masterwork.simpledocumentmanagmentapp.security.model.entity.User;
import com.masterwork.simpledocumentmanagmentapp.security.model.entity.UserRole;
import com.masterwork.simpledocumentmanagmentapp.security.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private RoleServiceImpl roleService;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Spy
  @InjectMocks
  private UserServiceImpl userService;

  private User user1;
  private User user2;
  private UserResDto userResDto1;
  private UserResDto userResDto2;
  private Role role1;
  private Role role2;
  private Role role3;
  private UserRole userRole1;
  private UserRole userRole2;
  private UserRole userRole3;
  private UsersDto usersDto;
  private UserReqDto userReqDto1;

  @Before
  public void init() {
    user1 = new User("johndoe", "John", "Doe",
        "$2a$10$b7wnqdlcepRbdn.PFKww8e11aZaLdZtOGl8RVNkd1mKFuWRzA/IpK", true);
    user2 = new User("janedoe", "Jane", "Doe",
        "$2a$10$b7wnqdlcepRbdn.PFKww8e11aZaLdZtOGl8RVNkd1mKFuWRzA/IpK", true);
    role1 = new Role("ADMIN", true);
    role2 = new Role("USER", true);
    role3 = new Role("GUEST", true);
    userRole1 = new UserRole(1L, user1, role1, true);
    userRole2 = new UserRole(2L, user1, role2, true);
    userRole3 = new UserRole(3L, user2, role3, true);
    user1.setUserRoles(new ArrayList<>(Arrays.asList(userRole1, userRole2)));
    user2.setUserRoles(new ArrayList<>(Arrays.asList(userRole3)));
    userResDto1 = new UserResDto("johndoe", "John", "Doe", true,
        new ArrayList<>(Arrays.asList(role1.getName(), role2.getName())));
    userResDto2 = new UserResDto("janedoe", "Jane", "Doe", true,
        new ArrayList<>(Arrays.asList(role3.getName())));
    usersDto = new UsersDto(new ArrayList<>(Arrays.asList(userResDto1, userResDto2)));
    userReqDto1 = new UserReqDto("johndoe", "John", "Doe", "veryverysecret", true);
  }

  @Test
  public void when_convertUserToUserResDto_should_returnValidUserResDto() {
    UserResDto actualUserResDto = userService.convertToResDto(user1);

    Assert.assertEquals(userResDto1, actualUserResDto);
  }

  @Test
  public void when_convertUserListToUsersDto_should_returnValidUsersDto() {
    UsersDto actualUsersDto = userService.convertToDtoList(new ArrayList<>(Arrays.asList(user1, user2)));

    Assert.assertEquals(usersDto, actualUsersDto);
  }

  @Test
  public void when_findUserByExistingUsername_should_returnAppropriateUser() {
    when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(user1));

    User actualUser = userService.findByName(user1.getUserName());

    Assert.assertEquals(user1, actualUser);
    verify(userRepository, times(1)).findByUserName(anyString());
  }

  @Test
  public void when_findUserByNotExistingUsername_should_returnAppropriateUser() {
    when(userRepository.findByUserName(anyString())).thenReturn(Optional.empty());
    String expectedMessage = "Not exisiting username: " + user1.getUserName();
    Exception actualException =
        assertThrows(RequestedResourceNotFoundException.class, () -> userService.findByName(user1.getUserName()));

    Assert.assertEquals(expectedMessage, actualException.getMessage());
    verify(userRepository, times(1)).findByUserName(anyString());
  }

  @Test
  public void when_findAllUserAndThereIsAtLeastOneInDB_should_returnAllExistingUserAsUsersDto() {
    when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
    Mockito.doReturn(usersDto).when(userService).convertToDtoList(any(List.class));

    UsersDto actualUsersDto = userService.findAll();

    Assert.assertEquals(usersDto, actualUsersDto);
    verify(userRepository, times(1)).findAll();
    verify(userService, times(1)).convertToDtoList(any(List.class));
  }

  @Test
  public void when_findAllUserAndThereIsNotInDB_should_throwAppropriateException() {
    when(userRepository.findAll()).thenReturn(Collections.emptyList());
    String expectedMessage = "Requested resource is not found";
    Exception actualException =
        assertThrows(RequestedResourceNotFoundException.class, () -> userService.findAll());

    Assert.assertEquals(expectedMessage, actualException.getMessage());
    verify(userRepository, times(1)).findAll();
    verify(userService, times(0)).convertToDtoList(any(List.class));
  }

  @Test
  public void when_createNewUserWhichIsNotExistYet_should_returnNewUser() {
    when(userRepository.existsByUserName(anyString())).thenReturn(false);
    when(passwordEncoder.encode(anyString())).thenReturn("something");
    when(userRepository.save(any(User.class))).thenReturn(user1);

    User actualUser = userService.createUser(userReqDto1);

    Assert.assertEquals(user1, actualUser);
    verify(userRepository, times(1)).existsByUserName(anyString());
    verify(passwordEncoder, times(1)).encode(anyString());
    verify(userRepository, times(1)).save(any(User.class));

  }

  @Test
  public void when_createANewDocumentWhichIsAlreadyExist_should_throwAppropriateException() {
    when(userRepository.existsByUserName(anyString())).thenReturn(true);
    String expectedMessage = "User is (" + userReqDto1.getUserName() + ") already exist.";

    Exception actualException =
        assertThrows(RequestCauseConflictException.class, () -> userService.createUser(userReqDto1));

    Assert.assertEquals(expectedMessage, actualException.getMessage());
    verify(userRepository, times(1)).existsByUserName(anyString());
    verify(passwordEncoder, times(0)).encode(anyString());
    verify(userRepository, times(0)).save(any(User.class));
  }

  @Test
  public void when_modifyUserBaseData_should_returnDocumentResDtoWithNewDocumentData() {
    Mockito.doReturn(user1).when(userService).findByName(anyString());
    when(passwordEncoder.encode(anyString())).thenReturn("something");
    when(userRepository.save(any(User.class))).thenReturn(user1);
    Mockito.doReturn(userResDto1).when(userService).convertToResDto(any(User.class));

    UserResDto actualUserResDto = userService.modifyBaseUserData(userReqDto1);

    Assert.assertEquals(userResDto1, actualUserResDto);
    verify(userService, times(1)).findByName(anyString());
    verify(passwordEncoder, times(1)).encode(anyString());
    verify(userRepository, times(1)).save(any(User.class));
    verify(userService, times(1)).convertToResDto(any(User.class));
  }

  @Test
  public void when_tryToDeleteUserWhichIsExistInDB_should_callOnesDeleteMethod() {
    Mockito.doReturn(user1).when(userService).findByName(anyString());
    Mockito.doNothing().when(userRepository).delete(any(User.class));

    userService.deleteUser(user1.getUserName());
    verify(userService, times(1)).findByName(anyString());
    verify(userRepository, times(1)).delete(any(User.class));
  }

  @Test
  public void when_tryToDeleteUserWhichIsNotExistInDB_should_throwAppropriateException() {
    Mockito.doThrow(new RequestedResourceNotFoundException("Not exisiting username: " + user1.getUserName()) )
        .when(userService).findByName(anyString());
    String expectedMessage = "Not exisiting username: " + user1.getUserName();

    Exception actualException =
        assertThrows(RequestedResourceNotFoundException.class, () -> userService.deleteUser(user1.getUserName()));

    Assert.assertEquals(expectedMessage, actualException.getMessage());
    verify(userService, times(1)).findByName(anyString());
    verify(userRepository, times(0)).delete(any(User.class));
  }

  @Test
  public void when_addNewRoleToAnExistingUser_should_returnAppropriateUserResDto() {
    Mockito.doReturn(user1).when(userService).findByName(anyString());
    when(roleService.findByName(anyString())).thenReturn(role1);
    when(userRepository.save(any(User.class))).thenReturn(user1);
    Mockito.doReturn(userResDto1).when(userService).convertToResDto(any(User.class));

    UserResDto actualUserResDto = userService.addRole(user1.getUserName(), new RoleDto(role1.getName(), true));

    Assert.assertEquals(userResDto1, actualUserResDto);
    verify(userService, times(1)).findByName(anyString());
    verify(roleService, times(1)).findByName(anyString());
    verify(userRepository, times(1)).save(any(User.class));
    verify(userService, times(1)).convertToResDto(any(User.class));
  }

}
