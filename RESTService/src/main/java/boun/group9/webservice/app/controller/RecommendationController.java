package boun.group9.webservice.app.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import boun.group9.webservice.app.Application;
import boun.group9.webservice.app.data.Concerts;
import boun.group9.webservice.helper.RecommendationChecker;

@RestController
public class RecommendationController {
	@RequestMapping(value="recommend/{userID}",method=RequestMethod.POST)
	public String recommend(@PathVariable(value="userID") int userID) {
		ArrayList<Concerts> concertList = RecommendationChecker.recommend(userID);
		return Application.gson.toJson(concertList);
	}
	@RequestMapping(value="recommend2/{userID}",method=RequestMethod.POST)
	public String recommend2(@PathVariable(value="userID") int userID) {
		ArrayList<Concerts> concertList = RecommendationChecker.recommend2(userID);
		return Application.gson.toJson(concertList);
	}
	@RequestMapping(value="recommend3/{userID}",method=RequestMethod.POST)
	public String recommend3(@PathVariable(value="userID") int userID) {
		ArrayList<Concerts> concertList = RecommendationChecker.recommend3(userID);
		return Application.gson.toJson(concertList);
	}
	@RequestMapping(value="recommend4/{userID}",method=RequestMethod.POST)
	public String recommend4(@PathVariable(value="userID") int userID) {
		ArrayList<Concerts> concertList = RecommendationChecker.recommend4(userID);
		return Application.gson.toJson(concertList);
	}
}