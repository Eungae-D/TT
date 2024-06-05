package gyuho.triptogether.domain.user.repository;

import gyuho.triptogether.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    //이메일 중복
    boolean existsByEmail(String email);
}
