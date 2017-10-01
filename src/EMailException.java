/*
* @author Zander Labuschagne 23585137
*
*/

public class EMailException extends Exception
{
    public EMailException()
    {
        super();
    }

    public EMailException(String ex)
    {
        super(ex);
    }

    public String toString()
    {
        return "Failed to Send E-Mail:" + super.toString();
    }
}