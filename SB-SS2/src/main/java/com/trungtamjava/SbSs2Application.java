package com.trungtamjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SpringBootApplication
public class SbSs2Application extends WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(SbSs2Application.class, args);
	}

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		// cau hinh ma hoa mat khau dung UserDetailService
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
				.antMatchers("/member/**").authenticated().anyRequest().permitAll()
				// moi request co duong dan la /admin/**(co ROLE_ADMIN) va duong dan la
				// /member/** thi deu phai xac thuc
				.and().formLogin().loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/member/home")
				.failureUrl("/login?err").and().logout().logoutUrl("/logout").logoutSuccessUrl("/login")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				// login page co duong dan la /login.Neu thanh cong thi chuyen huong sang
				// duong dan /member/home.Neu loi thi quay lai trang /login?err de thong bao
				// loi.Logout co url
				// la /logout,neu logout thanh cong thi quay lai trang login
				.permitAll().and().exceptionHandling().accessDeniedPage("/login?deny").and().httpBasic();

	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
		return bCryptPasswordEncoder;
		// Ma hoa mat khau nguoi dung
	}

}
