package dev.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "matches")
public class MatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player1_id", referencedColumnName = "id")
    private PlayerEntity playerEntity1;

    @ManyToOne
    @JoinColumn(name = "player2_id", referencedColumnName = "id")
    private PlayerEntity playerEntity2;

    @ManyToOne
    @JoinColumn(name = "winner_id", referencedColumnName = "id")
    private PlayerEntity winner;

    public MatchEntity() {
    }

    public MatchEntity(PlayerEntity playerEntity1, PlayerEntity playerEntity2) {
        this.playerEntity1 = playerEntity1;
        this.playerEntity2 = playerEntity2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlayerEntity getPlayer1() {
        return playerEntity1;
    }

    public void setPlayer1(PlayerEntity playerEntity1) {
        this.playerEntity1 = playerEntity1;
    }

    public PlayerEntity getPlayer2() {
        return playerEntity2;
    }

    public void setPlayer2(PlayerEntity playerEntity2) {
        this.playerEntity2 = playerEntity2;
    }

    public PlayerEntity getWinner() {
        return winner;
    }

    public void setWinner(PlayerEntity winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", player1=" + playerEntity1 +
                ", player2=" + playerEntity2 +
                ", winner=" + winner +
                '}';
    }
}
