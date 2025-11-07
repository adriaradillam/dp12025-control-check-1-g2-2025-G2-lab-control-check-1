
package es.us.dp1.chess.tournament;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.chess.tournament.match.ChessProblem;
import es.us.dp1.chess.tournament.match.ChessProblemRepository;
import es.us.dp1.chess.tournament.match.ChessProblemService;
import jakarta.persistence.EntityManager;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ChessProblemService.class))
public class Test2a {

    @Autowired(required = false)
    ChessProblemRepository pr;

    @Autowired(required = false)
    ChessProblemService ms;

    @Autowired
    EntityManager em;
        

    @Test
    void test2aChessProblemServiceIsAnnotatedWithService() {
        assertTrue(ChessProblemService.class.isAnnotationPresent(Service.class),
                "ChessProblemService must be annotated with @Service");
    }

    @Test
    void test2aRequiredPublicMethodsOfChessProblemServiceShouldBeTransactional() throws Exception {
        assertFalse(ChessProblemService.class.isAnnotationPresent(Transactional.class),
                "@Transactional must not be applied at class level");

        Method m1 = ChessProblemService.class.getMethod("getProblemsByDifficultyAndNumPieces", Integer.class, Integer.class);
        Method m2 = ChessProblemService.class.getMethod("getAll");
        Method m3 = ChessProblemService.class.getMethod("getById", Integer.class);
        Method m4 = ChessProblemService.class.getMethod("delete", ChessProblem.class);

        List<Method> methods = List.of(m1, m2, m3, m4);
        for (Method m : methods) {
            assertTrue(m.isAnnotationPresent(Transactional.class),
                    "Method " + m.getName() + " must be annotated with @Transactional");
        }
    }

    @Test
    void test2aChessProblemRepositoryShouldBeASpringDataRepository() {
        boolean isSpringDataRepo =
                Repository.class.isAssignableFrom(ChessProblemRepository.class) ||
                JpaRepository.class.isAssignableFrom(ChessProblemRepository.class);

        assertTrue(isSpringDataRepo,
                "ChessProblemRepository must be a Spring Data repository (e.g., extend JpaRepository)");

        // Optional: check for correct generic parameters <ChessProblem, Long>
        if (JpaRepository.class.isAssignableFrom(ChessProblemRepository.class)) {
            boolean genericsOk = false;
            for (var i : ChessProblemRepository.class.getGenericInterfaces()) {
                if (i instanceof ParameterizedType pt &&
                    pt.getRawType() instanceof Class<?> raw &&
                    JpaRepository.class.isAssignableFrom(raw)) {
                    var args = pt.getActualTypeArguments();
                    if (args.length == 2 &&
                        args[0].getTypeName().endsWith(".ChessProblem") &&
                        (args[1].getTypeName().endsWith(".Integer") ||
                         args[1].getTypeName().equals("java.lang.Integer"))) {
                        genericsOk = true; break;
                    }
                }
            }
            assertTrue(genericsOk, "JpaRepository must be parameterized as JpaRepository<ChessProblem, Long>");
        }
    }

     @Test
    void test2aChessProblemServiceGetProblemsByDifficultyAndNumPiecesDelegatesToRepository() {
        ChessProblemRepository repository = mock(ChessProblemRepository.class);
        int difficultyLevel = 3;
        int numPieces = 5;        
        var p1 = mock(ChessProblem.class);
        var p2 = mock(ChessProblem.class);
        when(repository.findByDifficultyAndNumPieces(difficultyLevel, numPieces))
            .thenReturn(List.of(p1, p2));
        
        ChessProblemService service = new ChessProblemService(Optional.of(repository));
        List<ChessProblem> result = service.getProblemsByDifficultyAndNumPieces(difficultyLevel, numPieces);

        assertThat(result)
            .isNotNull()
            .hasSize(2)
            .containsExactlyInAnyOrder(p1, p2);

        verify(repository, times(1)).findByDifficultyAndNumPieces(difficultyLevel, numPieces);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test2aChessProblemServiceGetAllDelegatesToRepository() {
        ChessProblemRepository repository = mock(ChessProblemRepository.class);
        var p1 = mock(ChessProblem.class);
        when(repository.findAll()).thenReturn(List.of(p1));
        
        ChessProblemService service = new ChessProblemService(Optional.of(repository));
        List<ChessProblem> result = service.getAll();

        assertThat(result)
            .isNotNull()
            .hasSize(1)
            .containsExactlyInAnyOrder(p1);

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test2aChessProblemServiceGetByIdDelegatesToRepository() {
        ChessProblemRepository repository = mock(ChessProblemRepository.class);
        var problem = mock(ChessProblem.class);
        when(repository.findById(42)).thenReturn(Optional.of(problem));
        
        ChessProblemService service = new ChessProblemService(Optional.of(repository));
        ChessProblem result = service.getById(42);

        assertSame(problem, result);
        verify(repository).findById(42);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test2aChessProblemServiceDeleteDelegatesToRepository() {
        ChessProblemRepository repository = mock(ChessProblemRepository.class);
        ChessProblemService service = new ChessProblemService(Optional.of(repository));
        var problem = mock(ChessProblem.class);

        service.delete(problem);

        verify(repository).delete(problem);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("difficulty >= 2 and pieces < 6 -> returns 'Fork tactics' and 'Endgame K+P'")
    void test2aDifficulty2PiecesLessThan6() {
        assertThat(pr).isNotNull();        
        List<ChessProblem> result = pr.findByDifficultyAndNumPieces(2, 6);

        assertThat(result)
            .extracting(ChessProblem::getName)
            .containsExactlyInAnyOrder("Fork tactics", "Endgame K+P");

        // Defensive checks: all returned problems match the filter
        assertTrue(result.stream().allMatch(p ->
                p.getDifficultyLevel() >= 2 &&
                p.getBoard().getPieces().size() < 6),
                "All returned problems must have difficulty >= 2 and less than 6 pieces");
    }

    @Test
    @DisplayName("difficulty >= 1 and pieces < 1 -> only empty-board problem ('Endgame K+P')")
    void test2aDifficulty1PiecesLessThan1() {
        assertThat(pr).isNotNull();
        List<ChessProblem> result = pr.findByDifficultyAndNumPieces(1, 1);

        assertThat(result)
            .extracting(ChessProblem::getName)
            .containsExactly("Endgame K+P");

        // Defensive checks: all returned problems match the filter
        assertTrue(result.stream().allMatch(p ->
                p.getDifficultyLevel() >= 1 &&
                p.getBoard().getPieces().size() < 1),
                "All returned problems must have difficulty >= 1 and less than 1 pieces");
    }
    

    @Test
    @DisplayName("difficulty >= 3 and pieces < 8 -> 'Mate in 2' only")
    void test2aDifficulty3PiecesLessThan8() {
        assertThat(pr).isNotNull();
        List<ChessProblem> result = pr.findByDifficultyAndNumPieces(3, 8);

        assertThat(result)
            .extracting(ChessProblem::getName)
            .containsExactly("Mate in 2");

        // Defensive checks: all returned problems match the filter
        assertTrue(result.stream().allMatch(p ->
                p.getDifficultyLevel() >= 3 &&
                p.getBoard().getPieces().size() < 8),
                "All returned problems must have difficulty >= 3 and less than 8 pieces");

    }

    @Test
    @DisplayName("difficulty >= 3 and pieces < 8 -> No results")
    void test2aEmptyResponses() {
        assertThat(pr).isNotNull();
        List<ChessProblem> result = pr.findByDifficultyAndNumPieces(4, 8);

        assertThat(result).isEmpty();
    }    
}
