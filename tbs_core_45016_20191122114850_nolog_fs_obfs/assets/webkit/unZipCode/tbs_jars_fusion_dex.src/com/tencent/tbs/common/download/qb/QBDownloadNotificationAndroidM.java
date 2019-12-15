package com.tencent.tbs.common.download.qb;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.tbs.common.resources.TBSResources;

@TargetApi(21)
class QBDownloadNotificationAndroidM
  extends QBDownloadNotification
{
  private Notification.Builder mNotificationBuilder;
  
  public QBDownloadNotificationAndroidM(Context paramContext)
  {
    super(paramContext);
  }
  
  protected Notification initNotification()
  {
    Object localObject = new Notification.Builder(this.mContext).setSmallIcon(17301633);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(TBSResources.getString("x5_qb_download_start"));
    localStringBuilder.append(" ");
    localStringBuilder.append(TBSResources.getString("x5_qb_download_filename"));
    this.mNotificationBuilder = ((Notification.Builder)localObject).setTicker(localStringBuilder.toString()).setWhen(System.currentTimeMillis());
    localObject = this.mNotificationBuilder.build();
    this.mNotificationId = initNotificationId();
    return (Notification)localObject;
  }
  
  protected void notificationFailed(Bundle paramBundle)
  {
    this.mNotificationManager.cancel(this.mNotificationId);
    this.mNotificationBuilder.setSmallIcon(17301634);
    this.mNotificationBuilder.setTicker(null);
    this.mNotificationBuilder.setOngoing(false);
    this.mNotificationBuilder.setAutoCancel(true);
    this.mNotificationBuilder.setWhen(System.currentTimeMillis());
    Object localObject = new Intent(QBDownloadNotification.QBDownloadBroadcastReceiver.TRY_DOWNLOAD);
    ((Intent)localObject).putExtras(paramBundle);
    paramBundle = PendingIntent.getBroadcast(this.mContext, genUniquePendingIntReqCode(), (Intent)localObject, 134217728);
    localObject = new Intent(QBDownloadNotification.QBDownloadBroadcastReceiver.NOTIFICATION_BE_DEL);
    localObject = PendingIntent.getBroadcast(this.mContext, genUniquePendingIntReqCode(), (Intent)localObject, 134217728);
    this.mNotificationBuilder.setDeleteIntent((PendingIntent)localObject);
    this.mNotificationBuilder.setContentIntent(paramBundle);
    this.mNotificationBuilder.setContentTitle(TBSResources.getString("x5_qb_download_filename"));
    this.mNotificationBuilder.setContentText(TBSResources.getString("x5_qb_download_failed_try"));
    this.mNotificationManager.notify(this.mNotificationId, this.mNotificationBuilder.build());
  }
  
  protected void notificationStart()
  {
    Object localObject = this.mNotificationBuilder;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(TBSResources.getString("x5_qb_download_start"));
    localStringBuilder.append(" ");
    localStringBuilder.append(TBSResources.getString("x5_qb_download_filename"));
    ((Notification.Builder)localObject).setTicker(localStringBuilder.toString());
    this.mNotificationBuilder.setOngoing(true);
    this.mNotificationBuilder.setWhen(System.currentTimeMillis());
    localObject = new Intent(QBDownloadNotification.QBDownloadBroadcastReceiver.PAUSE_DOWNLOAD);
    localObject = PendingIntent.getBroadcast(this.mContext, genUniquePendingIntReqCode(), (Intent)localObject, 134217728);
    this.mNotificationBuilder.setContentIntent((PendingIntent)localObject);
    this.mNotificationBuilder.setContentTitle(TBSResources.getString("x5_qb_download_filename"));
    this.mNotificationBuilder.setContentText(TBSResources.getString("x5_qb_download_start"));
    this.mNotificationManager.notify(this.mNotificationId, this.mNotificationBuilder.build());
  }
  
  protected void notificationSuccess(String paramString, Bundle paramBundle)
  {
    this.mNotificationManager.cancel(this.mNotificationId);
  }
  
  protected void notificationUpdate(int paramInt)
  {
    this.mNotificationBuilder.setTicker(null);
    this.mNotificationBuilder.setOngoing(true);
    this.mNotificationBuilder.setWhen(System.currentTimeMillis());
    Object localObject = new Intent(QBDownloadNotification.QBDownloadBroadcastReceiver.PAUSE_DOWNLOAD);
    localObject = PendingIntent.getBroadcast(this.mContext, genUniquePendingIntReqCode(), (Intent)localObject, 134217728);
    this.mNotificationBuilder.setContentIntent((PendingIntent)localObject);
    this.mNotificationBuilder.setContentTitle(TBSResources.getString("x5_qb_download_filename"));
    localObject = this.mNotificationBuilder;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(TBSResources.getString("x5_qb_download_downloading"));
    localStringBuilder.append(" ");
    localStringBuilder.append(paramInt);
    localStringBuilder.append("%");
    ((Notification.Builder)localObject).setContentText(localStringBuilder.toString());
    this.mNotificationManager.notify(this.mNotificationId, this.mNotificationBuilder.build());
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\qb\QBDownloadNotificationAndroidM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */