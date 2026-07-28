package nl.novi.smeticketapi.services;

import nl.novi.smeticketapi.dtos.authority.AuthorityRequestDTO;
import nl.novi.smeticketapi.dtos.user.UserRequestDTO;
import nl.novi.smeticketapi.dtos.user.UserResponseDTO;
import nl.novi.smeticketapi.entities.AuthorityEntity;
import nl.novi.smeticketapi.entities.UserEntity;
import nl.novi.smeticketapi.exceptions.RecordNotFoundException;
import nl.novi.smeticketapi.mappers.UserDTOMapper;
import nl.novi.smeticketapi.repositories.AuthorityRepository;
import nl.novi.smeticketapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserDTOMapper userDTOMapper;

    //Constructor
    public UserService (UserRepository userRepository, AuthorityRepository authorityRepository, UserDTOMapper userDTOMapper){
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.userDTOMapper = userDTOMapper;
    }

    //Methods

    //Method to retrieve a list of all users
    public List<UserResponseDTO> getAllUsers() {
        return userDTOMapper.mapToDto(userRepository.findAll());
    }

    //Private method to retrieve user entity
    private UserEntity getUserEntity(String username) {
        UserEntity existingUserEntity = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User " + username + " not found"));
        return existingUserEntity;
    }

    //Method to retrieve a specific user by username
    public UserResponseDTO getUserByUsername(String username) {
        UserEntity userEntity = getUserEntity(username);
        return  userDTOMapper.mapToDto(userEntity);
    }

    //Method to create a new user
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        if (userRepository.existsById(requestDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        UserEntity userEntity = userDTOMapper.mapToEntity(requestDTO);
        userEntity = userRepository.save(userEntity);
        return userDTOMapper.mapToDto(userEntity);

    }

    //Method to edit a user
    public UserResponseDTO updateUser(String username, UserRequestDTO requestDTO) {
        UserEntity existingUserEntity = getUserEntity(username);
        existingUserEntity.setEmail(requestDTO.getEmail());
        existingUserEntity.setPassword(requestDTO.getPassword());
        existingUserEntity = userRepository.save(existingUserEntity);
        return  userDTOMapper.mapToDto(existingUserEntity);
    }

    //Method to delete a user
    public void deleteUser(String username) {
        UserEntity existingUser = getUserEntity(username);
        userRepository.delete(existingUser);
    }

    //Method to assign a role to a user
    public void addUserAuthority(String username, AuthorityRequestDTO requestDTO){
        UserEntity existingUserEntity = getUserEntity(username);
        AuthorityEntity authorityEntity = new AuthorityEntity();
        authorityEntity.setAuthority(requestDTO.getAuthority());
        authorityEntity.setUser(existingUserEntity);
        authorityRepository.save(authorityEntity);
    }

    //Method to remove a role from a user
    public void removeUserAuthority(String username, String authorityToRemove) {
        UserEntity user = getUserEntity(username);

        boolean removed = user.getAuthorities().removeIf(
                authority -> authority.getAuthority().equalsIgnoreCase(authorityToRemove)
        );

        if (!removed) {
            throw new RecordNotFoundException("Rol " + authorityToRemove + " niet gevonden bij deze gebruiker.");
        }

        userRepository.save(user);
    }
}
