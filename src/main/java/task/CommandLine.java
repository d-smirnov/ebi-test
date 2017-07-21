package task;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;


public class 
CommandLine 
{
	private static final int EXIT_ERROR = 1;
	
	public static void 
	main( String args[] )
	{
		Params p = new Params();
		JCommander jc = new JCommander( p );
		try
		{
			jc.parse( args );
			System.out.println( AccessionStream.getRangeList( Params.splitParamsByComma( p.accession_list ) ) ); 
		} catch( ParameterException pe )
		{
			jc.usage();
			System.exit( EXIT_ERROR );
		}
	}
}
