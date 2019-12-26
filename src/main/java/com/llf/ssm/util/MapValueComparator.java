package com.llf.ssm.util;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;


public class MapValueComparator implements Comparator<Map.Entry<String, NlpObj>>{	

	@Override
	public int compare(Entry<String, NlpObj> o1, Entry<String, NlpObj> o2) {
		// TODO Auto-generated method stub
		NlpObj obj1 = o1.getValue();
		NlpObj obj2 = o2.getValue();
		return obj2.getSum().compareTo(obj1.getSum());
	}
}
