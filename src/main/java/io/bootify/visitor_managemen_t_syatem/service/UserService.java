package io.bootify.visitor_managemen_t_syatem.service;

import io.bootify.visitor_managemen_t_syatem.domain.Address;
import io.bootify.visitor_managemen_t_syatem.domain.Flat;
import io.bootify.visitor_managemen_t_syatem.domain.User;
import io.bootify.visitor_managemen_t_syatem.model.AddressDTO;
import io.bootify.visitor_managemen_t_syatem.model.UserDTO;
import io.bootify.visitor_managemen_t_syatem.model.UserStatus;
import io.bootify.visitor_managemen_t_syatem.repos.AddressRepository;
import io.bootify.visitor_managemen_t_syatem.repos.FlatRepository;
import io.bootify.visitor_managemen_t_syatem.repos.UserRepository;
import io.bootify.visitor_managemen_t_syatem.util.NotFoundException;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service

public class UserService {

    private final UserRepository userRepository;
    private final FlatRepository flatRepository;
    private final AddressRepository addressRepository;

    @Autowired
    AddressService addressService;

    public UserService(final UserRepository userRepository, final FlatRepository flatRepository,
            final AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.flatRepository = flatRepository;
        this.addressRepository = addressRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setRole(user.getRole());
        userDTO.setStatus(user.getStatus());
        Flat flat=user.getFlat();
        if(flat!=null){
            userDTO.setFlat_Number(flat.getNumber());
        }
        Address address= user.getAddress();
        AddressDTO addressDTO=new AddressDTO();
        addressService.mapToDTO(address,addressDTO);
        userDTO.setAddress(addressDTO);
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setRole(userDTO.getRole());
        user.setStatus(userDTO.getStatus());
        Flat flat=flatRepository.findByNumber(userDTO.getFlat_Number());

      if(flat==null){
           flat = new Flat();
           flat.setNumber(userDTO.getFlat_Number());
          flatRepository.save(flat);
      }
      user.setFlat(flat);
        final Address address = new Address();
        addressService.mapToEntity(userDTO.getAddress(),address);
        user.setAddress(address);
        addressRepository.save(address);
        return user;
    }

    public void markInactive(Long id){
      final User user=userRepository.findById(id).orElse(null);
      if(user==null){
          throw new NotFoundException();
      }
      user.setStatus(UserStatus.INACTIVE);
      userRepository.save(user);
    }
    public void markActive(Long id){
        final User user=userRepository.findById(id).get();
        if(user==null){
            throw new NotFoundException();
        }
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

}
