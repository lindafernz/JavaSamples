package model;

import java.util.ArrayList;
import java.util.Map;

public class SchoolMgmtNetworkTreeModel {
	
	ModelProcessorInterface mp = null;
	
	public SchoolMgmtNetworkTreeModel() {}

	public ArrayList<SchoolReportCard> getAllSchoolReports()
	{
		if(mp != null)
		{
			return mp.getSchoolReports();
		}
		
		return null;
	}
	
	public Map<String, ArrayList<Integer>> getSchRprtsByMgmtNetwork()
	{
		if(mp != null)
		{
			return  mp.getSchRprtsByMgmtNetwork();
		}
		
		return null;
	}
	
	public Map<String, ArrayList<Integer>> constructTree(String fileName) {
		
		mp = new JsonModelProcessor(new ArrayList<SchoolReportCard>());
		
		// process 
		mp.processSchoolReportsData(fileName);

		Map<String, ArrayList<Integer>> treeNodesMap = mp.getSchRprtsByMgmtNetwork();
	
		return treeNodesMap;

	}
}
