package com.masterwork.simpledocumentmanagmentapp.partner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.masterwork.simpledocumentmanagmentapp.TestNoSecurityConfig;
import com.masterwork.simpledocumentmanagmentapp.partner.model.dto.PartnerResDto;
import com.masterwork.simpledocumentmanagmentapp.partner.model.dto.PartnersDto;
import com.masterwork.simpledocumentmanagmentapp.partner.model.entity.Partner;
import com.masterwork.simpledocumentmanagmentapp.security.model.entity.Role;
import com.masterwork.simpledocumentmanagmentapp.security.model.entity.User;
import com.masterwork.simpledocumentmanagmentapp.security.model.entity.UserDetailsImpl;
import com.masterwork.simpledocumentmanagmentapp.security.model.entity.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestNoSecurityConfig.class)
@Transactional
@Sql("classpath:data.sql")
public class PartnerControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PartnerService partnerService;

  @Autowired
  ObjectMapper mapper;

  private Role role1;
  private User user1;
  private Partner testPartner1;
  private Partner testPartner2;
  private Partner testPartner3;
  private PartnerResDto testPartnerResDto1;
  private PartnerResDto testPartnerResDto2;
  private PartnerResDto testPartnerResDto3;

  @Before
  public void init() {
    role1 = new Role("ADMIN", true);
    user1 = new User(1L, "johndoe", "","", "$2a$10$fdfwiDTCQjarhj7U80RgJuImLhSOAKpOLXJYPtY15xpSyMWW.HfRy", true, Arrays.asList(new UserRole(1L, null, role1, true)));
    testPartner1 = new Partner(1L, "MOL", "1111 Bp. Texas utca 1.", "oil@mol.hu", "06-1-234-5678", true, null);
    testPartner2 = new Partner(2L, "Google", "1111 Bp. Kereső utca 1.", "info@google.com", "06-1-234-5678", true, null);
    testPartner3 = new Partner(3L, "Facebook", "1111 Bp. Cukkemberg utca 1.", "info@fb.com", "06-1-234-5678", true, null);
    testPartnerResDto1 = new PartnerResDto("MOL", "1111 Bp. Texas utca 1.", "oil@mol.hu", "06-1-234-5678", true);
    testPartnerResDto2 = new PartnerResDto("Google", "1111 Bp. Kereső utca 1.", "info@google.com", "06-1-234-5678", true);
    testPartnerResDto3 = new PartnerResDto("Facebook", "1111 Bp. Cukkemberg utca 1.", "info@fb.com", "06-1-234-5678", true);
  }

  @Test
  public void when_getPartnerWithoutPartnerName_should_respondAllPartnerInPartnersDtoJson()
      throws Exception {
    PartnersDto partnersDto = new PartnersDto(Arrays.asList(testPartnerResDto1, testPartnerResDto2, testPartnerResDto3));
    String expectedResponse = mapper.writeValueAsString(partnersDto);

    UserDetailsImpl userDetails = new UserDetailsImpl(user1);
    Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    mockMvc.perform(MockMvcRequestBuilders.get("/partner").principal(auth)).andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(expectedResponse));
  }

}
