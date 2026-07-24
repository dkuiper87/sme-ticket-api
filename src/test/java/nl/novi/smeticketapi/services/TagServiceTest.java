package nl.novi.smeticketapi.services;

import nl.novi.smeticketapi.dtos.tag.TagRequestDTO;
import nl.novi.smeticketapi.dtos.tag.TagResponseDTO;
import nl.novi.smeticketapi.entities.TagEntity;
import nl.novi.smeticketapi.exceptions.RecordNotFoundException;
import nl.novi.smeticketapi.mappers.TagDTOMapper;
import nl.novi.smeticketapi.repositories.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagDTOMapper tagDTOMapper;

    @InjectMocks
    private TagService tagService;


    //TESTS

    //Test getAllTags
    @Test
    void getAllTags_ShouldReturnListOfAllTags(){
        //ARRANGE
        //Create fake entity
        TagEntity fakeEntity = new TagEntity();
        fakeEntity.setId(1L);
        fakeEntity.setName("Urgent");
        fakeEntity.setColorHex("#FF0000");

        //Wrap in list
        List<TagEntity> fakeEntityList = List.of(fakeEntity);

        //Create fake DTO
        TagResponseDTO fakeDto = new TagResponseDTO();
        fakeDto.setId(1L);
        fakeDto.setName("Urgent");
        fakeDto.setColorHex("#FF0000");

        //Wrap in list
        List<TagResponseDTO> fakeDtoList = List.of(fakeDto);

        //Program Mocks
        when(tagRepository.findAll()).thenReturn(fakeEntityList);
        when(tagDTOMapper.mapToDto(fakeEntityList)).thenReturn(fakeDtoList);

        //ACT
        List<TagResponseDTO> result = tagService.getAllTags();

        //ASSERT
        assertEquals(1, result.size());
        assertEquals("Urgent", result.get(0).getName());
        assertEquals("#FF0000", result.get(0).getColorHex());
    }

    //Test getTagById - Tag is found
    @Test
    void getTagById_ShouldReturnTag(){
        //ARRANGE
        Long tagId = 1L;

        //Create fake entity
        TagEntity fakeEntity = new TagEntity();
        fakeEntity.setId(tagId);
        fakeEntity.setName("Urgent");
        fakeEntity.setColorHex("#FF0000");

        //Create fake DTO
        TagResponseDTO fakeDto = new TagResponseDTO();
        fakeDto.setId(tagId);
        fakeDto.setName("Urgent");
        fakeDto.setColorHex("#FF0000");

        //Program Mocks
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(fakeEntity));
        when(tagDTOMapper.mapToDto(fakeEntity)).thenReturn(fakeDto);

        //ACT
        TagResponseDTO result = tagService.getTagById(tagId);

        //ASSERT
        assertEquals(1L, result.getId());
        assertEquals("Urgent", result.getName());
        assertEquals("#FF0000", result.getColorHex());
    }

    //Test getTagById - Tag is not found
    @Test
    void getTagById_ShouldThrowException_WhenTagNotFound() {
        //ARRANGE
        Long tagId = 999L;

        //Return empty optional
        when(tagRepository.findById(tagId)).thenReturn(Optional.empty());

        //ACT & ASSERT
        org.junit.jupiter.api.Assertions.assertThrows(RecordNotFoundException.class, () -> {
            tagService.getTagById(tagId);
        });
    }

    //Test createTag
    @Test
    void createTag_ShouldReturnCreatedTag() {
        //ARRANGE
        //Create fake request DTO
        TagRequestDTO requestDto = new TagRequestDTO();
        requestDto.setName("Escaleren");
        requestDto.setColorHex("#FFFF00");

        //Create fake entity to save (no id yet)
        TagEntity entityToSave = new TagEntity();
        entityToSave.setName("Escaleren");
        entityToSave.setColorHex("#FFFF00");

        //Create fake response entity (with id)
        TagEntity savedEntity = new TagEntity();
        savedEntity.setId(2L);
        savedEntity.setName("Escaleren");
        savedEntity.setColorHex("#FFFF00");

        //Create fake response DTO
        TagResponseDTO responseDto = new TagResponseDTO();
        responseDto.setId(2L);
        responseDto.setName("Escaleren");
        responseDto.setColorHex("#FFFF00");

        //Program Mocks
        when(tagDTOMapper.mapToEntity(requestDto)).thenReturn(entityToSave);
        when(tagRepository.save(entityToSave)).thenReturn(savedEntity);
        when(tagDTOMapper.mapToDto(savedEntity)).thenReturn(responseDto);

        //ACT
        TagResponseDTO result = tagService.createTag(requestDto);

        //ASSERT
        assertEquals("Escaleren", result.getName());
        assertEquals(2L, result.getId());
        assertEquals("#FFFF00", result.getColorHex());
    }

    //Test updateTag
    @Test
    void updateTag_ShouldReturnUpdatedTag() {
        //ARRANGE
        Long tagId = 1L;

        //Create fake request DTO
        TagRequestDTO requestDto = new TagRequestDTO();
        requestDto.setName("Updated Urgent");
        requestDto.setColorHex("#00FF00");

        //Create fake existing entity
        TagEntity existingEntity = new TagEntity();
        existingEntity.setId(tagId);
        existingEntity.setName("Old Urgent");
        existingEntity.setColorHex("#FF0000");

        //Create fake saved entity
        TagEntity savedEntity = new TagEntity();
        savedEntity.setId(tagId);
        savedEntity.setName("Updated Urgent");
        savedEntity.setColorHex("#00FF00");

        //Create fake response DTO
        TagResponseDTO responseDto = new TagResponseDTO();
        responseDto.setId(tagId);
        responseDto.setName("Updated Urgent");
        responseDto.setColorHex("#00FF00");

        //Program Mocks
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(existingEntity));
        when(tagRepository.save(existingEntity)).thenReturn(savedEntity);
        when(tagDTOMapper.mapToDto(savedEntity)).thenReturn(responseDto);

        //ACT
        TagResponseDTO result = tagService.updateTag(tagId, requestDto);

        //ASSERT
        assertEquals("Updated Urgent", result.getName());
        assertEquals("#00FF00", result.getColorHex());
        assertEquals(tagId, result.getId());
    }

    //Test deleteTag
    @Test
    void deleteTag_ShouldDeleteTag() {
        //ARRANGE
        Long tagId = 1L;

        //Create fake existing entity
        TagEntity existingEntity = new TagEntity();
        existingEntity.setId(tagId);

        //Program Mocks
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(existingEntity));

        //ACT
        tagService.deleteTag(tagId);

        //ASSERT
        //Verify that the delete method on the repository was called exactly once with our entity
        org.mockito.Mockito.verify(tagRepository, org.mockito.Mockito.times(1)).delete(existingEntity);
    }

}