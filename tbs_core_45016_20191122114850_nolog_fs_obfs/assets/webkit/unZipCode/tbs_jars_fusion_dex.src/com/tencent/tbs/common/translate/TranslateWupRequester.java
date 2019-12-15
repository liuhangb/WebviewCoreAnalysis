package com.tencent.tbs.common.translate;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.tbs.common.MTT.DetectReq;
import com.tencent.tbs.common.MTT.DetectRsp;
import com.tencent.tbs.common.MTT.TranslateReq;
import com.tencent.tbs.common.MTT.TranslateRsp;
import com.tencent.tbs.common.wup.WUPRequest;
import com.tencent.tbs.common.wup.WUPTaskProxy;

public class TranslateWupRequester
  implements IWUPRequestCallBack
{
  private static TranslateWupRequester mInstance;
  private final String SERVER_NAME = "translate";
  private String mBrandWording = null;
  private String mDetectLang = null;
  private Object mDetectLock = new Object();
  private int mErrorPercent = 0;
  private Object mTranslateLock = new Object();
  private String mTranslateResult = null;
  private boolean mTranslateSuccessful = false;
  
  public static TranslateWupRequester getInstance()
  {
    if (mInstance == null) {
      try
      {
        if (mInstance == null) {
          mInstance = new TranslateWupRequester();
        }
      }
      finally {}
    }
    return mInstance;
  }
  
  private void notifyDetectDone()
  {
    try
    {
      synchronized (this.mDetectLock)
      {
        this.mDetectLock.notifyAll();
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
  }
  
  private void notifyTranslateDone()
  {
    try
    {
      synchronized (this.mTranslateLock)
      {
        this.mTranslateLock.notifyAll();
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
  }
  
  public String detectLanguage(String arg1, String paramString2)
  {
    try
    {
      this.mDetectLang = null;
      DetectReq localDetectReq = new DetectReq();
      localDetectReq.sText = ???;
      localDetectReq.sUrl = paramString2;
      ??? = new WUPRequest("translate", "detect");
      ???.setRequestCallBack(this);
      ???.setEncodeName("UTF-8");
      ???.put("req", localDetectReq);
      ???.setBindObject(null);
      WUPTaskProxy.send(???);
      try
      {
        synchronized (this.mDetectLock)
        {
          this.mDetectLock.wait();
        }
      }
      catch (InterruptedException paramString2)
      {
        paramString2.printStackTrace();
        ??? = this.mDetectLang;
        return (String)???;
      }
      throw paramString2;
    }
    finally {}
  }
  
  public void onWUPTaskFail(WUPRequestBase paramWUPRequestBase)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("TranslateWupRequester onWUPTaskFail error code:");
    localStringBuilder.append(paramWUPRequestBase.getErrorCode());
    localStringBuilder.toString();
    if (paramWUPRequestBase.getFuncName().equals("translate"))
    {
      this.mTranslateSuccessful = false;
      notifyTranslateDone();
      return;
    }
    if (paramWUPRequestBase.getFuncName().equals("detect"))
    {
      this.mDetectLang = null;
      notifyDetectDone();
    }
  }
  
  public void onWUPTaskSuccess(WUPRequestBase paramWUPRequestBase, WUPResponseBase paramWUPResponseBase)
  {
    int i = paramWUPResponseBase.getReturnCode().intValue();
    paramWUPResponseBase = paramWUPResponseBase.get("rsp");
    if (paramWUPRequestBase.getFuncName().equals("translate"))
    {
      paramWUPRequestBase = (TranslateRsp)paramWUPResponseBase;
      if ((paramWUPRequestBase != null) && (i == 0))
      {
        this.mTranslateResult = paramWUPRequestBase.sResult;
        this.mBrandWording = paramWUPRequestBase.sBrandWording;
        this.mErrorPercent = paramWUPRequestBase.iErrorPercent;
        this.mTranslateSuccessful = true;
        paramWUPResponseBase = new StringBuilder();
        paramWUPResponseBase.append("TranslateWupRequester rsp.result=");
        paramWUPResponseBase.append(paramWUPRequestBase.sResult);
        paramWUPResponseBase.toString();
      }
      else
      {
        this.mTranslateResult = "";
        this.mBrandWording = "";
        this.mErrorPercent = 100;
        this.mTranslateSuccessful = false;
      }
      notifyTranslateDone();
      return;
    }
    if (paramWUPRequestBase.getFuncName().equals("detect"))
    {
      paramWUPRequestBase = (DetectRsp)paramWUPResponseBase;
      if ((paramWUPRequestBase != null) && (i == 0))
      {
        this.mDetectLang = paramWUPRequestBase.sLang;
        paramWUPRequestBase = new StringBuilder();
        paramWUPRequestBase.append("detect mDetectLang=");
        paramWUPRequestBase.append(this.mDetectLang);
        paramWUPRequestBase.toString();
      }
      else if (i == -8)
      {
        this.mDetectLang = "forbidden_url";
        Log.e("odin_trans", "current url is forbidden to translate...");
      }
      notifyDetectDone();
    }
  }
  
  public Bundle translate(String arg1, String paramString2, String paramString3, String paramString4)
  {
    try
    {
      boolean bool = TextUtils.isEmpty(???);
      if (bool) {
        return null;
      }
      this.mTranslateResult = null;
      this.mTranslateSuccessful = false;
      TranslateReq localTranslateReq = new TranslateReq();
      localTranslateReq.sText = ???;
      if (!TextUtils.isEmpty(paramString2)) {
        localTranslateReq.sSrcLang = paramString2;
      }
      localTranslateReq.sDestLang = paramString3;
      localTranslateReq.sUrl = paramString4;
      ??? = new WUPRequest("translate", "translate");
      ???.setRequestCallBack(this);
      ???.setEncodeName("UTF-8");
      ???.put("req", localTranslateReq);
      ???.setBindObject(null);
      WUPTaskProxy.send(???);
      try
      {
        synchronized (this.mTranslateLock)
        {
          this.mTranslateLock.wait();
        }
      }
      catch (InterruptedException paramString2)
      {
        paramString2.printStackTrace();
        if (this.mTranslateSuccessful)
        {
          ??? = new Bundle();
          ???.putString("translateResult", this.mTranslateResult);
          ???.putString("brandWording", this.mBrandWording);
          ???.putInt("errorPercent", this.mErrorPercent);
          return (Bundle)???;
        }
        return null;
      }
      throw paramString2;
    }
    finally {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\translate\TranslateWupRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */