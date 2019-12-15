package com.tencent.tbs.common.baseinfo;

import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.mtt.ContextHolder;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.turingfd.sdk.tbs.TuringDIDService;
import com.tencent.turingfd.sdk.tbs.h;
import com.tencent.turingfd.sdk.tbs.j;
import com.tencent.turingfd.sdk.tbs.j.a;
import java.util.concurrent.ThreadPoolExecutor;

public class TuringDID
{
  private static volatile TuringDID instance;
  private String oaid = null;
  private String taid = null;
  
  private TuringDID()
  {
    BrowserExecutorSupplier.getInstance().getNetworkExecutor().execute(new Runnable()
    {
      public void run()
      {
        try
        {
          TuringDIDService.a(j.a(ContextHolder.getAppContext()).a());
          h localh = TuringDIDService.a(ContextHolder.getAppContext());
          if ((localh != null) && (localh.a() == 0))
          {
            TuringDID.access$002(TuringDID.this, localh.a());
            PublicSettingManager.getInstance().setTuringDTAID(TuringDID.this.taid);
            TuringDID.access$102(TuringDID.this, localh.b());
            PublicSettingManager.getInstance().setTuringDOAID(TuringDID.this.oaid);
            return;
          }
          if (localh != null)
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("TAID errorCode:");
            localStringBuilder.append(localh.a());
            localStringBuilder.toString();
          }
          return;
        }
        catch (Exception localException) {}
      }
    });
  }
  
  public static TuringDID getInstance()
  {
    if (instance == null) {
      try
      {
        if (instance == null) {
          instance = new TuringDID();
        }
      }
      finally {}
    }
    return instance;
  }
  
  public String getOAID()
  {
    return this.oaid;
  }
  
  public String getTAID()
  {
    return this.taid;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\TuringDID.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */