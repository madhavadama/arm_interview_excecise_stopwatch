
// Copyright (C) 2017, ARM Limited or its affiliates. All rights reserved.
package com.arm.exercise.tokeniser.impl;

import com.arm.exercise.tokeniser.EndOfStreamException;
import com.arm.exercise.tokeniser.Stream;
import com.arm.exercise.tokeniser.TokenReader;


public class TokenReaderImpl implements TokenReader{

	
	@Override
	public String readToken(Stream stream, String startMarker, String endMarker) throws EndOfStreamException {

		//Throw EndOfStreamException if
		// 1. stream is null
		// 2. startMarker is null or empty
		// 3. endMarker is null or empty
		if(stream ==null || startMarker == null || endMarker == null)
			throw new EndOfStreamException();
		
		if(startMarker.isEmpty() || endMarker.isEmpty() )
			throw new EndOfStreamException();
		
		boolean startMarkerMatched = matchStartMarker(stream, startMarker);
		if(startMarkerMatched)
		{	
			String token = matchEndMarker(stream, endMarker);
			return token;
		}
			
		throw new EndOfStreamException();
	}
	
	/**
	 * Returns a true if the startMarker found in the stream. For example, assuming
	 * <code>stream</code> contains <code>"qjfws{start}my string{end}plthrp"</code>, 
	 * <code>matchStartMarker(stream, "{start}")</code> returns <code>true</code>.
	 * @param stream stream of characters to read from
	 * @param startMarker string indicating the start of the token string to be found
	 * @return boolean if found the startMarker in the stream.
	 * @throws EndOfStreamException if the end of the stream is reached before finding the startMarker .
	 */
	protected boolean matchStartMarker(Stream stream, String startMarker) throws EndOfStreamException {
		
		boolean foundToken = false;
		int pos = 0; //Position of character to be matched in startMarker

		//Loop will be terminated in two cases
		// 1. matched the startMarker in stream of characters
		// 2. EndOfStreamException
		while( !foundToken ) {
			
			if(pos < startMarker.length()) {
			
				char c = stream.read();
				
				if(matchChar(pos,c,startMarker))
					//Matching the characters from stream with the startMarker Continuously
					pos++;
				else 
					//Not Matching the characters from stream with the startMarker Continuously
					//Hence resetting the position to start of startMarker
					pos=0;	
			}
			else return true;
		}
		return false;
	}
	
	/**
	 * Returns a string trailed by endMarker, reading from the given stream. For example, assuming
	 * <code>stream</code> contains <code>"my string{end}plthrp"</code>, 
	 * <code>matchEndMarker(stream, "{end}")</code> returns <code>"my string"</code>.
	 * @param stream stream of characters to read from
	 * @param endMarker string indicating the end of the token string to return
	 * @return string trailed by the  endMarker.
	 * @throws EndOfStreamException if the end of the stream is reached before finding the endMarker.
	 */
	
	protected String matchEndMarker(Stream stream, String endMarker) throws EndOfStreamException {
		
		boolean foundToken = false;
		
		StringBuilder token = new StringBuilder();
		StringBuilder tmp = null;
		
		int pos = 0; //Position of character to be matched in endtMarker
		
				
		while( !foundToken ) {
			
			if(pos < endMarker.length()) {
			
				char c = stream.read();
				
				//Create the new StringBuilder tmp 
				//To store the characters partially matched with the endMarker
				if(tmp == null)
					tmp = new StringBuilder();
				
				tmp.append(c);
				
				if(matchChar(pos,c,endMarker)) {
					
					//Matching the characters from stream with the endMarker Continuously 
					pos++;
				}
				else {
					//Not Matched the characters from stream with the endMarker Continuously
					//Hence resetting the position to start of endMarker
					pos=0;
					
					//Append the characters red still this point from the stream to token
					token.append(tmp.toString());
					
					//Reset the tmp to null, so that the next characters go into new StringBuilder
					tmp = null;
				}
				
			}
			else {
				//Found the endMarker in the stream 
				break;
			}
		}
		return token.toString();
	}
	
	/**
	 * Returns a boolean if the marker contains the given character c at the given pos . For example, assuming
	 * <code>marker</code> contains <code>"{start}"</code>, 
	 * <code>matchChar(0,'{', "{start}")</code> returns <code>true</code>.
	 * @param pos int is the position of character to be checked in the marker
	 * @param c char the character to be checked in the marker in given position
	 * @param marker string for matching the given char c at given pos
	 * @return boolean if matched the given character at given position in marker.
	 * @throws EndOfStreamException if the end of the stream is reached before finding the endMarker.
	 */
	protected boolean matchChar(int pos, char c, String marker) {
		
		
		if(pos < marker.length() && marker.charAt(pos) == c)
			return true;
		
		return false;
	}

}
