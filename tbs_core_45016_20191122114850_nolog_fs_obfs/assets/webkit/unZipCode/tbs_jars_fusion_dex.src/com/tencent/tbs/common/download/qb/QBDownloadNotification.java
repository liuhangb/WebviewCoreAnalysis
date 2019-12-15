package com.tencent.tbs.common.download.qb;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import com.tencent.tbs.common.resources.TBSResources;
import java.lang.reflect.Method;
import java.util.Random;

class QBDownloadNotification
  implements QBDownloadListener
{
  protected Context mContext;
  protected QBDownloadBroadcastReceiver mDownloadBroadcastReceiver;
  private Notification mNotification;
  protected int mNotificationId;
  protected NotificationManager mNotificationManager;
  
  public QBDownloadNotification(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
    this.mNotificationManager = ((NotificationManager)this.mContext.getSystemService("notification"));
    paramContext = this.mContext.getPackageName();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(QBDownloadBroadcastReceiver.PAUSE_DOWNLOAD);
    localStringBuilder.append("_");
    localStringBuilder.append(paramContext);
    QBDownloadBroadcastReceiver.PAUSE_DOWNLOAD = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(QBDownloadBroadcastReceiver.DO_DOWNLOAD);
    localStringBuilder.append("_");
    localStringBuilder.append(paramContext);
    QBDownloadBroadcastReceiver.DO_DOWNLOAD = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(QBDownloadBroadcastReceiver.TRY_DOWNLOAD);
    localStringBuilder.append("_");
    localStringBuilder.append(paramContext);
    QBDownloadBroadcastReceiver.TRY_DOWNLOAD = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(QBDownloadBroadcastReceiver.DO_INSTALL);
    localStringBuilder.append("_");
    localStringBuilder.append(paramContext);
    QBDownloadBroadcastReceiver.DO_INSTALL = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(QBDownloadBroadcastReceiver.NOTIFICATION_BE_DEL);
    localStringBuilder.append("_");
    localStringBuilder.append(paramContext);
    QBDownloadBroadcastReceiver.NOTIFICATION_BE_DEL = localStringBuilder.toString();
    this.mDownloadBroadcastReceiver = new QBDownloadBroadcastReceiver();
    paramContext = new IntentFilter();
    paramContext.addAction(QBDownloadBroadcastReceiver.PAUSE_DOWNLOAD);
    paramContext.addAction(QBDownloadBroadcastReceiver.DO_DOWNLOAD);
    paramContext.addAction(QBDownloadBroadcastReceiver.TRY_DOWNLOAD);
    paramContext.addAction(QBDownloadBroadcastReceiver.DO_INSTALL);
    paramContext.addAction(QBDownloadBroadcastReceiver.NOTIFICATION_BE_DEL);
    this.mContext.registerReceiver(this.mDownloadBroadcastReceiver, paramContext);
    this.mNotification = initNotification();
  }
  
  protected int genUniquePendingIntReqCode()
  {
    return new Random().nextInt(268435455);
  }
  
  protected Notification initNotification()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(TBSResources.getString("x5_qb_download_start"));
    ((StringBuilder)localObject).append(" ");
    ((StringBuilder)localObject).append(TBSResources.getString("x5_qb_download_filename"));
    localObject = new Notification(17301633, ((StringBuilder)localObject).toString(), System.currentTimeMillis());
    this.mNotificationId = initNotificationId();
    return (Notification)localObject;
  }
  
  protected int initNotificationId()
  {
    return new Random().nextInt(268435455);
  }
  
  protected void notificationFailed(Bundle paramBundle)
  {
    this.mNotificationManager.cancel(this.mNotificationId);
    Object localObject = this.mNotification;
    ((Notification)localObject).icon = 17301634;
    ((Notification)localObject).tickerText = null;
    ((Notification)localObject).flags = 16;
    localObject = new Intent(QBDownloadBroadcastReceiver.TRY_DOWNLOAD);
    ((Intent)localObject).putExtras(paramBundle);
    paramBundle = PendingIntent.getBroadcast(this.mContext, genUniquePendingIntReqCode(), (Intent)localObject, 134217728);
    this.mNotification.when = System.currentTimeMillis();
    localObject = new Intent(QBDownloadBroadcastReceiver.NOTIFICATION_BE_DEL);
    localObject = PendingIntent.getBroadcast(this.mContext, genUniquePendingIntReqCode(), (Intent)localObject, 134217728);
    Notification localNotification = this.mNotification;
    localNotification.deleteIntent = ((PendingIntent)localObject);
    try
    {
      localObject = localNotification.getClass().getDeclaredMethod("setLatestEventInfo", new Class[] { Context.class, CharSequence.class, CharSequence.class, PendingIntent.class });
      ((Method)localObject).setAccessible(true);
      ((Method)localObject).invoke(this.mNotification, new Object[] { this.mContext, TBSResources.getString("x5_qb_download_filename"), TBSResources.getString("x5_qb_download_failed_try"), paramBundle });
      this.mNotificationManager.notify(this.mNotificationId, this.mNotification);
      return;
    }
    catch (Throwable paramBundle) {}
  }
  
  protected void notificationStart()
  {
    Object localObject1 = this.mNotification;
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append(TBSResources.getString("x5_qb_download_start"));
    ((StringBuilder)localObject2).append(" ");
    ((StringBuilder)localObject2).append(TBSResources.getString("x5_qb_download_filename"));
    ((Notification)localObject1).tickerText = ((StringBuilder)localObject2).toString();
    this.mNotification.flags = 34;
    localObject1 = new Intent(QBDownloadBroadcastReceiver.PAUSE_DOWNLOAD);
    localObject1 = PendingIntent.getBroadcast(this.mContext, genUniquePendingIntReqCode(), (Intent)localObject1, 134217728);
    this.mNotification.when = System.currentTimeMillis();
    try
    {
      localObject2 = this.mNotification.getClass().getDeclaredMethod("setLatestEventInfo", new Class[] { Context.class, CharSequence.class, CharSequence.class, PendingIntent.class });
      ((Method)localObject2).setAccessible(true);
      ((Method)localObject2).invoke(this.mNotification, new Object[] { this.mContext, TBSResources.getString("x5_qb_download_filename"), TBSResources.getString("x5_qb_download_start"), localObject1 });
      this.mNotificationManager.notify(this.mNotificationId, this.mNotification);
      return;
    }
    catch (Throwable localThrowable) {}
  }
  
  protected void notificationSuccess(String paramString, Bundle paramBundle)
  {
    this.mNotificationManager.cancel(this.mNotificationId);
  }
  
  protected void notificationUpdate(int paramInt)
  {
    Object localObject = this.mNotification;
    ((Notification)localObject).tickerText = null;
    ((Notification)localObject).flags = 34;
    localObject = new Intent(QBDownloadBroadcastReceiver.PAUSE_DOWNLOAD);
    localObject = PendingIntent.getBroadcast(this.mContext, genUniquePendingIntReqCode(), (Intent)localObject, 134217728);
    this.mNotification.when = System.currentTimeMillis();
    try
    {
      Method localMethod = this.mNotification.getClass().getDeclaredMethod("setLatestEventInfo", new Class[] { Context.class, CharSequence.class, CharSequence.class, PendingIntent.class });
      localMethod.setAccessible(true);
      Notification localNotification = this.mNotification;
      Context localContext = this.mContext;
      String str = TBSResources.getString("x5_qb_download_filename");
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(TBSResources.getString("x5_qb_download_downloading"));
      localStringBuilder.append(" ");
      localStringBuilder.append(paramInt);
      localStringBuilder.append("%");
      localMethod.invoke(localNotification, new Object[] { localContext, str, localStringBuilder.toString(), localObject });
      this.mNotificationManager.notify(this.mNotificationId, this.mNotification);
      return;
    }
    catch (Throwable localThrowable) {}
  }
  
  public void onDownloadFailed(boolean paramBoolean, Bundle paramBundle)
  {
    if (!paramBoolean) {
      notificationFailed(paramBundle);
    }
  }
  
  public void onDownloadPause(boolean paramBoolean, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("QBDownloadNotification.onDownloadPause progress=");
    localStringBuilder.append(paramInt);
    localStringBuilder.toString();
  }
  
  public void onDownloadProgress(boolean paramBoolean, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("QBDownloadNotification.onDownloadProgress progress=");
    localStringBuilder.append(paramInt);
    localStringBuilder.toString();
    if (!paramBoolean) {
      notificationUpdate(paramInt);
    }
  }
  
  public void onDownloadResume(boolean paramBoolean, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("QBDownloadNotification.onDownloadResume progress=");
    localStringBuilder.append(paramInt);
    localStringBuilder.toString();
  }
  
  public void onDownloadStart(boolean paramBoolean)
  {
    if (!paramBoolean) {
      notificationStart();
    }
  }
  
  public void onDownloadSucess(boolean paramBoolean, String paramString, Bundle paramBundle)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("QBDownloadNotification.onDownloadSucess filePath=");
    localStringBuilder.append(paramString);
    localStringBuilder.toString();
    if (!paramBoolean) {
      notificationSuccess(paramString, paramBundle);
    }
  }
  
  protected static class QBDownloadBroadcastReceiver
    extends BroadcastReceiver
  {
    public static String DO_DOWNLOAD = "com.tencent.mtt.QBDownloadBroadcastReceiver.DO_DOWNLOAD";
    public static String DO_INSTALL = "com.tencent.mtt.QBDownloadBroadcastReceiver.INSTALL";
    public static String NOTIFICATION_BE_DEL = "com.tencent.mtt.QBDownloadBroadcastReceiver.BEDEL";
    public static String PAUSE_DOWNLOAD = "com.tencent.mtt.QBDownloadBroadcastReceiver.PAUSE_DOWNLOAD";
    public static String TRY_DOWNLOAD = "com.tencent.mtt.QBDownloadBroadcastReceiver.TRY_DOWNLOAD";
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if (paramIntent != null)
      {
        String str = paramIntent.getAction();
        if (str != null)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("QBDownloadBroadcastReceiver.onReceive this=");
          localStringBuilder.append(this);
          localStringBuilder.append(" action=");
          localStringBuilder.append(str);
          localStringBuilder.toString();
          if (str.equals(PAUSE_DOWNLOAD)) {
            return;
          }
          if (str.equals(DO_DOWNLOAD)) {
            return;
          }
          if (str.equals(TRY_DOWNLOAD))
          {
            paramIntent = paramIntent.getExtras();
            QBDownloadManager.getInstance().startDownload(paramContext, paramIntent);
            return;
          }
          if (str.equals(DO_INSTALL)) {
            return;
          }
          str.equals(NOTIFICATION_BE_DEL);
        }
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\qb\QBDownloadNotification.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */