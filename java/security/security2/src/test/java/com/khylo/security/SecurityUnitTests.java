package com.khylo.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


class SecurityUnitTests {

	@Test
	void testBcrypt(){
		System.out.println("Encoded password = "+ new BCryptPasswordEncoder().encode("password"));
		System.out.println("Encoded admin = "+ new BCryptPasswordEncoder().encode("admin"));
	}

}
