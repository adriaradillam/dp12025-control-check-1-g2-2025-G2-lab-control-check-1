package es.us.dp1.chess.tournament;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import es.us.dp1.chess.tournament.match.ChessProblemSolution;
import es.us.dp1.chess.tournament.match.ProblemSource;
import jakarta.persistence.EntityManager;

@DataJpaTest()
public class Test5 extends ReflexiveTest {

    @Autowired
    EntityManager em;

    // ---------- ProblemSolution ----------
    @Test
    public void test5ChessProblemSolution1() {
        ChessProblemSolution s901 = em.find(ChessProblemSolution.class, 901);
        assertThat(s901)
            .as("There should exist a ChessProblemSolution with id 901")
            .isNotNull();

        assertThat(s901.getName()).isEqualTo("Canonical Mate in 1 (line A)");
        assertThat(s901.getCreatedAt()).isEqualTo(LocalDateTime.of(2025, 1, 10, 9, 11, 0));
        assertThat(s901.isMinimal()).isTrue();
        assertThat(s901.getNotes()).isEqualTo("Immediate mate with Qh5# from the starting position.");
        assertThat(s901.getExpectedMoveCount()).isEqualTo(1);
    }

    @Test
    public void test5ChessProblemSolution2() {
        ChessProblemSolution s902 = em.find(ChessProblemSolution.class, 902);
        assertThat(s902)
            .as("There should exist a ChessProblemSolution with id 902")
            .isNotNull();

        assertThat(s902.getName()).isEqualTo("Canonical Mate in 2 (line B)");
        assertThat(s902.getCreatedAt()).isEqualTo(LocalDateTime.of(2025, 1, 10, 9, 21, 0));
        assertThat(s902.isMinimal()).isTrue();
        assertThat(s902.getNotes()).isEqualTo("Two-move forcing sequence ending in mate with Qd5#.");
        assertThat(s902.getExpectedMoveCount()).isEqualTo(2);
    }

    // ---------- ProblemSource ----------

    @Test
    public void test5ProblemSource1() {
        ProblemSource ps801 = em.find(ProblemSource.class, 801);
        assertThat(ps801)
            .as("There should exist a ProblemSource with id 801")
            .isNotNull();

        assertThat(ps801.getName()).isEqualTo("Classic Puzzle Compendium");
        assertThat(ps801.getAuthor()).isEqualTo("H. Steinitz");
        assertThat(ps801.getDescription()).isEqualTo("A curated set of elegant checkmate studies published in 1895.");
        assertThat(ps801.getContactEmail()).isEqualTo("steinitz@classicpuzzles.org");
    }


    @Test
    public void test5ProblemSource2() {
        ProblemSource ps802 = em.find(ProblemSource.class, 802);
        assertThat(ps802)
            .as("There should exist a ProblemSource with id 801")
            .isNotNull();

        assertThat(ps802.getName()).isEqualTo("Modern Engine Analysis 2025");
        assertThat(ps802.getAuthor()).isEqualTo("DeepEval AI");
        assertThat(ps802.getDescription()).isEqualTo("Computer-validated solutions using latest neural networks.");
        assertThat(ps802.getContactEmail()).isEqualTo("analysis@deepeval.ai");    
    }
    
    // -------------------------
    // ChessProblemSolution -> Problem
    // -------------------------

    @Test
    public void test5bChessProblemSolutionProblemLinks() {        
        String problemGetter = "getProblem";

        checkLinkedById(ChessProblemSolution.class, 901, problemGetter, 1, em);
        checkLinkedById(ChessProblemSolution.class, 902, problemGetter, 3, em);
    }

    // -------------------------
    // ChessProblemSolution -> Board
    // -------------------------

    @Test
    public void test5bChessProblemSolutionLinks() {        
        String getter = "getSolutionBoard";
        checkLinkedById(ChessProblemSolution.class, 901, getter, 910, em);
        checkLinkedById(ChessProblemSolution.class, 902, getter, 911, em);
    }


    // -------------------------
    // ProblemSource -> ChessProblem
    // -------------------------

    @Test
    public void test5bProblemSourceToChessProblemLinks() {        
        String getter = "getProblems";

        checkContainsById(ProblemSource.class, 801, getter, 1, em);
        checkContainsById(ProblemSource.class, 801, getter, 3, em);

        checkContainsById(ProblemSource.class, 802, getter, 2, em);
        checkContainsById(ProblemSource.class, 802, getter, 4, em);
        checkContainsById(ProblemSource.class, 802, getter, 5, em);
    }
  
}