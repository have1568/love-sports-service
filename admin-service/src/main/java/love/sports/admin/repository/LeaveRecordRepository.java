package love.sports.admin.repository;


import love.sports.admin.entity.model.LeaveRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 表名：tb_leave_record持久层
 *
 * @author have1568
 * @since 2022-02-18 20:34:24
 */
@Repository
public interface LeaveRecordRepository extends JpaRepository<LeaveRecord, Integer> {
}
