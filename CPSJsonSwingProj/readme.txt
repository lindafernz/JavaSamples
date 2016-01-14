
Feature: 
================
Display a list of school management networks and the schools managed by them.

Design Decisions:
===================

- The application presents a tree view of the school management networks and a quick snapshot of the details of the schools managed by that network.  

- It is designed as a java application using SWING components to design a basic UI to present this data.
See an overview of the JSON data below.

- The application reads in a JSON document, parses it, constructs the views to be presented in the UI. 

- The application constructs a data model using the field names from the input, and mapping them to the actual data values.
There is no hard-coding of the names of the fields into a rigid data model class.
For the purpose of creating a UI, the appropriate fields from the data are translated into the view.
Thus, new views can be added very easily to modify the filter(tree) and the details as required by the end-user.

- The user will be able to navigate the list using previous/next buttons for each school management network.

Run the application:
======================
Type on command line:
java -jar cpsjsonswingproj.jar

About the data:
=================
Using a public dataset available at :
https://catalog.data.gov/dataset/chicago-public-schools-elementary-school-progress-report-card-2012-2013-ee91e

Chicago Public Schools – Elementary School Progress Report Card (2012-2013)
Metadata Updated: Apr 09, 2015 

This dataset shows the 2012 School Progress Report Card data elements for each CPS elementary school.
The report card is an annual summary of how the school is doing

Input data file:
==================
data/cpsreportcards.json

The JSON input doc contains column names and its metadata in the meta->view->columns object.
And it contains the actual data of the school reports in meta->data array.

See a snapshot of the json file below:
{
  "meta" : {
    "view" : { ...
    	"columns" : [ {...}, ...
    		 ,{
		        "id" : 118417652,
		        "name" : "School ID",
		        "dataTypeName" : "number",
		        "fieldName" : "school_id", ...}, ...
		  ],
		  
	  },
	  "data" :  [ [ 1, "8ED2EAC9-4A0D-4C88-A21B-33CC32452BA9", 1, 1385493083, 
	  				"700397", 1385493083, "700397", "{\n}", "609772", "Addams",
	  				"Jane Addams Elementary School", "10810 S Avenue H", "Chicago",
	  				"IL", "60617", "(773) 535-6210", [ "http://www.jaddams.org", null ],
	  				"Lake Calumet Elementary Network", "Lake Calumet Elementary Network",
	  				"Standard", "Level 1", "No", "0", "NWEA", "Above Average",
	  				"69.70", "48.40", "50.00", "69.10", "81.00", "56.50", "62.50",
	  				"60.60", "66.30", "70.50", "92.60", "78.50", "74.80", "92.20",
	  				"78.90", "Average", "48.40", "35.60", "33.30", "54.30", "55.00",
	  				"56.80", "57.50", "49.00", "35.60", "20.70", "57.10", "52.50",
	  				"50.50", "67.60", "47.70", null, "60.10", null, null, "57.30",
	  				"60.40", null, "78.40", null, null, "73.30", "79.00", "Well-organized",
	  				"Strong", "Neutral", "Neutral", "Strong", "Strong", "Neutral", "73.90",
	  				"61.30", "3.4", "2.2", "96", "95.30", "96.80", "96.90", "No", "No", null,
	  				"1202811.359652669", "1833818.906231676", "-87.53301251", "41.69871497",
	  				[ null, "-87.53301251", "41.69871497", null, false ] ],
	  				[...] ...
	  			]
}
 

Assumptions:
=================
1. In the JSON doc, the field names (meta->view->columns->fieldName) are unique.
2. The JSON file has not been cleansed/modified/updated. We assume that the input is a well-formed json document and meets the basic structure as shown in the above snapshot.


