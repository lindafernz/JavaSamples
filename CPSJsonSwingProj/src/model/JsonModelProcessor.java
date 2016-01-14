package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonStructure;
import javax.json.JsonValue;

public class JsonModelProcessor implements ModelProcessorInterface {

	public ArrayList<SchoolReportCard> schDetails = null;

	private ArrayList<String[]> allFieldNames = new ArrayList<>();

	private int keyPos = 0;
	
	public JsonModelProcessor(ArrayList<SchoolReportCard> srpt) {		
		schDetails = srpt;
	}
	

	private String[] getNextKey()
	{
		String[] retVal = null;
		
		if(allFieldNames != null && keyPos < allFieldNames.size())
		{
			keyPos++;
			retVal = allFieldNames.get(keyPos-1);
		}
		else
		{
			keyPos = 1;
			retVal = allFieldNames.get(keyPos-1);
		}
		
		return retVal;
		
	}
	
	private void processJsonStructure(JsonValue tree, String key)
	{
	   switch(tree.getValueType())
	   {
	      case OBJECT:
//	         System.out.println("OBJECT");
	         JsonObject object = (JsonObject) tree;
	         for (String name : object.keySet())
	         {
	        	 //process the "view" element that contains the field names
	        	 if(name.equals("view"))
	        	 {
	        		 processView(object.getJsonObject("view"));
	        	 }
	        	 // process the "data" element that contains the actual data rows
	        	 else if(name.equals("data"))
	        	 {
	        		 processData(object.get(name), name);
	        	 }
	        	 else
	        	 {
	        		 processJsonStructure(object.get(name), name);
	        	 }
	         }
	    	  
	    	  break;
	      case ARRAY:
		  case STRING:
	      case NUMBER:
	      case TRUE:
	      case FALSE:
	      case NULL:
	         System.err.println(tree.getValueType().toString());
	         break;
	      default:
    	    throw new IllegalStateException("Unhandled valueType: " + tree.getValueType());
    	   
	   }
		      
	}

	private void processView(JsonObject obj)
	{		
		 JsonArray columnsArr = obj.getJsonArray("columns");
		 
		 for(JsonValue col : columnsArr)
		 {
			 JsonObject colObj = (JsonObject)col;
			 
			 //find the column name - fieldName
			 String fieldNameKey  = colObj.getString("fieldName");

			 if(fieldNameKey != null)
			 {
				 String[] colNames = null;
				 
				 JsonArray subColsArr  = colObj.getJsonArray("subColumnTypes");
				 if(subColsArr != null)
				 {
					// update the list so that we have an ordered list of all fields for later use
					 colNames = new String[subColsArr.size() + 1];
					 // at index 0 - store the parent field name
					 colNames[0] = fieldNameKey;
					 int i = 1;
					 
					 for(JsonValue subCol : subColsArr)
					 {
						 colNames[i] = new String(subCol.toString());
						 i++;
					 }

					 allFieldNames.add(colNames);
				 }
				 else
				 {
					 // update the list so that we have an ordered list of all fields for later use
					 colNames = new String[1];
					 colNames[0] = fieldNameKey;
					 allFieldNames.add(colNames);
				 }
			 }
		 }
	}
	
	private void processData(JsonValue tree, String key)
	{		
		if(tree.getValueType() == JsonValue.ValueType.ARRAY)
		{
	         JsonArray array = (JsonArray) tree;
	         for (JsonValue val : array)
	         {
	     		if(val.getValueType() == JsonValue.ValueType.ARRAY)
	     		{
	     			SchoolReportCard srpt = new SchoolReportCard(allFieldNames.size());
	     			
	     			processDataRow(val, srpt);
	     			
	     			schDetails.add(srpt);
	     		}
	         }
	         
	         schDetails.trimToSize();
		}
	}
	

	private void processDataRow(JsonValue tree, SchoolReportCard srpt)
	{
         JsonArray row = (JsonArray) tree;
         for (JsonValue val : row)
         {
        	 String[] colNameKeys = getNextKey();
        	 
        	 if(colNameKeys != null && colNameKeys.length > 0)
        	 {	        	 
	        	 switch(val.getValueType())
		      	   {
		      	      case OBJECT:		      	    	  
		      	    	  break;
		      	      case ARRAY:
		      	    	  // in this case, we have sub columns - index 0 has the parent column name, followed by the others

		      	    	  ArrayReportedVal arval = new ArrayReportedVal();
		      	    	  int i = 1;
						 
		      	    	  JsonArray subColsArr = (JsonArray)val;
		      	    	  
		      	    	  for(JsonValue subCol : subColsArr)
		      	    	  {
		      	    		  if(subCol != null)
		      	    		  {
		      	    			  arval.addSubColumn(colNameKeys[i], subCol.toString());
		      	    		  }
		      	    		  
		      	    		  i++;
		      	    	  }
		      	    	  
		      	    	  srpt.addColumn(colNameKeys[0], arval);
		      	    	  
		      	    	  break;
		      		  case STRING:
		      			  srpt.addColumn(colNameKeys[0], 
		      					  new SimpleStringReportedVal(((JsonString) val).getString()));
		      	    	  break;
		      			  
		      	      case NUMBER:	
		      	    	  srpt.addColumn(colNameKeys[0], 
		      					  new SimpleStringReportedVal(((JsonNumber)val).toString()));
		      	    	  break;
		      	      case TRUE:
		      	      case FALSE:
		      	      case NULL:
		      	    	  srpt.addColumn(colNameKeys[0], 
		      					  new SimpleStringReportedVal(val.getValueType().toString()));
		      	         break;
		      	    default:
		      	    	throw new IllegalStateException("Unhandled valueType: " + val.getValueType());
		      	   }// end switch
        	 }
         }// end for
		
	}
	

	public void processSchoolReportsData(String fileName)
	{		
		try (JsonReader reader = Json.createReader(new FileReader(fileName))) {
			
			JsonStructure jsonst = reader.read();
			
			if(jsonst != null)
			{
				processJsonStructure(jsonst, null);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalStateException ie) {
			ie.printStackTrace();
		}
	}

	public ArrayList<SchoolReportCard> getSchoolReports()
	{
		return schDetails;
	}
	

	public Map<String, ArrayList<Integer>> getSchRprtsByMgmtNetwork()
	{
		// filter out the schools by the network that manages them 
		LinkedHashMap<String, ArrayList<Integer>> retVal = new LinkedHashMap<>();
				
		for(int i=0; i < schDetails.size(); i++)
		{
			String nwkName = (String)schDetails.get(i)
								.getColumnValue(ModelConstants.MANAGED_BY_NWK_FIELD_NAME)
								.getReportedVal();
			if(retVal.containsKey(nwkName))
			{
				retVal.get(nwkName).add(i);
			}
			else
			{
				ArrayList<Integer> al = new ArrayList<>();
				al.add(i);
				
				retVal.put(nwkName, al);
				
			}
		}
		
		return retVal;
	}
}
