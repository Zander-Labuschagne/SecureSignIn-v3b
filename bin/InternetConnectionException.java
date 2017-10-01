/*
* @author Zander Labuschagne 23585137
* 
*/

public class InternetConnectionException extends Exception 
{
	public InternetConnectionException()
	{
		super();
	}
	
	public InternetConnectionException(String ex)
	{
		super(ex);
	}	
	
	public String toString()
	{
		return "Failed to Connect to Address:" + super.toString();
	}
}