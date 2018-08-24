public class LeapYear{
  static int year = 2000;
  public static void main(String[] args){
    if(year%400 == 0 || (year%4 == 0 && year%100 != 0))
      System.out.println(year + " is a leap year");
    else
      System.out.println(year + " is NOT a leap year");
  }
}
