package com.supermarket.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.supermarket.api.entity.User;
import com.supermarket.api.service.GlobalService.Constant;

public class SecurityUtils {
	public static String GenerateJwt(User user) {
		String jwt = JWT.create().withSubject(user.getFullname()).withIssuer(user.getId().toString())
				.withExpiresAt(Constant.ACCESS_TIME_EXPIRED).withClaim("role", user.getRole().getName())
				.sign(Constant.ENCODE_ALGORITHM);
		return jwt;
	}

	public static DecodedJWT decodedJWT(String RawJwt) {
		try {
			JWTVerifier jwtVerifier = JWT.require(Constant.ENCODE_ALGORITHM).build();
			DecodedJWT decodedJWT = jwtVerifier.verify(RawJwt);

			return decodedJWT;
		} catch (Exception e) {
			return null;
		}
	}
}
