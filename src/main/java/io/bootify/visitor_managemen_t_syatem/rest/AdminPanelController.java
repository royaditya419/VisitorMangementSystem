package io.bootify.visitor_managemen_t_syatem.rest;

import io.bootify.visitor_managemen_t_syatem.model.UserDTO;
import io.bootify.visitor_managemen_t_syatem.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminPanelController {
    @Autowired
    UserService userService;


    @PostMapping("/createUser")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createUser(@RequestBody  final UserDTO userDTO) {
        final Long createdId = userService.create(userDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }
    @PutMapping("/user/inactive/{userId}")
    public ResponseEntity<Void> userInactiveStatus(@PathVariable Long userId){
          userService.markInactive(userId);
          return ResponseEntity.ok().build();
    }
    @PutMapping("/user/active/{id}")
    public ResponseEntity<Void> userActiveStatus(@PathVariable Long id){
        userService.markActive(id);
        return ResponseEntity.ok().build(); //build is used to create object;bcz ok does not create object
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<UserDTO>> getUsers(){
       List<UserDTO> allusers= userService.findAll();
       return  ResponseEntity.ok(allusers);

    }
}
