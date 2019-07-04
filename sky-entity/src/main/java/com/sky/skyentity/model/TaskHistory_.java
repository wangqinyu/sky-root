package com.sky.skyentity.model;

import com.sky.skyentity.entity.TaskHistory;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TaskHistory.class)
public abstract class TaskHistory_ {

	public static volatile SingularAttribute<TaskHistory, Date> createdAt;
	public static volatile SingularAttribute<TaskHistory, Long> amount;
	public static volatile SingularAttribute<TaskHistory, Long> historyId;
	public static volatile SingularAttribute<TaskHistory, Long> projectId;
	public static volatile SingularAttribute<TaskHistory, Long> userId;
	public static volatile SingularAttribute<TaskHistory, Long> taskId;
	public static volatile SingularAttribute<TaskHistory, Date> updatedAt;

	public static final String CREATED_AT = "createdAt";
	public static final String AMOUNT = "amount";
	public static final String HISTORY_ID = "historyId";
	public static final String PROJECT_ID = "projectId";
	public static final String USER_ID = "userId";
	public static final String TASK_ID = "taskId";
	public static final String UPDATED_AT = "updatedAt";

}

