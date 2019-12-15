package com.tencent.mtt.browser.download.engine;

import com.tencent.common.http.MttRequestBase;
import com.tencent.common.http.MttRequestBase.IRequestInterceptor;
import java.util.Map;

public class DownloaderInterceptor
  implements MttRequestBase.IRequestInterceptor
{
  private static DownloaderInterceptor jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloaderInterceptor;
  private MttRequestBase.IRequestInterceptor jdField_a_of_type_ComTencentCommonHttpMttRequestBase$IRequestInterceptor;
  
  public static DownloaderInterceptor getInstance()
  {
    try
    {
      if (jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloaderInterceptor == null) {
        jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloaderInterceptor = new DownloaderInterceptor();
      }
      DownloaderInterceptor localDownloaderInterceptor = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloaderInterceptor;
      return localDownloaderInterceptor;
    }
    finally {}
  }
  
  public void onHeaderIntercept(MttRequestBase paramMttRequestBase, Map<String, String> paramMap)
  {
    MttRequestBase.IRequestInterceptor localIRequestInterceptor = this.jdField_a_of_type_ComTencentCommonHttpMttRequestBase$IRequestInterceptor;
    if (localIRequestInterceptor != null) {
      localIRequestInterceptor.onHeaderIntercept(paramMttRequestBase, paramMap);
    }
  }
  
  public void setRequestInterceptor(MttRequestBase.IRequestInterceptor paramIRequestInterceptor)
  {
    this.jdField_a_of_type_ComTencentCommonHttpMttRequestBase$IRequestInterceptor = paramIRequestInterceptor;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\browser\download\engine\DownloaderInterceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */