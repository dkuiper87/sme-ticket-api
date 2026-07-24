package nl.novi.smeticketapi.controllers;

import jakarta.validation.Valid;
import nl.novi.smeticketapi.dtos.tag.TagRequestDTO;
import nl.novi.smeticketapi.dtos.tag.TagResponseDTO;
import nl.novi.smeticketapi.services.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    //Constructor
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    //Endpoints
    //GET /tags - Returns a list of all tags
    @GetMapping
    public ResponseEntity<List<TagResponseDTO>> getAllTags() {
        List<TagResponseDTO> tags = tagService.getAllTags();
        return ResponseEntity.ok(tags);
    }

    //GET /tags/{id} - Returns a tag by id
    @GetMapping("/{id}")
    public ResponseEntity<TagResponseDTO> getTagById(@PathVariable Long id) {
        TagResponseDTO tag = tagService.getTagById(id);
        return ResponseEntity.ok(tag);
    }

    //POST /tags - Creates a new tag
    @PostMapping
    public ResponseEntity<TagResponseDTO> createTag(@RequestBody @Valid TagRequestDTO requestDTO) {
        TagResponseDTO newTag = tagService.createTag(requestDTO);

        URI location = org.springframework.web.servlet.support.ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newTag.getId())
                .toUri();

        return ResponseEntity.created(location).body(newTag);
    }

    //PUT /tags/{id} - Update an existing tag
    @PutMapping("/{id}")
    public ResponseEntity<TagResponseDTO> updateTag(@PathVariable Long id, @RequestBody @Valid TagRequestDTO requestDTO) {
        TagResponseDTO updatedTag = tagService.updateTag(id, requestDTO);
        return ResponseEntity.ok(updatedTag);
    }

    //DELETE /tags/{id} - Delete a tag
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
