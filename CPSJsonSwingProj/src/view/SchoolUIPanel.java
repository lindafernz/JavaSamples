package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.ModelConstants;
import model.School;
import model.SchoolReportCard;


public class SchoolUIPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 102030405063L;
	private JTextField name;
	private JTextField street_address;
	private JTextField city;
	private JTextField phone_number;
	private JTextField overall_rating;
	private JButton btnPrevious;
	private JButton btnNext;
	
	SchoolUIState schUIState = new SchoolUIState();
	
	// data model reference
	ArrayList<SchoolReportCard> schReportCardList = null;
	
	IvjEventHandler schoolUIEventHandler = new IvjEventHandler();
	private JPanel panel;

	class IvjEventHandler implements java.awt.event.ActionListener, java.beans.PropertyChangeListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (e.getSource() == getPreviousButton())
			{
				actionPreviousButton();
			}
			if (e.getSource() ==  getNextButton())
			{
				actionNextButton();
			}
			
			updatePrevNextBtnStates();
		};
		public void propertyChange(java.beans.PropertyChangeEvent evt) {
			if (evt.getSource() == SchoolUIPanel.this
					&& (evt.getPropertyName().equals(ModelConstants.SCHOOL_UI_PROPERTY_NAME))) 
			{
				setSchoolData((School)evt.getNewValue());
				updatePrevNextBtnStates();
			}
		};
		
		private void updatePrevNextBtnStates()
		{
			if(schUIState != null)
			{
				setBtnNextState(schUIState.hasMoreThanCurrentElements());
				setBtnPreviousState(schUIState.hasLessThanCurrentElements());
			}
		}
	};
	
	class SchoolUIState
	{
		public int[] schIndices = null; 
		public int currIdxInSchIndices = -1;
		
		public boolean incrState()
		{
			boolean hasMoreElems = false;
			
			if(schIndices != null && (currIdxInSchIndices + 1) < schIndices.length )
			{
				currIdxInSchIndices++;
				hasMoreElems = true;
			}
			
			return hasMoreElems;
		}
		
		public boolean decrState()
		{
			boolean hasMoreElems = false;
			
			if(schIndices != null && (currIdxInSchIndices - 1) >= 0)
			{
				currIdxInSchIndices--;
				hasMoreElems = true;
			}
			
			return hasMoreElems;
		}
		
		public boolean hasMoreThanCurrentElements()
		{
			return (schIndices != null) &&
					((currIdxInSchIndices + 1) < schIndices.length);
		}

		public boolean hasLessThanCurrentElements()
		{
			return (schIndices != null) &&
					((currIdxInSchIndices - 1) >= 0);
		}
		
		
	}
	
	/**
	 * Create the panel.
	 */
	public SchoolUIPanel(ArrayList<SchoolReportCard> al) {
		setForeground(Color.WHITE);
		setBackground(Color.WHITE);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
		addPropertyChangeListener(schoolUIEventHandler);
		
		schReportCardList = al;
		
		// 
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0};
		gridBagLayout.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel label = new JLabel("Name");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		add(label, gbc_label);
		
		name = new JTextField();
		name.setEditable(false);
		name.setColumns(10);
		GridBagConstraints gbc_name = new GridBagConstraints();
		gbc_name.insets = new Insets(0, 0, 5, 0);
		gbc_name.fill = GridBagConstraints.HORIZONTAL;
		gbc_name.gridx = 1;
		gbc_name.gridy = 0;
		add(name, gbc_name);
		
		JLabel label_1 = new JLabel("Street Address");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.anchor = GridBagConstraints.WEST;
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 1;
		add(label_1, gbc_label_1);
		
		street_address = new JTextField();
		street_address.setEditable(false);
		street_address.setColumns(10);
		GridBagConstraints gbc_street_address = new GridBagConstraints();
		gbc_street_address.insets = new Insets(0, 0, 5, 0);
		gbc_street_address.fill = GridBagConstraints.HORIZONTAL;
		gbc_street_address.gridx = 1;
		gbc_street_address.gridy = 1;
		add(street_address, gbc_street_address);
		
		JLabel label_2 = new JLabel("City");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.anchor = GridBagConstraints.WEST;
		gbc_label_2.gridx = 0;
		gbc_label_2.gridy = 2;
		add(label_2, gbc_label_2);
		
		city = new JTextField();
		city.setEditable(false);
		city.setColumns(10);
		GridBagConstraints gbc_city = new GridBagConstraints();
		gbc_city.insets = new Insets(0, 0, 5, 0);
		gbc_city.fill = GridBagConstraints.HORIZONTAL;
		gbc_city.gridx = 1;
		gbc_city.gridy = 2;
		add(city, gbc_city);
		
		JLabel label_3 = new JLabel("Phone Number");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.anchor = GridBagConstraints.WEST;
		gbc_label_3.gridx = 0;
		gbc_label_3.gridy = 3;
		add(label_3, gbc_label_3);
		
		phone_number = new JTextField();
		phone_number.setEditable(false);
		phone_number.setColumns(10);
		GridBagConstraints gbc_phone_number = new GridBagConstraints();
		gbc_phone_number.insets = new Insets(0, 0, 5, 0);
		gbc_phone_number.fill = GridBagConstraints.HORIZONTAL;
		gbc_phone_number.gridx = 1;
		gbc_phone_number.gridy = 3;
		add(phone_number, gbc_phone_number);
		
		JLabel label_4 = new JLabel("Overall Rating");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.anchor = GridBagConstraints.WEST;
		gbc_label_4.gridx = 0;
		gbc_label_4.gridy = 4;
		add(label_4, gbc_label_4);
		
		overall_rating = new JTextField();
		overall_rating.setEditable(false);
		overall_rating.setColumns(10);
		GridBagConstraints gbc_overall_rating = new GridBagConstraints();
		gbc_overall_rating.insets = new Insets(0, 0, 5, 0);
		gbc_overall_rating.fill = GridBagConstraints.HORIZONTAL;
		gbc_overall_rating.gridx = 1;
		gbc_overall_rating.gridy = 4;
		add(overall_rating, gbc_overall_rating);
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 5;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {0, 0, 0};
		gbl_panel.rowHeights = new int[]{23, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		btnPrevious = new JButton("Previous");
		btnPrevious.setBackground(Color.WHITE);
		GridBagConstraints gbc_btnPrevious = new GridBagConstraints();
		gbc_btnPrevious.insets = new Insets(0, 0, 0, 5);
		gbc_btnPrevious.gridx = 0;
		gbc_btnPrevious.gridy = 0;
		panel.add(btnPrevious, gbc_btnPrevious);
		btnPrevious.addActionListener(schoolUIEventHandler);
		
		btnNext = new JButton("Next");
		btnNext.setBackground(Color.WHITE);
		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.gridx = 2;
		gbc_btnNext.gridy = 0;
		panel.add(btnNext, gbc_btnNext);
		btnNext.addActionListener(schoolUIEventHandler);

	}
	

	public JButton getPreviousButton()
	{
		return btnPrevious;
	}

	public JButton getNextButton()
	{
		return btnNext;
	}

	public void populateSchoolData(School school, int[] schIdxs) {
		
		schUIState.schIndices = schIdxs;
		schUIState.currIdxInSchIndices = 0;
		SchoolUIPanel.this.firePropertyChange(ModelConstants.SCHOOL_UI_PROPERTY_NAME, null, school);
	}

	
	private void setSchoolData(School sch)
	{
		if(sch != null) {
			name.setText(sch.getName());
			street_address.setText(sch.getStreetAddress());
			city.setText(sch.getCity());
			phone_number.setText(sch.getPhoneNumber());
			overall_rating.setText(sch.getOverallRating());
		}
		else {
			name.setText("");
			street_address.setText("");
			city.setText("");
			phone_number.setText("");
			overall_rating.setText("");
		}
		
	}
	
	private void actionNextButton()
	{
		if(schReportCardList != null && schReportCardList.size() > 0 )
		{
			if(schUIState != null && schUIState.incrState())
			{
				SchoolUIPanel.this.firePropertyChange(
							ModelConstants.SCHOOL_UI_PROPERTY_NAME, 
							null,
							schReportCardList.get(
									schUIState.schIndices[schUIState.currIdxInSchIndices])
											.getSchool());		
			}
		}
	}
	
	private void setBtnNextState(boolean st)
	{
		btnNext.setEnabled(st);		
	}
	

	private void actionPreviousButton()
	{
		if(schReportCardList != null && schReportCardList.size() > 0 )
		{
			if(schUIState != null && schUIState.decrState())
			{
				SchoolUIPanel.this.firePropertyChange(
							ModelConstants.SCHOOL_UI_PROPERTY_NAME, 
							null,
							schReportCardList.get(
									schUIState.schIndices[schUIState.currIdxInSchIndices])
											.getSchool());		
			}
		}
	}
	

	private void setBtnPreviousState(boolean st)
	{
		btnPrevious.setEnabled(st);		
	}

}
