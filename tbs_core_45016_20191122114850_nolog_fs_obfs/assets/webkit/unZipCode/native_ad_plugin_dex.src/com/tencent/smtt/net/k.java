package com.tencent.smtt.net;

import android.text.TextUtils;
import com.tencent.common.http.Apn;
import com.tencent.smtt.util.j;
import com.tencent.smtt.webkit.AdInfoManager;
import com.tencent.smtt.webkit.WebSettingsExtension;
import com.tencent.smtt.webkit.e;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.partner.ad.AdInfoGenerater;
import com.tencent.tbs.core.webkit.tencent.TencentURLUtil;
import java.util.ArrayList;

public class k
{
  public static int a(String paramString, int paramInt, boolean paramBoolean)
  {
    int i = 0;
    int j = 0;
    if ((paramInt == 0) || (paramInt == 1))
    {
      if (paramInt == 0) {
        j = 1;
      }
      i = j;
      if (WebSettingsExtension.getRemoveAds())
      {
        i = j;
        if (paramInt == 0)
        {
          i = j;
          if (AdInfoManager.getInstance().shallRequestAdblockInfo(TencentURLUtil.getHost(paramString), true, paramBoolean)) {
            i = j | 0x80 | 0x100;
          }
        }
      }
      j = i;
      if (WebSettingsExtension.isWapSitePreferredS()) {
        j = i | 0x1000;
      }
      i = j | 0x800;
      if ((paramInt == 0) && (!SmttServiceProxy.getInstance().isX5ProxySupportReadMode())) {
        paramInt = i | 0x2000;
      } else {
        paramInt = i;
      }
      i = paramInt;
      if (WebSettingsExtension.getIsKidMode()) {
        i = paramInt | 0x10000;
      }
    }
    return i;
  }
  
  public static void a(ArrayList<String> paramArrayList, String paramString, boolean paramBoolean)
  {
    Object localObject;
    StringBuilder localStringBuilder;
    if (paramBoolean)
    {
      localObject = new StringBuffer();
      ((StringBuffer)localObject).append("PROTOCOL=");
      int i = 0;
      ((StringBuffer)localObject).append(0);
      int j = WebSettingsExtension.getImageQuality();
      ((StringBuffer)localObject).append(",QUALITY=");
      ((StringBuffer)localObject).append(j);
      ((StringBuffer)localObject).append(",APN=");
      ((StringBuffer)localObject).append(Apn.getApnName(Apn.sApnTypeS));
      if (e.a().b())
      {
        ((StringBuffer)localObject).append(",SupportedImage=");
        ((StringBuffer)localObject).append("127");
      }
      else
      {
        ((StringBuffer)localObject).append(",SupportedImage=");
        ((StringBuffer)localObject).append("63");
      }
      if (!WebSettingsExtension.getRemoveAds()) {
        i = 1;
      }
      if (i != 0)
      {
        ((StringBuffer)localObject).append(",FLAGS=");
        ((StringBuffer)localObject).append(i);
      }
      ((StringBuffer)localObject).append(",NAD=");
      if (SmttServiceProxy.getInstance().isTbsAdHttpProxySwitchOpened()) {
        ((StringBuffer)localObject).append(AdInfoGenerater.getNativeAdFlag());
      } else {
        ((StringBuffer)localObject).append("0");
      }
      if (SmttServiceProxy.getInstance().isKingCardUser()) {
        ((StringBuffer)localObject).append(",FREEFLOW=1");
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Q-PROXY-PARAM: ");
      localStringBuilder.append(((StringBuffer)localObject).toString());
      paramArrayList.add(localStringBuilder.toString());
    }
    if ((SmttServiceProxy.getInstance().isNeedQHead(j.c(paramString))) || (paramBoolean))
    {
      if (SmttServiceProxy.getInstance().getEnableQua1())
      {
        localObject = SmttServiceProxy.getInstance().getQUA();
        if ((localObject != null) && (((String)localObject).length() > 0))
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Q-UA: ");
          localStringBuilder.append((String)localObject);
          paramArrayList.add(localStringBuilder.toString());
        }
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Q-UA2: ");
      ((StringBuilder)localObject).append(SmttServiceProxy.getInstance().getQUA2());
      paramArrayList.add(((StringBuilder)localObject).toString());
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Q-GUID: ");
      ((StringBuilder)localObject).append(SmttServiceProxy.getInstance().getGUID());
      paramArrayList.add(((StringBuilder)localObject).toString());
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Q-Auth: ");
      ((StringBuilder)localObject).append(SmttServiceProxy.getInstance().getQAuth());
      paramArrayList.add(((StringBuilder)localObject).toString());
    }
    if (("recmd.html5.qq.com".equals(paramString)) || ("h5.gdt.qq.com".equals(paramString)) || ("qbdsp.html5.qq.com".equals(paramString)))
    {
      paramString = new StringBuilder();
      paramString.append("QAID: ");
      paramString.append(SmttServiceProxy.getInstance().getQAID());
      paramArrayList.add(paramString.toString());
      paramString = new StringBuilder();
      paramString.append("QIMEI: ");
      paramString.append(SmttServiceProxy.getInstance().getQIMEI());
      paramArrayList.add(paramString.toString());
      paramString = SmttServiceProxy.getInstance().getTAID();
      if (!TextUtils.isEmpty(paramString))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("QTAID: ");
        ((StringBuilder)localObject).append(paramString);
        paramArrayList.add(((StringBuilder)localObject).toString());
      }
      paramString = SmttServiceProxy.getInstance().getOAID();
      if (!TextUtils.isEmpty(paramString))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("QOAID: ");
        ((StringBuilder)localObject).append(paramString);
        paramArrayList.add(((StringBuilder)localObject).toString());
      }
    }
  }
  
  public static void a(ArrayList<String> paramArrayList, boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Q-UA2: ");
      localStringBuilder.append(SmttServiceProxy.getInstance().getQUA2());
      paramArrayList.add(localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Q-GUID: ");
      localStringBuilder.append(SmttServiceProxy.getInstance().getGUID());
      paramArrayList.add(localStringBuilder.toString());
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Q-Token: ");
    localStringBuilder.append(SmttServiceProxy.getInstance().getHttpsTunnelToken());
    paramArrayList.add(localStringBuilder.toString());
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\k.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */