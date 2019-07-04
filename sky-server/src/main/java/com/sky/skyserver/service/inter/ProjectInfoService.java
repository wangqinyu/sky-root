package com.sky.skyserver.service.inter;

import com.sky.skyentity.bean.ProjectInfoParams;
import com.sky.skyentity.entity.ProjectInfo;
import com.sky.skyserver.exception.BaseException;

import java.util.List;

public interface ProjectInfoService {
    //保存项目数据
    void save(ProjectInfo projectInfo) throws BaseException;
    //更新项目数据
    void update(ProjectInfo projectInfo) throws BaseException;
    //查询全部项目数据
    List<ProjectInfo> findAll();
    //按条件查询项目数据
    List<ProjectInfo> findByParams(ProjectInfoParams projectInfoParams);
    //可选条件查询
    List<ProjectInfo> findByOptional(ProjectInfoParams projectInfoParams);
    //删除项目数据
    void delete(Long projectId) throws BaseException;
    //统计项目量
    Long count();
    //根据条件统计项目量
    Long countByParams(ProjectInfoParams projectInfoParams);
    //获取项目信息
    ProjectInfo getProjectInfo(Long projectId) throws BaseException;
    //更改项目状态
    void updateStatus(ProjectInfoParams projectInfoParams) throws BaseException;
}
