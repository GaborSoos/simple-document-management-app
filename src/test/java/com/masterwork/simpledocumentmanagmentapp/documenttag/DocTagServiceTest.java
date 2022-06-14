package com.masterwork.simpledocumentmanagmentapp.documenttag;

import com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto.DocTagReqDto;
import com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto.DocTagResDto;
import com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto.DocTagsDto;
import com.masterwork.simpledocumentmanagmentapp.documenttag.model.entity.DocumentTag;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DocTagServiceTest {

  @Mock
  private DocTagRepository docTagRepository;

  @Spy
  @InjectMocks
  private DocTagServiceImpl docTagService;

  @Test
  public void when_convertDocumentTagToDocTagResDto_should_returnValidDocTagResDto() {
    String expectedDocTagName = "test-document";
    DocumentTag inputDocTag = new DocumentTag(expectedDocTagName);

    DocTagResDto actual = docTagService.convertToDto(inputDocTag);

    Assert.assertEquals(expectedDocTagName, actual.getName());
  }

  @Test
  public void when_convertDocumentTagListToDocTagsDto_should_returnValidDocTagsDto() {
    List<String> docTagNames = new ArrayList<>(Arrays.asList("test1-document", "test2-document"));
    List<DocumentTag> testDocTags = new ArrayList<>(Arrays.asList(
        new DocumentTag(docTagNames.get(0)), new DocumentTag(docTagNames.get(1))));
    DocTagsDto expectedDto = new DocTagsDto();
    expectedDto.getDocTagResDtos().add(new DocTagResDto(docTagNames.get(0)));
    expectedDto.getDocTagResDtos().add(new DocTagResDto(docTagNames.get(1)));

    DocTagsDto actualDto = docTagService.convertToDtoList(testDocTags);

    Assert.assertEquals(expectedDto, actualDto);
  }

  @Test
  public void when_findDocumentTagByExistingTagName_should_returnAppropriateDocumentTag() {
    String testTagName = "test-tag";
    DocumentTag expectedDocumentTag = new DocumentTag(1L, testTagName, null);
    when(docTagRepository.findByName(anyString())).thenReturn(Optional.of(expectedDocumentTag));

    DocumentTag actualDocumentTag = docTagService.findByName(testTagName);

    Assert.assertEquals(expectedDocumentTag, actualDocumentTag);
  }

  @Test
  public void when_findDocumentTagByNotExistingTagName_should_throwAppropriateException() {
    when(docTagRepository.findByName(anyString())).thenReturn(Optional.empty());
    String expectedMessage = "Requested resource is not found";

    Exception actualException =
        assertThrows(RequestedResourceNotFoundException.class, () -> docTagService.findByName("whatever"));

    Assert.assertEquals(expectedMessage, actualException.getMessage());
  }

  @Test
  public void when_findAllDocumentTagAndThereIsAtLeastOneInDB_should_returnAllExistingDocumentTagAsDocTagsDto() {
    List<String> docTagNames = new ArrayList<>(Arrays.asList("test1-document", "test2-document"));
    DocTagsDto expectedDocTagsDto = new DocTagsDto();
    expectedDocTagsDto.getDocTagResDtos().add(new DocTagResDto(docTagNames.get(0)));
    expectedDocTagsDto.getDocTagResDtos().add(new DocTagResDto(docTagNames.get(1)));
    when(docTagRepository.findAll()).thenReturn(
        new ArrayList<>(Arrays.asList(new DocumentTag(docTagNames.get(0)), new DocumentTag(docTagNames.get(1)))));

    DocTagsDto actualDocTagsDto = docTagService.findAll();

    Assert.assertEquals(expectedDocTagsDto, actualDocTagsDto);
  }

  @Test
  public void when_findAllDocumentTagAndThereIsNotInDB_should_throwAppropriateException() {
    when(docTagRepository.findAll()).thenReturn(Collections.emptyList());
    String expectedMessage = "Requested resource is not found";

    Exception actualException =
        assertThrows(RequestedResourceNotFoundException.class, () -> docTagService.findByName("whatever"));

    Assert.assertEquals(expectedMessage, actualException.getMessage());
  }

  @Test
  public void when_createANewDocumentTagWhichIsNotExistYet_should_returnTheNewDocTagAsDocResDto() {
    String newDocTagName = "test-document";
    DocTagReqDto testDocTagReqDto = new DocTagReqDto(newDocTagName);
    when(docTagRepository.existsDocumentTagByName(newDocTagName)).thenReturn(false);
    when(docTagRepository.save(any(DocumentTag.class))).thenReturn(new DocumentTag(newDocTagName));
    DocTagResDto expectedDocTagResDto = new DocTagResDto(newDocTagName);

    DocTagResDto actualDocTagResDto = docTagService.createDocTag(testDocTagReqDto);

    Assert.assertEquals(expectedDocTagResDto, actualDocTagResDto);
    verify(docTagRepository, times(1)).existsDocumentTagByName(anyString());
    verify(docTagRepository, times(1)).save(any(DocumentTag.class));
  }

  @Test
  public void when_createANewDocumentTagWhichIsAlreadyExist_should_throwAppropriateException() {
    String existingDocTagName = "test-document";
    DocTagReqDto testDocTagReqDto = new DocTagReqDto(existingDocTagName);
    when(docTagRepository.existsDocumentTagByName(existingDocTagName)).thenReturn(true);
    String expectedMessage = "DocumentTag is (" + existingDocTagName + ") already exist.";

    Exception actualException =
        assertThrows(RequestCauseConflictException.class, () -> docTagService.createDocTag(testDocTagReqDto));

    Assert.assertEquals(expectedMessage, actualException.getMessage());
    verify(docTagRepository, times(1)).existsDocumentTagByName(anyString());
    verify(docTagRepository, times(0)).save(any(DocumentTag.class));
  }

  @Test
  public void when_tryToDeleteADocumentTagWhichIsExistInDB_should_callOnesDeleteMethod() {
    String existingDocTagName = "test-document";
    DocumentTag existDocumentTag = new DocumentTag(existingDocTagName);
    when(docTagRepository.existsDocumentTagByName(existingDocTagName)).thenReturn(true);
    Mockito.doNothing().when(docTagRepository).delete(any(DocumentTag.class));
    Mockito.doReturn(existDocumentTag).when(docTagService).findByName(anyString());

    docTagService.deleteDocTag(existingDocTagName);

    verify(docTagRepository, times(1)).existsDocumentTagByName(anyString());
    verify(docTagRepository, times(1)).delete(any(DocumentTag.class));
  }

  @Test
  public void when_tryToDeleteADocumentTagWhichIsNotExistInDB_should_throwAppropriateException() {
    String notExistingDocTagName = "test-document";
    when(docTagRepository.existsDocumentTagByName(anyString())).thenReturn(false);
    String expectedMessage = "DocumentTag (" + notExistingDocTagName + ") is not exist.";
    Exception actualException =
        assertThrows(RequestedResourceNotFoundException.class, () -> docTagService.deleteDocTag(notExistingDocTagName));

    Assert.assertEquals(expectedMessage, actualException.getMessage());
    verify(docTagRepository, times(1)).existsDocumentTagByName(anyString());
    verify(docTagRepository, times(0)).delete(any(DocumentTag.class));
    verify(docTagService, times(0)).findByName(anyString());
  }

}
