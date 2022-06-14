package com.masterwork.simpledocumentmanagmentapp.document;

import com.masterwork.simpledocumentmanagmentapp.TestUtils;
import com.masterwork.simpledocumentmanagmentapp.document.model.dto.DocumentReqDto;
import com.masterwork.simpledocumentmanagmentapp.document.model.dto.DocumentResDto;
import com.masterwork.simpledocumentmanagmentapp.document.model.dto.DocumentsDto;
import com.masterwork.simpledocumentmanagmentapp.document.model.entity.Document;
import com.masterwork.simpledocumentmanagmentapp.documenttag.DocTagServiceImpl;
import com.masterwork.simpledocumentmanagmentapp.documenttag.model.dto.DocTagReqDto;
import com.masterwork.simpledocumentmanagmentapp.documenttag.model.entity.DocumentTag;
import com.masterwork.simpledocumentmanagmentapp.documenttype.DocTypeServiceImpl;
import com.masterwork.simpledocumentmanagmentapp.documenttype.model.entity.DocumentType;
import com.masterwork.simpledocumentmanagmentapp.exception.RequestCauseConflictException;
import com.masterwork.simpledocumentmanagmentapp.exception.RequestedResourceNotFoundException;
import com.masterwork.simpledocumentmanagmentapp.partner.PartnerServiceImpl;
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

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DocumentServiceTest {

  @Mock
  private DocumentRepository documentRepository;

  @Mock
  private PartnerServiceImpl partnerService;

  @Mock
  private DocTypeServiceImpl docTypeService;

  @Mock
  private DocTagServiceImpl docTagService;

  @Spy
  @InjectMocks
  private DocumentServiceImpl documentService;

  private DocumentType documentType1;
  private DocumentType documentType2;
  private DocumentTag documentTag1;
  private DocumentTag documentTag2;
  private Partner partner;
  private Document document1;
  private Document document2;
  private DocumentResDto documentResDto1;
  private DocumentResDto documentResDto2;
  private DocumentReqDto documentReqDto1;
  private DocumentsDto documentsDto;

  @Before
  public void init() {
    documentType1 = new DocumentType("invoice");
    documentType2 = new DocumentType("contract");
    documentTag1 = new DocumentTag("finance-document");
    documentTag2 = new DocumentTag("it-document");
    partner = TestUtils.partnerBuilder().withName("MOL").build();
    document1 = TestUtils.documentBuilder().withId(1L).withOriginalDocumentId("INV/2022/0001").withPartner(partner)
        .withDocumentType(documentType1).withOriginalDocumentDate(LocalDate.of(2022, 1, 1))
        .withSubject("Üzemanyag - 2022.01.").withDescription("Az ABC-123 autó 2022.01. havi üzemanyagszámlája.")
        .withDocumentTags(new ArrayList<>(Arrays.asList(documentTag1, documentTag2))).withEnabled(true).build();
    document2 = TestUtils.documentBuilder().withId(2L).withOriginalDocumentId("INV/2022/0002").withPartner(partner)
        .withDocumentType(documentType1).withOriginalDocumentDate(LocalDate.of(2022, 2, 1))
        .withSubject("Üzemanyag - 2022.02.").withDescription("Az ABC-123 autó 2022.02. havi üzemanyagszámlája.")
        .withDocumentTags(new ArrayList<>(Arrays.asList(documentTag1, documentTag2))).withEnabled(true).build();
    documentResDto1 = TestUtils.documentResDtoBuilder().withId(1L).withOriginalDocumentId("INV/2022/0001")
        .withPartnerName(partner.getName()).withDocumentTypeName(documentType1.getName())
        .withOriginalDocumentDate(LocalDate.of(2022, 1, 1))
        .withSubject("Üzemanyag - 2022.01.").withDescription("Az ABC-123 autó 2022.01. havi üzemanyagszámlája.")
        .withDocTags(new ArrayList<>(Arrays.asList(documentTag1.getName(), documentTag2.getName()))).withEnabled(true).build();
    documentResDto2 = TestUtils.documentResDtoBuilder().withId(2L).withOriginalDocumentId("INV/2022/0002")
        .withPartnerName(partner.getName()).withDocumentTypeName(documentType1.getName())
        .withOriginalDocumentDate(LocalDate.of(2022, 2, 1))
        .withSubject("Üzemanyag - 2022.02.").withDescription("Az ABC-123 autó 2022.02. havi üzemanyagszámlája.")
        .withDocTags(new ArrayList<>(Arrays.asList(documentTag1.getName(), documentTag2.getName()))).withEnabled(true).build();
    documentReqDto1 = TestUtils.documentReqDtoBuilder().withOriginalDocumentId("INV/2022/0001")
        .withPartnerName(partner.getName()).withDocumentTypeName(documentType1.getName())
        .withOriginalDocumentDate(LocalDate.of(2022, 1, 1))
        .withSubject("Üzemanyag - 2022.01.").withDescription("Az ABC-123 autó 2022.01. havi üzemanyagszámlája.")
        .withEnabled(true).build();
    documentsDto = TestUtils.documentsDtoBuilder()
        .withDocumentResDtos(new ArrayList<>(Arrays.asList(documentResDto1, documentResDto2))).build();
  }

  @Test
  public void when_convertDocumentToDocumentResDto_should_returnValidDocumentResDto() {
    DocumentResDto actualDocumentResDto = documentService.convertToDto(document1);

    Assert.assertEquals(documentResDto1, actualDocumentResDto);
  }

  @Test
  public void when_convertDocumentListToDocumentsDto_should_returnValidDocumentsDto() {
    DocumentsDto actualDocumentsDto = documentService.convertToDtoList(new ArrayList<>(Arrays.asList(document1, document2)));

    Assert.assertEquals(documentsDto, actualDocumentsDto);
  }

  @Test
  public void when_findDocumentByExistingDocumentId_should_returnAppropriateDocument() {
    when(documentRepository.findById(anyLong())).thenReturn(Optional.of(document1));

    Document actualDocument = documentService.findById(document1.getId());

    Assert.assertEquals(document1, actualDocument);
  }

  @Test
  public void when_findDocumentByNotExistingDocumentId_should_throwAppropriateException() {
    when(documentRepository.findById(anyLong())).thenReturn(Optional.empty());
    String expectedMessage = "Requested resource is not found: " + document1.getId() + " (documentId)";

    Exception actualException =
        assertThrows(RequestedResourceNotFoundException.class, () -> documentService.findById(document1.getId()));

    Assert.assertEquals(expectedMessage, actualException.getMessage());
  }

  @Test
  public void when_findAllDocumentByParamsAndThereIsAtLeastOneInDB_should_returnAllExistingDocumentAsDocumentsDto() {
    when(documentRepository.findAllByParam(
        anyString(), anyString(), anyString(), any(LocalDate.class), any(LocalDate.class)))
        .thenReturn(new ArrayList<>(Arrays.asList(document1, document2)));
    Mockito.doReturn(documentsDto).when(documentService).convertToDtoList(any(List.class));

    DocumentsDto actualDocumentsDto =
        documentService.findAllByParam("","","", LocalDate.of(2000, 1, 1), LocalDate.of(2022, 1, 1));

    Assert.assertEquals(documentsDto, actualDocumentsDto);
  }

  @Test
  public void when_createNewDocumentWhichIsNotExistYet_should_returnTheNewDocumentAsDocumentResDto() {
    when(partnerService.findByName(anyString())).thenReturn(partner);
    when(documentRepository.existsDocumentByOriginalDocumentIdAndPartner(anyString(), any(Partner.class))).thenReturn(false);
    when(docTypeService.findByName(anyString())).thenReturn(documentType1);
    when(documentRepository.save(any(Document.class))).thenReturn(document1);
    Mockito.doReturn(documentResDto1).when(documentService).convertToDto(any(Document.class));

    DocumentResDto actualDocumentResDto = documentService.createDocument(documentReqDto1);

    Assert.assertEquals(documentResDto1, actualDocumentResDto);
    verify(partnerService, times(1)).findByName(anyString());
    verify(documentRepository, times(1))
        .existsDocumentByOriginalDocumentIdAndPartner(anyString(), any(Partner.class));
    verify(docTypeService, times(1)).findByName(anyString());
    verify(documentRepository, times(1)).save(any(Document.class));
    verify(documentService, times(1)).convertToDto(any(Document.class));
  }

  @Test
  public void when_createANewDocumentWhichIsAlreadyExist_should_throwAppropriateException() {
    when(partnerService.findByName(anyString())).thenReturn(partner);
    when(documentRepository.existsDocumentByOriginalDocumentIdAndPartner(anyString(), any(Partner.class))).thenReturn(true);
    String expectedMessage = "Document with the following data - partner: " + partner.getName()
        + ", original document id: " + documentReqDto1.getOriginalDocumentId() + " - already exist.";

    Exception actualException =
        assertThrows(RequestCauseConflictException.class, () -> documentService.createDocument(documentReqDto1));

    Assert.assertEquals(expectedMessage, actualException.getMessage());
    verify(partnerService, times(1)).findByName(anyString());
    verify(documentRepository, times(1))
        .existsDocumentByOriginalDocumentIdAndPartner(anyString(), any(Partner.class));
  }

  @Test
  public void when_modifyDocumentBaseData_should_returnDocumentResDtoWithNewDocumentData() {
    Mockito.doReturn(document1).when(documentService).findById(anyLong());
    when(partnerService.findByName(anyString())).thenReturn(partner);
    when(docTypeService.findByName(anyString())).thenReturn(documentType1);
    when(documentRepository.save(any(Document.class))).thenReturn(document1);
    Mockito.doReturn(documentResDto1).when(documentService).convertToDto(any(Document.class));

    DocumentResDto actualDocumentResDto = documentService.modifyDocumentBaseData(1L, documentReqDto1);

    Assert.assertEquals(documentResDto1, actualDocumentResDto);
    verify(documentService, times(1)).findById(anyLong());
    verify(partnerService, times(1)).findByName(anyString());
    verify(docTypeService, times(1)).findByName(anyString());
    verify(documentRepository, times(1)).save(any(Document.class));
    verify(documentService, times(1)).convertToDto(any(Document.class));
  }

  @Test
  public void when_tryToDeleteADocumentWhichIsExistInDB_should_callOnesDeleteMethod() {
    Mockito.doReturn(document1).when(documentService).findById(anyLong());
    Mockito.doNothing().when(documentRepository).deleteById(anyLong());

    documentService.deleteById(document1.getId());

    verify(documentService, times(1)).findById(anyLong());
    verify(documentRepository, times(1)).deleteById(anyLong());
  }

  @Test
  public void when_tryToDeleteADocumentWhichIsNotExistInDB_should_throwAppropriateException() {
    String expectedMessage = "Requested resource is not found: " + document1.getId() + " (documentId)";
    Mockito.doThrow(new RequestedResourceNotFoundException(expectedMessage)).when(documentService).findById(anyLong());

    Exception actualException = assertThrows(
        RequestedResourceNotFoundException.class, () -> documentService.deleteById(document1.getId()));

    Assert.assertEquals(expectedMessage, actualException.getMessage());
    verify(documentService, times(1)).findById(anyLong());
    verify(documentRepository, times(0)).deleteById(anyLong());
  }

  @Test
  public void when_tryToAddAnExistingTagToAnExistingDocument_should_returnDocumentResDtoWithNewDocumentData() {
    document1.getDocumentTags().remove(documentTag1);
    Mockito.doReturn(document1).when(documentService).findById(anyLong());
    when(docTagService.findByName(anyString())).thenReturn(documentTag1);
    when(documentRepository.save(any(Document.class))).thenReturn(document1);
    Mockito.doReturn(documentResDto1).when(documentService).convertToDto(any(Document.class));

    DocumentResDto actualDocumentResDto = documentService.addTag(document1.getId(), new DocTagReqDto(documentTag1.getName()));

    Assert.assertEquals(documentResDto1, actualDocumentResDto);
    verify(documentService, times(1)).findById(anyLong());
    verify(docTagService, times(1)).findByName(anyString());
    verify(documentRepository, times(1)).save(any(Document.class));
    verify(documentService, times(1)).convertToDto(any(Document.class));
  }

  @Test
  public void when_tryToDeleteAnExistingTagFromAnExistingDocument_should_callOnceTheRequestedFunctions() {
    Mockito.doReturn(document1).when(documentService).findById(anyLong());
    when(docTagService.findByName(anyString())).thenReturn(documentTag1);
    document1.getDocumentTags().remove(documentTag1);
    when(documentRepository.save(any(Document.class))).thenReturn(document1);

    documentService.deleteTag(document1.getId(), documentTag1.getName());

    verify(documentService, times(1)).findById(anyLong());
    verify(docTagService, times(1)).findByName(anyString());
    verify(documentRepository, times(1)).save(any(Document.class));
  }

}
