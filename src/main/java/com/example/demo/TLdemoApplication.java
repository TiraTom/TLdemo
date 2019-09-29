package com.example.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.tldemo.constants.Constants;

@SpringBootApplication
@Controller
@EnableJpaRepositories("jp.tldemo.repositories")
public class TLdemoApplication {

	@Autowired
	ActivityService activityService = new ActivityService();

	public static void main(String[] args) {
		SpringApplication.run(TLdemoApplication.class, args);
	}

	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView index(ModelAndView mav) {
		mav.setViewName("Index");

		List<String> activities = activityService.getNamesForRoulette(Constants.ROULETTE_CANDIDATE_NUMBER);
		mav.addObject("activityNames", activities);
		return mav;
	}

	@RequestMapping(value="/suggestActivityAjax", method=RequestMethod.POST)
	@ResponseBody
	public String suggestActivityForAjax(@RequestParam String budget) {

		int budgetInt;
		Activity activity;

		if (budget.isEmpty()) {
			// 予算入力がない場合は、ランダムに出す
			activity = activityService.getRandomly();

		} else {
			try {
				budgetInt = Integer.parseInt(budget);
				activity = activityService.searchSuggestion(budgetInt);
			} catch (NumberFormatException ex) {
				activity = new Activity();
				activity.setCost(-1);
				activity.setTitle(Constants.ACTIVITY_SEARCH_CONDITION_INVALID);
			}
		}
		Map<String, String> result = new HashMap<>();
		result.put("title", activity.getTitle());
		result.put("cost", String.valueOf(activity.getCost()));

		try {
			return new ObjectMapper().writeValueAsString(result);
		} catch (JsonProcessingException e) {
			return "";
		}

	}
}
