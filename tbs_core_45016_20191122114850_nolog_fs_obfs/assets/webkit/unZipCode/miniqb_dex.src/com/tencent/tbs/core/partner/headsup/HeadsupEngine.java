package com.tencent.tbs.core.partner.headsup;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.tencent.common.utils.MttLoader;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.service.SmttServiceProxy;

public class HeadsupEngine
{
  private void sendHeadsUpIntent(Context paramContext, String paramString1, String paramString2)
  {
    Intent localIntent;
    if (MttLoader.isSupportingTbsTips(paramContext))
    {
      localIntent = new Intent("com.tencent.mtt.ACTION_TBS_TIPS");
      localIntent.setPackage("com.tencent.mtt");
      localIntent.putExtra("extra_version", 1);
      localIntent.putExtra("extra_action_type", "tbs_tips");
      localIntent.putExtra("extra_tbs_tips_type", "0");
      localIntent.putExtra("extra_url", paramString1);
      localIntent.putExtra("extra_title", "快速打开");
      localIntent.putExtra("extra_content", paramString1);
      localIntent.putExtra("extra_dismiss_time", 5000);
      localIntent.putExtra("extra_stat_key", paramString2);
    }
    try
    {
      paramContext.startService(localIntent);
      return;
    }
    catch (Throwable paramContext) {}
  }
  
  public void doHeadsup(String paramString)
  {
    for (;;)
    {
      String str1;
      try
      {
        Context localContext = ContextHolder.getInstance().getApplicationContext();
        localObject = localContext.getPackageName();
        if (("com.tencent.mm".equals(localObject)) || ("com.tencent.mobileqq".equals(localObject)))
        {
          Uri localUri = Uri.parse(paramString);
          if ((localUri != null) && (localUri.getHost() != null))
          {
            paramString = localUri.getHost();
            boolean bool = "com.tencent.mm".equals(localObject);
            str1 = null;
            if (bool)
            {
              if ((SmttServiceProxy.getInstance().isHeadsUpTaobaoLinkEnabled(localContext)) && (paramString.endsWith("support.weixin.qq.com")) && ("w_redirect_taobao".equals(localUri.getQueryParameter("t"))))
              {
                String str2 = Uri.decode(localUri.getQueryParameter("url"));
                paramString = str2;
                localObject = str1;
                if (MttLoader.isSupportingTbsTips(localContext))
                {
                  paramString = new Intent("com.tencent.mtt.ACTION_TBS_TIPS");
                  paramString.setPackage("com.tencent.mtt");
                  paramString.putExtra("extra_url", str2);
                  paramString.putExtra("extra_action_type", "taobao_link");
                  try
                  {
                    localContext.startService(paramString);
                    paramString = str2;
                    localObject = str1;
                  }
                  catch (Throwable paramString)
                  {
                    paramString.printStackTrace();
                    paramString = str2;
                    localObject = str1;
                  }
                }
              }
              else if ((SmttServiceProxy.getInstance().isHeadsUpRiskWebsite(localContext)) && ("rd.wechat.com".equals(paramString)) && ("/redirect/confirm".equals(localUri.getPath())) && ("2".equals(localUri.getQueryParameter("block_type"))))
              {
                localObject = "02";
                paramString = null;
              }
              else
              {
                if ((!SmttServiceProxy.getInstance().isHeadsUpTranscodingPageEnabled(localContext)) || (!"qbview.url.cn".equals(paramString))) {
                  break label384;
                }
                localObject = "03";
                paramString = null;
              }
            }
            else
            {
              if ((!SmttServiceProxy.getInstance().isHeadsUpRiskWebsite(localContext)) || (!"/cgi-bin/httpconn".equals(localUri.getPath())) || (!"0x6ff0080".equals(localUri.getQueryParameter("htcmd")))) {
                break label384;
              }
              localObject = "02";
              paramString = Uri.decode(localUri.getQueryParameter("u"));
            }
            str1 = paramString;
            if (localObject != null)
            {
              str1 = paramString;
              if (paramString == null) {
                str1 = Uri.decode(localUri.getQueryParameter("url"));
              }
            }
            if ((localObject != null) && (str1 != null)) {
              sendHeadsUpIntent(localContext, str1, (String)localObject);
            }
          }
        }
        return;
      }
      catch (Throwable paramString)
      {
        return;
      }
      label384:
      paramString = null;
      Object localObject = str1;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\headsup\HeadsupEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */