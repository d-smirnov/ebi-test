package task;


import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class 
AccessionStream
{
    public final static String SYNID = "#NOT#MATCHED#";
    public final static String SYNID_MASK = "";
    public final static String MASK = "0";

    
    private static String[] 
    match_split( String s )
    {
        Matcher m = null;
        return ( m = Pattern.compile( "(\\D+)(\\d+)" ).matcher( s ) ).find() && 2 == m.groupCount() ? new String[] { m.group( 1 ), m.group( 2 ) } 
                                                                                                    : new String[] { SYNID, s }; 
    }
    
    
    private static List<SimpleEntry<String, String>>
    entry_reduce( List<SimpleEntry<String, String>> a, List<SimpleEntry<String, String>> e )
    {
        if( !a.isEmpty() && 1 >= -Integer.parseInt( a.get( a.size() - 1 ).getValue(), 10 ) + Integer.parseInt( e.get( 0 ).getValue(), 10 ) )
            a.get( a.size() - 1 ).setValue( e.get( 0 ).getValue() );
        else
            a.addAll( e );
        return a;
    }
    

    static Map<Entry<String, String>, List<String>>
    getRanges( Collection<String> accession_list )
    {
        return
        accession_list.stream()
                      .collect( Collectors.groupingBy( e -> new SimpleEntry<String, String> ( match_split( e )[ 0 ], Stream.generate( () -> MASK ).limit( e.length() ).collect( Collectors.joining() ) ),
                                                       Collectors.mapping( e -> match_split( e )[ 1 ],
                                                                            Collectors.toList() ) ) )
                      .entrySet()
                      .stream()
                      .map( ( e ) -> SYNID == e.getKey().getKey() ? new SimpleEntry<Entry<String, String>, List<String>>( new SimpleEntry<String, String> ( SYNID, SYNID_MASK ), 
                                                                                                                          e.getValue()
                                                                                                                           .stream()
                                                                                                                           .sorted()
                                                                                                                           .collect( Collectors.toList() ) )
                                                                                                                            
                                                                  : new SimpleEntry<Entry<String, String>, List<String>>( e.getKey(), 
                                                                                                                          e.getValue()
                                                                                                                           .stream()
                                                                                                                           .sorted( ( o1, o2 ) -> Integer.compare( Integer.parseInt( o1, 10 ), Integer.parseInt( o2, 10 ) ) )
                                                                                                                           .map( i -> Arrays.asList( new SimpleEntry<String, String>( i, i ) ) )
                                                                                                                           .reduce( new ArrayList<>(), AccessionStream::entry_reduce )
                                                                                                                           .stream()
                                                                                                                           .map( i -> i.getKey() == i.getValue() ? e.getKey().getKey() + i.getKey() : e.getKey().getKey() + i.getKey() + '-' + e.getKey().getKey() + i.getValue() )
                                                                                                                           .collect( Collectors.toList() ) ) )
                       .collect( Collectors.toMap( i -> i.getKey(), i -> i.getValue(), 
                                                   ( v1, v2 ) -> { v1.addAll( v2 ); return v1.stream().sorted().collect( Collectors.toList() ); } ) );
    }

    
    public static List<String>
    getRangeList( Collection<String> accession_list )
    {
        return getRanges( accession_list ).entrySet()
                                          .stream()
                                          .sorted( ( e1, e2 ) -> e1.getKey().toString().compareTo( e2.getKey().toString() ) )
                                          .map( e -> e.getValue() )
                                          .flatMap( List::stream )
                                          .collect( Collectors.toList() );
    }
    
    
}
/*
A00000, A00000, A00001, A0001, A0003, ERR000111, ERR000112, ERR000113, ERR000115, ERR000116, ERR100114, ERR200000001, ERR200000002, ERR200000003, DRR2110012, SRR211001, ABCDEFG1
A00000,A00000,A00001,A0001,A0003,ERR000111,ERR000112,ERR000113,ERR000115,ERR000116,ERR100114,ERR200000001,ERR200000002,ERR200000003,DRR2110012,SRR211001,ABCDEFG1
*/