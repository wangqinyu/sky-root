package com.sky.skyadapterkidcrm;

import com.sky.skyadapterkidcrm.job.TaskJob;
import com.sky.skyadapterkidcrm.service.inter.JobService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SkyAdapterKidcrmApplicationTests {

    @Autowired
    private JobService jobService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void addNewJob() throws Exception {
        String jobClassName = TaskJob.class.getName();//"com.sky.skyadapterkidcrm.job.TaskJob";
        String jobGroupName = "任务组-001";
        String cronExpression = "0 0 1 * * ?";
        String jobDescription = "生产任务-动态任务";
       /* Map<String, Object> params = new HashMap<>();
        params.put("张三", "13999999999");
        System.out.println(cronExpression);*/
        jobService.addJob(jobClassName, jobGroupName, cronExpression, jobDescription, new HashMap<>());
    }

    @Test
    public void delJob() throws Exception {
        String jobClassName = TaskJob.class.getName();//"com.sky.skyadapterkidcrm.job.TaskJob";
        String jobGroupName = "任务组-001";
        jobService.removeJob(jobClassName, jobGroupName);
    }
}
