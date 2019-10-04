package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

	List<String> infoMessages;
	List<String> errorMessages;

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

		// activities画面に渡すメッセージリストの初期化
		infoMessages = new ArrayList<String>();
		errorMessages = new ArrayList<String>();

		Optional<Activity> activityObj = repository.findById(activityId);
		if (activityObj.isPresent()) {
			activity = activityObj.get();
			mav.setViewName("ActivityEdit");
			mav.addObject("activity", activity);
		} else {
			// データが存在しない場合は一覧に遷移する
			infoMessages.add(Constants.ACTIVITY_NOT_FOUND_MESSAGE);
			mav.addObject("errorMessages", errorMessages);
			return showAll(mav);
		}

		return mav;
	}


	@RequestMapping(value="/activity/{activityId}", method=RequestMethod.DELETE)
	@Transactional(readOnly=false)
	public ModelAndView delete(ModelAndView mav
								, @PathVariable("activityId") Long activityId
								, RedirectAttributes redirectAttributes) {

		// activities画面に渡すメッセージリストの初期化
		infoMessages = new ArrayList<String>();
		errorMessages = new ArrayList<String>();

		try {
			repository.deleteById(activityId);
			infoMessages.add(Constants.DELETE_SUCCESS_MESSAGE);
			mav.addObject("infoMessages", infoMessages);
		} catch (Exception ex) {
			infoMessages.add(Constants.DB_ERROR_MESSAGE);
			mav.addObject("errorMessages", errorMessages);
		}
		return showAll(mav);
	}


	@RequestMapping(value="/activity", method=RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView upsert(@ModelAttribute @Validated Activity activity
								, ModelAndView mav
								, BindingResult result
								, RedirectAttributes redirectAttributes) {

		// activities画面に渡すメッセージリストの初期化
		infoMessages = new ArrayList<String>();
		errorMessages = new ArrayList<String>();

		if (result.hasErrors()) {
			infoMessages = Arrays.asList(Constants.VALIDATION_ERROR);
			// TODO エラーごとにメッセージを出し分ける（SpringBootの機能を利用する）
			infoMessages = Arrays.asList(Constants.VALIDATION_ERROR_TITLE_LENGTH);
			infoMessages = Arrays.asList(Constants.VALIDATION_ERROR_COST);
			mav.addObject("errorMessages", errorMessages);

			mav.setViewName("redirect://");
			return mav;

		}


		if (activity.getTitle().isBlank()) {
			return showAll(mav);
		}

		try {
			repository.saveAndFlush(activity);
			infoMessages = Arrays.asList(Constants.SAVE_SUCCESS_MESSAGE);
			mav.addObject("infoMessages", infoMessages);

		} catch (Exception ex) {
			infoMessages = Arrays.asList(Constants.DB_ERROR_MESSAGE);
			mav.addObject("errorMessages", errorMessages);
		}

		return showAll(mav);
	}

}
