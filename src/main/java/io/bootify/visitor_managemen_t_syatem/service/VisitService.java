package io.bootify.visitor_managemen_t_syatem.service;

import io.bootify.visitor_managemen_t_syatem.domain.Flat;
import io.bootify.visitor_managemen_t_syatem.domain.User;
import io.bootify.visitor_managemen_t_syatem.domain.Visit;
import io.bootify.visitor_managemen_t_syatem.domain.Visitor;
import io.bootify.visitor_managemen_t_syatem.model.VisitDTO;
import io.bootify.visitor_managemen_t_syatem.repos.FlatRepository;
import io.bootify.visitor_managemen_t_syatem.repos.UserRepository;
import io.bootify.visitor_managemen_t_syatem.repos.VisitRepository;
import io.bootify.visitor_managemen_t_syatem.repos.VisitorRepository;
import io.bootify.visitor_managemen_t_syatem.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class VisitService {

    private final VisitRepository visitRepository;
    private final VisitorRepository visitorRepository;
    private final FlatRepository flatRepository;
    private final UserRepository userRepository;

    public VisitService(final VisitRepository visitRepository,
            final VisitorRepository visitorRepository, final FlatRepository flatRepository,
            final UserRepository userRepository) {
        this.visitRepository = visitRepository;
        this.visitorRepository = visitorRepository;
        this.flatRepository = flatRepository;
        this.userRepository = userRepository;
    }

    public List<VisitDTO> findAll() {
        final List<Visit> visits = visitRepository.findAll(Sort.by("id"));
        return visits.stream()
                .map(visit -> mapToDTO(visit, new VisitDTO()))
                .toList();
    }

    public VisitDTO get(final Long id) {
        return visitRepository.findById(id)
                .map(visit -> mapToDTO(visit, new VisitDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final VisitDTO visitDTO) {
        final Visit visit = new Visit();
        mapToEntity(visitDTO, visit);
        return visitRepository.save(visit).getId();
    }

    public void update(final Long id, final VisitDTO visitDTO) {
        final Visit visit = visitRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(visitDTO, visit);
        visitRepository.save(visit);
    }

    public void delete(final Long id) {
        visitRepository.deleteById(id);
    }

    private VisitDTO mapToDTO(final Visit visit, final VisitDTO visitDTO) {
        visitDTO.setId(visit.getId());
        visitDTO.setStatus(visit.getStatus());
        visitDTO.setInTime(visit.getInTime());
        visitDTO.setOutTime(visit.getOutTime());
        visitDTO.setPurpose(visit.getPurpose());
        visitDTO.setImage(visit.getImage());
        visitDTO.setNoOFUser(visit.getNoOFUser());
        visitDTO.setVistor(visit.getVistor() == null ? null : visit.getVistor().getId());
        visitDTO.setFlat(visit.getFlat() == null ? null : visit.getFlat().getId());
        visitDTO.setUser(visit.getUser() == null ? null : visit.getUser().getId());
        return visitDTO;
    }

    private Visit mapToEntity(final VisitDTO visitDTO, final Visit visit) {
        visit.setStatus(visitDTO.getStatus());
        visit.setInTime(visitDTO.getInTime());
        visit.setOutTime(visitDTO.getOutTime());
        visit.setPurpose(visitDTO.getPurpose());
        visit.setImage(visitDTO.getImage());
        visit.setNoOFUser(visitDTO.getNoOFUser());
        final Visitor vistor = visitDTO.getVistor() == null ? null : visitorRepository.findById(visitDTO.getVistor())
                .orElseThrow(() -> new NotFoundException("vistor not found"));
        visit.setVistor(vistor);
        final Flat flat = visitDTO.getFlat() == null ? null : flatRepository.findById(visitDTO.getFlat())
                .orElseThrow(() -> new NotFoundException("flat not found"));
        visit.setFlat(flat);
        final User user = visitDTO.getUser() == null ? null : userRepository.findById(visitDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        visit.setUser(user);
        return visit;
    }

}
