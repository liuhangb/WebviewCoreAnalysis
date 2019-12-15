package com.tencent.tbs.common.videosearch;

import android.util.Log;
import android.webkit.ValueCallback;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.tbs.common.MTT.SmartBox_GetVideoInfoReq;
import com.tencent.tbs.common.MTT.SmartBox_GetVideoInfoRsp;
import com.tencent.tbs.common.MTT.SmartBox_PicInfo;
import com.tencent.tbs.common.MTT.SmartBox_VideoInfo;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.utils.TbsInfoUtils;
import com.tencent.tbs.common.wup.WUPRequest;
import com.tencent.tbs.common.wup.WUPTaskProxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class QBPageVideoSearchWup
  implements IWUPRequestCallBack
{
  private static final String TAG = "QBPageVideoSearchWup";
  private static QBPageVideoSearchWup mInstance;
  private static boolean skip = false;
  private ValueCallback<Map<String, String>> mCallback = null;
  
  private QBPageVideoSearchWup()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("QBPageVideoSearchWup NEW Thread =");
    localStringBuilder.append(Thread.currentThread().getName());
    localStringBuilder.toString();
  }
  
  public static QBPageVideoSearchWup getInstance()
  {
    if (mInstance == null) {
      try
      {
        if (mInstance == null)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("QBPageVideoSearchWup NEW Thread =");
          localStringBuilder.append(Thread.currentThread().getName());
          localStringBuilder.toString();
          mInstance = new QBPageVideoSearchWup();
        }
      }
      finally {}
    }
    return mInstance;
  }
  
  public WUPRequest getSearchWupRequest(Map<String, String> paramMap)
  {
    if (paramMap == null) {
      return null;
    }
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("getSearchWupRequest ");
    ((StringBuilder)localObject1).append(paramMap.toString());
    ((StringBuilder)localObject1).toString();
    localObject1 = new WUPRequest("SearchVideoWithPic", "getVideoInfo");
    try
    {
      SmartBox_GetVideoInfoReq localSmartBox_GetVideoInfoReq = new SmartBox_GetVideoInfoReq();
      ArrayList localArrayList = new ArrayList();
      Object localObject2 = new SmartBox_PicInfo();
      ((SmartBox_PicInfo)localObject2).sUrl = ((String)paramMap.get("imageUrl"));
      ((SmartBox_PicInfo)localObject2).sMd5 = ((String)paramMap.get("md5"));
      ((SmartBox_PicInfo)localObject2).iHeight = Integer.parseInt((String)paramMap.get("height"));
      ((SmartBox_PicInfo)localObject2).iWidth = Integer.parseInt((String)paramMap.get("width"));
      localArrayList.add(localObject2);
      localSmartBox_GetVideoInfoReq.sGuid = GUIDFactory.getInstance().getStrGuid();
      localSmartBox_GetVideoInfoReq.sQua2 = TbsInfoUtils.getQUA2();
      localSmartBox_GetVideoInfoReq.sUrl = ((String)paramMap.get("mainUrl"));
      paramMap = new URL(localSmartBox_GetVideoInfoReq.sUrl).getHost();
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("getSearchWupRequest domain");
      ((StringBuilder)localObject2).append(paramMap);
      ((StringBuilder)localObject2).toString();
      localSmartBox_GetVideoInfoReq.sDomain = paramMap;
      localSmartBox_GetVideoInfoReq.vecPicInfo = localArrayList;
      ((WUPRequest)localObject1).put("req", localSmartBox_GetVideoInfoReq);
      return (WUPRequest)localObject1;
    }
    catch (Exception paramMap) {}
    return null;
  }
  
  public void onVideoInfo(WUPResponseBase paramWUPResponseBase, HashMap<String, String> paramHashMap)
  {
    if (paramWUPResponseBase != null)
    {
      paramWUPResponseBase = (SmartBox_GetVideoInfoRsp)paramWUPResponseBase.get("rsp");
      if (paramWUPResponseBase != null)
      {
        int i = paramWUPResponseBase.iRetCode;
        if (i != 0)
        {
          paramWUPResponseBase = new StringBuilder();
          paramWUPResponseBase.append("onVideoInfo iRetCode Error: ");
          paramWUPResponseBase.append(i);
          LogUtils.d("QBPageVideoSearchWup", paramWUPResponseBase.toString());
          return;
        }
        Object localObject3 = paramWUPResponseBase.sUrl;
        Object localObject2 = paramWUPResponseBase.sCue;
        Object localObject1 = paramWUPResponseBase.sShorterCue;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(i);
        localStringBuilder.append("");
        paramHashMap.put("code", localStringBuilder.toString());
        paramHashMap.put("mainUrl", localObject3);
        paramHashMap.put("cue", localObject2);
        paramHashMap.put("shorterCue", localObject1);
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("onVideoInfo iRetCode: ");
        localStringBuilder.append(i);
        LogUtils.d("QBPageVideoSearchWup", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("onVideoInfo url: ");
        localStringBuilder.append((String)localObject3);
        LogUtils.d("QBPageVideoSearchWup", localStringBuilder.toString());
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("onVideoInfo cue: ");
        ((StringBuilder)localObject3).append((String)localObject2);
        LogUtils.d("QBPageVideoSearchWup", ((StringBuilder)localObject3).toString());
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("onVideoInfo shorterCue: ");
        ((StringBuilder)localObject2).append((String)localObject1);
        LogUtils.d("QBPageVideoSearchWup", ((StringBuilder)localObject2).toString());
        paramWUPResponseBase = paramWUPResponseBase.vecVideoInfo;
        if (paramWUPResponseBase != null)
        {
          paramWUPResponseBase = paramWUPResponseBase.iterator();
          while (paramWUPResponseBase.hasNext())
          {
            localObject1 = (SmartBox_VideoInfo)paramWUPResponseBase.next();
            paramHashMap.put("imageUrl", ((SmartBox_VideoInfo)localObject1).sUrl);
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("onVideoInfo imageUrl: ");
            ((StringBuilder)localObject2).append(((SmartBox_VideoInfo)localObject1).sUrl);
            LogUtils.d("QBPageVideoSearchWup", ((StringBuilder)localObject2).toString());
            paramHashMap.put("title", ((SmartBox_VideoInfo)localObject1).sTitle);
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("onVideoInfo title: ");
            ((StringBuilder)localObject2).append(((SmartBox_VideoInfo)localObject1).sTitle);
            LogUtils.d("QBPageVideoSearchWup", ((StringBuilder)localObject2).toString());
            paramHashMap.put("vid", ((SmartBox_VideoInfo)localObject1).sVid);
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("onVideoInfo sVid: ");
            ((StringBuilder)localObject2).append(((SmartBox_VideoInfo)localObject1).sVid);
            LogUtils.d("QBPageVideoSearchWup", ((StringBuilder)localObject2).toString());
            paramHashMap.put("duration", ((SmartBox_VideoInfo)localObject1).sDuration);
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("onVideoInfo sDuration: ");
            ((StringBuilder)localObject2).append(((SmartBox_VideoInfo)localObject1).sDuration);
            LogUtils.d("QBPageVideoSearchWup", ((StringBuilder)localObject2).toString());
            paramHashMap.put("iconUrl", ((SmartBox_VideoInfo)localObject1).sIcon);
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("onVideoInfo sIcon: ");
            ((StringBuilder)localObject2).append(((SmartBox_VideoInfo)localObject1).sIcon);
            LogUtils.d("QBPageVideoSearchWup", ((StringBuilder)localObject2).toString());
            paramHashMap.put("jumpUrl", ((SmartBox_VideoInfo)localObject1).sJumpUrl);
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("onVideoInfo sJumpUrl: ");
            ((StringBuilder)localObject2).append(((SmartBox_VideoInfo)localObject1).sJumpUrl);
            LogUtils.d("QBPageVideoSearchWup", ((StringBuilder)localObject2).toString());
            paramHashMap.put("md5", ((SmartBox_VideoInfo)localObject1).sMd5);
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("onVideoInfo sMd5: ");
            ((StringBuilder)localObject2).append(((SmartBox_VideoInfo)localObject1).sMd5);
            LogUtils.d("QBPageVideoSearchWup", ((StringBuilder)localObject2).toString());
          }
          paramWUPResponseBase = this.mCallback;
          if (paramWUPResponseBase != null) {
            paramWUPResponseBase.onReceiveValue(paramHashMap);
          }
        }
      }
      else
      {
        LogUtils.d("QBPageVideoSearchWup", "onVideoInfo packet.get(rsp) failed");
      }
    }
  }
  
  public void onWUPTaskFail(WUPRequestBase paramWUPRequestBase)
  {
    paramWUPRequestBase = new StringBuilder();
    paramWUPRequestBase.append("QBPageVideoSearchWup onWUPTaskFail Thread =");
    paramWUPRequestBase.append(Thread.currentThread().getName());
    paramWUPRequestBase.toString();
  }
  
  public void onWUPTaskSuccess(WUPRequestBase paramWUPRequestBase, WUPResponseBase paramWUPResponseBase)
  {
    paramWUPRequestBase = new StringBuilder();
    paramWUPRequestBase.append("QBPageVideoSearchWup onWUPTaskSuccess Thread =");
    paramWUPRequestBase.append(Thread.currentThread().getName());
    LogUtils.d("QBPageVideoSearchWup", paramWUPRequestBase.toString());
    onVideoInfo(paramWUPResponseBase, new HashMap());
  }
  
  public void reportPageImageInfo(Map<String, String> paramMap)
  {
    paramMap = getSearchWupRequest(paramMap);
    if (paramMap != null)
    {
      paramMap.setRequestCallBack(this);
      boolean bool = WUPTaskProxy.send(paramMap);
      paramMap = new StringBuilder();
      paramMap.append("reportPageImageInfo  result = ");
      paramMap.append(bool);
      paramMap.append(" thread");
      paramMap.append(Thread.currentThread().getName());
      Log.e("QBPageVideoSearchWup", paramMap.toString());
    }
  }
  
  public void setCallback(ValueCallback<Map<String, String>> paramValueCallback)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("QBPageVideoSearchWup setCallback Thread =");
    localStringBuilder.append(Thread.currentThread().getName());
    localStringBuilder.toString();
    this.mCallback = paramValueCallback;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\videosearch\QBPageVideoSearchWup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */