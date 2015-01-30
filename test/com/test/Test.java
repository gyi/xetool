package com.test;

import java.util.List;
import java.util.Map;

import com.xe.xml.DefDataTool;

public class Test {

	public static void main(String[] args) throws Exception {
		Map<String, List<Object>> dataMap = DefDataTool.instance().getBaseObjectMap();
	}

}