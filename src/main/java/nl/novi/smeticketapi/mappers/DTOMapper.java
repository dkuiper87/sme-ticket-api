package nl.novi.smeticketapi.mappers;

import nl.novi.smeticketapi.entities.BaseEntity;

import java.util.List;

public interface DTOMapper <RESPONSE, REQUEST, ENTITY>{
    RESPONSE mapToDto(ENTITY entity);
    List<RESPONSE> mapToDto(List<ENTITY> entities);
    ENTITY mapToEntity(REQUEST request);
}
