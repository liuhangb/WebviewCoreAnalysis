package com.tencent.tbs.common.wifi;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import com.taf.JceUtil;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.tbs.common.MTT.ZeusBI;
import com.tencent.tbs.common.MTT.ZeusQI;
import com.tencent.tbs.common.MTT.ZeusQRI;
import com.tencent.tbs.common.MTT.ZeusQRQ;
import com.tencent.tbs.common.MTT.ZeusRID;
import com.tencent.tbs.common.MTT.ZeusRRQ;
import com.tencent.tbs.common.stat.WupStatManager;
import com.tencent.tbs.common.wup.WUPRequest;
import com.tencent.tbs.common.wup.WUPTaskProxy;
import java.util.ArrayList;
import java.util.Iterator;

public class WifiWupRequester
{
  private final String SERVER_NAME = "zeusast";
  public IWUPRequestCallBack mCallBack = null;
  
  public void qureyPassword(ArrayList<WifiApInfo> paramArrayList, int paramInt, Object paramObject)
  {
    Object localObject = new ArrayList();
    ArrayList localArrayList = new ArrayList();
    paramArrayList = paramArrayList.iterator();
    while (paramArrayList.hasNext())
    {
      WifiApInfo localWifiApInfo = (WifiApInfo)paramArrayList.next();
      ZeusQI localZeusQI = new ZeusQI();
      localZeusQI.s = localWifiApInfo.mSsid;
      localZeusQI.b = localWifiApInfo.mBssid;
      localZeusQI.t = localWifiApInfo.mSafeType;
      ((ArrayList)localObject).add(localZeusQI);
    }
    paramArrayList = new ZeusQRQ();
    paramArrayList.vQ = ((ArrayList)localObject);
    paramArrayList.vD = localArrayList;
    paramArrayList.iT = paramInt;
    localObject = new WUPRequest("zeusast", "qurey");
    ((WUPRequest)localObject).setRequestCallBack(this.mCallBack);
    ((WUPRequest)localObject).put("stRQ", paramArrayList);
    ((WUPRequest)localObject).setBindObject(paramObject);
    WUPTaskProxy.send((WUPRequest)localObject);
  }
  
  public void reportWifiInfo(ArrayList<KeyInfo> paramArrayList, Object paramObject, int paramInt, boolean paramBoolean)
  {
    if (paramArrayList != null)
    {
      if (paramArrayList.isEmpty()) {
        return;
      }
      ZeusRID localZeusRID = new ZeusRID();
      ZeusRRQ localZeusRRQ = new ZeusRRQ();
      localZeusRRQ.t = paramInt;
      WUPRequest localWUPRequest = new WUPRequest("zeusast", "report");
      localWUPRequest.put("stRQ", localZeusRRQ);
      if ((paramInt == 0) || (paramInt == 8))
      {
        ArrayList localArrayList = new ArrayList();
        paramArrayList = paramArrayList.iterator();
        while (paramArrayList.hasNext())
        {
          Object localObject = paramArrayList.next();
          if ((localObject instanceof KeyInfo)) {
            localArrayList.add(((KeyInfo)localObject).toJce());
          }
        }
        localZeusRID.vR = localArrayList;
        localZeusRRQ.vData = JceUtil.jce2Bytes(localZeusRID);
        localWUPRequest.setRequestCallBack(this.mCallBack);
        localWUPRequest.setBindObject(paramObject);
      }
      if (paramBoolean)
      {
        WifiStatManger.getInstance().statReq(localWUPRequest);
        return;
      }
      WUPTaskProxy.send(localWUPRequest);
      return;
    }
  }
  
  public static class KeyInfo
  {
    public long actionTime;
    public String bssid;
    public long from;
    public String password;
    public int pwdAvialble;
    public String ssid;
    public int type;
    
    public ZeusBI toJce()
    {
      ZeusBI localZeusBI = new ZeusBI();
      localZeusBI.s = this.ssid;
      localZeusBI.b = this.bssid;
      localZeusBI.f = this.from;
      localZeusBI.e = this.type;
      localZeusBI.p = this.password;
      localZeusBI.ps = this.pwdAvialble;
      localZeusBI.t = this.actionTime;
      return localZeusBI;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder("KeyInfo: ");
      localStringBuilder.append("ssid: ");
      localStringBuilder.append(this.ssid);
      localStringBuilder.append(", bssid: ");
      localStringBuilder.append(this.bssid);
      localStringBuilder.append(", type: ");
      localStringBuilder.append(this.type);
      localStringBuilder.append(", password: ");
      localStringBuilder.append(this.password);
      localStringBuilder.append(", from: ");
      localStringBuilder.append(this.from);
      localStringBuilder.append(", pwdAvialble: ");
      localStringBuilder.append(this.pwdAvialble);
      localStringBuilder.append(", actionTime: ");
      localStringBuilder.append(this.actionTime);
      return localStringBuilder.toString();
    }
  }
  
  public static class WifiKeyQureyRspInfo
  {
    public long acitonType = 0L;
    public String brand;
    public String bssid;
    public boolean force;
    public boolean hasPwd;
    public String logurl;
    public String portal;
    public String pwd;
    public int score;
    public String ssid;
    public String weixinSchema;
    
    public WifiKeyQureyRspInfo() {}
    
    public WifiKeyQureyRspInfo(ZeusQRI paramZeusQRI)
    {
      this.ssid = paramZeusQRI.s;
      this.bssid = paramZeusQRI.b;
      this.pwd = paramZeusQRI.p;
      this.force = paramZeusQRI.c;
      if ((!TextUtils.isEmpty(this.pwd)) || (paramZeusQRI.hasPsw)) {
        this.hasPwd = true;
      }
      this.acitonType = paramZeusQRI.t;
      this.brand = paramZeusQRI.br;
      this.portal = paramZeusQRI.pu;
      this.logurl = paramZeusQRI.lu;
      this.weixinSchema = paramZeusQRI.ws;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder("KeyQureyInfo: ");
      localStringBuilder.append("ssid: ");
      localStringBuilder.append(this.ssid);
      localStringBuilder.append(", bssid: ");
      localStringBuilder.append(this.bssid);
      localStringBuilder.append(", pwd: ");
      localStringBuilder.append(this.pwd);
      localStringBuilder.append(", force: ");
      localStringBuilder.append(this.force);
      return localStringBuilder.toString();
    }
  }
  
  public static class WifiStatManger
    implements Handler.Callback
  {
    private static final int MSG_LOAD = 2;
    private static final int MSG_SAVE = 1;
    private static WifiStatManger sInstance;
    Handler mHandler = null;
    WupStatManager mWupStatManager = WupStatManager.getInstance();
    
    public static WifiStatManger getInstance()
    {
      if (sInstance == null) {
        try
        {
          if (sInstance == null) {
            sInstance = new WifiStatManger();
          }
        }
        finally {}
      }
      return sInstance;
    }
    
    public boolean handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default: 
        break;
      case 2: 
        load();
        break;
      case 1: 
        this.mWupStatManager.saveCurrentFailList();
      }
      return false;
    }
    
    public void load()
    {
      this.mWupStatManager.loadFailList();
    }
    
    public void statReq(WUPRequest paramWUPRequest)
    {
      this.mWupStatManager.sendWUPStatReq(paramWUPRequest);
      this.mHandler.removeMessages(1);
      this.mHandler.sendEmptyMessageDelayed(1, 10000L);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wifi\WifiWupRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */