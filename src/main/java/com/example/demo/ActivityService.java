package com.example.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.tldemo.constants.Constants;
import jp.tldemo.repositories.ActivityRepository;

@Service
public class ActivityService {

	@Autowired
	ActivityRepository repository;

	@Transactional(readOnly=true)
	public Activity searchSuggestion(int cost) {

		Activity activityCheapThanCost;
		Activity activityExpensiveThanCost;

		try {

			// costが一致する暇つぶしを検索対象に入れるため、LessThan/GreaterThanの引数を＋1またはー1する
			var cheap = repository.findByCostLessThanOrderByCostDesc(cost + 1);
			var expensive = repository.findByCostGreaterThanOrderByCostAsc(cost - 1);

			if (cheap.isEmpty() && expensive.isEmpty()) {
				Activity activity = new Activity();
				activity.setCost(-1);
				activity.setTitle(Constants.SERACH_ACTIVITY_FAILED_MESSAGE);
				return activity;
			}

			if (cheap.isEmpty() || expensive.isEmpty()) {
				if (cheap.size() > 0) {
					return cheap.get(0);
				}
				if (expensive.size() > 0) {
					return expensive.get(0);
				}
			}

			activityCheapThanCost = cheap.get(0);
			activityExpensiveThanCost = expensive.get(0);

			 if (cost - activityCheapThanCost.getCost() < activityExpensiveThanCost.getCost() - cost) {
				 return activityCheapThanCost;
			 } else {
				 if ((activityExpensiveThanCost.getCost() * Constants.COST_OVER_BORDER) <= (activityExpensiveThanCost.getCost() - cost)){
					 return activityExpensiveThanCost;
				 } else {
					 return activityCheapThanCost;
				 }
			 }

		} catch (Exception ex){
			Activity activity = new Activity();
			activity.setCost(-1);
			activity.setTitle(Constants.DB_ERROR_MESSAGE);
			return activity;
		}
	}

	@Transactional(readOnly=true)
	public Activity getRandomly() {
		List<Activity> activities = repository.findAll();
		Random rand = new Random();
		int randomIndex = (int) rand.nextInt(activities.size());
		return activities.get(randomIndex);
	}

	@Transactional(readOnly=true)
	public List<String> getNamesForRoulette(int number){
		List<String> nameList = new ArrayList<>();
		repository.findAll().forEach(activity -> nameList.add(activity.getTitle()));
		Collections.shuffle(nameList);
		return (number <= nameList.size()) ? nameList.subList(0, number) : nameList.subList(0, (nameList.size()));
	}
}
