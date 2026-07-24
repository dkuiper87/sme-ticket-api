package nl.novi.smeticketapi.services;

import nl.novi.smeticketapi.dtos.tag.TagRequestDTO;
import nl.novi.smeticketapi.dtos.tag.TagResponseDTO;
import nl.novi.smeticketapi.entities.TagEntity;
import nl.novi.smeticketapi.exceptions.RecordNotFoundException;
import nl.novi.smeticketapi.mappers.TagDTOMapper;
import nl.novi.smeticketapi.repositories.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final TagDTOMapper tagDTOMapper;

    //Constructor
    public TagService(TagRepository tagRepository, TagDTOMapper tagDTOMapper) {
        this.tagRepository = tagRepository;
        this.tagDTOMapper = tagDTOMapper;
    }

    //Methods

    //Method to retrieve a list of all Tags
    public List<TagResponseDTO> getAllTags() {
        return tagDTOMapper.mapToDto(tagRepository.findAll());
    }

    //Private method to retrieve tag entity
    private TagEntity getTagEntity(Long id) {
        TagEntity existingTagEntity = tagRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Tag " + id + " not found"));
        return existingTagEntity;
    }

    //Method to retrieve a specific Tag by id
    public TagResponseDTO getTagById(Long id) {
        TagEntity tagEntity = getTagEntity(id);
        return tagDTOMapper.mapToDto(tagEntity);
    }

    //Method to create new tag
    public TagResponseDTO createTag(TagRequestDTO requestDTO) {
        TagEntity tagEntity = tagDTOMapper.mapToEntity(requestDTO);
        tagEntity = tagRepository.save(tagEntity);
        return tagDTOMapper.mapToDto(tagEntity);
    }

    //Method to edit a tag
    public TagResponseDTO updateTag(Long id, TagRequestDTO requestDTO) {
        TagEntity existingTagEntity = getTagEntity(id);
        existingTagEntity.setName(requestDTO.getName());
        existingTagEntity.setColorHex(requestDTO.getColorHex());
        existingTagEntity = tagRepository.save(existingTagEntity);
        return tagDTOMapper.mapToDto(existingTagEntity);
    }

    //Method to delete a tag
    public void deleteTag(Long id) {
        TagEntity existingTag = getTagEntity(id);
        tagRepository.delete(existingTag);
    }
}
