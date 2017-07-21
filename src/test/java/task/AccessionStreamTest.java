package task;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

public class 
AccessionStreamTest 
{
	@Test public void
    testRanges()
    {
        List<String> list = Arrays.asList( "00000", "0001", "ERR000111", "ERR000112", "ERR200000001", "ERR200000002", 
                                           "ERR200000003", "DRR211001", "SRR211001", "ABCDEFG" );
        
        Map<Entry<String, String>, List<String>> result = AccessionStream.getRanges( list );
        System.out.println( result );
        
        //keys
        Assert.assertEquals( 5, result.size() );
        Assert.assertTrue( result.keySet().stream().filter( e -> e.getKey().equals( AccessionStream.SYNID ) ).findFirst().isPresent() );
        Assert.assertTrue( result.keySet().stream().filter( e -> e.getKey().equals( "ERR" ) && Stream.generate( () -> AccessionStream.MASK ).limit( 9 ).collect( Collectors.joining() ).equals( e.getValue() ) ).findFirst().isPresent() );
        Assert.assertTrue( result.keySet().stream().filter( e -> e.getKey().equals( "ERR" ) && Stream.generate( () -> AccessionStream.MASK ).limit( 12 ).collect( Collectors.joining() ).equals( e.getValue() ) ).findFirst().isPresent() );
        Assert.assertTrue( result.keySet().stream().filter( e -> e.getKey().equals( "SRR" ) ).findFirst().isPresent() );
        Assert.assertTrue( result.keySet().stream().filter( e -> e.getKey().equals( "DRR" ) ).findFirst().isPresent() );
        
        //ranges and orders
        Assert.assertEquals( 1, result.get( new SimpleEntry<String, String>( "ERR", Stream.generate( () -> AccessionStream.MASK ).limit( 9 ).collect( Collectors.joining() ) ) ).size() );
        Assert.assertTrue( result.get( new SimpleEntry<String, String>( "ERR", Stream.generate( () -> AccessionStream.MASK ).limit( 9 ).collect( Collectors.joining() ) ) ).get( 0 ).matches( "ERR000111.{1,}ERR000112" ) );
        Assert.assertEquals( 1, result.get( new SimpleEntry<String, String>( "ERR", Stream.generate( () -> AccessionStream.MASK ).limit( 12 ).collect( Collectors.joining() ) ) ).size() );
        Assert.assertTrue( result.get( new SimpleEntry<String, String>( "ERR", Stream.generate( () -> AccessionStream.MASK ).limit( 12 ).collect( Collectors.joining() ) ) ).get( 0 ).matches( "ERR200000001.{1,}ERR200000003" ) );

        Assert.assertEquals( 1, result.get( new SimpleEntry<String, String>( "SRR", Stream.generate( () -> AccessionStream.MASK ).limit( 9 ).collect( Collectors.joining() ) ) ).size() );
        Assert.assertEquals( 1, result.get( new SimpleEntry<String, String>( "DRR", Stream.generate( () -> AccessionStream.MASK ).limit( 9 ).collect( Collectors.joining() ) ) ).size() );
        
        Assert.assertEquals( 3, result.get( new SimpleEntry<String, String>( AccessionStream.SYNID, AccessionStream.SYNID_MASK ) ).size() );
        
        Assert.assertTrue( result.get( new SimpleEntry<String, String>( AccessionStream.SYNID, AccessionStream.SYNID_MASK ) ).get( 0 ).matches( "00000" ) );
        Assert.assertTrue( result.get( new SimpleEntry<String, String>( AccessionStream.SYNID, AccessionStream.SYNID_MASK ) ).get( 1 ).matches( "0001" ) );
        Assert.assertTrue( result.get( new SimpleEntry<String, String>( AccessionStream.SYNID, AccessionStream.SYNID_MASK ) ).get( 2 ).matches( "ABCDEFG" ) );
        
        System.out.println( AccessionStream.getRangeList( list ) );    
        
        List<String> accessionList = Arrays.asList( "A00000",
        											"A00000",
                                                    "A00001",
                                                    "A0001",
                                                    "A0003",
                                                    "ERR000111",
                                                    "ERR000112",
                                                    "ERR000113",
                                                    "ERR000115",
                                                    "ERR000116",
                                                    "ERR100114",
                                                    "ERR200000001",
                                                    "ERR200000002",
                                                    "ERR200000003",
                                                    "DRR2110012",
                                                    "SRR211001",
                                                    "ABCDEFG1" );

       List<String> accessionRangeList = AccessionStream.getRangeList( accessionList );

       System.out.println( accessionRangeList );

       Assert.assertEquals( Arrays.asList( "A0001", 
                                           "A0003", 
                                           "A00000-A00001",
                                           "ABCDEFG1",
                                           "DRR2110012",
                                           "ERR000111-ERR000113",
                                           "ERR000115-ERR000116",
                                           "ERR100114",
                                           "ERR200000001-ERR200000003",
                                           "SRR211001" ), accessionRangeList );
    }
}
