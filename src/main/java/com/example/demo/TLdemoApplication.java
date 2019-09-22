package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.tldemo.repositories.ActivityRepository;

@SpringBootApplication
@Controller
@EnableJpaRepositories("jp.tldemo.repositories")
public class TLdemoApplication {

	@Autowired
	ActivityRepository activityRepository;

	public static void main(String[] args) {
		SpringApplication.run(TLdemoApplication.class, args);
	}

	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView index(ModelAndView mav) {
		mav.setViewName("Index");
		return mav;
	}

	@RequestMapping(value="/activities", method=RequestMethod.GET)
	public ModelAndView ShowAllActivities(ModelAndView mav) {
		mav.setViewName("Activities");
		Iterable<Activity> activityList = activityRepository.findAll();
		mav.addObject("activityList", activityList);
		return mav;
	}




}
