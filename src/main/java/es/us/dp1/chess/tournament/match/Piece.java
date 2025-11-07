package es.us.dp1.chess.tournament.match;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.us.dp1.chess.tournament.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Piece extends BaseEntity implements Cloneable {

    /** 
     * Example: "K" = White King, "k" = Black King, "Q" = White Queen, etc.
     */
    @Column(nullable = false, length = 2)
    private String type;

    /** 
     * Color can be "W" (white) or "B" (black)
     */
    @Column(nullable = false, length = 1)
    private String color;

    /**
     * Board coordinate file, e.g. "a"–"h"
     */
    @Column(nullable = false, length = 1)
    private String posFile;

    /**
     * Board coordinate rank, e.g. 1–8
     */
    @Column(nullable = false)
    private Integer posRank;

    /**
     * Many pieces belong to one board
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    @JsonIgnore
    private ChessBoard board;

    /**
     * Creates a shallow copy of this piece (without keeping the same ID).
     * The `board` reference is not cloned — it should be set manually by the caller.
     */
    @Override
    public Piece clone() {
        try {
            Piece cloned = (Piece) super.clone();
            cloned.setId(null);      // avoid reusing the same primary key
            cloned.setBoard(null);   // avoid back-reference issues; caller should reassign
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning not supported", e);
        }
    }    
}
