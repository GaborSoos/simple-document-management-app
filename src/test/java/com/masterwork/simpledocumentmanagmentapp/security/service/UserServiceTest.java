package com.masterwork.simpledocumentmanagmentapp.security.service;

import com.masterwork.simpledocumentmanagmentapp.document.model.dto.DocumentResDto;
import com.masterwork.simpledocumentmanagmentapp.document.model.dto.DocumentsDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.UserResDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.dto.UsersDto;
import com.masterwork.simpledocumentmanagmentapp.security.model.entity.Role;
import com.masterwork.simpledocumentmanagmentapp.security.model.entity.User;
import com.masterwork.simpledocumentmanagmentapp.security.model.entity.UserRole;
import com.masterwork.simpledocumentmanagmentapp.security.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

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
  
}
