package com.sky.skyserver.util.security;

import com.sky.skyentity.entity.ProjectInfo;
import com.sky.skyentity.entity.UserInfo;

import java.util.Iterator;
import java.util.List;

import static com.sky.skyserver.common.ConstantEnum.PROJECT_STATUS_ONLINE;

public class PrivacyUtils {
    /**
     * 用户信息隐私设置
     *
     * @param userInfo
     * @return
     */
    public static UserInfo privacy(UserInfo userInfo) {
        if (null != userInfo) {
            userInfo.setPassword("******");
            userInfo.setSalt(null);
            userInfo.setUserPid(null);
        }
        return userInfo;
    }

    /**
     * 用户信息隐私设置（List）
     * @param userInfoList
     * @return
     */
    public static List<UserInfo> privacyAll(List<UserInfo> userInfoList) {
        if (null != userInfoList && userInfoList.size() > 0) {
            for (UserInfo userInfo : userInfoList) {
                userInfo.setPassword("******");
                userInfo.setSalt(null);
                userInfo.setUserPid(null);
            }
        }
        return userInfoList;
    }

    /**
     * 过滤已上线应用
     * @param projectInfoList
     * @return
     */
    public static List<ProjectInfo> getOnlineProject(List<ProjectInfo> projectInfoList){
        if (null != projectInfoList && projectInfoList.size()>0){
            Iterator iterator = projectInfoList.iterator();
            while (iterator.hasNext()){
                ProjectInfo next = (ProjectInfo) iterator.next();
                if (PROJECT_STATUS_ONLINE.getIntCode() != next.getStatus())
                    iterator.remove();
            }
        }
        return projectInfoList;
    }
}
