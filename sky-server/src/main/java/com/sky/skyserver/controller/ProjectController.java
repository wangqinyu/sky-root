package com.sky.skyserver.controller;

import com.sky.skyentity.bean.ProjectInfoParams;
import com.sky.skyserver.common.BaseDataResp;
import com.sky.skyserver.common.BaseExceptionEnum;
import com.sky.skyentity.entity.ProjectInfo;
import com.sky.skyserver.exception.BaseException;
import com.sky.skyserver.service.inter.ProjectInfoService;
import com.sky.skyserver.util.security.PrivacyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.sky.skyserver.common.BaseExceptionEnum.SESSION_LOGIN_EXPIRED;

@RestController
@RequestMapping("/project")
@Api(value = "项目管理", description = "项目管理API")
public class ProjectController {
    private Logger logger = LoggerFactory.getLogger(ProjectController.class);
    @Autowired
    ProjectInfoService projectInfoService;
    //==============平台基础操作接口(不对普通用户开放)==============
    @ApiOperation(value = "保存(admin)", notes = "保存项目数据")
    @RequestMapping(value = "/admin/save", method = {RequestMethod.POST})
    public BaseDataResp save(ProjectInfo projectInfo) {
        BaseDataResp baseDataResp;
        try {
            if (null == projectInfo)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            projectInfoService.save(projectInfo);
            baseDataResp = new BaseDataResp("操作成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "更新(admin)", notes = "更新项目数据")
    @RequestMapping(value = "/admin/update", method = {RequestMethod.POST})
    public BaseDataResp update(ProjectInfo projectInfo) {
        BaseDataResp baseDataResp;
        try {
            if (null == projectInfo)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            projectInfoService.update(projectInfo);
            baseDataResp = new BaseDataResp("操作成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "查询全部(admin)", notes = "查询全部项目数据")
    @RequestMapping(value = "/admin/findAll", method = {RequestMethod.POST})
    public BaseDataResp<List<ProjectInfo>> findAll() {
        BaseDataResp baseDataResp;
        try {
            List<ProjectInfo> projectInfoList = projectInfoService.findAll();
            baseDataResp = new BaseDataResp("查询成功",projectInfoList);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "条件查询(admin)", notes = "按条件AND查询项目数据")
    @RequestMapping(value = "/admin/findByParams", method = {RequestMethod.POST})
    public BaseDataResp<List<ProjectInfo>> findByParams(ProjectInfoParams projectInfoParams) {
        BaseDataResp baseDataResp;
        try {
            if (null == projectInfoParams)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            List<ProjectInfo> projectInfoList = projectInfoService.findByParams(projectInfoParams);
            baseDataResp = new BaseDataResp("查询成功",projectInfoList);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "可选条件查询(admin)", notes = "通过多个可选条件OR查询项目数据")
    @RequestMapping(value = "/admin/findByOptional", method = {RequestMethod.POST})
    public BaseDataResp<List<ProjectInfo>> findByOptional(ProjectInfoParams projectInfoParams) {
        BaseDataResp baseDataResp;
        try {
            if (null == projectInfoParams)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            List<ProjectInfo> projectInfoList = projectInfoService.findByOptional(projectInfoParams);
            baseDataResp = new BaseDataResp("查询成功",projectInfoList);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "删除(admin)", notes = "删除项目数据")
    @RequestMapping(value = "/admin/delete", method = {RequestMethod.POST})
    public BaseDataResp delete(Long projectId) {
        BaseDataResp baseDataResp;
        try {
            projectInfoService.delete(projectId);
            baseDataResp = new BaseDataResp("操作成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "统计(admin)", notes = "统计项目量")
    @RequestMapping(value = "/admin/count", method = {RequestMethod.POST})
    public BaseDataResp count() {
        BaseDataResp baseDataResp;
        try {
            Long projectAmount = projectInfoService.count();
            baseDataResp = new BaseDataResp("操作成功",projectAmount);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "条件统计(admin)", notes = "根据条件统计项目量")
    @RequestMapping(value = "/admin/countByParams", method = {RequestMethod.POST})
    public BaseDataResp countByParams(ProjectInfoParams projectInfoParams) {
        BaseDataResp baseDataResp;
        try {
            if (null == projectInfoParams)
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            Long projectAmount = projectInfoService.countByParams(projectInfoParams);
            baseDataResp = new BaseDataResp("操作成功",projectAmount);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "获取项目信息(admin)", notes = "获取项目信息")
    @RequestMapping(value = "/admin/getProjectInfo", method = {RequestMethod.POST})
    public BaseDataResp getProjectInfoAdmin(Long projectId) {
        BaseDataResp baseDataResp;
        try {
            ProjectInfo projectInfo = projectInfoService.getProjectInfo(projectId);
            baseDataResp = new BaseDataResp("查询成功",projectInfo);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "更改项目状态(admin)", notes = "更改项目状态")
    @RequestMapping(value = "/admin/resetProjectStatus", method = {RequestMethod.POST})
    public BaseDataResp resetProjectStatus(ProjectInfoParams projectInfoParams) {
        BaseDataResp baseDataResp;
        try {
            if (null == projectInfoParams || null == projectInfoParams.getProjectId() || null == projectInfoParams.getStatus())
                throw new BaseException(BaseExceptionEnum.VALUE_NOT_COMPLETED);
            projectInfoService.updateStatus(projectInfoParams);
            baseDataResp = new BaseDataResp("状态更新成功");
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    //==================2、功能操作接口(对外开放)====================

    @ApiOperation(value = "查询全部项目（状态：2 已上线）", notes = "查询全部项目数据（状态：2 已上线）")
    @RequestMapping(value = "/findAllOnline", method = {RequestMethod.POST})
    public BaseDataResp<List<ProjectInfo>> findAllOnline(HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new BaseException(SESSION_LOGIN_EXPIRED);
            }
            List<ProjectInfo> projectInfoList = projectInfoService.findAll();
            baseDataResp = new BaseDataResp("查询成功", PrivacyUtils.getOnlineProject(projectInfoList));
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }

    @ApiOperation(value = "获取项目信息", notes = "获取项目信息")
    @RequestMapping(value = "/getProjectInfo", method = {RequestMethod.POST})
    public BaseDataResp getProjectInfo(Long projectId,HttpServletRequest request) {
        BaseDataResp baseDataResp;
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new BaseException(SESSION_LOGIN_EXPIRED);
            }
            ProjectInfo projectInfo = projectInfoService.getProjectInfo(projectId);
            baseDataResp = new BaseDataResp("查询成功",projectInfo);
        }catch (Exception e){
            logger.error("【错误信息】"+e.toString());
            baseDataResp = new BaseDataResp(e);
        }
        return baseDataResp;
    }
}
