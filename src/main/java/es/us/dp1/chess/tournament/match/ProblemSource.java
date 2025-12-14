package es.us.dp1.chess.tournament.match;

import java.util.List;

import es.us.dp1.chess.tournament.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ProblemSource extends BaseEntity {

    @Size(min = 3, max = 50)
    @NotBlank
    String name;

    @Size(min = 3, max = 100)
    @NotNull
    String author;

    @Size(max = 1000)
    String description;

    @Email
    String contactEmail;

    @ManyToMany
    List<ChessProblem> problems;
}
