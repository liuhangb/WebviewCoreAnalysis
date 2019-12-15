package com.tencent.tbs.common.baseinfo;

import android.content.Context;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.tbs.common.download.qb.QBDownloadManager;

public class TargetAdapter
{
  public static void onPreference(WUPResponseBase paramWUPResponseBase, byte paramByte)
  {
    PreferenceManager.getInstancce().onPreference(paramWUPResponseBase, paramByte);
  }
  
  public static boolean setAutoSilentDownloadSwitch(Context paramContext, boolean paramBoolean)
  {
    return QBDownloadManager.getInstance().setAutoSilentDownloadSwitch(paramContext, paramBoolean);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\TargetAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */