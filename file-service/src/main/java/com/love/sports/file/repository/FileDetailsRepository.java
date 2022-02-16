package com.love.sports.file.repository;


import com.love.sports.file.entity.model.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 持久层
 *
 * @author have1568
 * @since 2022-02-13 16:39:02
 */
@Repository
public interface FileDetailsRepository extends JpaRepository<FileDetails, String> {
}
