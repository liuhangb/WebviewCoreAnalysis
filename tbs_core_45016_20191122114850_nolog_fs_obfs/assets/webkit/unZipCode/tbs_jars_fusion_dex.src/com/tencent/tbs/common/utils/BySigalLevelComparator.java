package com.tencent.tbs.common.utils;

import com.tencent.common.utils.WifiInfo;
import java.util.Comparator;

public class BySigalLevelComparator
  implements Comparator<WifiInfo>
{
  public final int compare(WifiInfo paramWifiInfo1, WifiInfo paramWifiInfo2)
  {
    return paramWifiInfo1.compareTo(paramWifiInfo2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\BySigalLevelComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */