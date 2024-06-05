package gyuho.triptogether.domain.user.entity;

import gyuho.triptogether.domain.token.entity.Token;
import gyuho.triptogether.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //PUBLIC, PROTECTED, PRIVATE 차이 정리
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 10)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = true)
    private String profileImage;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Token> token = new ArrayList<>();

    @Builder
    public User(SocialType socialType, String email, String password, String nickname, Role role, String profileImage){
        this.socialType = socialType;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.profileImage = profileImage;
    }
}
