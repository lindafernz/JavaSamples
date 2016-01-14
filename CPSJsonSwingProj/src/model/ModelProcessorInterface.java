package model;

import java.util.ArrayList;
import java.util.Map;

public interface ModelProcessorInterface {
	
	public void processSchoolReportsData(String fileName);
	
	public ArrayList<SchoolReportCard> getSchoolReports();

	public Map<String, ArrayList<Integer>> getSchRprtsByMgmtNetwork();
}
