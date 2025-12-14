package es.us.dp1.chess.tournament.match;


import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.us.dp1.chess.tournament.user.UserService;
@RestController
@RequestMapping("api/v1/attempts")
public class ChessProblemAttemptController {

    UserService userService;
    ChessProblemAttemptService matchService;

    public ChessProblemAttemptController(ChessProblemAttemptService service, UserService userService){
        this.matchService=service;
        this.userService=userService;    
    }

    @GetMapping
    public List<ChessProblemAttempt> findAll() {
        return matchService.getAttempts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChessProblemAttempt> findById(@PathVariable Integer id) {
        return matchService.getMatchById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}