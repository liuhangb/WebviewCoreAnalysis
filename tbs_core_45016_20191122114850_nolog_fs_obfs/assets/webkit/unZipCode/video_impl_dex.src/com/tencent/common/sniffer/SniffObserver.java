package com.tencent.common.sniffer;

import android.os.Bundle;
import java.util.List;

public abstract interface SniffObserver
{
  public static final String KEY_CLARITY_NUM = "clarityNum";
  public static final String KEY_CUR_INDEX = "curIndex";
  public static final String KEY_REQ_WEBURL = "reqWebUrl";
  public static final String KEY_SCRIPT = "script";
  public static final String KEY_SCRIPT_STATUS = "scriptStatus";
  public static final String KEY_UA = "ua";
  
  public abstract void onSniffCompleted(List<String> paramList, Bundle paramBundle);
  
  public static abstract interface InternalObserver
  {
    public abstract void onReceivedString(String paramString);
    
    public abstract void onSniffCompleted(List<String> paramList, int paramInt1, int paramInt2, String paramString1, int paramInt3, String paramString2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\sniffer\SniffObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */