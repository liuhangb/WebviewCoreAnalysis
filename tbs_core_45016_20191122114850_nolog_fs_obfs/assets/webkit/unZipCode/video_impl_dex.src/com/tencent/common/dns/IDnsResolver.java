package com.tencent.common.dns;

import com.tencent.common.manifest.annotation.Service;

@Service
public abstract interface IDnsResolver
{
  public abstract String resolveDomain(String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\dns\IDnsResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */