package model;


class SimpleStringReportedVal extends BaseReportedValue
{
	String val = "";
	
	public SimpleStringReportedVal(String v) {
		// TODO Auto-generated constructor stub
		this.val = v;
	}
	
	@Override
	public Object getReportedVal()
	{
		return val;
	}
}
