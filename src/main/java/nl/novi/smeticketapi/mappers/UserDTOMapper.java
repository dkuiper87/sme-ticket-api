package nl.novi.smeticketapi.mappers;

import nl.novi.smeticketapi.dtos.user.UserRequestDTO;
import nl.novi.smeticketapi.dtos.user.UserResponseDTO;
import nl.novi.smeticketapi.entities.UserEntity;
import nl.novi.smeticketapi.entities.AuthorityEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserDTOMapper implements DTOMapper<UserResponseDTO, UserRequestDTO, UserEntity> {

    @Override
    public UserResponseDTO mapToDto(UserEntity entity) {
        var result = new UserResponseDTO();
        result.setUsername(entity.getUsername());
        result.setEmail(entity.getEmail());

        if (entity.getAuthorities() != null) {
            Set<String> authorityStrings = new HashSet<>();
            for (AuthorityEntity auth : entity.getAuthorities()) {
                authorityStrings.add(auth.getAuthority());
            }
            result.setAuthorities(authorityStrings);
        }

        return result;
    }

    @Override
    public List<UserResponseDTO> mapToDto(List<UserEntity> entities) {
        var result = new ArrayList<UserResponseDTO>();
        for (UserEntity entity : entities) {
            result.add(mapToDto(entity));
        }
        return result;
    }

    @Override
    public UserEntity mapToEntity(UserRequestDTO dto) {
        var result = new UserEntity();
        result.setUsername(dto.getUsername());
        result.setPassword(dto.getPassword());
        result.setEmail(dto.getEmail());
        // Let op: Rollen (Authorities) komen niet vanuit de standaard request mee.
        return result;
    }
}