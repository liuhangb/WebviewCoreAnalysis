package com.tencent.smtt.jsApi.impl.utils;

public final class AppAdMediaInfo
{
  String a;
  String b;
  String c;
  
  AppAdMediaInfo() {}
  
  AppAdMediaInfo(String paramString1, String paramString2, String paramString3)
  {
    this.a = paramString1;
    this.b = paramString2;
    this.c = paramString3;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("{");
    localStringBuilder.append("packageName:");
    localStringBuilder.append(this.a);
    localStringBuilder.append(",tbsFrontDomain:");
    localStringBuilder.append(this.b);
    localStringBuilder.append(",tbsAdkey:");
    localStringBuilder.append(this.c);
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\utils\AppAdMediaInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */