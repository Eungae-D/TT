package gyuho.triptogether.global.config;

import gyuho.triptogether.domain.token.repository.TokenRepository;
import gyuho.triptogether.global.util.jwt.CustomLogoutFilter;
import gyuho.triptogether.global.util.jwt.JWTFilter;
import gyuho.triptogether.global.util.jwt.JwtUtil;
import gyuho.triptogether.global.util.jwt.LoginFilter;
import gyuho.triptogether.global.util.oauth2.CustomOAuth2UserService;
import gyuho.triptogether.global.util.oauth2.CustomSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity // security 활성화 어노테이션
@RequiredArgsConstructor
public class SecurityConfig {
    private final CorsConfigurationSource corsConfigurationSource;

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{


        http
                // CSRF 비활성화 (token을 사용하는 방식이기 때문에 csrf.token이 필요 없음)
                .csrf(CsrfConfigurer::disable)
                //시큐리티 폼 로그인 방식 사용 x
                .formLogin(auth -> auth.disable())
                //httpBasic방식 사용 x -> Bearer 방식
                .httpBasic(auth -> auth.disable())
                //oauth2
                .oauth2Login(oauth2 -> oauth2.userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2UserService)).successHandler(customSuccessHandler))
                // corsConfig 사용
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                //세션 사용 하지 않음(ALWAYS, IF_REQUIRED, NEVER등)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.dispatcherTypeMatchers().permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/api/v1/refresh-token").permitAll()
                        .requestMatchers("/api/v1/users").permitAll()
                        .requestMatchers("/api/v1/users/email-check").permitAll()
                        .requestMatchers("/api/v1/users/**").hasAuthority("USER")
                        .anyRequest().authenticated()) // 허가된 사람만 인가
                .addFilterAt(new JWTFilter(jwtUtil),LoginFilter.class)
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, tokenRepository), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, tokenRepository), LogoutFilter.class);

        return http.build();

    }
}