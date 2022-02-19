package love.sports.admin.controller;


import lombok.extern.slf4j.Slf4j;
import love.sports.admin.utils.ImageUtil;
import love.sports.admin.vo.JsonResult;
import love.sports.admin.vo.ProcessDeploymentVo;
import love.sports.admin.vo.Resp;
import org.flowable.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @Description:
 * @author: fengli
 * @date: 2020/7/24 2:11 下午
 */
@RestController
@RequestMapping("/api/flow")
@Slf4j
public class FlowableController {

    @Value("${file.diagram.path}")
    private String diagramPath;

    @Resource
    private RepositoryService repositoryService;

    @PostMapping("/deploy")
    public JsonResult deploy(@RequestBody ProcessDeploymentVo deployment) {
        String xmlFileName = deployment.getResourceName() + ".bpmn20.xml";
        String pngFileName = deployment.getResourceName() + ".png";
        repositoryService.createDeployment().
                addBytes(xmlFileName, deployment.getXml().getBytes(StandardCharsets.UTF_8))
                .addBytes(pngFileName, deployment.getSvg().getBytes(StandardCharsets.UTF_8))

                .name(deployment.getProcessName())
                .key(deployment.getProcessKey())
                .deploy();

        return Resp.ok("流程发布成功");
    }


}
