package gyuho.triptogether.domain.token.entity;

import gyuho.triptogether.domain.user.entity.User;
import gyuho.triptogether.global.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Token extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="user_id",nullable = false)
    private User user;

    @Column(nullable = true, length = 200)
    private String accessToken;

    @Column(nullable = true, length = 200)
    private String refreshToken;

    private LocalDateTime expiredDate;


}
