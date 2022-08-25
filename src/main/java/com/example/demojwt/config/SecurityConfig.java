package com.example.demojwt.config;

import com.example.demojwt.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import javax.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true,
                            jsr250Enabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public CustomUserDetailsService customUserDetailsService;

    @Autowired
    public JwtAuthenticationFilter jwtFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    String PUBLICURLS[]=new String[]{
            "/login",
            "/getBooks",
            "/register",
            "/issuebook",
            "/returnbook"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                 .authorizeRequests()
                 .antMatchers(PUBLICURLS).permitAll()
                  //.and()
                  //.authorizeRequests()
                  //.antMatchers("/admin/**").hasRole("ADMIN")
                  .anyRequest().authenticated()
                  .and()
                  .httpBasic();

                http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                http.cors().disable();
                http.csrf().disable();
        http.exceptionHandling()
                .authenticationEntryPoint(this.jwtAuthenticationEntryPoint);
        //http.csrf().ignoringAntMatchers(AntMatchers).disable();

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
       // http.headers().frameOptions().sameOrigin();
    }

   /* @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.customUserDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
        //return new BCryptPasswordEncoder();

    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        //return super.authenticationManagerBean();
        return super.authenticationManager();
    }
}
