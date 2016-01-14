package model;

import java.util.LinkedHashMap;
import java.util.Map;


public class SchoolReportCard {

	private LinkedHashMap<String, BaseReportedValue> allData = null;
	
	public SchoolReportCard(int numOfKeys) {
		// TODO Auto-generated constructor stub
		allData = new LinkedHashMap<>(numOfKeys);
	}

	public boolean addColumn(String k,  BaseReportedValue v)
	{			
		
		if(allData != null && k != null && !"".equals(k) )
		{
			allData.put(k, v);
			
			return true;
		}
		
		return false;
	}
	

	public BaseReportedValue getColumnValue(String k)
	{
		if(allData != null && k != null && !"".equals(k) )
		{
			return allData.get(k);
		}
		
		return null;
		
	}
	
	public School getSchool()
	{
		School retVal = new School();
		
		retVal.setName((String)getColumnValue(ModelConstants.SCHOOL_NAME).getReportedVal());
		retVal.setStreetAddress((String)getColumnValue(ModelConstants.STREET_ADDRESS).getReportedVal());
		retVal.setCity((String)getColumnValue(ModelConstants.CITY).getReportedVal());
		retVal.setPhoneNumber((String)getColumnValue(ModelConstants.PHONE_NUMBER).getReportedVal());
		retVal.setOverallRating((String)getColumnValue(ModelConstants.OVERAL_RATING).getReportedVal());
		
		return retVal;
	}
	
	private void printOrderedElems()
	{
		for( Map.Entry<String, BaseReportedValue> entry :  allData.entrySet())
		{
			System.out.print("key=" + entry.getKey() );
			
			if(entry.getValue() != null)
			{
				System.out.print(" value=" + entry.getValue().getReportedVal());
			}
			
			System.out.println("");
			
		}
		
	}
}
