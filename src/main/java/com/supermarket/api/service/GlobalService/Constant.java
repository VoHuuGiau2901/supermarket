package com.supermarket.api.service.GlobalService;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.auth0.jwt.algorithms.Algorithm;

public class Constant {
	public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

	public static final Date getCurrentDateTime() {
		return new Date();
	}

	public static final Integer ACTIVE_STATUS = 1;
	public static final Integer PENDING_STATUS = 0;
	public static final Integer INACTIVE_STATUS = -1;

	public static final String TABLE_PREFIX = "superMarket";

	public static final String SERVER_PUBLIC_FOLDER_LINK = "http://localhost:5000/public/";

	public static final String USER_ROLE = "USER";
	public static final String ADMIN_ROLE = "ADMIN";

	public static final Algorithm ENCODE_ALGORITHM = Algorithm.HMAC256("sUpER_MaRKet".getBytes());
	public static final Date ACCESS_TIME_EXPIRED = new Date(System.currentTimeMillis() + 30 * 1000 * 60);
}