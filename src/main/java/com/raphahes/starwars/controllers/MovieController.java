package com.raphahes.starwars.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	@GetMapping(params = { "film_id" })
	public List<String> findByFilmId(@RequestParam("film_id") int film_id) {
		List<String> retFilm = service.findByFilm(film_id,0);
		
		if(null == retFilm)
			throw new EntityNotFoundException("Not found the film id: " + film_id);
		
		return retFilm;
	}
	
	@GetMapping(params = { "film_id", "character_id" })
	public List<String> findByFilmIdAndCharacterId(@RequestParam("film_id") int film_id,
												   @RequestParam("character_id") int character_id) {
		List<String> retFilm = service.findByFilm(film_id,character_id);
		
		if(null == retFilm)
			throw new EntityNotFoundException("Not found the film id: " + film_id);
		
		return retFilm;
	}
}
