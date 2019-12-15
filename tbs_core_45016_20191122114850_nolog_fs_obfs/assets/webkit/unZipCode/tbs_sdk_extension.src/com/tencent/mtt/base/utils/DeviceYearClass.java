package com.tencent.mtt.base.utils;

import java.util.ArrayList;
import java.util.Collections;

public class DeviceYearClass
{
  public static final int CLASS_2008 = 2008;
  public static final int CLASS_2009 = 2009;
  public static final int CLASS_2010 = 2010;
  public static final int CLASS_2011 = 2011;
  public static final int CLASS_2012 = 2012;
  public static final int CLASS_2013 = 2013;
  public static final int CLASS_2014 = 2014;
  public static final int CLASS_UNKNOWN = -1;
  private static volatile Integer a;
  
  private static int a()
  {
    ArrayList localArrayList = new ArrayList();
    a(localArrayList, b());
    a(localArrayList, c());
    a(localArrayList, d());
    if (localArrayList.isEmpty()) {
      return -1;
    }
    Collections.sort(localArrayList);
    if ((localArrayList.size() & 0x1) == 1) {
      return ((Integer)localArrayList.get(localArrayList.size() / 2)).intValue();
    }
    int i = localArrayList.size() / 2 - 1;
    return ((Integer)localArrayList.get(i)).intValue() + (((Integer)localArrayList.get(i + 1)).intValue() - ((Integer)localArrayList.get(i)).intValue()) / 2;
  }
  
  private static void a(ArrayList<Integer> paramArrayList, int paramInt)
  {
    if (paramInt != -1) {
      paramArrayList.add(Integer.valueOf(paramInt));
    }
  }
  
  private static int b()
  {
    int i = DeviceUtilsF.getNumberOfCPUCores();
    if (i < 1) {
      return -1;
    }
    if (i == 1) {
      return 2008;
    }
    if (i <= 3) {
      return 2011;
    }
    return 2012;
  }
  
  private static int c()
  {
    long l = DeviceUtilsF.getCPUMaxFreqKHz();
    if (l == -1L) {
      return -1;
    }
    if (l <= 528000L) {
      return 2008;
    }
    if (l <= 620000L) {
      return 2009;
    }
    if (l <= 1020000L) {
      return 2010;
    }
    if (l <= 1220000L) {
      return 2011;
    }
    if (l <= 1520000L) {
      return 2012;
    }
    if (l <= 2020000L) {
      return 2013;
    }
    return 2014;
  }
  
  private static int d()
  {
    long l = DeviceUtilsF.getTotalRAMMemory();
    if (l <= 0L) {
      return -1;
    }
    if (l <= 192L) {
      return 2008;
    }
    if (l <= 290L) {
      return 2009;
    }
    if (l <= 512L) {
      return 2010;
    }
    if (l <= 1024L) {
      return 2011;
    }
    if (l <= 1536L) {
      return 2012;
    }
    if (l <= 2048L) {
      return 2013;
    }
    return 2014;
  }
  
  public static int get()
  {
    if (a == null) {
      try
      {
        if (a == null) {
          a = Integer.valueOf(a());
        }
      }
      finally {}
    }
    return a.intValue();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\DeviceYearClass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */