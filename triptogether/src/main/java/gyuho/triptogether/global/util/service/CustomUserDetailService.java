package gyuho.triptogether.global.util.service;

import gyuho.triptogether.domain.exception.exception.UserException;
import gyuho.triptogether.domain.exception.responseCode.UserExceptionResponseCode;
import gyuho.triptogether.domain.user.entity.User;
import gyuho.triptogether.domain.user.repository.UserRepository;
import gyuho.triptogether.global.util.response.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    //UserDetails인터페이스를 반환해야 하지만 CustomUserDetails는 UserDetails 인터페이스를 구현하고 있는 클래스이므로 가능하다.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("load user by username");
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UserException(UserExceptionResponseCode.NOT_FOUND_USER,Long.parseLong(username)+"유저를 찾지 못했습니다."));
        return new CustomUserDetails(user);
    }

}
