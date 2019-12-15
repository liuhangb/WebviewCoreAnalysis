package com.tencent.tbs.tbsshell.common.x5core;

import android.os.Handler;
import com.tencent.common.http.Apn;
import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.baseinfo.TbsUserInfo;
import com.tencent.tbs.common.task.IQProxyCheckCallBack;
import com.tencent.tbs.tbsshell.TBSShell;
import java.net.URL;

public class QProxyDetect
{
  private static final int CHECK_HTTPS_TUNNEL_PROXY_DELAY = 30000;
  private static final int CHECK_QPROXY_DELAY = 300000;
  private static final String TAG = "QProxyDetect";
  private static QProxyDetect mInstance;
  int ii = 0;
  private IQProxyCheckCallBack mCheckCallBack = null;
  boolean mIsCheckHttpTunnelProxy = false;
  boolean mIsCheckQProxy = false;
  private QProxyWorkerThread mQProxyWorker = new QProxyWorkerThread();
  private X5CoreInit mX5CoreInit = null;
  
  public static QProxyDetect getInstance()
  {
    if (mInstance == null) {
      mInstance = new QProxyDetect();
    }
    return mInstance;
  }
  
  private void startQProxyDetect(URL paramURL, IQProxyCheckCallBack paramIQProxyCheckCallBack)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("QProxyDetect startQProxyDetect()-callBack = ");
    localStringBuilder.append(paramIQProxyCheckCallBack);
    localStringBuilder.append(", mIsCheckQProxy = ");
    localStringBuilder.append(this.mIsCheckQProxy);
    localStringBuilder.append(", qqroxy = ");
    localStringBuilder.append(paramURL);
    LogUtils.d("QProxyDetect", localStringBuilder.toString());
    if (paramURL.getPort() == 8091)
    {
      if (this.mIsCheckHttpTunnelProxy) {
        return;
      }
      this.mIsCheckHttpTunnelProxy = true;
    }
    else
    {
      if (this.mIsCheckQProxy) {
        return;
      }
      this.mIsCheckQProxy = true;
    }
    this.mCheckCallBack = paramIQProxyCheckCallBack;
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("******QProxyDetect startQProxyDetect()-callBack = ");
    localStringBuilder.append(paramIQProxyCheckCallBack);
    localStringBuilder.append(", mIsCheckQProxy = ");
    localStringBuilder.append(this.mIsCheckQProxy);
    localStringBuilder.append(", qqroxy = ");
    localStringBuilder.append(paramURL);
    LogUtils.d("QProxyDetect", localStringBuilder.toString());
    if (this.mX5CoreInit == null) {
      this.mX5CoreInit = new X5CoreInit(null, true, false, TBSShell.mTbsResourcesPath);
    }
    this.mX5CoreInit.startQProxyDetectWithSpdy(paramURL);
  }
  
  public void notifyQProxyDetectResult(Boolean paramBoolean, final String paramString)
  {
    if (paramBoolean.booleanValue())
    {
      this.mIsCheckQProxy = false;
      paramBoolean = this.mCheckCallBack;
      if (paramBoolean != null) {
        paramBoolean.onCheckTaskComplete(paramString);
      }
    }
    else
    {
      this.mIsCheckQProxy = false;
      int j = 300000;
      int i = j;
      if (Apn.sApnTypeS != 4)
      {
        i = j;
        if (TbsUserInfo.getInstance().isGreatKingCard()) {
          i = 30000;
        }
      }
      this.mQProxyWorker.postDelayed(new Runnable()
      {
        public void run()
        {
          if (QProxyDetect.this.mCheckCallBack != null) {
            QProxyDetect.this.mCheckCallBack.onCheckTaskFailed(paramString);
          }
        }
      }, i);
      LogUtils.d("QProxyDetect", "QProxyDetect onTaskFailed()-CHECK_QPROXY_DELAY = 300000");
    }
  }
  
  public void setCallback(IQProxyCheckCallBack paramIQProxyCheckCallBack)
  {
    int i = this.ii;
    if (i == 0)
    {
      this.mCheckCallBack = paramIQProxyCheckCallBack;
      this.ii = (i + 1);
    }
  }
  
  public void setX5CoreInit(X5CoreInit paramX5CoreInit)
  {
    this.mX5CoreInit = paramX5CoreInit;
    paramX5CoreInit = new StringBuilder();
    paramX5CoreInit.append("QProxyDetect--setX5CoreInit()--mX5CoreInit = ");
    paramX5CoreInit.append(this.mX5CoreInit);
    LogUtils.d("QProxyDetect", paramX5CoreInit.toString());
  }
  
  public void startQProxyDetectWithSpdy(final URL paramURL, final IQProxyCheckCallBack paramIQProxyCheckCallBack)
  {
    this.mQProxyWorker.post(new Runnable()
    {
      public void run()
      {
        QProxyDetect.this.startQProxyDetect(paramURL, paramIQProxyCheckCallBack);
      }
    });
    paramIQProxyCheckCallBack = new StringBuilder();
    paramIQProxyCheckCallBack.append("QProxyDetect--mQProxyWorker.post--startQProxyDetect QProxyAddress=");
    paramIQProxyCheckCallBack.append(paramURL);
    LogUtils.d("QProxyDetect", paramIQProxyCheckCallBack.toString());
  }
  
  private class QProxyWorkerThread
  {
    private Handler mHandler = null;
    
    public QProxyWorkerThread() {}
    
    public final boolean post(Runnable paramRunnable)
    {
      return this.mHandler.post(paramRunnable);
    }
    
    public final boolean postDelayed(Runnable paramRunnable, long paramLong)
    {
      return this.mHandler.postDelayed(paramRunnable, paramLong);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\x5core\QProxyDetect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */