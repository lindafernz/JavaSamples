package model;

import java.util.LinkedHashMap;
import java.util.Map;

class ArrayReportedVal extends BaseReportedValue
{	
	Map<String, String> subColumns = new LinkedHashMap<>();
	
	public ArrayReportedVal() {}
	
	@Override
	public Object getReportedVal()
	{
		return subColumns;
	}
	
	public boolean addSubColumn(String k, String v)
	{
		if(subColumns != null && k != null && v != null
				&& !"".equals(k)
				&& subColumns.get(k) == null )
		{
			subColumns.put(k, v);
			return true;
		}
		
		return false;
	}
	
	public String getSubColumnValue(String k)
	{
		if(subColumns != null && k != null && !"".equals(k)
				&& subColumns.get(k) != null )
		{
			return subColumns.get(k);
		}
		
		return "";
	}
}