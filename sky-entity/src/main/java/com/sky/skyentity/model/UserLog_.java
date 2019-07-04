package com.sky.skyentity.model;

import com.sky.skyentity.entity.UserLog;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserLog.class)
public abstract class UserLog_ {

	public static volatile SingularAttribute<UserLog, Date> createdAt;
	public static volatile SingularAttribute<UserLog, Long> relayId;
	public static volatile SingularAttribute<UserLog, String> extra;
	public static volatile SingularAttribute<UserLog, String> ip;
	public static volatile SingularAttribute<UserLog, String> memo;
	public static volatile SingularAttribute<UserLog, Long> logId;
	public static volatile SingularAttribute<UserLog, Long> userId;
	public static volatile SingularAttribute<UserLog, Date> updatedAt;

	public static final String CREATED_AT = "createdAt";
	public static final String RELAY_ID = "relayId";
	public static final String EXTRA = "extra";
	public static final String IP = "ip";
	public static final String MEMO = "memo";
	public static final String LOG_ID = "logId";
	public static final String USER_ID = "userId";
	public static final String UPDATED_AT = "updatedAt";

}

