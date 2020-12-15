package constant;

import handler.MouseController;

public enum SessionVariable
	{
	
	myID(3);
	
	private int value;
	
	SessionVariable(int value)
		{
		this.setValue(value);
		}

	public int getValue()
		{
		return value;
		}

	public void setValue(int value) 
		{
		this.value = value;
		}
	
	}
