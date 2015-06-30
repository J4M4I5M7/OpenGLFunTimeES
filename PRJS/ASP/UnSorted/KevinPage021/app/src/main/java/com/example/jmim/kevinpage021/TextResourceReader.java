package com.example.jmim.kevinpage021;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by JMIM on 6/29/2015.
 */
public class TextResourceReader
{
	/**
	 *
	 * @param context
	 * @param resourceID
	 * @return
	 */
	public static String readTextFileFromResource
	(Context context, int resourceID)
	{
		/** The body of text we are building up. **/
		StringBuilder body = new StringBuilder();

		//Page 37 and 38:
		try
		{
			InputStream inputStream =
			context.getResources()
			.openRawResource(resourceID);

			InputStreamReader inputStreamReader =
			new InputStreamReader(inputStream);

			BufferedReader bufferedReader =
			new BufferedReader(inputStreamReader);

			String nextLine;

			while((nextLine = bufferedReader.readLine()) != null)
			{
				body.append(nextLine);
				body.append("\n");
			}//INF LOOP
		}
		catch(IOException e)
		{
			throw new RuntimeException(
			"could not open resource:" + resourceID, e);
		}
		catch(Resources.NotFoundException nfe)
		{
			throw new RuntimeException
			("Resource not found:" + resourceID, nfe);
		}

		String op = body.toString();

		//linux style line endings could be your answer?
		//http://stackoverflow.com/questions
		// /3776923/how-can-i-normalize-the-eol-character-in-java
		op = op.replaceAll("\\r\\n", "\n");
		op = op.replaceAll("\\r", "\n");

		return op;
	}//end function.
}//end class.
