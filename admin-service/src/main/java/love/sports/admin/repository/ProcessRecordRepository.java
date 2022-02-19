package love.sports.admin.repository;


import love.sports.admin.entity.model.ProcessRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 表名：tb_process_record持久层
 *
 * @author have1568
 * @since 2022-02-18 20:34:24
 */
@Repository
public interface ProcessRecordRepository extends JpaRepository<ProcessRecord, Integer> {
}
