package hello;

import static org.assertj.core.api.Assertions.filter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.omg.CORBA_2_3.portable.InputStream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    @RequestMapping("/data")
    
    public List<String> dataNegara(
    		@RequestParam("pre")String prefix){
    	List<String> data = new ArrayList<>();
    	data.add("Indonesia");
    	data.add("Malaysia");
    	data.add("Brunei");
    	data.add("Timor Leste");
    	
    	return data.stream()
    	 .filter(line -> 
    	// line.startsWith(prefix))
    	 line.contains(prefix))
    	 .collect(Collectors.toList());
    			
    	//return data;
    }
    	
    @RequestMapping("/countries")
    public String getCountries() throws MalformedURLException{
    	URL url = new URL("http://www.webservicex.net/country.asmx/GetCountries?");
    	
    	URLConnection connectiion = url.openConnection();
    	connection.setDoOutput (true);
    	
    	InputStream stream = url.openConnection().getInputStream();
    	InputStreamReader reader = new InputStreamReader(stream);
    	BufferedReader buffer = new BufferedReader(reader);
    	
    	String line;
    	StringBuilder builder = new StringBuilder();
    	while((line = buffer.readLine())!= null) {
    		builder.append(line);
    	}
    	
    	
    	
    }
    
    

     
}
