package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.tldemo.constants.Constants;
import jp.tldemo.repositories.ActivityRepository;

@Controller
public class ActivityController {

	@Autowired
	ActivityRepository repository;

	List<String> messages;

	@RequestMapping(value="/activities", method=RequestMethod.GET)
	public ModelAndView showAll(ModelAndView mav) {
		mav.setViewName("Activities");
		Iterable<Activity> activityList = repository.findAll();
		mav.addObject("activityList", activityList);
		return mav;
	}

	@RequestMapping(value="/activity", method=RequestMethod.GET)
	public ModelAndView showNew(ModelAndView mav
							, @ModelAttribute("activity") Activity activity) {
		mav.setViewName("ActivityNew");
		mav.addObject("activity", activity);
		return mav;
	}

	@RequestMapping(value="/activity/{activityId}", method=RequestMethod.GET)
	public ModelAndView showEdit(ModelAndView mav
								,@PathVariable(name = "activityId", required = false) Long activityId
								,@ModelAttribute Activity activity
								, RedirectAttributes redirectAttributes) {

		messages = new ArrayList<String>();

		Optional<Activity> activityObj = repository.findById(activityId);
		if (activityObj.isPresent()) {
			activity = activityObj.get();
			mav.setViewName("ActivityEdit");
			mav.addObject("activity", activity);
		} else {
			// データが存在しない場合は一覧に遷移する
			messages.add(Constants.ACTIVITY_NOT_FOUND_MESSAGE);
			redirectAttributes.addFlashAttribute("messages", messages);
			return showAll(mav);
		}

		return mav;
	}


	@RequestMapping(value="/activity/{activityId}", method=RequestMethod.DELETE)
	@Transactional(readOnly=false)
	public ModelAndView delete(ModelAndView mav
								, @PathVariable("activityId") Long activityId
								, RedirectAttributes redirectAttributes) {

		messages = new ArrayList<String>();

		// TODO 画面上で削除確認ボタン->削除結果をモーダルで表示

		try {
			repository.deleteById(activityId);
			messages.add(Constants.DELETE_SUCCESS_MESSAGE);
		} catch (Exception ex) {
			messages.add(Constants.DB_ERROR_MESSAGE);
		}
		redirectAttributes.addFlashAttribute("message", messages);
		return showAll(mav);
	}


	@RequestMapping(value="/activity", method=RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView upsert(@ModelAttribute Activity activity
								, ModelAndView mav
								, RedirectAttributes redirectAttributes) {

		if (activity.getTitle().isBlank()) {
			return showAll(mav);
		}

		messages = new ArrayList<String>();

		try {
			repository.saveAndFlush(activity);
			messages = Arrays.asList(Constants.SAVE_SUCCESS_MESSAGE);

		} catch (Exception ex) {
			messages = Arrays.asList(Constants.DB_ERROR_MESSAGE);
		}

		redirectAttributes.addFlashAttribute("messages", messages);
		return showAll(mav);
	}
}
