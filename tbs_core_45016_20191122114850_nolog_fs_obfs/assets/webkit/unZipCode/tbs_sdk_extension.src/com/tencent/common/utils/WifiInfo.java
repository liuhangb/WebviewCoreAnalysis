package com.tencent.common.utils;

import java.util.Comparator;

public class WifiInfo
  implements Comparable
{
  private int jdField_a_of_type_Int;
  private String jdField_a_of_type_JavaLangString;
  private String b;
  
  public WifiInfo(String paramString1, String paramString2, int paramInt)
  {
    this.jdField_a_of_type_JavaLangString = paramString1;
    this.b = paramString2;
    this.jdField_a_of_type_Int = paramInt;
  }
  
  public int compareTo(Object paramObject)
  {
    paramObject = (WifiInfo)paramObject;
    if (getLevel() < ((WifiInfo)paramObject).getLevel()) {
      return 1;
    }
    if (getLevel() > ((WifiInfo)paramObject).getLevel()) {
      return -1;
    }
    return 0;
  }
  
  public int getLevel()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public String getMac()
  {
    return this.b;
  }
  
  public String getName()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public long getSsid()
  {
    try
    {
      if (this.b == null) {
        return 0L;
      }
      String[] arrayOfString = this.b.replaceAll(":", ".").replaceAll("-", ".").split("\\.");
      long l1 = Long.parseLong(arrayOfString[0], 16);
      long l2 = Long.parseLong(arrayOfString[1], 16);
      long l3 = Long.parseLong(arrayOfString[2], 16);
      long l4 = Long.parseLong(arrayOfString[3], 16);
      long l5 = Long.parseLong(arrayOfString[4], 16);
      long l6 = Long.parseLong(arrayOfString[5], 16);
      return l6 | l1 << 40 | l2 << 32 | l3 << 24 | l4 << 16 | l5 << 8;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0L;
  }
  
  public void setLevel(int paramInt)
  {
    this.jdField_a_of_type_Int = paramInt;
  }
  
  public void setMac(String paramString)
  {
    this.b = paramString;
  }
  
  public void setName(String paramString)
  {
    this.jdField_a_of_type_JavaLangString = paramString;
  }
  
  public static final class BySigalLevelComparator
    implements Comparator<WifiInfo>
  {
    public int compare(WifiInfo paramWifiInfo1, WifiInfo paramWifiInfo2)
    {
      return paramWifiInfo1.compareTo(paramWifiInfo2);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\WifiInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */