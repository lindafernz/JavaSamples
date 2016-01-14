package view;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import model.ModelConstants;
import model.SchoolMgmtNetworkTreeModel;
import model.SchoolReportCard;

public class AppPanel extends JPanel 
		implements TreeSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 102030405061L;
    
    // get the model
    private SchoolMgmtNetworkTreeModel nwkTreeModel =  new SchoolMgmtNetworkTreeModel();
    private JTree nwkNameTree;

    // reference to the school details UI
	private SchoolUIPanel schoolDetailsPanel = null;
    
	public AppPanel() {		

        super(new GridLayout(1,0));
        
        //Create the nodes.
        DefaultMutableTreeNode top = createNodes();

        //Create a tree that allows one selection at a time
        nwkNameTree = new JTree(top);
        nwkNameTree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        //Listen for when the selection changes
        nwkNameTree.addTreeSelectionListener(this);
        
        //Create the scroll pane and add the tree to it
        JScrollPane treeView = new JScrollPane(nwkNameTree);

        //Create the school details panel
        schoolDetailsPanel = new SchoolUIPanel(nwkTreeModel.getAllSchoolReports());

        //Add the scroll panes to a split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(treeView);
        splitPane.setRightComponent(schoolDetailsPanel);

        Dimension minimumSize = new Dimension(100, 50);
        schoolDetailsPanel.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(250); 
        splitPane.setPreferredSize(new Dimension(500, 300));

        //Add the split pane to this panel.
        add(splitPane);
	}


	private DefaultMutableTreeNode createNodes() {
		
		DefaultMutableTreeNode top = 
	            new DefaultMutableTreeNode(ModelConstants.MANAGED_BY_NWK_FIELD_NAME.toUpperCase());
		
		// load the json file and process it
		Map<String, ArrayList<Integer>> nwkSchoolMap = nwkTreeModel.constructTree("data/cpsreportcards.json");
		
		if( nwkSchoolMap != null )
		{
			nwkSchoolMap.keySet().stream()
								.forEach((nwkName) -> {
									top.add(new DefaultMutableTreeNode(nwkName));	
								});
		}
		
		return top;
	}


	@Override
	public void valueChanged(TreeSelectionEvent arg0) {

        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                           nwkNameTree.getLastSelectedPathComponent();

        if (node == null) return;

        Object nodeInfo = node.getUserObject();
        if (node.isLeaf()) {
            if(nwkTreeModel != null)
            {
            	Map<String, ArrayList<Integer>> nwkSchoolMap = nwkTreeModel.getSchRprtsByMgmtNetwork();
            	
        		if( nwkSchoolMap != null )
        		{
        			ArrayList<Integer> schIndexes = nwkSchoolMap.get((String)nodeInfo);
        			
        			if(schIndexes != null && schIndexes.size() > 0)
        			{
        				ArrayList<SchoolReportCard> schList = nwkTreeModel.getAllSchoolReports();

        				if(schList != null)
        				{
        					int[] temp = schIndexes.stream().mapToInt(i -> i).toArray();
        					
        					int firstSchIdx = schIndexes.get(0);
        					
        					schoolDetailsPanel.populateSchoolData(
        							schList.get(firstSchIdx).getSchool(),temp);
        				}
        			}
        			

        		}
            	
            }
        }
        else {
        	schoolDetailsPanel.populateSchoolData(null, null);
        }
		
	}
	
}
