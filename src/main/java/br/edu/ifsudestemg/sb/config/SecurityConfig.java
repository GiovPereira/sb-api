package br.edu.ifsudestemg.sb.config;

import br.edu.ifsudestemg.sb.security.JwtAuthFilter;
import br.edu.ifsudestemg.sb.security.JwtService;
import br.edu.ifsudestemg.sb.service.UsuarioService;
//import br.edu.ifsudestemg.sb.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()

                .antMatchers(HttpMethod.POST, "/api/v1/usuarios").permitAll()
                .antMatchers("/api/v1/usuarios/auth").permitAll()

                .antMatchers("/api/v1/usuarios/**").hasRole("ADMIN")
                .antMatchers("/api/v1/valordiariomultas/**").hasRole("ADMIN")
                .antMatchers("/api/v1/duracaopadraoemprestimos/**").hasRole("ADMIN")
                .antMatchers("/api/v1/duracaopadraoreservas/**").hasRole("ADMIN")

                .antMatchers("/api/v1/clientes/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/emprestimos/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/reservas/**").hasAnyRole("USER", "ADMIN")

                .antMatchers(HttpMethod.GET, "/api/v1/autores/**", "/api/v1/editoras/**", "/api/v1/generos/**",
                        "/api/v1/idiomas/**", "/api/v1/secoes/**", "/api/v1/obras/**", "/api/v1/exemplares/**")
                .hasAnyRole("USER", "ADMIN")

                .antMatchers("/api/v1/autores/**", "/api/v1/editoras/**", "/api/v1/generos/**",
                        "/api/v1/idiomas/**", "/api/v1/secoes/**", "/api/v1/obras/**", "/api/v1/exemplares/**")
                .hasRole("ADMIN")

                .anyRequest().authenticated()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .addFilterBefore(
                        jwtFilter(),
                        UsernamePasswordAuthenticationFilter.class
                );
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}

