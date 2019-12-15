package org.chromium.android_webview;

public final class AwSafeBrowsingConversionHelper
{
  public static final int SAFE_BROWSING_THREAT_MALWARE = 1;
  public static final int SAFE_BROWSING_THREAT_PHISHING = 2;
  public static final int SAFE_BROWSING_THREAT_UNKNOWN = 0;
  public static final int SAFE_BROWSING_THREAT_UNWANTED_SOFTWARE = 3;
  
  public static int convertThreatType(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return 0;
    case 4: 
      return 3;
    case 3: 
      return 1;
    }
    return 2;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwSafeBrowsingConversionHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */