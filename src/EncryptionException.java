/*
* @author Zander Labuschagne 23585137
*
*/

public class EncryptionException extends Exception
{
    public EncryptionException()
    {
        super();
    }

    public EncryptionException(String ex)
    {
        super(ex);
    }

    public String toString()
    {
        return "Failed to Encrypt Password:" + super.toString();
    }
}