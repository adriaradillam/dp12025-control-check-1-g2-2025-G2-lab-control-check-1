package es.us.dp1.chess.tournament;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.us.dp1.chess.tournament.exceptions.UnfeasibleAttemptException;
import es.us.dp1.chess.tournament.match.ChessBoard;
import es.us.dp1.chess.tournament.match.ChessProblem;
import es.us.dp1.chess.tournament.match.ChessProblemAttempt;
import es.us.dp1.chess.tournament.match.ChessProblemAttemptRepository;
import es.us.dp1.chess.tournament.match.ChessProblemAttemptService;
import es.us.dp1.chess.tournament.user.Authorities;
import es.us.dp1.chess.tournament.user.User;


@ExtendWith(MockitoExtension.class)
public class Test1 extends ReflexiveTest{
    @Mock
    ChessProblemAttemptRepository mr;

    ChessProblemAttemptService ms;

    @BeforeEach
    public void configuation(){
        ms=new ChessProblemAttemptService(mr);
    }

    @Test
    public void test1CheckTransactionalityOfAttemptService(){
        checkTransactionalRollbackFor(ChessProblemAttemptService.class,"save", UnfeasibleAttemptException.class,ChessProblemAttempt.class);        
    }    

     
    @Test
    public void test1AttemptServiceCanSaveAttemptsWithoutOngoing() throws UnfeasibleAttemptException {        
        lenient().when(mr.save(any(ChessProblemAttempt.class))).thenReturn(null);
        lenient().when(mr.findOngoingAttemptsByUserAndProblem(any(User.class), any(ChessProblem.class))).thenReturn(List.of());

        User player=createUser("player");
        ChessProblem problem=createProblem("problem", 1, "goal");
        ChessBoard board = createBoard();

        ChessProblemAttempt m=new ChessProblemAttempt();        
        m.setPlayer(player);
        m.setProblem(problem);
        m.setStart(LocalDateTime.of(2025, 10, 31, 0, 0, 0));
        m.setFinish(LocalDateTime.of(2025, 11, 2, 12, 0, 0));
        m.setBoard(board);
        
        ms.save(m);

        // The test fails if the service does not invoke the save function of the repository:
        verify(mr).findOngoingAttemptsByUserAndProblem(player, problem);
        verify(mr).save(m);
    } 

    @Test
    public void test1AttemptServiceCanSaveAttemptsWithoutOngoingWithoutFinishDate() throws UnfeasibleAttemptException {        
        lenient().when(mr.save(any(ChessProblemAttempt.class))).thenReturn(null);
        lenient().when(mr.findOngoingAttemptsByUserAndProblem(any(User.class), any(ChessProblem.class))).thenReturn(List.of());

        User player=createUser("player");
        ChessProblem problem=createProblem("problem", 1, "goal");
        ChessBoard board = createBoard();

        ChessProblemAttempt m=new ChessProblemAttempt();        
        m.setPlayer(player);
        m.setProblem(problem);
        m.setStart(LocalDateTime.of(2025, 10, 31, 0, 0, 0));
        m.setBoard(board);
        ms.save(m);
        // The test fails if the service does not invoke the save function of the repository:
        verify(mr).findOngoingAttemptsByUserAndProblem(player, problem);
        verify(mr).save(m);
    }

    @Test
    public void test1AttemptServiceCanSaveAttemptsWithSameOngoing() throws UnfeasibleAttemptException {
        lenient().when(mr.save(any(ChessProblemAttempt.class))).thenReturn(null);
        ChessProblemAttempt ongoing = new ChessProblemAttempt();
        ongoing.setId(20);
        lenient().when(mr.findOngoingAttemptsByUserAndProblem(any(User.class), any(ChessProblem.class))).thenReturn(List.of(ongoing));

        User player=createUser("player");
        ChessProblem problem=createProblem("problem", 1, "goal");
        ChessBoard board = createBoard();

        ChessProblemAttempt m=new ChessProblemAttempt();        
        m.setId(20);
        m.setPlayer(player);
        m.setProblem(problem);
        m.setStart(LocalDateTime.of(2025, 10, 31, 0, 0, 0));
        m.setBoard(board);

        ms.save(m);
        // The test fails if the service does not invoke the save function of the repository:
        verify(mr).findOngoingAttemptsByUserAndProblem(player, problem);
        verify(mr).save(m);
    } 


    private ChessBoard createBoard() {
        ChessBoard board=new ChessBoard();
        board.setCreatorTurn(true);
        return board;
    } 

    @Test
    public void test1AttemptServiceCanNotSaveAttemptsWithOngoing() throws UnfeasibleAttemptException {
        lenient().when(mr.save(any(ChessProblemAttempt.class))).thenReturn(null);
        ChessProblemAttempt ongoing = new ChessProblemAttempt();
        ongoing.setId(33);
        when(mr.findOngoingAttemptsByUserAndProblem(any(User.class), any(ChessProblem.class))).thenReturn(List.of(ongoing));

        User player=createUser("player");
        ChessProblem problem=createProblem("problem", 1, "goal");
        ChessBoard board = createBoard();

        ChessProblemAttempt m=new ChessProblemAttempt();        
        m.setId(20);
        m.setPlayer(player);
        m.setProblem(problem);
        m.setStart(LocalDateTime.of(2025, 10, 31, 0, 0, 0));
        m.setBoard(board);

        // The test fails if the service does not throw the exception;
        assertThrows(UnfeasibleAttemptException.class, ()->{
            ms.save(m);
        });                

        // The test fails if the service invokes the save function of the repository:
        verify(mr,never()).save(m);
    } 

    @Test
    public void test1AttemptsServiceCanNotSaveAttemptsWithInvalidDates() throws UnfeasibleAttemptException {
        lenient().when(mr.save(any(ChessProblemAttempt.class))).thenReturn(null);
        lenient().when(mr.findOngoingAttemptsByUserAndProblem(any(User.class), any(ChessProblem.class))).thenReturn(List.of());

        User player=createUser("player");
        ChessProblem problem=createProblem("problem", 1, "goal");
        ChessBoard board = createBoard();

        ChessProblemAttempt m=new ChessProblemAttempt();        
        m.setPlayer(player);
        m.setProblem(problem);
        m.setBoard(board);

        // Warning!!!! The finish date is before the start date!
        m.setFinish(LocalDateTime.of(2025, 10, 31, 0, 0, 0));
        m.setStart(LocalDateTime.of(2025, 11, 2, 12, 0, 0));

        // The test fails if the service does not throw the exception;
        assertThrows(UnfeasibleAttemptException.class, ()->{
            ms.save(m);
        });                

        // The test fails if the service invokes the save function of the repository:
        verify(mr,never()).save(m);
    } 


    public static User createUser(String name){
        Authorities a1=new Authorities();
        a1.setAuthority("PANA");
        User u1=new User();
        setValue(u1,"username",String.class,name);
        setValue(u1, "authority", Authorities.class, a1);
        return u1;
    }

    public static ChessProblem createProblem(String name, int difficultyLevel, String goal){
        ChessProblem p=new ChessProblem();
        setValue(p,"name",String.class,name);
        p.setDifficultyLevel(difficultyLevel);
        p.setGoal(goal);
        return p;
    }
}
