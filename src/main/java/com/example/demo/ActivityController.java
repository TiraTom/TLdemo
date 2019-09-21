package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RestController
public class ActivityController {

	@RequestMapping(value="/activity", method=RequestMethod.GET)
	public ModelAndView Show(ModelAndView mav, @RequestParam(name = "activityId", defaultValue = "") String activityId) {

		if (activityId.isBlank()) {
			// TODO 新規作成画面へ遷移


		} else {
			// TODO アクテビティ詳細の取得

		}

		mav.setViewName("Activity");

		return mav;
	}


	@RequestMapping(value="/activity", method=RequestMethod.DELETE)
	public Boolean Delete(ModelAndView mav, @RequestParam("activityId") String activityId) {
		// TODO　アクティビティ削除
		return true;
	}


	@RequestMapping(value="/activity", method=RequestMethod.POST)
	public Boolean Upsert(@RequestParam(name = "activityId", defaultValue = "") String activityId) {

		if (activityId.isBlank()) {
			// TODO Insert

		} else {
			// TODO Update
		}

		return true;
	}
}
