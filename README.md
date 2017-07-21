Programming task
--

- Please create a Java 8 project using standard maven directory layout.
- Please build your project using gradle or maven.
- Please provide instructions in README.md file formatted using GitHub markdown syntax.
- Please unit test the functionality.
- Please provide a command line endpoint which accepts a list of accession numbers as input and 
  returns an output as described below.
- Optional: please provide a web endpoint using an embedded web server which accepts a list of 
  accession numbers as input and returns an output as described below.
- Please implement the following functionality preferably using the Java Stream API: 

You are given a list of comma separated accession numbers as input.

An accession number has the following format: L...LN...N (e.g. AB1234)

where L...L denotes one or more ASCII7 letters and N denotes one or more digits (0,1,2,3,4,5,6,7,8 or 9).

Please return an ordered list of accession numbers where any consecutive digits N...N that share the same prefix L...L  
are collapsed into an accession number range.

An accession number range has the following format : L...LN...N-L...LN...N (e.g. A00001-A00005).

Please note that the N...N digits are left padded using 0s (e.g. 00001) and that the length of the N...N digits must be the same
for accession numbers to be part of the same accession number range.

Example input:
A00000, A0001, ERR000111, ERR000112, ERR000113, ERR000115, ERR000116, ERR100114, 
ERR200000001, ERR200000002, ERR200000003, DRR2110012, SRR211001, ABCDEFG1

Expected output:
A0001, A00000, ABCDEFG1, DRR2110012, ERR000111-ERR000113,
ERR000115-ERR000116, ERR100114, ERR200000001-ERR200000003, SRR211001

Launch
-
 
git clone https://github.com/d-smirnov/ebi-test.git

Compile:

	cd ebi-test
	mvn package


Command line endpoint:

	java -cp target\ebi-test-0.0.1-SNAPSHOT-jar-with-dependencies.jar task.CommandLine COMMA_SEPARATED_ACCESSION_LIST

Web endpoint:

	run WebLine:
		java -cp target\ebi-test-0.0.1-SNAPSHOT-jar-with-dependencies.jar task.WebLine
		
	then open in browser: 
		http://localhost:4567/COMMA_SEPARATED_ACCESSION_LIST


Dmitriy Smirnov