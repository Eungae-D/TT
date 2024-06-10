package gyuho.triptogether.global.config;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                // CSRF 비활성화 (token을 사용하는 방식이기 때문에 csrf.token이 필요 없음)
                .csrf(CsrfConfigurer::disable)
                // corsConfig 사용
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                //세션 사용 하지 않음(ALWAYS, IF_REQUIRED, NEVER등)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.dispatcherTypeMatchers().permitAll()
                        .requestMatchers("/api/users/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()); //모든 요청에 대해 인증을 요청

        return http.build();
    }

}

//  .authorizeHttpRequests(auth -> auth.dispatcherTypeMatchers(DispatcherType.ERROR).permitAll() // 에러가 발생했을 때 사용자는 인증 여부와 상관 없이 에러 페이지를 볼 수 있다.
//                        .requestMatchers("/api/")) //로그인 안했으면 로그인폼 제공?,