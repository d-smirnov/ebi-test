package task;

import spark.Spark;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

public class 
WebLine 
{
	  public static void 
	  main( String[] args ) 
	  {
		  Spark.port( 4567 );
		  Spark.get( "/*", ( req, res ) -> {
			  Params p = new Params();
			  JCommander jc = new JCommander( p );
			  try
			  {
				  jc.parse( req.pathInfo().substring( 1 ) ); 
				  return AccessionStream.getRangeList( Params.splitParamsByComma( p.accession_list ) ).toString();
			  } catch( ParameterException pe )
			  {
				  StringBuilder sb = new StringBuilder();
				  jc.usage( sb );
				  return sb.toString();
			  }
		  } );
	  }
}
