package com.bezkoder.springjwt.security;

import com.bezkoder.springjwt.security.services.UserDetailsServiceImpl;
import com.bezkoder.springjwt.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bezkoder.springjwt.security.jwt.AuthEntryPointJwt;
import com.bezkoder.springjwt.security.jwt.AuthTokenFilter;

//@Configuration的作用同以前的xml配置檔（例如Spring的applicationContext.xml或dispatcher-servlet.xml），
//用來設定Spring環境配置，例如宣告及註冊bean至Spring容器中，注入properties參數等。
@Configuration
//簡單來說＠EnableWebSecurity是用來啟用Spring Security所需的各項配置。
@EnableWebSecurity
//@EnableGlobalMethodSecurity用途相當於Spring MVC傳統xml配置的<global-method-security>。
//@EnableGlobalMethodSecurity用來啟用基於annotation註解如@Security， @PreAuthorize，@RolesAllowed的服務層安全機制。
//@EnableGlobalMethodSecurity有三個屬性值，
//securedEnabled設定是否啟用@Secured，
//prePostEnabled設定是否啟用@PreAuthorize，
//jsr250Enabled設定是否啟用@RolesAllowed。
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//	@Autowired 
//	spring 開發團隊不推薦使用autowired
	private final UserDetailsServiceImpl userDetailsService;

    private final CustomUserDetailsService service;

    //	@Autowired
//	spring 開發團隊不推薦使用autowired
    private AuthEntryPointJwt unauthorizedHandler;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, CustomUserDetailsService service, AuthEntryPointJwt unauthorizedHandler) {
        this.userDetailsService = userDetailsService;
        this.service = service;
        this.unauthorizedHandler = unauthorizedHandler;
    }

//	不使用autowired 推薦使用建構子
//	public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, AuthEntryPointJwt unauthorizedHandler) {
//		super();
//		this.userDetailsService = userDetailsService;
//		this.unauthorizedHandler = unauthorizedHandler;
//	}

    // @Bean???????????????????
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

//	我們覆寫WebSecurityConfigurerAdapter interface中的configure，他會告訴spring security我們要如何配置 CROS以及 CSRF，
//	當我們要求所有的user需要認證或不需要認證，而filter(AuthTokenFilter)會在UsernamePasswordAuthenticationFilter執行前就先執行，
//	而excepotion管理會在AuthEntryPointJwt

//	我們可以看到UserDetailsServiceImpl繼承了UserDetailsService，而UserDetailsServiceImpl中覆寫了UserDetailsService中的loadUserByUsername，
//	使用UserRepository加上req的username去取得使用者資料

    //	We override the configure(HttpSecurity http) method from WebSecurityConfigurerAdapter interface.
//	It tells Spring Security how we configure CORS and CSRF, when we want to require all users to be authenticated or not, 
//	which filter (AuthTokenFilter) and when we want it to work (filter before UsernamePasswordAuthenticationFilter), 
//	which Exception Handler is chosen (AuthEntryPointJwt).
//	看不懂在做甚麼
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()

                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers(
                        // -- Swagger UI v2
                        "/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        // -- Swagger UI v3 (OpenAPI)
                        "/v3/api-docs/**",
                        "/swagger-ui/**").permitAll()
                .antMatchers("/api/test/**").permitAll().anyRequest()
                .authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);


    }
}
