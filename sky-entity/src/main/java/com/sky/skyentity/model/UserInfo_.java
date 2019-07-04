package com.sky.skyentity.model;

import com.sky.skyentity.entity.UserInfo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserInfo.class)
public abstract class UserInfo_ {

	public static volatile SingularAttribute<UserInfo, String> salt;
	public static volatile SingularAttribute<UserInfo, String> mail;
	public static volatile SingularAttribute<UserInfo, Long> userAuth;
	public static volatile SingularAttribute<UserInfo, String> userGuid;
	public static volatile SingularAttribute<UserInfo, Integer> type;
	public static volatile SingularAttribute<UserInfo, Long> userId;
	public static volatile SingularAttribute<UserInfo, Long> version;
	public static volatile SingularAttribute<UserInfo, String> realName;
	public static volatile SingularAttribute<UserInfo, Long> score;
	public static volatile SingularAttribute<UserInfo, Date> createdAt;
	public static volatile SingularAttribute<UserInfo, String> password;
	public static volatile SingularAttribute<UserInfo, String> phone;
	public static volatile SingularAttribute<UserInfo, String> name;
	public static volatile SingularAttribute<UserInfo, Long> userPid;
	public static volatile SingularAttribute<UserInfo, Integer> status;
	public static volatile SingularAttribute<UserInfo, Date> updatedAt;

	public static final String SALT = "salt";
	public static final String MAIL = "mail";
	public static final String USER_AUTH = "userAuth";
	public static final String USER_GUID = "userGuid";
	public static final String TYPE = "type";
	public static final String USER_ID = "userId";
	public static final String VERSION = "version";
	public static final String REAL_NAME = "realName";
	public static final String SCORE = "score";
	public static final String CREATED_AT = "createdAt";
	public static final String PASSWORD = "password";
	public static final String PHONE = "phone";
	public static final String NAME = "name";
	public static final String USER_PID = "userPid";
	public static final String STATUS = "status";
	public static final String UPDATED_AT = "updatedAt";

}

