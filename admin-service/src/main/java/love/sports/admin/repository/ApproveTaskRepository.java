package love.sports.admin.repository;


import love.sports.admin.entity.model.ApproveTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 表名：tb_approve_task持久层
 *
 * @author have1568
 * @since 2022-02-18 20:34:21
 */
@Repository
public interface ApproveTaskRepository extends JpaRepository<ApproveTask, Integer> {
}
