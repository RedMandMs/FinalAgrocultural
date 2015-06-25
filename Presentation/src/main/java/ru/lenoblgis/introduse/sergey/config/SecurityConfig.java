package ru.lenoblgis.introduse.sergey.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import ru.lenoblgis.introduse.sergey.domen.user.UserRole;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	/**
	 * ������ ��� ������ � ��������������
	 */
	@Autowired
    private UserDetailsService userDetailsService;
 
    /**
     * ������������ ���� ���������� UserDetailsService, � ����� MD5Encoder ��� ���������� ������ � ������ MD5
     * @param auth - ������ ��������� ��������������
     * @throws Exception - ����� ������
     */
	@Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(getMd5PasswordEncoder());
    }
 
    /**
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // �������� ������ �� CSRF ����
        http.csrf()
                .disable()
                // ��������� ������� ��������
                // �� ������� ����� ����������� ������ � �������� � ��������� ������
                .authorizeRequests()
                .antMatchers("/login", "/registration", "/").permitAll()
                .antMatchers("/organization/**", "/passport/**")
                					.authenticated()
                .antMatchers("/admin/**").hasAuthority(UserRole.ADMIN.getName())
                .anyRequest().permitAll()
                .and();
 
        http.formLogin()
                // ��������� �������� � ������ ������
                .loginPage("/login")
                // ��������� action � ����� ������
                .loginProcessingUrl("/j_spring_security_check")
                // ��������� URL ��� ��������� ������
                .failureUrl("/login?error")
                // ��������� ��������� ������ � ������ � ����� ������
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                //��������� ���������, ������� �������� ��������� � ���� ����� ����� �������� ��������������
                .successHandler(new MyAuthenticationSuccessHandler())
                // ���� ������ � ����� ������ ����
                .permitAll();
 
        http.logout()
                // ��������� ������ ������ ����
                .permitAll()
                // ��������� URL �������
                .logoutUrl("/logout")
                // ��������� URL ��� ������� �������
                .logoutSuccessUrl("/login?logout")
                // ������ �� �������� ������� ������
                .invalidateHttpSession(true);
    }
    
    
	/**
	 * �������� bean MD5-encoder
	 * @return - MD5-encoder
	 */
    @Bean
    public Md5PasswordEncoder getMd5PasswordEncoder(){
        return new Md5PasswordEncoder();
    }
}
