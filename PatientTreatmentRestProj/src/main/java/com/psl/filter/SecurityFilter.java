package com.psl.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.psl.service.*;
import com.psl.utility.JWTUtil;

@Component
public class SecurityFilter extends OncePerRequestFilter{

	@Autowired
	private JWTUtil jwtutil;
	
	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String auth = 	request.getHeader("authorization");
		String token = null;
		String userName = null;
		
		if(null != auth && auth.startsWith("Bearer ")) {
			token = auth.substring(7);
			userName = jwtutil.getClaims("pslkey", token).getSubject();
		}
		
		if(null != userName && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails  = null;
			userDetails = userService.loadUserByUsername(userName);
			if(jwtutil.isTokenValid("pslkey", token)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
				                                                                      new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource()
						                                                              .buildDetails(request));
				
				SecurityContextHolder.getContext()
				                                       .setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}
}//class
