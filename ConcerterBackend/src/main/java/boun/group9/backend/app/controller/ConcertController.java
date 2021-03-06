package boun.group9.backend.app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import boun.group9.backend.app.Application;
import boun.group9.backend.app.Application.STATUS;
import boun.group9.backend.app.data.Artists;
import boun.group9.backend.app.data.Attend;
import boun.group9.backend.app.data.Comments;
import boun.group9.backend.app.data.Concerts;
import boun.group9.backend.app.data.Locations;
import boun.group9.backend.app.data.Search;
import boun.group9.backend.app.data.Users;
import boun.group9.backend.app.helper.ConcertOperations;
import boun.group9.backend.app.helper.CommentOperations;
@Controller
public class ConcertController {
	private static STATUS status;
	/*
	@RequestMapping(value="/new-comment",method=RequestMethod.POST)
	public ModelAndView newComment(@ModelAttribute Comments newComment) {
		//newComment.setCommented_by(7);
		System.out.println(newComment.getComment());
		System.out.println(newComment.getConcert_id());
		System.out.println(newComment.getCommented_by());
		String jsonString = Application.gson.toJson(newComment);
		status = CommentOperations.createComment(newComment);
		return new ModelAndView("redirect:/index");
	}
	*/
	@RequestMapping("/concert/{concertID}")
	public String concertPage(@PathVariable("concertID") int concertID,Model model,HttpSession session) {
		Concerts concert = ConcertOperations.getConcert(concertID);
		model.addAttribute("concert",concert);
		model.addAttribute("newComment",new Comments());
		
		model.addAttribute("search",new Search());
		model.addAttribute("loggedInUser",(Users)session.getAttribute("loggedInUser"));
		return "concert";
	}
	@RequestMapping(value="/new-concert",method=RequestMethod.GET)
	public String newConcert(Model model) {
		model.addAttribute("concert",new Concerts());
		return "new-concert";
	}
	@RequestMapping(value="/new-concert",method=RequestMethod.POST)
	public ModelAndView submitNewConcert(@ModelAttribute Concerts concert,HttpSession session) {
		Users user = (Users) session.getAttribute("loggedInUser");
		System.out.println(concert.getName());
		System.out.println(concert.getArtist_name());
		System.out.println(concert.getLocation_name());
		System.out.println(concert.getDate_str());
		System.out.println(concert.getTime_str());
		System.out.println(concert.getMin_price());
		System.out.println(concert.getMax_price());
		System.out.println(concert.getImage_path());
		System.out.println(concert.getLat());
		System.out.println(concert.getLng());
		concert.setCreated_by_id(user.getId());
		status = ConcertOperations.createConcert(concert);
		if(status == STATUS.SUCCESS) {
			return new ModelAndView("redirect:/index");
		}else {
			return new ModelAndView("redirect:/error");
		}
	}
	
	@RequestMapping("/concert/{concertID}/comments")
	public String getAllComments(@PathVariable("concertID") int concertID,Model model) {
		ArrayList<Comments> commentList = ConcertOperations.getAllComments(concertID);
		model.addAttribute("commentList",commentList);
		return "concert";
	}
	@RequestMapping(value="/attend",method=RequestMethod.POST)
	public ModelAndView attendConcert(@ModelAttribute Attend attend,HttpSession session) {
		Users user = (Users)session.getAttribute("loggedInUser");
		Application.STATUS status = ConcertOperations.attendConcert(user,attend.getConcert_id());
		if(status == Application.STATUS.SUCCESS) {
			return new ModelAndView("redirect:/index");
		}else {
			return new ModelAndView("redirect:/error");
		}
	}
	
	
	@RequestMapping(value = "/rateConcert", method=RequestMethod.GET)
    public ModelAndView RateForConcert( @RequestParam(name="concert_id") String concertID ,@RequestParam(name="rate") String rate,  HttpSession session){
		Users user = (Users)session.getAttribute("loggedInUser");
    	int userID = user.getId(); 	
    	int concertid=Integer.parseInt(concertID);
    	int rate_=Integer.parseInt(rate);
		status= ConcertOperations.submitRateForConcert(concertid, rate_);
        System.out.println(status);
        if(status == Application.STATUS.SUCCESS){
        	return new ModelAndView("redirect:/index");
        }else{
            return new ModelAndView("redirect:/error");
        }

    }
	
	//concerts that the user attended is considered.
		@RequestMapping(value = "/concert/{concertID}/{rate}")
		public ModelAndView submitRateForConcert(@PathVariable("concertID") int concertID, @PathVariable("rate") int rate , HttpSession session)
		{
			Users user = (Users)session.getAttribute("loggedInUser");
			int userID = user.getId();
			
			Application.STATUS status= ConcertOperations.submitRateConcert(userID ,concertID, rate);
			if(status == STATUS.SUCCESS) {
				return new ModelAndView("redirect:/concert");
			}else {
				return new ModelAndView("redirect:/error");
			}
		}	


	@RequestMapping(value = "/pastconcerts",method=RequestMethod.GET)
	public String getPastConcerts(Model model) {
		ArrayList<Concerts> concertList;
        concertList = ConcertOperations.getPastConcerts();
		
        for (Concerts oneConcert : concertList) {
			System.out.println(oneConcert.getId());
		}
        
        model.addAttribute("concertList",concertList);
		return "index";
	}
	
	@RequestMapping(value = "/nextconcerts",method=RequestMethod.GET)
	public String getNextConcerts(Model model) {
		ArrayList<Concerts> concertList;
        concertList = ConcertOperations.getNextConcerts();
        
        for (Concerts oneConcert : concertList) {
			System.out.println(oneConcert.getId());
		}
        
        model.addAttribute("concertList",concertList);
		return "index";
	}
	
}
