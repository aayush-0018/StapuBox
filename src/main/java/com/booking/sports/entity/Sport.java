package com.booking.sports.entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "sports",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "sport_code"),
                @UniqueConstraint(columnNames = "sport_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // internal DB id

    @Column(name = "sport_id", nullable = false, unique = true)
    private Long externalSportId;

    @Column(name = "sport_code", nullable = false, unique = true)
    private String sportCode;

    @Column(name = "sport_name", nullable = false)
    private String sportName;
}
