package love.sports.admin.controller;


import love.sports.admin.entity.model.ApproveTask;
import love.sports.admin.service.ApproveTaskService;
import love.sports.admin.vo.JsonResult;
import love.sports.admin.vo.Resp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description:
 * @author: fengli
 * @date: 2020/8/19 2:12 下午
 */
@RestController
@RequestMapping("/task")
public class ApproveTaskController {

    @Resource
    private ApproveTaskService approveTaskService;

    @GetMapping("/{userId}/todo")
    public JsonResult todo(@PathVariable("userId") Integer userId, Integer page, Integer pageSize) {
        ApproveTask approveTask = new ApproveTask();
        approveTask.setApproveId(userId);
        approveTask.setStatus((byte) 1);

        return Resp.ok(approveTaskService.findAll());
    }

    @GetMapping("/{userId}/done")
    public JsonResult done(@PathVariable("userId") Integer userId, Integer page, Integer pageSize) {
        ApproveTask approveTask = new ApproveTask();
        approveTask.setApproveId(userId);
        approveTask.setStatus((byte) 2);
        return Resp.ok(approveTaskService.findAll());
    }
}
