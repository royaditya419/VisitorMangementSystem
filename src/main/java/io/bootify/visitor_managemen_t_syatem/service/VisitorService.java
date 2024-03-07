package io.bootify.visitor_managemen_t_syatem.service;

import io.bootify.visitor_managemen_t_syatem.domain.Address;
import io.bootify.visitor_managemen_t_syatem.domain.Visitor;
import io.bootify.visitor_managemen_t_syatem.model.AddressDTO;
import io.bootify.visitor_managemen_t_syatem.model.VisitorDTO;
import io.bootify.visitor_managemen_t_syatem.repos.AddressRepository;
import io.bootify.visitor_managemen_t_syatem.repos.VisitorRepository;
import io.bootify.visitor_managemen_t_syatem.util.NotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class VisitorService {

    private final VisitorRepository visitorRepository;
    private final AddressRepository addressRepository;

    @Autowired AddressService addressService;

    public VisitorService(final VisitorRepository visitorRepository,
            final AddressRepository addressRepository) {
        this.visitorRepository = visitorRepository;
        this.addressRepository = addressRepository;
    }

    public List<VisitorDTO> findAll() {
        final List<Visitor> visitors = visitorRepository.findAll(Sort.by("id"));
        return visitors.stream()
                .map(visitor -> mapToDTO(visitor, new VisitorDTO()))
                .toList();
    }

    public VisitorDTO get(final Long id) {
        return visitorRepository.findById(id)
                .map(visitor -> mapToDTO(visitor, new VisitorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final VisitorDTO visitorDTO) {
        final Visitor visitor = new Visitor();
        mapToEntity(visitorDTO, visitor);
        return visitorRepository.save(visitor).getId();
    }

    public void update(final Long id, final VisitorDTO visitorDTO) {
        final Visitor visitor = visitorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(visitorDTO, visitor);
        visitorRepository.save(visitor);
    }

    public void delete(final Long id) {
        visitorRepository.deleteById(id);
    }

    private VisitorDTO mapToDTO(final Visitor visitor, final VisitorDTO visitorDTO) {
        visitorDTO.setId(visitor.getId());
        visitorDTO.setEmail(visitor.getEmail());
        visitorDTO.setName(visitor.getName());
        visitorDTO.setPhone(visitor.getPhone());
        visitorDTO.setIDNumber(visitor.getIDNumber());
        final AddressDTO addressDTO=new AddressDTO();
        addressService.mapToDTO(visitor.getAddress(),addressDTO);
        visitorDTO.setAddress(addressDTO);
        return visitorDTO;
    }

    private Visitor mapToEntity(final VisitorDTO visitorDTO, final Visitor visitor) {
        visitor.setEmail(visitorDTO.getEmail());
        visitor.setName(visitorDTO.getName());
        visitor.setPhone(visitorDTO.getPhone());
        visitor.setIDNumber(visitorDTO.getIDNumber());
        final  Address address=new Address();
        addressService.mapToEntity(visitorDTO.getAddress(), address);
        addressRepository.save(address);
        visitor.setAddress(address);
        return visitor;
    }

    public boolean phoneExists(final Long phone) {
        return visitorRepository.existsByPhone(phone);
    }

    public boolean iDNumberExists(final String iDNumber) {
        return visitorRepository.existsByIDNumberIgnoreCase(iDNumber);
    }

}
