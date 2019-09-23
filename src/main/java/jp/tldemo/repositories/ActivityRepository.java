package jp.tldemo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

	Optional<Activity> findById(Long activityId);
}
