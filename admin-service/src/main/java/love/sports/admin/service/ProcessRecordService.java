package love.sports.admin.service;
    
    



import java.lang.Integer;

import lombok.extern.slf4j.Slf4j;
import love.sports.admin.entity.model.ProcessRecord;
import love.sports.admin.repository.ProcessRecordRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Optional;


/**
 * 表名：tb_process_record业务层
 *
 * @author have1568
 * @since 2022-02-18 20:34:24
 */
@Slf4j
@Service
public class ProcessRecordService {

	@Resource
    private ProcessRecordRepository processRecordRepository;

    @Transactional
    public ProcessRecord save(ProcessRecord processRecord) {
       return processRecordRepository.save(processRecord);
    }

 
    @Transactional
    public boolean deleteById(Integer id) {
        Optional<ProcessRecord> optional = processRecordRepository.findById(id);
        if (optional.isPresent()) {
            ProcessRecord entity= optional.get();
            entity.setDelFlag(true);
            return true;
        }
        return false;
    }
    
    public void delete(Integer id) {
        processRecordRepository.deleteById(id);
    }

    @Transactional
    public boolean update(ProcessRecord processRecord, Integer id) {
        Assert.notNull(processRecord, "数据不存在");
        boolean exist = processRecordRepository.existsById(id);
        if (!exist) {
            return false;
        }
        processRecordRepository.save(processRecord);
        return true;
    }

    public ProcessRecord findById(Integer id) {
        Assert.notNull(id, "查询Id不能为空");
        return processRecordRepository.findById(id).orElse(null);
    }
 

    public Page<ProcessRecord> findByCondition(ProcessRecord processRecord,Pageable page) {
        if (processRecord == null) {
            return processRecordRepository.findAll(page);
        }
        Example<ProcessRecord> example = Example.of( processRecord);
        return processRecordRepository.findAll(example,page);
    }
}

