package hello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.model.Actor;
import hello.model.Film;
import hello.model.Greeting;

@RestController
@RequestMapping("/greet")
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
    private Greeting x;
    
    @RequestMapping("/greeting")
    public Greeting greeting() {
        return x;
    }
    //@RequestMapping("/greeting")
//    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
//        return new Greeting(counter.incrementAndGet(),
//                            String.format(template, name));
//    }
    
    @Autowired
    private EntityManagerFactory em;
	private Object id;
    
    //@RequestMapping(method={RequestMethod.POST, RequestMethod.GET})
	@CrossOrigin(origins= {"*"})
    @PostMapping("/addActor")
    public int addActor(Actor actor) {
    	int hasil = 0;
    	try {
    	
    	//Actor newActor = new Actor();
    	//newActor.setFirstName("Bandung");
    	//newActor.setLastName("Lautan");
    	actor.setLastUpdate(new Date ());
    	
    	EntityManager e = em.createEntityManager();
    	e.getTransaction().begin();
    	e.persist(actor);
    	e.getTransaction().commit();
    	
    	}catch (Exception ex) {
    		System.out.println(ex.getMessage());
    		hasil = 1;
    	}
    	return hasil;
    }
 
	@CrossOrigin(origins= {"*"})
    @PostMapping("/editActor")
    public int editActor(Actor actor) {
    	int hasil = 0;
    	try {
    	
    	EntityManager e = em.createEntityManager();
    	
    	e.getTransaction().begin();
    	
		Actor currentActor = e.find(Actor.class, actor.getActorId());
    	currentActor.setFirstName(actor.getFirstName());
    	currentActor.setLastName(actor.getLastName());
    	currentActor.setLastUpdate(new Date ());
    	
    	    	
    	e.getTransaction().commit();
    	
    	}catch (Exception ex) {
    		System.out.println(ex.getMessage());
    		hasil = 1;
    	}
    	return hasil;
    }
	
	@CrossOrigin(origins= {"*"})
    @GetMapping("/getActor")
    public Actor getActor(@RequestParam("id")Short id) {
		return
				em.createEntityManager().find(Actor.class, id);
    	
	}
    	
    	
    
    
   @CrossOrigin(origins= {"*"})
    @RequestMapping("/actors")
    public List<Actor> allActors(){
    	return em. createEntityManager()
    			.createQuery("from Actor")
    			.getResultList();
    }
   
   @CrossOrigin(origins= {"*"})
   @RequestMapping("/films")
   public List<Film> allFilms(){
      	return em. createEntityManager()
   			.createQuery("from Film")
   			.getResultList();
   }
    @RequestMapping("/data")
    public List<String> dataNegara(@RequestParam("pre") String prefix){
    	List<String> data = new ArrayList();
    	data.add("Indonesia");
    	data.add("Timor Leste");
    	data.add("Singapura");
    	data.add("Brunei");
    	data.add("Malaysia");
    	
    	
    	  	
    	return data.stream()
    			.filter(line->line.contains(prefix))//startsWith(prefix))//contains(prefix))// startsWith(prefix))
    			.collect(Collectors.toList());
    }
    @RequestMapping("/countries")
    public String getCountries() throws IOException {
    	URL url = new URL("http://www.webservicex.net/globalweather.asmx/GetCitiesByCountry?CountryName=German");
    	InputStream stream= url.openConnection().getInputStream();
    	URLConnection connection =url.openConnection();
    	//connection.setDoOutput(true);
    	//connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    	//connection.setRequestProperty("Content-Length", "0");
    	/*bisa juga*/
    	 InputStreamReader reader = new InputStreamReader(stream);
    	  BufferedReader buffer = new BufferedReader(reader);
    	  
    	 
    	String line;
    	StringBuilder builder =  new StringBuilder();
    	while((line=buffer.readLine())!=null)
    	{
    	builder.append(line);	
    	}
    	return builder.toString();
    	//return buffer.lines().collect(Collectors.joining("\n"));
    	//return new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.joining("\n"));
    	
    }
}
