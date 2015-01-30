package com.xe;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import com.core.general.loader.DynamicClassLoader;
import com.core.general.loader.PackageScanner;
import com.xe.annotation.Describle;
import com.xe.tool.data.MyField;
import com.xe.tool.impl.ExcelTool;
import com.xe.tool.impl.XmlTool;
import com.xe.xml.DefDataTool;

public class XETool {

	public static void start(String path, boolean hasType) throws Exception {
		Set<Class<?>> classSet = PackageScanner.scanPackages(path, true, new DynamicClassLoader());
		TreeMap<String, List<MyField>> treeMapXML = new TreeMap<String, List<MyField>>();
		TreeMap<String, List<MyField>> treeMapExcel = new TreeMap<String, List<MyField>>();
		for (Class<?> claz : classSet) {
			// if (BaseDef.class.isAssignableFrom(claz)) {
			if (claz.getAnnotation(Describle.class) == null) {
				continue;
			}
			// xml信息
			try {
				List<MyField> filedsXml = XmlTool.fieldOne(claz);
				treeMapXML.put(claz.getName(), filedsXml);
				// excel信息
				List<MyField> filedsExcel = XmlTool.getFieldDesc(claz);
				String desc_clazz = DefDataTool.getClassDescrible(claz);
				treeMapExcel.put(desc_clazz + "=" + claz.getName(), filedsExcel);
			} catch (Exception e) {
				System.out.println("error defClass: " + claz);
				e.printStackTrace();
			}
			// }
		}

		// XmlTool.convertToXml(treeMapXML, hasType);
		ExcelTool.converToExcel(treeMapExcel);
	}

}
