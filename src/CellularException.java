/*
* @author Zander Labuschagne 23585137
*
*/

public class CellularException extends Exception
{
    public CellularException()
    {
        super();
    }

    public CellularException(String ex)
    {
        super(ex);
    }

    public String toString()
    {
        return "Failed to Send SMS:" + super.toString();
    }
}