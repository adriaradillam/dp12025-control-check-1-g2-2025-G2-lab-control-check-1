package es.us.dp1.chess.tournament;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import es.us.dp1.chess.tournament.match.ChessBoard;
import es.us.dp1.chess.tournament.match.ChessProblem;
import es.us.dp1.chess.tournament.match.ChessProblemSolution;
import es.us.dp1.chess.tournament.match.ChessProblemSolutionRepository;
import es.us.dp1.chess.tournament.match.ProblemSource;
import es.us.dp1.chess.tournament.match.ProblemSourceRepository;
import es.us.dp1.chess.tournament.user.Authorities;
import es.us.dp1.chess.tournament.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;

@DataJpaTest()
public class Test4a extends ReflexiveTest {

    @Autowired(required = false)
    ChessProblemSolutionRepository solutionRepo;

    @Autowired(required = false)
    ProblemSourceRepository sourceRepo;

    @Autowired
    EntityManager em;

    @Test
    public void test4aProblemSourceRepositoryContainsMethod() {
        if (sourceRepo != null) {
            Optional<ProblemSource> v = sourceRepo.findById(12);
            assertThat(v)
                    .withFailMessage("No result (null) should be returned for a ProblemSource that does not exist")
                    .isNotNull()
                    .isNotPresent();
        } else {
            fail("The ProblemSource repository was not injected into the tests, its autowired value was null");
        }
    }

    @Test
    public void test4aSolutionRepositoryContainsMethod() {
        if (solutionRepo != null) {
            Optional<ChessProblemSolution> v = solutionRepo.findById(12);
            assertThat(v)
                    .withFailMessage("No result (null) should be returned for a ChessProblemSolution that does not exist")
                    .isNotNull()
                    .isNotPresent();
        } else
            fail("The ChessProblemSolution repository was not injected into the tests, its autowired value was null");
    }

    @Test
    public void test4aCheckChessProblemSolutionConstraints() {
        Map<String, List<Object>> invalidValues = Map.of(
            "name", List.of("      ", "a", randomString(60)),
            "notes", List.of(randomString(4100)),
            "expectedMoveCount", List.of(-1, 0, 20)
        );

        ChessProblemSolution s = createValidChessProblemSolution(em);
        em.persist(s);

        checkThatFieldsAreMandatory(s, em, "name", "createdAt");

        checkThatValuesAreNotValid(s, invalidValues, em);
    }

    @Test
    public void test4aCheckProblemSourceContraints() {
        Map<String, List<Object>> invalidValues = Map.of(
            "name", List.of("", "aa", randomString(60)),
            "author", List.of("", "aa", randomString(150)),
            "description", List.of(randomString(1200)),
            "contactEmail", List.of("not-an-email", "missing-at-sign.com", "missing-domain@.com")
        );

        ProblemSource t = createValidProblemSource(em);
        em.persist(t);

        checkThatFieldsAreMandatory(t, em, "name", "author");

        checkThatValuesAreNotValid(t, invalidValues, em);
    }

    @Test
    public void test4aCheckChessProblemSolutionAnnotations() throws NoSuchFieldException, SecurityException {
        assertTrue(classIsAnnotatedWith(ChessProblemSolution.class, Entity.class));
    }

    @Test
    public void test4aCheckProblemSourceAnnotations() throws NoSuchFieldException, SecurityException {
        assertTrue(classIsAnnotatedWith(ProblemSource.class, Entity.class));
    }

    public static ProblemSource createValidProblemSource(EntityManager em) {
        ProblemSource problemSource = new ProblemSource();
        ChessProblem p = null;

        if (em != null) {
            p = em.find(ChessProblem.class, 1);
        } else {
            p = createProblem();
        }

        problemSource.setName("Test Problem Source");
        problemSource.setAuthor("Author Name");
        problemSource.setDescription("Description of the problem source");
        problemSource.setContactEmail("test@gg.com");
        problemSource.setProblems(List.of(p));

        return problemSource;
    }

    private static ChessProblem createProblem() {
        ChessProblem problem = new ChessProblem();

        problem.setName("Test Problem");
        problem.setDifficultyLevel(3);
        problem.setGoal("Checkmate in 2 moves");
        problem.setBoard(new ChessBoard());

        return problem;
    }

    public static ChessProblemSolution createValidChessProblemSolution(EntityManager em) {
        ChessProblemSolution solution = new ChessProblemSolution();
        solution.setName("Autumn Open Tournament");
        solution.setCreatedAt(LocalDateTime.of(2025, 10, 20, 12, 00));
        solution.setMinimal(true);
        solution.setExpectedMoveCount(10);
        solution.setProblem(em != null ? em.find(ChessProblem.class, 1) : createProblem());
        solution.setSolutionBoard(em != null ? em.find(ChessBoard.class, 910) : new ChessBoard());
        
        return solution;
    }

    public static User createUser(String name) {
        Authorities a1 = new Authorities();
        a1.setAuthority("PANA");
        User u1 = new User();
        setValue(u1, "username", String.class, name);
        setValue(u1, "authority", Authorities.class, a1);
        return u1;
    }

    private static final String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();

    public static String randomString(int length) {
        if (length <= 0) throw new IllegalArgumentException("Length must be positive");
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARSET.length());
            sb.append(CHARSET.charAt(index));
        }
        return sb.toString();
    }

}
