package com.sky.skyentity.model;

import com.sky.skyentity.entity.TaskInfo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TaskInfo.class)
public abstract class TaskInfo_ {

	public static volatile SingularAttribute<TaskInfo, Long> benefitActual;
	public static volatile SingularAttribute<TaskInfo, Long> amount;
	public static volatile SingularAttribute<TaskInfo, String> userGuid;
	public static volatile SingularAttribute<TaskInfo, Long> version;
	public static volatile SingularAttribute<TaskInfo, Long> userId;
	public static volatile SingularAttribute<TaskInfo, Long> benefit;
	public static volatile SingularAttribute<TaskInfo, Long> confirm;
	public static volatile SingularAttribute<TaskInfo, Date> createdAt;
	public static volatile SingularAttribute<TaskInfo, Long> left;
	public static volatile SingularAttribute<TaskInfo, Long> report;
	public static volatile SingularAttribute<TaskInfo, Long> taskPid;
	public static volatile SingularAttribute<TaskInfo, Long> projectId;
	public static volatile SingularAttribute<TaskInfo, Long> taskId;
	public static volatile SingularAttribute<TaskInfo, Integer> status;
	public static volatile SingularAttribute<TaskInfo, Date> updatedAt;

	public static final String BENEFIT_ACTUAL = "benefitActual";
	public static final String AMOUNT = "amount";
	public static final String USER_GUID = "userGuid";
	public static final String VERSION = "version";
	public static final String USER_ID = "userId";
	public static final String BENEFIT = "benefit";
	public static final String CONFIRM = "confirm";
	public static final String CREATED_AT = "createdAt";
	public static final String LEFT = "left";
	public static final String REPORT = "report";
	public static final String TASK_PID = "taskPid";
	public static final String PROJECT_ID = "projectId";
	public static final String TASK_ID = "taskId";
	public static final String STATUS = "status";
	public static final String UPDATED_AT = "updatedAt";

}

