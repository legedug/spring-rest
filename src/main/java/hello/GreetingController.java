package hello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
    
    
    //@RequestMapping("/actors")
    @RequestMapping("/films")
    public List<Film> allFilms(){
    //public List<Actor> allActors(){
    	return em. createEntityManager()
    			.createQuery("from Film")
    			//.createQuery("from Actor")
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
