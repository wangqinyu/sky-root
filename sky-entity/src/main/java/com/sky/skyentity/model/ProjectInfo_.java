package com.sky.skyentity.model;

import com.sky.skyentity.entity.ProjectInfo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProjectInfo.class)
public abstract class ProjectInfo_ {

	public static volatile SingularAttribute<ProjectInfo, Long> minAmount;
	public static volatile SingularAttribute<ProjectInfo, Long> amount;
	public static volatile SingularAttribute<ProjectInfo, Long> version;
	public static volatile SingularAttribute<ProjectInfo, String> content;
	public static volatile SingularAttribute<ProjectInfo, Date> createdAt;
	public static volatile SingularAttribute<ProjectInfo, Integer> urlType;
	public static volatile SingularAttribute<ProjectInfo, Integer> postFlag;
	public static volatile SingularAttribute<ProjectInfo, Long> left;
	public static volatile SingularAttribute<ProjectInfo, String> name;
	public static volatile SingularAttribute<ProjectInfo, Date> startTime;
	public static volatile SingularAttribute<ProjectInfo, Date> endTime;
	public static volatile SingularAttribute<ProjectInfo, Long> maxAmount;
	public static volatile SingularAttribute<ProjectInfo, Long> projectId;
	public static volatile SingularAttribute<ProjectInfo, String> urlGen;
	public static volatile SingularAttribute<ProjectInfo, Integer> status;
	public static volatile SingularAttribute<ProjectInfo, String> desc;
	public static volatile SingularAttribute<ProjectInfo, Date> updatedAt;

	public static final String MIN_AMOUNT = "minAmount";
	public static final String AMOUNT = "amount";
	public static final String VERSION = "version";
	public static final String CONTENT = "content";
	public static final String CREATED_AT = "createdAt";
	public static final String URL_TYPE = "urlType";
	public static final String POST_FLAG = "postFlag";
	public static final String LEFT = "left";
	public static final String NAME = "name";
	public static final String START_TIME = "startTime";
	public static final String END_TIME = "endTime";
	public static final String MAX_AMOUNT = "maxAmount";
	public static final String PROJECT_ID = "projectId";
	public static final String URL_GEN = "urlGen";
	public static final String STATUS = "status";
	public static final String DESC = "desc";
	public static final String UPDATED_AT = "updatedAt";

}

