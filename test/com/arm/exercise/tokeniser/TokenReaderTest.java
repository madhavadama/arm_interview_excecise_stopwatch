package com.arm.exercise.tokeniser;

import static org.junit.Assert.assertNotSame;

import org.junit.Test;

import com.arm.exercise.tokeniser.impl.TokenReaderImpl;

public class TokenReaderTest {
	
	//Negative Test Case, expected EndOfStreamException i.e stream =null
	@Test(expected=EndOfStreamException.class)
	public void testTokeniser001() throws EndOfStreamException
	{
		boolean endOfStream = false;
		Stream stream = null;
		TokenReader tokenReader = new TokenReaderImpl();
				
		while(!endOfStream) {
						
			String token = tokenReader.readToken(stream, "{start}", "{end}");
		}	
	}

	//Negative Test Case, expected EndOfStreamException i.e startMarker=null
	@Test(expected=EndOfStreamException.class)
	public void testTokeniser002() throws EndOfStreamException
	{
		boolean endOfStream = false;
		Stream stream = new StringStream("Test 1234 {start} Sample Token {end}") ;
		TokenReader tokenReader = new TokenReaderImpl();
		
		while(!endOfStream) {
					
			String token = tokenReader.readToken(stream, null, "{end}");
		}	
	}
	
	//Negative Test Case, expected EndOfStreamException , i.e endMarker=null
		@Test(expected=EndOfStreamException.class)
		public void testTokeniser003() throws EndOfStreamException
		{
			boolean endOfStream = false;
			Stream stream = new StringStream("Test 1234 {start} Sample Token {end}") ;
			TokenReader tokenReader = new TokenReaderImpl();
			
			while(!endOfStream) {
						
				String token = tokenReader.readToken(stream, "{start}", null);
			}	
		}
	//Negative Test Case, expected EndOfStreamException i.e. startMarker is empty
	@Test(expected=EndOfStreamException.class)
	public void testTokeniser004() throws EndOfStreamException
	{
		boolean endOfStream = false;
		Stream stream = new StringStream("Test 1234 {start} Sample Token {end}") ;
		TokenReader tokenReader = new TokenReaderImpl();
		
		while(!endOfStream) {
					
			String token = tokenReader.readToken(stream, "", "{end}");
		}	
	}
	//Negative Test Case, expected EndOfStreamException i.e. endMarker is empty
	@Test(expected = EndOfStreamException.class)
	public void testTokeniser005() throws EndOfStreamException {
		boolean endOfStream = false;
		Stream stream = new StringStream("Test 1234 {start} Sample Token {end}");
		TokenReader tokenReader = new TokenReaderImpl();

		while (!endOfStream) {

			String token = tokenReader.readToken(stream, "{start}", "");
		}
	}
	//Negative Test Case, expected EndOfStreamException i.e. stream is empty
	@Test(expected=EndOfStreamException.class)
	public void testTokeniser006() throws EndOfStreamException
	{
		boolean endOfStream = false;
		Stream stream = new StringStream("");
		TokenReader tokenReader = new TokenReaderImpl();
		
		while(!endOfStream) {
				
				String token = tokenReader.readToken(stream, "{start}", "{end}");
		}	
	}
	
	//Negative Test Case, expected EndOfStreamException i.e. No Token
	@Test(expected = EndOfStreamException.class)
	public void testTokeniser007() throws EndOfStreamException {
		boolean endOfStream = false;
		Stream stream = new StringStream(
				"Test 1234 {11start} Sample Token {22end} Test 1234 {33start} Second Token {44end}");
		TokenReader tokenReader = new TokenReaderImpl();

		while (!endOfStream) {

			String token = tokenReader.readToken(stream, "{start}", "{end}");
		}
	}
	//Stream contains 2 tokens
	@Test(expected=EndOfStreamException.class)
	public void testTokeniser008() throws EndOfStreamException
	{
		boolean endOfStream = false;
		Stream stream = new StringStream("Test 1234 {start} Sample Token {end} Test 1234 {start} Second Token {end}");
		TokenReader tokenReader = new TokenReaderImpl();
		String tockens[] = {" Sample Token ", " Second Token "};
		int i=0;
		while(!endOfStream) {

				String token = tokenReader.readToken(stream, "{start}", "{end}");
				assertNotSame(tockens[i], token);
				i++;
		}	
	}
	
	//Empty Token Test i.e. stream is "{start}{end}"
	@Test(expected=EndOfStreamException.class)
	public void testTokeniser009() throws EndOfStreamException
	{
		boolean endOfStream = false;
		Stream stream = new StringStream("{start}{end}");
		TokenReader tokenReader = new TokenReaderImpl();

		while(!endOfStream) {

			
				String token = tokenReader.readToken(stream, "{start}", "{end}");
				assertNotSame("",token);

		}	
	}
	//Stream contains differtn kind of start and end markers and 1 right token
	@Test(expected=EndOfStreamException.class)
	public void testTokeniser010() throws EndOfStreamException
	{
		boolean endOfStream = false;
		Stream stream = new StringStream("Test 1234 {11start} Sample Token {22end} Test 1234 {start} Second Token {end}");
		TokenReader tokenReader = new TokenReaderImpl();
		
		while(!endOfStream) {
				
				String token = tokenReader.readToken(stream, "{start}", "{end}");
				assertNotSame(" Second Token ",token);
			
		}	
	}
	// endMarker before the right token, which is expected to be ingonred by tokenizer
	@Test(expected=EndOfStreamException.class)
	public void testTokeniser011() throws EndOfStreamException
	{
		boolean endOfStream = false;
		Stream stream = new StringStream("Test 1234 {11start} Sample Token {end} Test 1234 {start} Second Token {end}");
		TokenReader tokenReader = new TokenReaderImpl();
		
		while(!endOfStream) {

				String token = tokenReader.readToken(stream, "{start}", "{end}");
				assertNotSame(" Second Token ",token);

		}	
	}
	// Token contains startMarker, which is to be considered as paart token
	@Test(expected=EndOfStreamException.class)
	public void testTokeniser012() throws EndOfStreamException
	{
		boolean endOfStream = false;
		Stream stream = new StringStream("Test 1234 {start} Sample Token {111end} Test 1234 {start} Second Token {end}");
		TokenReader tokenReader = new TokenReaderImpl();
		
		while(!endOfStream) {
				
				String token = tokenReader.readToken(stream, "{start}", "{end}");
				assertNotSame(" Sample Token {111end} Test 1234 {start} Second Token ",token);
		}	
	}
	
	//one endMarker after a successfull first Token, which needs to be ignored
	@Test(expected=EndOfStreamException.class)
	public void testTokeniser013() throws EndOfStreamException
	{
		boolean endOfStream = false;
		Stream stream = new StringStream("Test 1234 {start} Sample Token {end} Test 1234 {111start} Second Token {end}");
		TokenReader tokenReader = new TokenReaderImpl();
		
		while(!endOfStream) {
				
				String token = tokenReader.readToken(stream, "{start}", "{end}");
				assertNotSame(" Sample Token ",token);
		}	
	}
}
