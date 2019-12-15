package com.tencent.mtt.base.utils;

import android.content.pm.Signature;
import android.text.TextUtils;
import com.tencent.common.utils.Md5Utils;

public class DLPackageUtils
{
  public static String getSignMd5(String paramString)
  {
    String str1 = "";
    Signature[] arrayOfSignature = DLSignatureUtil.collectCertificates(paramString);
    String str2 = "";
    paramString = str2;
    if (arrayOfSignature != null)
    {
      paramString = str2;
      if (arrayOfSignature.length > 0) {
        paramString = arrayOfSignature[0].toCharsString();
      }
    }
    if (!TextUtils.isEmpty(paramString)) {
      str1 = Md5Utils.getMD5(paramString).toLowerCase();
    }
    return str1;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\DLPackageUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */