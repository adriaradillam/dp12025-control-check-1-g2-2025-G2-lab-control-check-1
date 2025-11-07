package es.us.dp1.chess.tournament.match;


import es.us.dp1.chess.tournament.user.UserService;



public class ChessProblemAttemptController {

    UserService userService;
    ChessProblemAttemptService matchService;


    public ChessProblemAttemptController(ChessProblemAttemptService service, UserService userService){
        this.matchService=service;
        this.userService=userService;    
    }

}