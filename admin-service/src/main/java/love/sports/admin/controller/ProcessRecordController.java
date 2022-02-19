package love.sports.admin.controller;

import love.sports.admin.entity.model.ProcessRecord;
import love.sports.admin.service.ProcessRecordService;
import love.sports.admin.utils.ImageUtil;
import love.sports.admin.vo.JsonResult;
import love.sports.admin.vo.Resp;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Description:
 * @author: fengli
 * @date: 2020/8/14 10:18 上午
 */
@RestController
@RequestMapping("/api/process")
public class ProcessRecordController {

    @Value("${file.diagram.path}")
    private String diagramPath;

    @Resource
    private ProcessRecordService processRecordService;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @PostMapping("/buffer")
    public JsonResult<Integer> buffer(@RequestBody ProcessRecord processRecord) {
        processRecordService.save(processRecord);
        return Resp.ok();
    }

    /**
     * 删除流程定义
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}/remove")
    public JsonResult<Integer> remove(@PathVariable("id") Integer id) {
        processRecordService.deleteById(id);
        return Resp.ok();
    }

    /**
     * 流程发布
     *
     * @param id
     * @return
     */
    @PutMapping("/{id}/deploy")
    public JsonResult deploy(@PathVariable("id") Integer id) {
        ProcessRecord processRecord = new ProcessRecord();
        processRecord.setId(id);
        processRecord.setDelFlag(false);
        ProcessRecord processRecordDB = processRecordService.findById(processRecord.getId());

        String xmlFileName = processRecordDB.getProcId() + ".bpmn20.xml";
        String pngFileName = processRecordDB.getProcId() + ".png";
        File xmlFile = new File(diagramPath + xmlFileName);
        File pngFile = new File(diagramPath + pngFileName);
        ImageUtil.convertToPng(processRecordDB.getSvgStr(), pngFile);

        InputStream xmlInputStream = null;
        InputStream pngInputStream = null;
        try {
            FileCopyUtils.copy(processRecordDB.getXmlStr().getBytes(), xmlFile);
            xmlInputStream = new FileInputStream(xmlFile);
            pngInputStream = new FileInputStream(pngFile);
        } catch (IOException e) {
        }
        repositoryService.createDeployment().
                addInputStream(xmlFileName, xmlInputStream)
                .addInputStream(pngFileName, pngInputStream)
                .name(processRecordDB.getName())
                .key(processRecordDB.getProcId())
                .deploy();
        processRecord.setStatus((byte) 1);
        processRecordService.update(processRecord, id);
        return Resp.ok();
    }

    @GetMapping("/list")
    public JsonResult<Page<ProcessRecord>> pageQuery(Integer page, Integer pageSize) {

        Page<ProcessRecord> recordPage = processRecordService.findByCondition(null, PageRequest.of(0, 10));
        return Resp.ok(recordPage);
    }

    @GetMapping("/inst/list")
    public JsonResult<List<ProcessInstance>> definitionQuery() {
        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().list();
        return Resp.ok(processInstances);
    }
}
