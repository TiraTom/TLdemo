package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.tldemo.constants.Constants;

@SpringBootApplication
@Controller
@EnableJpaRepositories("jp.tldemo.repositories")
public class TLdemoApplication {

	@Autowired
	ActivityService activitiService = new ActivityService();

	public static void main(String[] args) {
		SpringApplication.run(TLdemoApplication.class, args);
	}

	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView index(ModelAndView mav) {
		mav.setViewName("Index");
		return mav;
	}

	@RequestMapping(value="/suggestActivity", method=RequestMethod.POST)
	public String suggestActivity(ModelAndView mav
										, String budget
										, RedirectAttributes redirectAttributes) {

		int budgetInt;
		Activity activity;

		try {
			budgetInt = Integer.parseInt(budget);
			activity = activitiService.search(budgetInt);
			redirectAttributes.addFlashAttribute("budget", budgetInt);
		} catch (NumberFormatException ex) {
			activity = new Activity();
			activity.setCost(-1);
			activity.setTitle(Constants.ACTIVITY_SEARCH_CONDITION_INVALID);
		}

		redirectAttributes.addFlashAttribute("activity", activity);

		return "redirect:";
	}

}
