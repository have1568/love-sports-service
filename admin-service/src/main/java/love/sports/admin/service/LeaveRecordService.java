package love.sports.admin.service;
    
    



import java.lang.Integer;

import lombok.extern.slf4j.Slf4j;
import love.sports.admin.entity.model.LeaveRecord;
import love.sports.admin.repository.LeaveRecordRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Optional;


/**
 * 表名：tb_leave_record业务层
 *
 * @author have1568
 * @since 2022-02-18 20:34:24
 */
@Slf4j
@Service
public class LeaveRecordService {

	@Resource
    private LeaveRecordRepository leaveRecordRepository;

    @Transactional
    public LeaveRecord save(LeaveRecord leaveRecord) {
       return leaveRecordRepository.save(leaveRecord);
    }

 
    @Transactional
    public boolean deleteById(Integer id) {
        Optional<LeaveRecord> optional = leaveRecordRepository.findById(id);
        if (optional.isPresent()) {
            LeaveRecord entity= optional.get();
            entity.setDelFlag(true);
            return true;
        }
        return false;
    }
    
    public void delete(Integer id) {
        leaveRecordRepository.deleteById(id);
    }

    @Transactional
    public boolean update(LeaveRecord leaveRecord, Integer id) {
        Assert.notNull(leaveRecord, "数据不存在");
        boolean exist = leaveRecordRepository.existsById(id);
        if (!exist) {
            return false;
        }
        leaveRecordRepository.save(leaveRecord);
        return true;
    }

    public LeaveRecord findById(Integer id) {
        Assert.notNull(id, "查询Id不能为空");
        return leaveRecordRepository.findById(id).orElse(null);
    }
 

    public Page<LeaveRecord> findByCondition(LeaveRecord leaveRecord,Pageable page) {
        if (leaveRecord == null) {
            return leaveRecordRepository.findAll(page);
        }
        Example<LeaveRecord> example = Example.of( leaveRecord);
        return leaveRecordRepository.findAll(example,page);
    }
}

