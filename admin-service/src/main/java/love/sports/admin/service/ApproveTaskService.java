package love.sports.admin.service;
    
    



import java.lang.Integer;

import lombok.extern.slf4j.Slf4j;
import love.sports.admin.entity.model.ApproveTask;
import love.sports.admin.repository.ApproveTaskRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;


/**
 * 表名：tb_approve_task业务层
 *
 * @author have1568
 * @since 2022-02-18 20:34:23
 */
@Slf4j
@Service
public class ApproveTaskService {

	@Resource
    private ApproveTaskRepository approveTaskRepository;

    @Transactional
    public ApproveTask save(ApproveTask approveTask) {
       return approveTaskRepository.save(approveTask);
    }

 
    @Transactional
    public boolean deleteById(Integer id) {
        Optional<ApproveTask> optional = approveTaskRepository.findById(id);
        if (optional.isPresent()) {
            ApproveTask entity= optional.get();
            entity.setDelFlag(true);
            return true;
        }
        return false;
    }
    
    public void delete(Integer id) {
        approveTaskRepository.deleteById(id);
    }

    @Transactional
    public boolean update(ApproveTask approveTask, Integer id) {
        Assert.notNull(approveTask, "数据不存在");
        boolean exist = approveTaskRepository.existsById(id);
        if (!exist) {
            return false;
        }
        approveTaskRepository.save(approveTask);
        return true;
    }

    public ApproveTask findById(Integer id) {
        Assert.notNull(id, "查询Id不能为空");
        return approveTaskRepository.findById(id).orElse(null);
    }
 

    public Page<ApproveTask> findByCondition(ApproveTask approveTask,Pageable page) {
        if (approveTask == null) {
            return approveTaskRepository.findAll(page);
        }
        Example<ApproveTask> example = Example.of( approveTask);
        return approveTaskRepository.findAll(example,page);
    }

    public List<ApproveTask> findAll() {
      return   approveTaskRepository.findAll();
    }
}

