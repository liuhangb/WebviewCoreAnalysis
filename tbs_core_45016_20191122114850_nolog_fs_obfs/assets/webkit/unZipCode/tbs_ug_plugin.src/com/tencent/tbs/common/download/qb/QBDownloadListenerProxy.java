package com.tencent.tbs.common.download.qb;

import android.os.Bundle;

class QBDownloadListenerProxy
  implements QBDownloadListener
{
  private Object mDecorator;
  
  public QBDownloadListenerProxy(Object paramObject)
  {
    this.mDecorator = paramObject;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("QBDownloadListenerProxy decorator=");
    localStringBuilder.append(paramObject);
    localStringBuilder.toString();
  }
  
  public void onDownloadFailed(boolean paramBoolean, Bundle paramBundle)
  {
    Object localObject = this.mDecorator;
    if (localObject != null) {
      QBDownloadProxy.invokeMethod(localObject, "onDownloadFailed", new Class[] { Boolean.TYPE, Bundle.class }, new Object[] { Boolean.valueOf(paramBoolean), paramBundle });
    }
  }
  
  public void onDownloadPause(boolean paramBoolean, int paramInt)
  {
    Object localObject = this.mDecorator;
    if (localObject != null) {
      QBDownloadProxy.invokeMethod(localObject, "onDownloadPause", new Class[] { Boolean.TYPE, Integer.TYPE }, new Object[] { Boolean.valueOf(paramBoolean), Integer.valueOf(paramInt) });
    }
  }
  
  public void onDownloadProgress(boolean paramBoolean, int paramInt)
  {
    Object localObject = this.mDecorator;
    if (localObject != null) {
      QBDownloadProxy.invokeMethod(localObject, "onDownloadProgress", new Class[] { Boolean.TYPE, Integer.TYPE }, new Object[] { Boolean.valueOf(paramBoolean), Integer.valueOf(paramInt) });
    }
  }
  
  public void onDownloadResume(boolean paramBoolean, int paramInt)
  {
    Object localObject = this.mDecorator;
    if (localObject != null) {
      QBDownloadProxy.invokeMethod(localObject, "onDownloadResume", new Class[] { Boolean.TYPE, Integer.TYPE }, new Object[] { Boolean.valueOf(paramBoolean), Integer.valueOf(paramInt) });
    }
  }
  
  public void onDownloadStart(boolean paramBoolean)
  {
    Object localObject = this.mDecorator;
    if (localObject != null) {
      QBDownloadProxy.invokeMethod(localObject, "onDownloadStart", new Class[] { Boolean.TYPE }, new Object[] { Boolean.valueOf(paramBoolean) });
    }
  }
  
  public void onDownloadSucess(boolean paramBoolean, String paramString, Bundle paramBundle)
  {
    Object localObject = this.mDecorator;
    if (localObject != null) {
      QBDownloadProxy.invokeMethod(localObject, "onDownloadSucess", new Class[] { Boolean.TYPE, String.class, Bundle.class }, new Object[] { Boolean.valueOf(paramBoolean), paramString, paramBundle });
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\qb\QBDownloadListenerProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */