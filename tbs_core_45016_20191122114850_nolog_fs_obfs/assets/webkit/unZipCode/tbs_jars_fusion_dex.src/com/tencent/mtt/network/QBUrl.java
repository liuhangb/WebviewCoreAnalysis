package com.tencent.mtt.network;

import com.tencent.basesupport.ModuleProxy;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class QBUrl
{
  private String jdField_a_of_type_JavaLangString = "";
  private final URL jdField_a_of_type_JavaNetURL;
  private boolean jdField_a_of_type_Boolean = true;
  
  public QBUrl(String paramString)
    throws MalformedURLException
  {
    this.jdField_a_of_type_JavaNetURL = new URL(paramString);
  }
  
  public QBUrl(URL paramURL)
  {
    this.jdField_a_of_type_JavaNetURL = paramURL;
  }
  
  public static String getQueenProxyUrl(String paramString)
  {
    return ((QBUrlHandler)QBUrlHandler.PROXY.get()).getQueenProxyUrl(paramString);
  }
  
  public String getTag()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public URL getUrl()
  {
    return this.jdField_a_of_type_JavaNetURL;
  }
  
  public boolean isQueenProxyEnable()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public URLConnection openConnection()
    throws IOException
  {
    return ((QBUrlHandler)QBUrlHandler.PROXY.get()).openConnection(this);
  }
  
  public URLConnection openConnection(Proxy paramProxy)
    throws IOException
  {
    return ((QBUrlHandler)QBUrlHandler.PROXY.get()).openConnection(this, paramProxy);
  }
  
  public QBUrl setQueenProxyEnable(boolean paramBoolean)
  {
    this.jdField_a_of_type_Boolean = paramBoolean;
    return this;
  }
  
  public QBUrl setTag(String paramString)
  {
    this.jdField_a_of_type_JavaLangString = paramString;
    return this;
  }
  
  public String toString()
  {
    return this.jdField_a_of_type_JavaNetURL.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\network\QBUrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */