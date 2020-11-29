package com.chatserver.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.chatserver.model.SessionModel;
import com.chatserver.service.MyUserDetailsService;
import com.chatserver.service.SessionService;
import com.chatserver.util.Utility;

@Component
public class SessionRequestFilter extends OncePerRequestFilter{
	
	@Autowired MyUserDetailsService myUserDetailsService;
	@Autowired SessionService sessionService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String auth = request.getHeader("Authorization");
			SessionModel aSession = null;
			
			if (auth !=null && auth.length() == Utility.SESSIONID_LENGTH && sessionService.sessionExists(auth)) {
				aSession = sessionService.getSession(auth);
			}

			if (aSession != null && SecurityContextHolder.getContext().getAuthentication()== null) {
				// username pulled from token and nothing stored in the security context
				// UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(username);
				UserDetails userDetails = new User(aSession.getUserName(), "", new ArrayList<>());
				
				UsernamePasswordAuthenticationToken aPAT = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				aPAT.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(aPAT);
				aSession.hitSession();
			}
		}
		catch (Exception e) {
			System.out.println("error caught");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
	        response.getWriter().write("Session token invalid");
	        return ;
		}    
		filterChain.doFilter(request, response);
	}

}
