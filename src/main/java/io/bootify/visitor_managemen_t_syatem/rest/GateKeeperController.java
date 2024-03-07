package io.bootify.visitor_managemen_t_syatem.rest;

import io.bootify.visitor_managemen_t_syatem.domain.Visit;
import io.bootify.visitor_managemen_t_syatem.model.VisitDTO;
import io.bootify.visitor_managemen_t_syatem.model.VisitorDTO;
import io.bootify.visitor_managemen_t_syatem.service.VisitService;
import io.bootify.visitor_managemen_t_syatem.service.VisitorService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gatekeeper")
public class GateKeeperController {
    @Autowired
    VisitorService visitorService;

    @Autowired
        VisitService visitService;
        @PostMapping("/createVisitor")
        @ApiResponse(responseCode = "201")
        public ResponseEntity<Long> createVisitor(@RequestBody @Valid final VisitorDTO visitorDTO) {
                final Long createdId = visitorService.create(visitorDTO);
                return new ResponseEntity<>(createdId, HttpStatus.CREATED);
        }
        @PostMapping("createVisit")
        @ApiResponse(responseCode = "201")
        public ResponseEntity<Long> createVisit(@RequestBody @Valid final VisitDTO visitDTO) {
                final Long createdId = visitService.create(visitDTO);
                return new ResponseEntity<>(createdId, HttpStatus.CREATED);
        }

        }
