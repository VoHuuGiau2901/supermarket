package com.supermarket.api.service.GlobalService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.auth0.jwt.algorithms.Algorithm;
import com.supermarket.api.config.ResourceConfig;

public class Constant {
	public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public static Date getCurrentDateTime() {
		return new Date();
	}

	public static final Integer ACTIVE_STATUS = 1;
	public static final Integer PENDING_STATUS = 0;
	public static final Integer INACTIVE_STATUS = -1;

	public static final String TABLE_PREFIX = "superMarket";

	public static final String SERVER_PUBLIC_FOLDER_LINK = "http://localhost:" + ResourceConfig.serverPort + "/public/";

	public static final String USER_ROLE = "USER";
	public static final String ADMIN_ROLE = "ADMIN";

	public static Algorithm ENCODE_ALGORITHM = Algorithm.HMAC256("sUpER_MaRKet".getBytes());
	public static Integer ACCESS_TIME_EXPIRED = 30 * 1000 * 60;
}