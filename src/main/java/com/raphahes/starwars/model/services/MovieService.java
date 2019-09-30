package com.raphahes.starwars.model.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.raphahes.starwars.model.commons.utils.StringUtil;
import com.raphahes.starwars.model.commons.utils.URLCertsUtil;
import com.raphahes.starwars.model.entities.Films;
import com.raphahes.starwars.model.entities.People;
import com.raphahes.starwars.model.entities.Species;

/**
 * @author RaphaelHespanhol
 * Business Layer where we will validate the information
 */
@Service
public class MovieService {
	
	private static final Logger log = LoggerFactory.getLogger(MovieService.class);
	
	@Value("${service.swapi.films}")
	private String swapiFilms;
	
	@Value("${service.swapi.people}")
	private String swapiPeople;
	
	private List<String> characters = null;
	
	/*****************************************************************
	 * Films Calls
	 *****************************************************************/
	public List<String> findByFilm(int film_id, 
							 	   int character_id) {
		
		String callF = openConnection(swapiFilms.concat(String.valueOf(film_id)));
		
		if (!StringUtil.isEmpty(callF)) {
			Films film = new Gson().fromJson(callF, Films.class);
			log.info("Film title: "+film.getTitle());
			this.characters = film.getCharacters();
			
			if (character_id > 0) {
				String callP = openConnection(swapiPeople.concat(String.valueOf(character_id)));
				
				if (!StringUtil.isEmpty(callP)) {
					People people = new Gson().fromJson(callP, People.class);
					log.info("People name: "+people.getName());
					
					String swapiSpecies = "";
					for(String specieURL: people.getSpecies())
						swapiSpecies = specieURL;
					
					if (!StringUtil.isEmpty(swapiSpecies)) {
						String callS = openConnection(swapiSpecies);
						
						if (!StringUtil.isEmpty(callS)) {
							Species species = new Gson().fromJson(callS, Species.class);
							log.info("Species name: "+species.getName());
							return findAllCharactersBySpecie(species.getName());
						}
					}
				}
	        }
		}
		
		return null;
	}
	
	private List<String> findAllCharactersBySpecie(String specie){
		ArrayList<String> speciesList = new ArrayList<String>();
		
		for(String characterURL : characters) {
			String callP = openConnection(characterURL);
			
			if (!StringUtil.isEmpty(callP)) {
				People people = new Gson().fromJson(callP, People.class);
				for(String specieURL : people.getSpecies()) {
					String callS = openConnection(specieURL);
					
					if (!StringUtil.isEmpty(callS)) {
						Species species = new Gson().fromJson(callS, Species.class);
						if (species.getName().equalsIgnoreCase(specie)) {
							speciesList.add(people.getName());
							log.info("Person added: "+people.getName());
						}
					}
				}
			}
		}		
		
		return speciesList;
	}
	
	private static String openConnection(String swapiURL) {
		HttpURLConnection con = null;
		try {
			//Accepting all Hosts
			URLCertsUtil.trustAllHosts();
			
			//Creating a Request
			URL url = new URL(swapiURL);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			//Setting Request Headers
			con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			con.setRequestProperty("Content-Type", "application/json");
			con.addRequestProperty("Accept", "application/json");
			
			//Setting Timeouts
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			
			//Setting the User Interaction
			con.setUseCaches(false);
			con.setAllowUserInteraction(false);
			
			//Connecting
			con.connect();
			
			//Getting the status code
	        int status = con.getResponseCode();
	        
			if (status > 299)
				log.error("URL error, see the details: " +con.getErrorStream());
			else
				return readResponse(con);
			
		} catch (IOException e) {
			e.printStackTrace();
			log.error("General error: "+e.getMessage());
		} finally {
			try {
				//Closing the connection
				if (con != null) con.disconnect();
			} catch (Exception e) {}
		}
		return null;
	}
	
	private static String readResponse(HttpURLConnection con) throws IOException {
        BufferedReader bfr = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder stb = new StringBuilder();
        String line;
        while ((line = bfr.readLine()) != null) {
            stb.append(line+"\n");
        }
        bfr.close();
        
        return stb.toString();
    }
}
