package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.CustomUserDetails;
import com.bezkoder.springjwt.models.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Tag(name = "測試controller")
@RequestMapping("/api/test")
public class TestController {

	@Operation(summary = "所有角色request(無角色)")
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}

	@Operation(summary = "USER、MODERATOR、ADMIN角色request user頁面資訊")
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
//	標註此方法需要有token的驗證才可以使用
	@SecurityRequirement(name = "Bearer Authentication")
	public String userAccess(Principal principal, Authentication authentication1, @AuthenticationPrincipal CustomUserDetails user ) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("getName : "+authentication.getName());
		System.out.println("Principal getName : "+principal.getName());
		System.out.println("Authentication getName : "+authentication1.getName());
		System.out.println("getPrincipal : "+authentication.getPrincipal());
		System.out.println("isAuthenticated : "+authentication.isAuthenticated());
		System.out.println("getAuthorities : "+authentication.getAuthorities());
		System.out.println("getDetails : "+authentication.getDetails());
//		System.out.println("@AuthenticationPrincipal getUsername : "+user.getUsername());

		return "User Content.";
	}

	@Operation(summary = "MODERATOR角色Admin角色request頁面頁面")
	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	@SecurityRequirement(name = "Bearer Authentication")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@Operation(summary = "Admin角色request頁面")
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "Bearer Authentication")
	public String adminAccess() {
		return "Admin Board.";
	}
}
