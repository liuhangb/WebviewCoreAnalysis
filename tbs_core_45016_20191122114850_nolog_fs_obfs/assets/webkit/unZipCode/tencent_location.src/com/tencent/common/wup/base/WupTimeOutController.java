package com.tencent.common.wup.base;

import android.os.SystemClock;
import com.tencent.common.http.Apn;

public class WupTimeOutController
{
  private static WupTimeOutController jdField_a_of_type_ComTencentCommonWupBaseWupTimeOutController = new WupTimeOutController();
  private volatile int jdField_a_of_type_Int = -1;
  private long jdField_a_of_type_Long = -1L;
  private a jdField_a_of_type_ComTencentCommonWupBaseA = null;
  private Object jdField_a_of_type_JavaLangObject = new Object();
  private volatile long jdField_b_of_type_Long = -1L;
  private Object jdField_b_of_type_JavaLangObject = new Object();
  
  private void a(boolean paramBoolean)
  {
    if ((this.jdField_a_of_type_Long > 0L) && (SystemClock.elapsedRealtime() - this.jdField_a_of_type_Long > 600000L)) {
      this.jdField_a_of_type_ComTencentCommonWupBaseA.a();
    }
    if (paramBoolean) {
      this.jdField_a_of_type_Long = SystemClock.elapsedRealtime();
    }
  }
  
  public static WupTimeOutController getInstance()
  {
    return jdField_a_of_type_ComTencentCommonWupBaseWupTimeOutController;
  }
  
  public void addRequestTime(double paramDouble)
  {
    ??? = new StringBuilder();
    ((StringBuilder)???).append("addRequestTime: time = ");
    ((StringBuilder)???).append(paramDouble);
    ((StringBuilder)???).toString();
    synchronized (this.jdField_b_of_type_JavaLangObject)
    {
      a(true);
      this.jdField_a_of_type_ComTencentCommonWupBaseA.a(paramDouble);
      return;
    }
  }
  
  public void enlargeWUPNetTimeOut()
  {
    long l = SystemClock.elapsedRealtime();
    int i = getConnTimeOut();
    if (l - this.jdField_b_of_type_Long < i) {
      return;
    }
    this.jdField_b_of_type_Long = l;
    Object localObject1 = this.jdField_a_of_type_JavaLangObject;
    i += 2000;
    int j = 30000;
    if (i >= 30000) {
      i = j;
    }
    try
    {
      this.jdField_a_of_type_Int = i;
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("enlage mWupTimeOut=");
      ((StringBuilder)localObject1).append(this.jdField_a_of_type_Int);
      ((StringBuilder)localObject1).toString();
      return;
    }
    finally {}
  }
  
  public int getConnTimeOut()
  {
    int j = this.jdField_a_of_type_Int;
    int i = j;
    if (j < 6000) {
      i = getDefaultConnTimeOut();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getConnTimeOut=");
    localStringBuilder.append(i);
    localStringBuilder.toString();
    return i;
  }
  
  public int getDefaultConnTimeOut()
  {
    if (Apn.isWifiMode(false)) {
      return 6000;
    }
    return 10000;
  }
  
  public int getDefaultReadTimeOut()
  {
    if (Apn.isWifiMode(false)) {
      return 20000;
    }
    return 30000;
  }
  
  public int getLeastReadTimeOut()
  {
    if (Apn.isWifiMode(false)) {
      return 10000;
    }
    return 20000;
  }
  
  public int getReadTimeOut()
  {
    a(false);
    synchronized (this.jdField_b_of_type_JavaLangObject)
    {
      int j = (int)this.jdField_a_of_type_ComTencentCommonWupBaseA.a();
      int i = getDefaultReadTimeOut();
      if (j <= 0)
      {
        i += 15000;
      }
      else
      {
        int k = i + 15000;
        if (j >= k) {
          i = k;
        } else if (j >= i) {
          i = j;
        }
      }
      ??? = new StringBuilder();
      ((StringBuilder)???).append("getReadTimeOut = ");
      ((StringBuilder)???).append(i);
      ((StringBuilder)???).append(", mAvgCalculator = ");
      ((StringBuilder)???).append(j);
      ((StringBuilder)???).toString();
      return i;
    }
  }
  
  public void resetAvgReadTimeout()
  {
    synchronized (this.jdField_b_of_type_JavaLangObject)
    {
      this.jdField_a_of_type_ComTencentCommonWupBaseA.a();
      return;
    }
  }
  
  public void resetWUPConnTimeout()
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      this.jdField_a_of_type_Int = -1;
      return;
    }
  }
  
  public void restoreWUPNetTimeOut()
  {
    long l = SystemClock.elapsedRealtime();
    int j = getConnTimeOut();
    if (l - this.jdField_b_of_type_Long < j / 2) {
      return;
    }
    this.jdField_b_of_type_Long = l;
    int i = getDefaultConnTimeOut();
    Object localObject1 = this.jdField_a_of_type_JavaLangObject;
    if (j - 2000 > i) {
      i = j - 2000;
    }
    try
    {
      this.jdField_a_of_type_Int = i;
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("reatore mWupTimeOut=");
      ((StringBuilder)localObject1).append(this.jdField_a_of_type_Int);
      ((StringBuilder)localObject1).toString();
      return;
    }
    finally {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\base\WupTimeOutController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */