package com.tencent.mtt.base.task;

public class WalledGardenMessage
{
  public int delayed;
  public String exceptionMsg;
  public int httpCode;
  public int result;
  public String ssid;
  public Object tag;
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ssid:");
    localStringBuilder.append(this.ssid);
    localStringBuilder.append("|请求通道:");
    localStringBuilder.append(this.tag);
    localStringBuilder.append("|探测结果:");
    int i = this.result;
    if (i == 0) {
      localStringBuilder.append("可以上网");
    } else if (i == 2) {
      localStringBuilder.append("需要认证");
    } else if (i == 1) {
      localStringBuilder.append("探测失败");
    } else {
      localStringBuilder.append("出现异常");
    }
    localStringBuilder.append("|异常信息:");
    localStringBuilder.append(this.exceptionMsg);
    localStringBuilder.append("|HTTP CODE:");
    localStringBuilder.append(this.httpCode);
    localStringBuilder.append("|探测耗时:");
    localStringBuilder.append(this.delayed);
    return localStringBuilder.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\task\WalledGardenMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */