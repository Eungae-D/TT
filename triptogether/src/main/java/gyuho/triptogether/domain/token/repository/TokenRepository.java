package gyuho.triptogether.domain.token.repository;

import gyuho.triptogether.domain.token.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface TokenRepository extends JpaRepository<Token,Long> {

    boolean existsByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken);

}
