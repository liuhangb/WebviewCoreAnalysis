package com.tencent.tbs.common.ar.WEBAR;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.common.wup.WUPTaskProxy;
import com.tencent.tbs.common.ar.MTT.ARCloudMarkerInfo;
import com.tencent.tbs.common.ar.MTT.ARRetrievalInfo;
import com.tencent.tbs.common.ar.MTT.ARUserInfo;
import com.tencent.tbs.common.ar.MTT.CoordinInfo;
import com.tencent.tbs.common.ar.MTT.ImageMarkerRecognitionReq;
import com.tencent.tbs.common.ar.MTT.ImageMarkerRecognitionResp;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.wup.WUPRequest;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

public class WebARCloudRecognition
{
  private static final int ERR_COMPRESS_FAILED = 2;
  private static final int ERR_EMPTY_RESPONSE = 3;
  private static final int ERR_NETWORK_EXCEPTION = 5;
  private static final int ERR_NO_NEED_RECOGNITION = 1;
  private static final int ERR_REQUEST_FAILED = 4;
  public static final int ERR_UNKNOW = 999;
  private static final String FUNCTION_NAME = "ImageMarkerClassifyRecognition";
  private static final int RECOGNITION_SUCCESS = 0;
  private static final String SERVER_NAME = "tbs_img_recognition";
  private static final String TAG = "WebARCloudRecognition";
  private Context mApp;
  private String mAppid;
  private IWebARCloudRecognitionCallback mCallback;
  private ArrayList<String> mCategorie;
  private boolean mIsCloudRecognizing = false;
  private boolean mNeedDoRecognition = false;
  private int mScene;
  private String mSig;
  private long mTimeStamp;
  private String maccessKey;
  
  public WebARCloudRecognition(Context paramContext)
  {
    this.mApp = paramContext.getApplicationContext();
  }
  
  private void RecognitionFailed(int paramInt, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("RecognitionFailed ");
    localStringBuilder.append(paramInt);
    localStringBuilder.append(" msg:");
    localStringBuilder.append(paramString);
    localStringBuilder.toString();
    try
    {
      if (this.mCallback != null) {
        this.mCallback.onCloudRecognitionEvent(1, paramInt, paramString);
      }
      this.mIsCloudRecognizing = false;
      return;
    }
    finally {}
  }
  
  private void RecognitionSuccess(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("RecognitionSuccess jsonResult: ");
    localStringBuilder.append(paramString);
    localStringBuilder.toString();
    try
    {
      if (this.mCallback != null) {
        this.mCallback.onCloudRecognitionEvent(0, 0, paramString);
      }
      this.mIsCloudRecognizing = false;
      return;
    }
    finally {}
  }
  
  private boolean canDoRecognition()
  {
    try
    {
      boolean bool = this.mNeedDoRecognition;
      return bool;
    }
    finally {}
  }
  
  public void doRecognition(byte[] paramArrayOfByte)
  {
    if (!canDoRecognition()) {
      return;
    }
    if (paramArrayOfByte.length == 0)
    {
      RecognitionFailed(2, "no data to upload");
      return;
    }
    Object localObject1 = new ARUserInfo(GUIDFactory.getInstance().getStrGuid(), "");
    ArrayList localArrayList = new ArrayList();
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("maccessKey = ");
    ((StringBuilder)localObject2).append(this.maccessKey);
    ((StringBuilder)localObject2).toString();
    localArrayList.add(this.maccessKey);
    localObject2 = this.mCategorie;
    if (paramArrayOfByte.length < 4)
    {
      RecognitionFailed(1, "no client/data to upload");
      return;
    }
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("appid: ");
    ((StringBuilder)localObject2).append(this.mAppid);
    ((StringBuilder)localObject2).append(" sig: ");
    ((StringBuilder)localObject2).append(this.mSig);
    ((StringBuilder)localObject2).append(" maccessid: ");
    ((StringBuilder)localObject2).append(this.maccessKey);
    ((StringBuilder)localObject2).append(" mTimeStamp:");
    ((StringBuilder)localObject2).append(this.mTimeStamp);
    ((StringBuilder)localObject2).toString();
    paramArrayOfByte = new ImageMarkerRecognitionReq((ARUserInfo)localObject1, this.mAppid, localArrayList, this.mSig, this.mScene, paramArrayOfByte, this.mTimeStamp);
    localObject1 = new WUPRequest();
    ((WUPRequest)localObject1).setClassLoader(getClass().getClassLoader());
    ((WUPRequest)localObject1).setServerName("tbs_img_recognition");
    ((WUPRequest)localObject1).setFuncName("ImageMarkerClassifyRecognition");
    ((WUPRequest)localObject1).put("req", paramArrayOfByte);
    ((WUPRequest)localObject1).setRequestCallBack(new IWUPRequestCallBack()
    {
      public void onWUPTaskFail(WUPRequestBase paramAnonymousWUPRequestBase)
      {
        int i = 4;
        if (paramAnonymousWUPRequestBase == null)
        {
          WebARCloudRecognition.this.RecognitionFailed(4, "wup task fail");
          return;
        }
        if (paramAnonymousWUPRequestBase.getErrorCode() == 63528) {
          i = 5;
        }
        Object localObject = paramAnonymousWUPRequestBase.getFailedReason();
        WebARCloudRecognition.this.RecognitionFailed(i, (String)localObject);
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("onWUPTaskFail error code:");
        ((StringBuilder)localObject).append(paramAnonymousWUPRequestBase.getErrorCode());
        ((StringBuilder)localObject).append(" reason:");
        ((StringBuilder)localObject).append(paramAnonymousWUPRequestBase.getFailedReason());
        ((StringBuilder)localObject).toString();
        paramAnonymousWUPRequestBase.setRequestCallBack(null);
      }
      
      public void onWUPTaskSuccess(WUPRequestBase paramAnonymousWUPRequestBase, WUPResponseBase paramAnonymousWUPResponseBase)
      {
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append("onWUPTaskSuccess ");
        ((StringBuilder)localObject).append(System.currentTimeMillis());
        ((StringBuilder)localObject).toString();
        paramAnonymousWUPRequestBase.setRequestCallBack(null);
        if (paramAnonymousWUPResponseBase == null)
        {
          WebARCloudRecognition.this.RecognitionFailed(3, "response is empty");
          return;
        }
        paramAnonymousWUPRequestBase = paramAnonymousWUPResponseBase.getResponseData("rsp");
        if ((paramAnonymousWUPRequestBase != null) && ((paramAnonymousWUPRequestBase instanceof ImageMarkerRecognitionResp)))
        {
          localObject = (ImageMarkerRecognitionResp)paramAnonymousWUPRequestBase;
          paramAnonymousWUPRequestBase = new StringBuilder();
          paramAnonymousWUPRequestBase.append("imageResp.vMarker.size()=");
          paramAnonymousWUPRequestBase.append(((ImageMarkerRecognitionResp)localObject).vMarker.size());
          paramAnonymousWUPRequestBase.toString();
          paramAnonymousWUPRequestBase = new StringBuilder();
          paramAnonymousWUPRequestBase.append("imageResp.vRetrievalInfo.size()=");
          paramAnonymousWUPRequestBase.append(((ImageMarkerRecognitionResp)localObject).vRetrievalInfo.size());
          paramAnonymousWUPRequestBase.toString();
          paramAnonymousWUPRequestBase = new StringBuilder();
          paramAnonymousWUPRequestBase.append("imageResp.iRetCode=");
          paramAnonymousWUPRequestBase.append(((ImageMarkerRecognitionResp)localObject).iRetCode);
          paramAnonymousWUPRequestBase.toString();
          try
          {
            JSONObject localJSONObject1 = new JSONObject();
            JSONArray localJSONArray1 = new JSONArray();
            JSONArray localJSONArray2 = new JSONArray();
            localJSONObject1.put("sRetCode", ((ImageMarkerRecognitionResp)localObject).iRetCode);
            localJSONObject1.put("sMsg", ((ImageMarkerRecognitionResp)localObject).sMsg);
            localJSONObject1.put("sResponsType", ((ImageMarkerRecognitionResp)localObject).iResponsType);
            localJSONObject1.put("sKey", ((ImageMarkerRecognitionResp)localObject).sKey);
            Iterator localIterator = ((ImageMarkerRecognitionResp)localObject).vMarker.iterator();
            while (localIterator.hasNext())
            {
              ARCloudMarkerInfo localARCloudMarkerInfo = (ARCloudMarkerInfo)localIterator.next();
              paramAnonymousWUPRequestBase = new StringBuilder();
              paramAnonymousWUPRequestBase.append("onReceiveValue item.sUrl:");
              paramAnonymousWUPRequestBase.append(localARCloudMarkerInfo.sUrl);
              paramAnonymousWUPRequestBase.toString();
              JSONObject localJSONObject2 = new JSONObject();
              paramAnonymousWUPRequestBase = new String(localARCloudMarkerInfo.vMetaData, "UTF-8");
              localJSONObject2.put("sTid", localARCloudMarkerInfo.sTid);
              localJSONObject2.put("sTargetName", localARCloudMarkerInfo.sTargetName);
              localJSONObject2.put("sMetaData", paramAnonymousWUPRequestBase);
              localJSONObject2.put("sModifyTime", localARCloudMarkerInfo.sModifyTime);
              paramAnonymousWUPResponseBase = localARCloudMarkerInfo.sUrl;
              paramAnonymousWUPRequestBase = paramAnonymousWUPResponseBase;
              if (!TextUtils.isEmpty(localARCloudMarkerInfo.sUrl))
              {
                paramAnonymousWUPRequestBase = paramAnonymousWUPResponseBase;
                if (!localARCloudMarkerInfo.sUrl.startsWith("http"))
                {
                  paramAnonymousWUPRequestBase = paramAnonymousWUPResponseBase;
                  if (!localARCloudMarkerInfo.sUrl.startsWith("https"))
                  {
                    paramAnonymousWUPRequestBase = new StringBuilder();
                    paramAnonymousWUPRequestBase.append("http://");
                    paramAnonymousWUPRequestBase.append(paramAnonymousWUPResponseBase);
                    paramAnonymousWUPRequestBase = paramAnonymousWUPRequestBase.toString();
                    paramAnonymousWUPResponseBase = new StringBuilder();
                    paramAnonymousWUPResponseBase.append("markerUrl:");
                    paramAnonymousWUPResponseBase.append(paramAnonymousWUPRequestBase);
                    paramAnonymousWUPResponseBase.toString();
                  }
                }
              }
              localJSONObject2.put("sUrl", paramAnonymousWUPRequestBase);
              localJSONArray2.put(localJSONObject2);
            }
            paramAnonymousWUPRequestBase = ((ImageMarkerRecognitionResp)localObject).vRetrievalInfo.iterator();
            while (paramAnonymousWUPRequestBase.hasNext())
            {
              paramAnonymousWUPResponseBase = (ARRetrievalInfo)paramAnonymousWUPRequestBase.next();
              localObject = new JSONObject();
              ((JSONObject)localObject).put("sLabel", paramAnonymousWUPResponseBase.sLabel);
              ((JSONObject)localObject).put("sTop", paramAnonymousWUPResponseBase.stCoodinInfo.iTop);
              ((JSONObject)localObject).put("sLeft", paramAnonymousWUPResponseBase.stCoodinInfo.iLeft);
              ((JSONObject)localObject).put("sBottom", paramAnonymousWUPResponseBase.stCoodinInfo.iBottom);
              ((JSONObject)localObject).put("sRight", paramAnonymousWUPResponseBase.stCoodinInfo.iRight);
              localJSONArray1.put(localObject);
            }
            localJSONObject1.put("vRetrievalInfo", localJSONArray1);
            localJSONObject1.put("vMarker", localJSONArray2);
            WebARCloudRecognition.this.RecognitionSuccess(localJSONObject1.toString());
            return;
          }
          catch (Exception paramAnonymousWUPRequestBase)
          {
            paramAnonymousWUPRequestBase.printStackTrace();
            WebARCloudRecognition.this.RecognitionFailed(999, paramAnonymousWUPRequestBase.toString());
          }
        }
      }
    });
    WUPTaskProxy.send((WUPRequestBase)localObject1);
  }
  
  public void recognizeFrame(final byte[] paramArrayOfByte)
  {
    try
    {
      if (this.mIsCloudRecognizing) {
        return;
      }
      BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          WebARCloudRecognition.this.doRecognition(paramArrayOfByte);
        }
      });
      return;
    }
    finally {}
  }
  
  public void setCategories(String paramString1, ArrayList<String> paramArrayList, String paramString2, String paramString3, long paramLong, int paramInt)
  {
    this.maccessKey = paramString1;
    this.mCategorie = paramArrayList;
    this.mAppid = paramString2;
    this.mTimeStamp = paramLong;
    this.mScene = paramInt;
    this.mSig = paramString3;
    try
    {
      this.mNeedDoRecognition = true;
      return;
    }
    finally {}
  }
  
  public void setCloudRecognitionCallback(IWebARCloudRecognitionCallback paramIWebARCloudRecognitionCallback)
  {
    try
    {
      this.mCallback = paramIWebARCloudRecognitionCallback;
      return;
    }
    finally {}
  }
  
  public void stopCloudRecognition()
  {
    try
    {
      this.mNeedDoRecognition = false;
      return;
    }
    finally {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ar\WEBAR\WebARCloudRecognition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */