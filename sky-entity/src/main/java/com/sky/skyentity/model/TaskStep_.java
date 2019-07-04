package com.sky.skyentity.model;

import com.sky.skyentity.entity.TaskStep;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TaskStep.class)
public abstract class TaskStep_ {

	public static volatile SingularAttribute<TaskStep, Date> createdAt;
	public static volatile SingularAttribute<TaskStep, String> extra;
	public static volatile SingularAttribute<TaskStep, Long> stepId;
	public static volatile SingularAttribute<TaskStep, String> name;
	public static volatile SingularAttribute<TaskStep, Integer> index;
	public static volatile SingularAttribute<TaskStep, String> alias;
	public static volatile SingularAttribute<TaskStep, Long> projectId;
	public static volatile SingularAttribute<TaskStep, Date> updatedAt;

	public static final String CREATED_AT = "createdAt";
	public static final String EXTRA = "extra";
	public static final String STEP_ID = "stepId";
	public static final String NAME = "name";
	public static final String INDEX = "index";
	public static final String ALIAS = "alias";
	public static final String PROJECT_ID = "projectId";
	public static final String UPDATED_AT = "updatedAt";

}

