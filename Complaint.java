public class Complaint
{
    // Attributes
    private String causeOfAction, plaintiffCitizenship, defendandCitizenship, originalStateOfFilling;
    private double amountInControversy;
    private int id;
    private static int nextID = 1;


    // Constructor

    public Complaint(String causeOfAction, String plaintiffCitizenship, String defendandCitizenship, String originalStateOfFilling, double amountInControversy)
    {
        this.causeOfAction = causeOfAction;
        this.plaintiffCitizenship = plaintiffCitizenship;
        this.defendandCitizenship = defendandCitizenship;
        this.originalStateOfFilling = originalStateOfFilling;
        this.amountInControversy = amountInControversy;
        id = nextID;
        nextID++;
    }

    // Getter methods

    public String getCauseOfAction()
    {
        return causeOfAction;
    }

    public String getPlaintiffCitizenship()
    {
        return plaintiffCitizenship;
    }

    public String getDefendandCitizenship()
    {
        return defendandCitizenship;
    }

    public String getOriginalStateOfFilling()
    {
        return originalStateOfFilling;
    }

    public double getAmountInControversy()
    {
        return amountInControversy;
    }

    public int getId()
    {
        return id;
    }
}
