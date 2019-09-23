package com.example.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.tldemo.repositories.ActivityRepository;

@Controller
public class ActivityController {

	@Autowired
	ActivityRepository repository;


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
								,@ModelAttribute Activity activity) {

		Optional<Activity> activityObj = repository.findById(activityId);
		if (activityObj.isPresent()) {
			activity = activityObj.get();
			mav.setViewName("ActivityEdit");
			mav.addObject("activity", activity);
		} else {
			// データが存在しない場合は一覧に遷移する
			mav.setViewName("Activities");
		}

		return mav;
	}


	@RequestMapping(value="/activity/{activityId}", method=RequestMethod.DELETE)
	@Transactional(readOnly=false)
	public ModelAndView delete(ModelAndView mav, @PathVariable("activityId") Long activityId) {

		// TODO 画面上で削除確認ボタン->削除結果をモーダルで表示

		try {
			repository.deleteById(activityId);
			return showAll(mav);
		} catch (Exception ex) {
			return showAll(mav);
		}
	}


	@RequestMapping(value="/activity", method=RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView upsert(@ModelAttribute Activity activity, ModelAndView mav) {
		try {
			repository.saveAndFlush(activity);
			return showAll(mav);

		} catch (Exception ex) {
			// TODO DB更新で例外が発生した場合に処理を分ける
			repository.saveAndFlush(activity);
			return showAll(mav);
		}
	}
}
