package com.tencent.tbs.common.lbs;

import android.content.Context;
import android.os.Build.VERSION;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LbsInfoManager
{
  private static final String TAG = "LbsInfoManager";
  private int cellid = 0;
  private int lac = 0;
  private Context mContext;
  private TelephonyManager mTelManager = null;
  private int mcc = 0;
  private int mnc = 0;
  private ArrayList<Integer> neighboringCell = null;
  
  public LbsInfoManager()
  {
    try
    {
      this.mContext = LBS.getContext().getApplicationContext();
      this.mTelManager = ((TelephonyManager)this.mContext.getSystemService("phone"));
      return;
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
    }
  }
  
  private void readCell()
  {
    resetData();
    TelephonyManager localTelephonyManager = this.mTelManager;
    int i;
    Object localObject3;
    Object localObject4;
    Object localObject2;
    if (localTelephonyManager != null)
    {
      try
      {
        i = localTelephonyManager.getPhoneType();
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
        i = 0;
      }
      if (i == 1)
      {
        localObject3 = null;
        try
        {
          localObject4 = this.mTelManager.getCellLocation();
          Object localObject1 = localObject3;
          if (localObject4 != null)
          {
            localObject1 = localObject3;
            if ((localObject4 instanceof GsmCellLocation)) {
              localObject1 = (GsmCellLocation)localObject4;
            }
          }
        }
        catch (Throwable localThrowable1)
        {
          localThrowable1.printStackTrace();
          localObject2 = localObject3;
        }
        if (localObject2 == null) {
          break label418;
        }
        this.lac = ((GsmCellLocation)localObject2).getLac();
        this.cellid = ((GsmCellLocation)localObject2).getCid();
        if ((this.lac <= 0) && (this.cellid <= 0))
        {
          this.lac = 0;
          this.cellid = 0;
          break label418;
        }
        localObject2 = this.mTelManager.getNetworkOperator();
        if (TextUtils.isEmpty((CharSequence)localObject2)) {}
      }
    }
    try
    {
      this.mcc = Integer.parseInt(((String)localObject2).substring(0, 3));
      this.mnc = Integer.parseInt(((String)localObject2).substring(3));
    }
    catch (Exception localException4)
    {
      for (;;) {}
    }
    this.mcc = 0;
    this.mnc = 0;
    this.lac = 0;
    this.cellid = 0;
    return;
    try
    {
      localObject2 = this.mTelManager.getNeighboringCellInfo();
      if (localObject2 != null)
      {
        localObject2 = ((List)localObject2).iterator();
        while (((Iterator)localObject2).hasNext())
        {
          localObject3 = (NeighboringCellInfo)((Iterator)localObject2).next();
          this.neighboringCell.add(Integer.valueOf(((NeighboringCellInfo)localObject3).getCid()));
          continue;
          if ((Integer.parseInt(Build.VERSION.SDK) >= 4) && (i == 2)) {
            try
            {
              localObject3 = Class.forName("android.telephony.cdma.CdmaCellLocation");
              if (localObject3 != null)
              {
                ((Class)localObject3).getConstructor(new Class[0]);
                localObject2 = this.mTelManager.getCellLocation();
                localObject4 = ((Class)localObject3).getMethod("getSystemId", new Class[0]);
                if (localObject4 != null) {
                  this.lac = ((Integer)((Method)localObject4).invoke(localObject2, new Object[0])).intValue();
                }
                localObject3 = ((Class)localObject3).getMethod("getBaseStationId", new Class[0]);
                if (localObject3 != null) {
                  this.cellid = ((Integer)((Method)localObject3).invoke(localObject2, new Object[0])).intValue();
                }
                localObject2 = this.mTelManager.getNetworkOperator();
                if (localObject2 != null) {
                  try
                  {
                    this.mcc = Integer.parseInt(((String)localObject2).substring(0, 3));
                    this.mnc = Integer.parseInt(((String)localObject2).substring(3));
                  }
                  catch (Exception localException2)
                  {
                    FLogger.d("LbsInfoManager", localException2.getMessage());
                  }
                }
              }
              localStringBuilder = new StringBuilder();
            }
            catch (Exception localException3)
            {
              localException3.printStackTrace();
            }
          }
        }
      }
    }
    catch (Throwable localThrowable2)
    {
      label418:
      StringBuilder localStringBuilder;
      for (;;) {}
    }
    localStringBuilder.append("lbs readcell mcc=");
    localStringBuilder.append(this.mcc);
    localStringBuilder.append("; mnc=");
    localStringBuilder.append(this.mnc);
    localStringBuilder.append("; lac=");
    localStringBuilder.append(this.lac);
    localStringBuilder.append("; cellid=");
    localStringBuilder.append(this.cellid);
    localStringBuilder.toString();
  }
  
  private void resetData()
  {
    this.mcc = 0;
    this.mnc = 0;
    this.lac = 0;
    this.cellid = 0;
    ArrayList localArrayList = this.neighboringCell;
    if (localArrayList == null)
    {
      this.neighboringCell = new ArrayList();
      return;
    }
    try
    {
      localArrayList.clear();
      return;
    }
    catch (Exception localException) {}
  }
  
  public Cell getCell()
  {
    readCell();
    return new Cell((short)this.mcc, (short)this.mnc, this.lac, this.cellid);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\lbs\LbsInfoManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */