package com.example.jmim.opengljustin02;

/**
 * Created by JMIM on 6/21/2015.
 */
public class ShaderLog
{

	private static ShaderLog ourInstance = new ShaderLog();

	public static ShaderLog getInstance() {
		return ourInstance;
	}

	private ShaderLog() {
	}

	public static int compileFlag = 0;
}
