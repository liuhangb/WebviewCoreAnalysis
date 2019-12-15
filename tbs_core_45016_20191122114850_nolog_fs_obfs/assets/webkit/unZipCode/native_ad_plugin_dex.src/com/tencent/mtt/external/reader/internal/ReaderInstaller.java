package com.tencent.mtt.external.reader.internal;

import com.tencent.common.utils.LinuxToolsJni;
import com.tencent.mtt.external.reader.utils.AppEngine;
import com.tencent.mtt.external.reader.utils.FileOpenUtils;
import java.io.File;

public class ReaderInstaller
{
  public static final String DEXMTTFILE = "mttreader.jar";
  public static final String DEXZIPFILE = "ZIPReader.jar";
  public static final int SO_ERROR_COPY = 1002;
  public static final int SO_ERROR_JNITOOL = 1003;
  public static final int SO_ERROR_LOAD = 1001;
  public static final int SO_LOAD_SUCCESS = 0;
  private static ReaderInstaller jdField_a_of_type_ComTencentMttExternalReaderInternalReaderInstaller;
  private File jdField_a_of_type_JavaIoFile = new File(FileOpenUtils.getPluginCache(AppEngine.getInstance().getApplicationContext()), "reader");
  final String jdField_a_of_type_JavaLangString = "reader/ZIPReader.jar";
  final String b = "reader/mttreader.jar";
  final String c = "reader/libLineBreak.so";
  final String d = "reader/libunrar.so";
  protected String mCachePath = "";
  
  public static ReaderInstaller getInstance()
  {
    try
    {
      if (jdField_a_of_type_ComTencentMttExternalReaderInternalReaderInstaller == null) {
        jdField_a_of_type_ComTencentMttExternalReaderInternalReaderInstaller = new ReaderInstaller();
      }
      ReaderInstaller localReaderInstaller = jdField_a_of_type_ComTencentMttExternalReaderInternalReaderInstaller;
      return localReaderInstaller;
    }
    finally {}
  }
  
  public void chmod(String paramString)
  {
    LinuxToolsJni localLinuxToolsJni = new LinuxToolsJni();
    if (LinuxToolsJni.gJniloaded) {
      localLinuxToolsJni.Chmod(new File(this.jdField_a_of_type_JavaIoFile, paramString).getAbsolutePath(), "644");
    }
  }
  
  public String getReaderSharePath()
  {
    return this.jdField_a_of_type_JavaIoFile.getAbsolutePath();
  }
  
  public int installReaderFromApkIfNeed()
  {
    return 0;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\ReaderInstaller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */