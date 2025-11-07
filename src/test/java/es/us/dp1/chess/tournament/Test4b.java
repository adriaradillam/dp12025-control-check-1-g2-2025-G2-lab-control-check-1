package es.us.dp1.chess.tournament;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import es.us.dp1.chess.tournament.match.ChessProblemSolution;
import es.us.dp1.chess.tournament.match.ProblemSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@DataJpaTest()
public class Test4b extends ReflexiveTest{

    @Autowired(required = false)
    EntityManager em;

    @Test
    public void test4bSolutionProblemMatchAnnotations() {
        checkThatFieldIsAnnotatedWith(ChessProblemSolution.class, "problem", ManyToOne.class);        
        checkThatFieldIsAnnotatedWith(ChessProblemSolution.class, "problem", NotNull.class);        
    }

    @Test
    public void test4bSolutionBoardMatchAnnotations() {
        checkThatFieldIsAnnotatedWith(ChessProblemSolution.class, "solutionBoard", OneToOne.class);        
        checkThatFieldIsAnnotatedWith(ChessProblemSolution.class, "solutionBoard", NotNull.class);        
    }

    @Test
    public void test2SourceProblemAnnotations() {
        checkThatFieldIsAnnotatedWith(ProblemSource.class, "problems", ManyToMany.class);
    }    

}
