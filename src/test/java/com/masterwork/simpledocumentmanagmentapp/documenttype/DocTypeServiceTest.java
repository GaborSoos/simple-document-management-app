package com.masterwork.simpledocumentmanagmentapp.documenttype;

import com.masterwork.simpledocumentmanagmentapp.documenttype.model.dto.DocTypeReqDto;
import com.masterwork.simpledocumentmanagmentapp.documenttype.model.dto.DocTypeResDto;
import com.masterwork.simpledocumentmanagmentapp.documenttype.model.dto.DocTypesDto;
import com.masterwork.simpledocumentmanagmentapp.documenttype.model.entity.DocumentType;
import com.masterwork.simpledocumentmanagmentapp.exception.RequestCauseConflictException;
import com.masterwork.simpledocumentmanagmentapp.exception.RequestedResourceNotFoundException;
import org.junit.Assert;
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
public class DocTypeServiceTest {

  @Mock
  private DocTypeRepository docTypeRepository;

  @Spy
  @InjectMocks
  private DocTypeServiceImpl docTypeService;

  @Test
  public void when_convertDocumentTypeToDocTypeResDto_should_returnValidDocTypeResDto() {
    String expectedDocTypeName = "invoice";
    DocumentType inputDocType = new DocumentType(expectedDocTypeName);

    DocTypeResDto actual = docTypeService.convertToDto(inputDocType);

    Assert.assertEquals(expectedDocTypeName, actual.getName());
  }

  @Test
  public void when_convertDocumentTypeListToDocTypesDto_should_returnValidDocTypesDto() {
    List<String> docTypeNames = new ArrayList<>(Arrays.asList("invoice", "contract"));
    List<DocumentType> testDocTypes = new ArrayList<>(Arrays.asList(
        new DocumentType(docTypeNames.get(0)), new DocumentType(docTypeNames.get(1))));
    DocTypesDto expectedDto = new DocTypesDto();
    expectedDto.getDocTypeResDtos().add(new DocTypeResDto(docTypeNames.get(0)));
    expectedDto.getDocTypeResDtos().add(new DocTypeResDto(docTypeNames.get(1)));

    DocTypesDto actualDto = docTypeService.convertToDtoList(testDocTypes);

    Assert.assertEquals(expectedDto, actualDto);
  }


  @Test
  public void when_findDocumentTypeByExistingTypeName_should_returnAppropriateDocumentType() {
    String testTypeName = "invoice";
    DocumentType expectedDocumentType = new DocumentType(1L, testTypeName, null);
    when(docTypeRepository.findByName(anyString())).thenReturn(Optional.of(expectedDocumentType));

    DocumentType actualDocumentType = docTypeService.findByName(testTypeName);

    Assert.assertEquals(expectedDocumentType, actualDocumentType);
  }

  @Test
  public void when_findDocumentTypeByNotExistingTypeName_should_throwAppropriateException() {
    when(docTypeRepository.findByName(anyString())).thenReturn(Optional.empty());
    String expectedMessage = "Requested resource is not found";

    Exception actualException =
        assertThrows(RequestedResourceNotFoundException.class, () -> docTypeService.findByName("whatever"));

    Assert.assertEquals(expectedMessage, actualException.getMessage());
  }

  @Test
  public void when_findAllDocumentTypeAndThereIsAtLeastOneInDB_should_returnAllExistingDocumentTypeAsDocTypesDto() {
    List<String> docTypeNames = new ArrayList<>(Arrays.asList("invoice", "contract"));
    DocTypesDto expectedDocTypesDto = new DocTypesDto();
    expectedDocTypesDto.getDocTypeResDtos().add(new DocTypeResDto(docTypeNames.get(0)));
    expectedDocTypesDto.getDocTypeResDtos().add(new DocTypeResDto(docTypeNames.get(1)));
    when(docTypeRepository.findAll()).thenReturn(
        new ArrayList<>(Arrays.asList(new DocumentType(docTypeNames.get(0)), new DocumentType(docTypeNames.get(1)))));

    DocTypesDto actualDocTagsDto = docTypeService.findAll();

    Assert.assertEquals(expectedDocTypesDto, actualDocTagsDto);
  }

  @Test
  public void when_findAllDocumentTagAndThereIsNotInDB_should_throwAppropriateException() {
    String expectedMessage = "Requested resource is not found";

    Exception actualException =
        assertThrows(RequestedResourceNotFoundException.class, () -> docTypeService.findByName("whatever"));

    Assert.assertEquals(expectedMessage, actualException.getMessage());
  }

  @Test
  public void when_createANewDocumentTypeWhichIsNotExistYet_should_returnTheNewDocTypeAsDocResDto() {
    String newDocTypeName = "invoice";
    DocTypeReqDto testDocTypeReqDto = new DocTypeReqDto(newDocTypeName);
    when(docTypeRepository.existsDocumentTypeByName(newDocTypeName)).thenReturn(false);
    when(docTypeRepository.save(any(DocumentType.class))).thenReturn(new DocumentType(newDocTypeName));
    DocTypeResDto expectedDocTypeResDto = new DocTypeResDto(newDocTypeName);

    DocTypeResDto actualDocTypeResDto = docTypeService.createDocType(testDocTypeReqDto);

    Assert.assertEquals(expectedDocTypeResDto, actualDocTypeResDto);
    verify(docTypeRepository, times(1)).existsDocumentTypeByName(anyString());
    verify(docTypeRepository, times(1)).save(any(DocumentType.class));
  }

  @Test
  public void when_createANewDocumentTypeWhichIsAlreadyExist_should_throwAppropriateException() {
    String existingDocTypeName = "invoice";
    DocTypeReqDto testDocTypeReqDto = new DocTypeReqDto(existingDocTypeName);
    when(docTypeRepository.existsDocumentTypeByName(existingDocTypeName)).thenReturn(true);
    String expectedMessage = "DocumentType is (" + existingDocTypeName + ") already exist.";

    Exception actualException =
        assertThrows(RequestCauseConflictException.class, () -> docTypeService.createDocType(testDocTypeReqDto));

    Assert.assertEquals(expectedMessage, actualException.getMessage());
    verify(docTypeRepository, times(1)).existsDocumentTypeByName(anyString());
    verify(docTypeRepository, times(0)).save(any(DocumentType.class));
  }

  @Test
  public void when_modifyDocTypeName_should_returnDocResDtoWithTheNewName() {
    String existingDocTypeName = "invoice";
    DocTypeReqDto testDocTypeReqDto = new DocTypeReqDto(existingDocTypeName);
    DocumentType testDocumentType = new DocumentType(existingDocTypeName);
    Mockito.doReturn(testDocumentType).when(docTypeService).findByName(anyString());
    when(docTypeRepository.save(any(DocumentType.class))).thenReturn(testDocumentType);
    Mockito.doReturn(new DocTypeResDto(existingDocTypeName)).when(docTypeService).convertToDto(any(DocumentType.class));
    DocTypeResDto expectedDocTypeResDto = new DocTypeResDto(existingDocTypeName);

    DocTypeResDto actualDocTypeResDto = docTypeService.modifyDocTypeName(testDocTypeReqDto);

    Assert.assertEquals(expectedDocTypeResDto, actualDocTypeResDto);
    verify(docTypeRepository, times(1)).save(any(DocumentType.class));
    verify(docTypeService, times(1)).findByName(anyString());
    verify(docTypeService, times(1)).convertToDto(any(DocumentType.class));
  }

  @Test
  public void when_tryToDeleteADocumentTypeWhichIsExistInDB_should_callOnesDeleteMethod() {
    String existingDocTypeName = "invoice";
    when(docTypeRepository.existsDocumentTypeByName(existingDocTypeName)).thenReturn(true);
    Mockito.doNothing().when(docTypeRepository).deleteByName(anyString());

    docTypeService.deleteDocType(existingDocTypeName);

    verify(docTypeRepository, times(1)).existsDocumentTypeByName(anyString());
    verify(docTypeRepository, times(1)).deleteByName(anyString());
  }

  @Test
  public void when_tryToDeleteADocumentTypeWhichIsNotExistInDB_should_throwAppropriateException() {
    String notExistingDocTypeName = "invoice";
    when(docTypeRepository.existsDocumentTypeByName(anyString())).thenReturn(false);
    String expectedMessage = "DocumentType (" + notExistingDocTypeName + ") is not exist.";
    Exception actualException =
        assertThrows(RequestedResourceNotFoundException.class, () -> docTypeService.deleteDocType(notExistingDocTypeName));

    Assert.assertEquals(expectedMessage, actualException.getMessage());
    verify(docTypeRepository, times(1)).existsDocumentTypeByName(anyString());
    verify(docTypeRepository, times(0)).deleteByName(anyString());
    verify(docTypeService, times(0)).findByName(anyString());
  }

}
