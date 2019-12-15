package com.tencent.common.utils;

import com.tencent.basesupport.PackageInfo.PackageInfoProvider;
import com.tencent.common.manifest.annotation.CreateMethod;
import com.tencent.common.manifest.annotation.ServiceImpl;

@ServiceImpl(createMethod=CreateMethod.NEW, service=PackageInfo.PackageInfoProvider.class)
public class QBPackageInfoProvider
  implements PackageInfo.PackageInfoProvider
{
  public String broadcastPermission()
  {
    return "com.tencent.mtt.broadcast";
  }
  
  public String keyName()
  {
    return "QQBrowser";
  }
  
  public String packageName()
  {
    return TbsMode.QB_PKGNAME();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\QBPackageInfoProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */