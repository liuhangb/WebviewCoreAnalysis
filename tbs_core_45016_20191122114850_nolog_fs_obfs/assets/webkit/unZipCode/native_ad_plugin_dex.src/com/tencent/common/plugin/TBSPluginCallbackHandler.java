package com.tencent.common.plugin;

import android.content.Context;
import android.os.DeadObjectException;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.common.http.Apn;
import com.tencent.common.utils.TbsMode;
import com.tencent.mtt.browser.download.engine.DownloadTask;
import java.util.ArrayList;
import java.util.Iterator;

public class TBSPluginCallbackHandler
  extends PluginCallbackHandler
{
  public void handleMessage(Message paramMessage)
  {
    for (;;)
    {
      try
      {
        Iterator localIterator = this.mQBPluignCallbackList.iterator();
        if (paramMessage.what == QBPluginServiceImpl.TYPE_RETRY_PULL_PLUGINLIST)
        {
          removeMessages(QBPluginServiceImpl.TYPE_RETRY_PULL_PLUGINLIST);
          if (!Apn.isNetworkAvailable())
          {
            if ((this.mQBPluginServiceImpl != null) && (this.mQBPluginServiceImpl.getPluginCallbackHandler() != null))
            {
              paramMessage = this.mQBPluginServiceImpl.getPluginCallbackHandler().obtainMessage(QBPluginServiceImpl.TYPE_RETRY_PULL_PLUGINLIST);
              this.mQBPluginServiceImpl.getPluginCallbackHandler().sendMessageDelayed(paramMessage, 120000L);
            }
          }
          else if ((this.mQBPluginServiceImpl != null) && ((paramMessage.arg1 == 2) || (paramMessage.arg1 == 1))) {
            this.mQBPluginServiceImpl.refreshPluignListIfNeeded(paramMessage.arg1);
          }
        }
        else if (paramMessage.what == QBPluginServiceImpl.TYPE_PLUGIN_GETLIST_SUCC)
        {
          if (localIterator.hasNext())
          {
            IQBPluginCallback localIQBPluginCallback1 = (IQBPluginCallback)localIterator.next();
            if (localIQBPluginCallback1 == null) {
              continue;
            }
            try
            {
              localIQBPluginCallback1.onGetPluginListSucc((String)paramMessage.obj, paramMessage.arg1);
            }
            catch (DeadObjectException localDeadObjectException1)
            {
              localIterator.remove();
              localDeadObjectException1.printStackTrace();
            }
            continue;
          }
        }
        else if (paramMessage.what == QBPluginServiceImpl.TYPE_PLUGIN_GETLIST_FAILED)
        {
          if (localIterator.hasNext())
          {
            IQBPluginCallback localIQBPluginCallback2 = (IQBPluginCallback)localIterator.next();
            if (localIQBPluginCallback2 == null) {
              continue;
            }
            try
            {
              localIQBPluginCallback2.onGetPluginListFailed((String)paramMessage.obj, paramMessage.arg1);
            }
            catch (DeadObjectException localDeadObjectException2)
            {
              localIterator.remove();
              localDeadObjectException2.printStackTrace();
            }
            continue;
          }
        }
        else
        {
          Object localObject1;
          if (paramMessage.what == QBPluginServiceImpl.TYPE_PLUGIN_DOWNLOAD_CREATE)
          {
            if ((paramMessage.obj instanceof DownloadTask))
            {
              localObject1 = (DownloadTask)paramMessage.obj;
              if (localIterator.hasNext())
              {
                IQBPluginCallback localIQBPluginCallback3 = (IQBPluginCallback)localIterator.next();
                if (localIQBPluginCallback3 == null) {
                  continue;
                }
                try
                {
                  localIQBPluginCallback3.onPluginDownloadCreated(((DownloadTask)localObject1).getAnnotation(), ((DownloadTask)localObject1).getTaskUrl(), ((DownloadTask)localObject1).mStatus, paramMessage.arg1);
                }
                catch (DeadObjectException localDeadObjectException4)
                {
                  localIterator.remove();
                  localDeadObjectException4.printStackTrace();
                }
                continue;
              }
            }
          }
          else if (paramMessage.what == QBPluginServiceImpl.TYPE_PLUGIN_DOWNLOAD_START)
          {
            if ((paramMessage.obj instanceof DownloadTask))
            {
              localObject1 = (DownloadTask)paramMessage.obj;
              if (localIterator.hasNext())
              {
                IQBPluginCallback localIQBPluginCallback4 = (IQBPluginCallback)localIterator.next();
                if (localIQBPluginCallback4 == null) {
                  continue;
                }
                try
                {
                  localIQBPluginCallback4.onPluginDownloadStarted(((DownloadTask)localObject1).getAnnotation(), ((DownloadTask)localObject1).getTaskUrl(), (int)((DownloadTask)localObject1).getDownloadedSize(), ((DownloadTask)localObject1).getProgress(), ((DownloadTask)localObject1).mStatus, paramMessage.arg1);
                }
                catch (DeadObjectException localDeadObjectException5)
                {
                  localIterator.remove();
                  localDeadObjectException5.printStackTrace();
                }
                continue;
              }
            }
          }
          else if (paramMessage.what == QBPluginServiceImpl.TYPE_PLUGIN_DOWNLOAD_SUCESSED)
          {
            if ((paramMessage.obj instanceof DownloadTask))
            {
              localObject1 = (DownloadTask)paramMessage.obj;
              if (localIterator.hasNext())
              {
                IQBPluginCallback localIQBPluginCallback5 = (IQBPluginCallback)localIterator.next();
                if (localIQBPluginCallback5 == null) {
                  continue;
                }
                try
                {
                  String str1 = ((DownloadTask)localObject1).getAnnotation();
                  String str2 = ((DownloadTask)localObject1).getFileFolderPath();
                  String str3 = ((DownloadTask)localObject1).getFileName();
                  String str4 = ((DownloadTask)localObject1).getTaskUrl();
                  int i = (int)((DownloadTask)localObject1).getDownloadedSize();
                  int j = ((DownloadTask)localObject1).getDownloadTaskId();
                  int k = ((DownloadTask)localObject1).mStatus;
                  int m = paramMessage.arg1;
                  if (TextUtils.isEmpty(((DownloadTask)localObject1).mHijackInfo)) {
                    break label1475;
                  }
                  bool = true;
                  localIQBPluginCallback5.onPluginDownloadSuccessed(str1, str2, str3, str4, i, j, k, m, bool);
                }
                catch (DeadObjectException localDeadObjectException6)
                {
                  localIterator.remove();
                  localDeadObjectException6.printStackTrace();
                }
                continue;
              }
              ((DownloadTask)localObject1).getTaskUrl();
            }
          }
          else
          {
            Object localObject3;
            if (paramMessage.what == QBPluginServiceImpl.TYPE_PLUGIN_DOWNLOAD_FAILED)
            {
              if ((paramMessage.obj instanceof DownloadTask))
              {
                localObject3 = (DownloadTask)paramMessage.obj;
                if (TbsMode.thirdTbsMode())
                {
                  if ((!TextUtils.isEmpty(((DownloadTask)localObject3).getAnnotation())) && (this.mQBPluginServiceImpl.isSpecialPkg(((DownloadTask)localObject3).getAnnotation()))) {
                    localObject1 = this.mQBPluginServiceImpl.callerAppContext();
                  } else {
                    localObject1 = this.mQBPluginServiceImpl.tesProviderAppContext();
                  }
                }
                else {
                  localObject1 = this.mQBPluginServiceImpl.tesProviderAppContext();
                }
                this.mQBPluginServiceImpl.deletePidFile((Context)localObject1, ((DownloadTask)localObject3).getAnnotation());
                if (localIterator.hasNext())
                {
                  localObject1 = (IQBPluginCallback)localIterator.next();
                  if (localObject1 == null) {
                    continue;
                  }
                  try
                  {
                    PluginStatBehavior.addLogPath(((DownloadTask)localObject3).getAnnotation(), 4, "dlfail");
                    ((IQBPluginCallback)localObject1).onPluginDownloadFailed(((DownloadTask)localObject3).getAnnotation(), ((DownloadTask)localObject3).getTaskUrl(), ((DownloadTask)localObject3).mStatus, ((DownloadTask)localObject3).getErrorCode(), paramMessage.arg1);
                  }
                  catch (DeadObjectException localDeadObjectException3)
                  {
                    localIterator.remove();
                    localDeadObjectException3.printStackTrace();
                  }
                  continue;
                }
              }
            }
            else
            {
              Object localObject2;
              if (paramMessage.what == QBPluginServiceImpl.TYPE_PLUGIN_DOWNLOAD_CANCLED)
              {
                paramMessage = (String)paramMessage.obj;
                if (TbsMode.TBSISQB())
                {
                  localObject3 = ((ArrayList)this.mQBPluginServiceImpl.getAllPluginListWithnoReq(1)).iterator();
                  if (((Iterator)localObject3).hasNext())
                  {
                    localObject2 = (QBPluginItemInfo)((Iterator)localObject3).next();
                    if (!((QBPluginItemInfo)localObject2).mUrl.equalsIgnoreCase(paramMessage)) {
                      continue;
                    }
                    if (localIterator.hasNext())
                    {
                      localObject3 = (IQBPluginCallback)localIterator.next();
                      if (localObject3 == null) {
                        continue;
                      }
                      try
                      {
                        if (!((QBPluginItemInfo)localObject2).mUrl.equalsIgnoreCase(paramMessage)) {
                          continue;
                        }
                        PluginStatBehavior.addLogPath(((QBPluginItemInfo)localObject2).mPackageName, 4, "dlcl(1)");
                        ((IQBPluginCallback)localObject3).onPluginDownloadFailed(((QBPluginItemInfo)localObject2).mPackageName, ((QBPluginItemInfo)localObject2).mUrl, -1, -1, 1);
                      }
                      catch (DeadObjectException localDeadObjectException7)
                      {
                        localIterator.remove();
                        localDeadObjectException7.printStackTrace();
                      }
                      continue;
                    }
                  }
                }
                Object localObject4 = ((ArrayList)this.mQBPluginServiceImpl.getAllPluginListWithnoReq(2)).iterator();
                if (((Iterator)localObject4).hasNext())
                {
                  localObject2 = (QBPluginItemInfo)((Iterator)localObject4).next();
                  if (!((QBPluginItemInfo)localObject2).mUrl.equalsIgnoreCase(paramMessage)) {
                    continue;
                  }
                  if (localIterator.hasNext())
                  {
                    localObject4 = (IQBPluginCallback)localIterator.next();
                    if (localObject4 == null) {
                      continue;
                    }
                    try
                    {
                      if (!((QBPluginItemInfo)localObject2).mUrl.equalsIgnoreCase(paramMessage)) {
                        continue;
                      }
                      PluginStatBehavior.addLogPath(((QBPluginItemInfo)localObject2).mPackageName, 4, "dlcl(2)");
                      ((IQBPluginCallback)localObject4).onPluginDownloadFailed(((QBPluginItemInfo)localObject2).mPackageName, ((QBPluginItemInfo)localObject2).mUrl, -1, -1, 2);
                    }
                    catch (DeadObjectException localDeadObjectException8)
                    {
                      localIterator.remove();
                      localDeadObjectException8.printStackTrace();
                    }
                    continue;
                  }
                }
              }
              else if (paramMessage.what == QBPluginServiceImpl.TYPE_PLUGIN_DOWNLOAD_PROGRESS)
              {
                if ((paramMessage.obj instanceof DownloadTask))
                {
                  localObject2 = (DownloadTask)paramMessage.obj;
                  if (localIterator.hasNext())
                  {
                    IQBPluginCallback localIQBPluginCallback6 = (IQBPluginCallback)localIterator.next();
                    if (localIQBPluginCallback6 == null) {
                      continue;
                    }
                    try
                    {
                      localIQBPluginCallback6.onPluginDownloadProgress(((DownloadTask)localObject2).getAnnotation(), ((DownloadTask)localObject2).getTaskUrl(), (int)((DownloadTask)localObject2).getDownloadedSize(), ((DownloadTask)localObject2).getProgress(), ((DownloadTask)localObject2).mStatus, paramMessage.arg1);
                    }
                    catch (DeadObjectException localDeadObjectException9)
                    {
                      localIterator.remove();
                      localDeadObjectException9.printStackTrace();
                    }
                    continue;
                  }
                }
              }
              else if (paramMessage.what == QBPluginServiceImpl.TYPE_PLUGIN_INSTALL_COMPLETE)
              {
                localObject2 = (QBPluginItemInfo)paramMessage.obj;
                if (localObject2 == null) {
                  return;
                }
                if (localIterator.hasNext())
                {
                  IQBPluginCallback localIQBPluginCallback7 = (IQBPluginCallback)localIterator.next();
                  if (localIQBPluginCallback7 == null) {
                    continue;
                  }
                  try
                  {
                    localIQBPluginCallback7.onPluginInstallSuccessed(((QBPluginItemInfo)localObject2).mPackageName, (QBPluginItemInfo)localObject2, paramMessage.arg1, paramMessage.arg2);
                  }
                  catch (DeadObjectException localDeadObjectException10)
                  {
                    localIterator.remove();
                    localDeadObjectException10.printStackTrace();
                  }
                  continue;
                }
              }
              else if (paramMessage.what == QBPluginServiceImpl.TYPE_PLUGIN_INSTALL_FAILED)
              {
                localObject2 = (QBPluginItemInfo)paramMessage.obj;
                if (localObject2 == null) {
                  return;
                }
                if (localIterator.hasNext())
                {
                  IQBPluginCallback localIQBPluginCallback8 = (IQBPluginCallback)localIterator.next();
                  if (localIQBPluginCallback8 == null) {
                    continue;
                  }
                  try
                  {
                    localIQBPluginCallback8.onPluginInstallFailed(((QBPluginItemInfo)localObject2).mPackageName, paramMessage.arg1, paramMessage.arg2);
                  }
                  catch (DeadObjectException localDeadObjectException11)
                  {
                    localIterator.remove();
                    localDeadObjectException11.printStackTrace();
                  }
                  continue;
                }
              }
            }
          }
        }
        return;
      }
      catch (Exception paramMessage)
      {
        paramMessage.printStackTrace();
      }
      label1475:
      boolean bool = false;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\plugin\TBSPluginCallbackHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */