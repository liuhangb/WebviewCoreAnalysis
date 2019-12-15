package com.tencent.smtt.net;

import com.tencent.smtt.webkit.service.SmttServiceProxy;
import org.chromium.base.annotations.CalledByNative;

public class SWReporter
{
  private static SWReporter jdField_a_of_type_ComTencentSmttNetSWReporter;
  private final String jdField_a_of_type_JavaLangString = "SERVICEWORKER";
  
  @CalledByNative
  private void OnReportSWRegistJNI(String paramString)
  {
    SmttServiceProxy.getInstance().onReportSWRegistInfo(paramString);
  }
  
  @CalledByNative
  public static SWReporter getInstance()
  {
    try
    {
      if (jdField_a_of_type_ComTencentSmttNetSWReporter == null) {
        jdField_a_of_type_ComTencentSmttNetSWReporter = new SWReporter();
      }
      SWReporter localSWReporter = jdField_a_of_type_ComTencentSmttNetSWReporter;
      return localSWReporter;
    }
    finally {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\SWReporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */