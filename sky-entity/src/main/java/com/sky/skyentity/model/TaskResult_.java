package com.sky.skyentity.model;

import com.sky.skyentity.entity.TaskResult;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TaskResult.class)
public abstract class TaskResult_ {

	public static volatile SingularAttribute<TaskResult, Date> createdAt;
	public static volatile SingularAttribute<TaskResult, Long> amount;
	public static volatile SingularAttribute<TaskResult, Long> resultId;
	public static volatile SingularAttribute<TaskResult, Integer> index;
	public static volatile SingularAttribute<TaskResult, String> userGuid;
	public static volatile SingularAttribute<TaskResult, Integer> type;
	public static volatile SingularAttribute<TaskResult, Long> userId;
	public static volatile SingularAttribute<TaskResult, Long> projectId;
	public static volatile SingularAttribute<TaskResult, Long> taskId;
	public static volatile SingularAttribute<TaskResult, Date> taskTime;
	public static volatile SingularAttribute<TaskResult, Date> updatedAt;

	public static final String CREATED_AT = "createdAt";
	public static final String AMOUNT = "amount";
	public static final String RESULT_ID = "resultId";
	public static final String INDEX = "index";
	public static final String USER_GUID = "userGuid";
	public static final String TYPE = "type";
	public static final String USER_ID = "userId";
	public static final String PROJECT_ID = "projectId";
	public static final String TASK_ID = "taskId";
	public static final String TASK_TIME = "taskTime";
	public static final String UPDATED_AT = "updatedAt";

}

