package io.bootify.visitor_managemen_t_syatem.service;

import io.bootify.visitor_managemen_t_syatem.domain.Flat;
import io.bootify.visitor_managemen_t_syatem.repos.FlatRepository;
import io.bootify.visitor_managemen_t_syatem.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class FlatService {

    private final FlatRepository flatRepository;

    public FlatService(final FlatRepository flatRepository) {
        this.flatRepository = flatRepository;
    }

    public List<FlatDTO> findAll() {
        final List<Flat> flats = flatRepository.findAll(Sort.by("id"));
        return flats.stream()
                .map(flat -> mapToDTO(flat, new FlatDTO()))
                .toList();
    }

    public FlatDTO get(final Long id) {
        return flatRepository.findById(id)
                .map(flat -> mapToDTO(flat, new FlatDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final FlatDTO flatDTO) {
        final Flat flat = new Flat();
        mapToEntity(flatDTO, flat);
        return flatRepository.save(flat).getId();
    }

    public void update(final Long id, final FlatDTO flatDTO) {
        final Flat flat = flatRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(flatDTO, flat);
        flatRepository.save(flat);
    }

    public void delete(final Long id) {
        flatRepository.deleteById(id);
    }

    private FlatDTO mapToDTO(final Flat flat, final FlatDTO flatDTO) {
        flatDTO.setId(flat.getId());
        flatDTO.setFlatNumber(flat.getNumber());
        return flatDTO;
    }

    private Flat mapToEntity(final FlatDTO flatDTO, final Flat flat) {
        flat.setNumber(flatDTO.getFlatNumber());
        return flat;
    }

}
