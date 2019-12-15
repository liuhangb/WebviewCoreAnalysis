package com.tencent.tbs.common.sniffer;

import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.UrlUtils;
import com.tencent.mtt.ContextHolder;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.baseinfo.TbsDomainListDataProvider;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.utils.TbsInfoUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class VideoSniffer
  extends SnifferCore
  implements SniffObserver
{
  static HashMap<String, Boolean> sDisableList;
  static List<String> sSupportHostList;
  public SniffObserver mObserver;
  String mWebUrl;
  
  static
  {
    ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(64);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("6.5 ");
    localStringBuilder.append(TbsInfoUtils.getMode());
    SnifferCore.setVideoSniffList(localArrayList, localStringBuilder.toString(), GUIDFactory.getInstance().getStrGuid());
  }
  
  public VideoSniffer()
  {
    super(ContextHolder.getAppContext());
  }
  
  static void initSniffDisableListIfNeeded()
  {
    try
    {
      if (sDisableList == null)
      {
        sDisableList = new HashMap();
        Object localObject1 = PublicSettingManager.getInstance().getSniffDisableList();
        if (localObject1 != null)
        {
          String[] arrayOfString = ((String)localObject1).split("\\|");
          localObject1 = sDisableList;
          int i = 0;
          try
          {
            while (i < arrayOfString.length)
            {
              sDisableList.put(arrayOfString[i], Boolean.valueOf(false));
              i += 1;
            }
          }
          finally {}
        }
      }
      return;
    }
    finally {}
  }
  
  public static boolean isSniffSupported(String arg0)
  {
    initSniffDisableListIfNeeded();
    String str = UrlUtils.getHost(???);
    if (str != null)
    {
      Object localObject2 = (Boolean)sDisableList.get(str);
      if (localObject2 != null)
      {
        if (!((Boolean)localObject2).booleanValue())
        {
          new VideoSniffer().sniffVideo(???, 0, null);
          return false;
        }
      }
      else
      {
        if (sSupportHostList == null) {
          sSupportHostList = sniffSupportList();
        }
        ??? = sSupportHostList;
        if (??? != null)
        {
          if (???.size() <= 0) {
            return false;
          }
          synchronized (sSupportHostList)
          {
            localObject2 = sSupportHostList.iterator();
            while (((Iterator)localObject2).hasNext()) {
              if (str.contains((CharSequence)((Iterator)localObject2).next())) {
                return true;
              }
            }
            return false;
          }
        }
        return false;
      }
    }
    return false;
  }
  
  public static void onSniffPlayFailed(String paramString)
  {
    SnifferCore.onSniffPlayFailed(paramString);
  }
  
  public static boolean shouldOverrideStandardPlay(String paramString)
  {
    return SnifferCore.shouldOverrideStandardPlay(paramString);
  }
  
  public void onSniffCompleted(List<String> arg1, Bundle paramBundle)
  {
    String str = paramBundle.getString("reqWebUrl");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onSniffCompleted weburl=");
    localStringBuilder.append(str);
    LogUtils.d("VideoSniffer", localStringBuilder.toString());
    int i = paramBundle.getInt("curIndex");
    if ((??? != null) && (i >= 0) && (i < ???.size())) {
      str = (String)???.get(i);
    } else {
      str = null;
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("onSniffCompleted videoUrl=");
    localStringBuilder.append(str);
    LogUtils.d("VideoSniffer", localStringBuilder.toString());
    try
    {
      if (this.mObserver != null)
      {
        this.mObserver.onSniffCompleted(???, paramBundle);
        this.mObserver = null;
      }
      str = UrlUtils.getHost(this.mWebUrl);
      if (!TextUtils.isEmpty(str))
      {
        initSniffDisableListIfNeeded();
        synchronized (sDisableList)
        {
          boolean bool = sDisableList.containsKey(str);
          i = paramBundle.getInt("scriptStatus");
          if (bool)
          {
            if (i == 0)
            {
              sDisableList.remove(str);
              saveSniffDisableList();
            }
            else if (i < 0)
            {
              sDisableList.put(str, Boolean.valueOf(true));
            }
          }
          else if (i < 0)
          {
            sDisableList.put(str, Boolean.valueOf(true));
            saveSniffDisableList();
          }
          return;
        }
      }
      return;
    }
    finally {}
  }
  
  void saveSniffDisableList()
  {
    Object localObject = sDisableList;
    if (localObject != null)
    {
      Iterator localIterator = ((HashMap)localObject).keySet().iterator();
      localObject = null;
      while (localIterator.hasNext()) {
        if (localObject == null)
        {
          localObject = (String)localIterator.next();
        }
        else
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append((String)localObject);
          localStringBuilder.append("|");
          localStringBuilder.append((String)localIterator.next());
          localObject = localStringBuilder.toString();
        }
      }
      PublicSettingManager.getInstance().setSniffDisableList((String)localObject);
    }
  }
  
  public void sniffVideo(String paramString, int paramInt, SniffObserver paramSniffObserver)
  {
    this.mObserver = paramSniffObserver;
    paramSniffObserver = new StringBuilder();
    paramSniffObserver.append("sniffVideo weburl=");
    paramSniffObserver.append(paramString);
    LogUtils.d("VideoSniffer", paramSniffObserver.toString());
    super.sniffVideo(paramString, paramInt, this);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\sniffer\VideoSniffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */