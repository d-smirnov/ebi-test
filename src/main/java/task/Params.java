package task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.beust.jcommander.Parameter;

public class 
Params 
{
	@Parameter( description = "Comma-separated list of accessions. E.g. ACC001, ACC002", required = true )
	public List<String> accession_list = new ArrayList<>();
	
	public static List<String> 
	splitParamsByComma( List<String> param )
	{
		return param.stream().map( e -> Arrays.asList( e.split( ", *" ) ) ).flatMap( e-> e.stream() ).filter( e -> null != e && e.length() > 0 ).collect( Collectors.toList() );
	}
}
