


public class Person
{
    ///region fields
    private String userId;
    private String userName;
    private String userAddress;
    private double userBalance;

    ///endregion

    /// Props

   // public String UserID => this.userId; I forgot no props in java

    public String GetUserID()
    {
        return userId;
    }

    public String GetUserName()
    {
        return userName;
    }

    public String GetUserAddress()
    {
        return userAddress;
    }

    public double GetUserBalance()
    {
        return userBalance;
    }




    /// Ctor
    public Person(String UserId, String UserName, String UserAddress, double UserBalance)
    {
        userId = UserId;
        userName = UserName;
        userAddress = UserAddress;
        userBalance = UserBalance;
    }


    @Override
    public String toString()
    {
        return "ID: " + userId + ", Name: " + userName +", Address: " + userAddress + ", Balance: " + userBalance;
    }


}
