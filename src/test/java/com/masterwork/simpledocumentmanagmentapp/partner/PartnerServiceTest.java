package com.masterwork.simpledocumentmanagmentapp.partner;

import com.masterwork.simpledocumentmanagmentapp.exception.RequestCauseConflictException;
import com.masterwork.simpledocumentmanagmentapp.exception.RequestedResourceNotFoundException;
import com.masterwork.simpledocumentmanagmentapp.partner.model.dto.PartnerReqDto;
import com.masterwork.simpledocumentmanagmentapp.partner.model.dto.PartnerResDto;
import com.masterwork.simpledocumentmanagmentapp.partner.model.dto.PartnersDto;
import com.masterwork.simpledocumentmanagmentapp.partner.model.entity.Partner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PartnerServiceTest {

  @Mock
  private PartnerRepository partnerRepository;

  @Spy
  @InjectMocks
  private PartnerServiceImpl partnerService;

  private Partner testPartner1;
  private Partner testPartner2;
  private List<Partner> testPartners;
  private PartnerReqDto testPartnerReqDto1;
  private PartnerReqDto testPartnerReqDto2;
  private PartnerResDto expectedPartnerResDto1;
  private PartnerResDto expectedPartnerResDto2;
  private PartnersDto expectedPartnersDto;

  @Before
  public void init() {
    testPartner1 = new Partner("Google", "1221 Bp. Kereső utca 1.", "info@google.com", "06-1-234-5678", true);
    testPartner2 = new Partner("Facebook", "1221 Bp. Cukkemberg utca 1.", "info@fb.com", "06-1-234-5678", true);
    testPartners = new ArrayList<>(Arrays.asList(testPartner1, testPartner2));
    testPartnerReqDto1 =
        new PartnerReqDto("Google", "1221 Bp. Kereső utca 1.", "info@google.com", "06-1-234-5678", true);
    testPartnerReqDto2 =
        new PartnerReqDto("Facebook", "1221 Bp. Cukkemberg utca 1.", "info@fb.com", "06-1-234-5678", true);
    expectedPartnerResDto1 = new PartnerResDto("Google", "1221 Bp. Kereső utca 1.", "info@google.com", "06-1-234-5678", true);
    expectedPartnerResDto2 = new PartnerResDto("Facebook", "1221 Bp. Cukkemberg utca 1.", "info@fb.com", "06-1-234-5678", true);
    expectedPartnersDto = new PartnersDto();
    expectedPartnersDto.getPartnerResDtos().add(expectedPartnerResDto1);
    expectedPartnersDto.getPartnerResDtos().add(expectedPartnerResDto2);
  }

  @Test
  public void when_convertPartnerToPartnerResDto_should_returnValidPartnerResDto() {
    PartnerResDto actualPartnerResDto = partnerService.convertToDto(testPartner1);

    Assert.assertEquals(expectedPartnerResDto1, actualPartnerResDto);
  }

  @Test
  public void when_convertPartnerListToPartnersDto_should_returnValidPartnersDto() {
    PartnersDto actualPartnersDto = partnerService.convertToDtoList(testPartners);

    Assert.assertEquals(expectedPartnersDto, actualPartnersDto);
  }

  @Test
  public void when_findPartnerByExistingPartnerName_should_returnAppropriatePartner() {
    String testPartnerName = testPartner1.getName();
    Partner expectedPartner = testPartner1;
    when(partnerRepository.findByName(anyString())).thenReturn(Optional.of(expectedPartner));

    Partner actualPartner = partnerService.findByName(testPartnerName);

    Assert.assertEquals(expectedPartner, actualPartner);
  }

  @Test
  public void when_findPartnerByNotExistingPartnerName_should_throwAppropriateException() {
    when(partnerRepository.findByName(anyString())).thenReturn(Optional.empty());
    String expectedMessage = "Requested resource is not found";

    Exception actualException =
        assertThrows(RequestedResourceNotFoundException.class, () -> partnerService.findByName("whatever"));

    Assert.assertEquals(expectedMessage, actualException.getMessage());
  }

  @Test
  public void when_findAllPartnerAndThereIsAtLeastOneInDB_should_returnAllExistingPartnerAsPartnersDto() {
    when(partnerRepository.findAll()).thenReturn(testPartners);
    Mockito.doReturn(expectedPartnersDto).when(partnerService).convertToDtoList(any(List.class));

    PartnersDto actualPartnersDto = partnerService.findAll();

    Assert.assertEquals(expectedPartnersDto, actualPartnersDto);
  }

  @Test
  public void when_findAllDocumentTagAndThereIsNotInDB_should_throwAppropriateException() {
    String expectedMessage = "Requested resource is not found";

    Exception actualException =
        assertThrows(RequestedResourceNotFoundException.class, () -> partnerService.findByName("whatever"));

    Assert.assertEquals(expectedMessage, actualException.getMessage());
  }

  @Test
  public void when_createANewPartnerWhichIsNotExistYet_should_returnTheNewPartnerAsPartnerResDto() {
    when(partnerRepository.existsPartnerByName(testPartnerReqDto1.getName())).thenReturn(false);
    when(partnerRepository.save(any(Partner.class))).thenReturn(testPartner1);
    Mockito.doReturn(expectedPartnerResDto1).when(partnerService).convertToDto(any(Partner.class));

    PartnerResDto actualPartnerResDto = partnerService.createPartner(testPartnerReqDto1);

    Assert.assertEquals(expectedPartnerResDto1, actualPartnerResDto);
    verify(partnerRepository, times(1)).existsPartnerByName(anyString());
    verify(partnerRepository, times(1)).save(any(Partner.class));
    verify(partnerService, times(1)).convertToDto(any(Partner.class));
  }

  @Test
  public void when_createANewDocumentTypeWhichIsAlreadyExist_should_throwAppropriateException() {
    when(partnerRepository.existsPartnerByName(testPartnerReqDto1.getName())).thenReturn(true);
    String expectedMessage = "Partner is (" + testPartnerReqDto1.getName() + ") already exist.";

    Exception actualException =
        assertThrows(RequestCauseConflictException.class, () -> partnerService.createPartner(testPartnerReqDto1));

    Assert.assertEquals(expectedMessage, actualException.getMessage());
    verify(partnerRepository, times(1)).existsPartnerByName(anyString());
    verify(partnerRepository, times(0)).save(any(Partner.class));
    verify(partnerService, times(0)).convertToDto(any(Partner.class));
  }

  @Test
  public void when_modifyPartnerBaseData_should_returnPartnerResDtoWithNewPartnerData() {
    Mockito.doReturn(testPartner1).when(partnerService).findByName(anyString());
    when(partnerRepository.save(any(Partner.class))).thenReturn(testPartner2);
    Mockito.doReturn(expectedPartnerResDto2).when(partnerService).convertToDto(any(Partner.class));

    PartnerResDto actualPartnerResDto = partnerService.modifyPartnerBaseData(testPartnerReqDto2);

    Assert.assertEquals(expectedPartnerResDto2, actualPartnerResDto);
    verify(partnerService, times(1)).findByName(anyString());
    verify(partnerRepository, times(1)).save(any(Partner.class));
    verify(partnerService, times(1)).convertToDto(any(Partner.class));
  }

  @Test
  public void when_tryToDeleteAPartnerWhichIsExistInDB_should_callOnesDeleteMethod() {
    when(partnerRepository.existsPartnerByName(testPartnerReqDto1.getName())).thenReturn(true);
    Mockito.doNothing().when(partnerRepository).deleteByName(anyString());

    partnerService.deletePartner(testPartnerReqDto1.getName());

    verify(partnerRepository, times(1)).existsPartnerByName(anyString());
    verify(partnerRepository, times(1)).deleteByName(anyString());
  }

  @Test
  public void when_tryToDeleteAPartnerWhichIsNotExistInDB_should_throwAppropriateException() {
    when(partnerRepository.existsPartnerByName(anyString())).thenReturn(false);
    String expectedMessage = "Partner (" + testPartnerReqDto1.getName() + ") is not exist.";

    Exception actualException = assertThrows(
        RequestedResourceNotFoundException.class, () -> partnerService.deletePartner(testPartnerReqDto1.getName()));

    Assert.assertEquals(expectedMessage, actualException.getMessage());
    verify(partnerRepository, times(1)).existsPartnerByName(anyString());
    verify(partnerRepository, times(0)).deleteByName(anyString());
  }

}
