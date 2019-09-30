package com.raphahes.starwars.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.raphahes.starwars.model.commons.exceptions.EntityNotFoundException;
import com.raphahes.starwars.model.services.MovieService;

/**
 * @author RaphaelHespanhol
 * Presentation Layer - Converts and response on JSON format
 */
@RestController
@RequestMapping(value = "api/jdtest")
public class MovieController {
	
	private final MovieService service;

	@Autowired
	public MovieController(MovieService service) {
		this.service = service;
	}
	
	/*****************************************************************
	 * Films Calls
	 *****************************************************************/
	@GetMapping(params = { "film_id", "character_id", "page" })
	public Object findByFilmIdAndCharacterId(@RequestParam("film_id") int film_id,
											 @RequestParam("character_id") int character_id,
											 @RequestParam(value = "page", required = false ) String page,
											 RedirectAttributes redir) {
		List<String> retFilm = service.findByFilm(film_id,character_id);
		
		if(null == retFilm)
			throw new EntityNotFoundException("Not found the film id: " + film_id);
		
		if (null != page) {
			ModelAndView mv = new ModelAndView(); 
			mv.setViewName("redirect:/");
			redir.addFlashAttribute("characters", retFilm);
			return mv;
		} else {
			return retFilm;	
		}
	}
}
