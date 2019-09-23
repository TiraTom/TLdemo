package jp.tldemo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

	public Optional<Activity> findById(Long activityId);

	public List<Activity> findByCostLessThanOrderByCostDesc(int cost);

	public List<Activity> findByCostGreaterThanOrderByCostAsc(int cost);
}
