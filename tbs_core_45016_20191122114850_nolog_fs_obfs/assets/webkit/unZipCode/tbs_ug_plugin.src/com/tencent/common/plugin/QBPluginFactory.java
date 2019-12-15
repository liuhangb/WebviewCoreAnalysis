package com.tencent.common.plugin;

import android.content.Context;
import com.tencent.basesupport.FLogger;
import java.util.ArrayList;

public class QBPluginFactory
  implements QBPluginProxy.a
{
  public static QBPluginFactory mInstance;
  private Context jdField_a_of_type_AndroidContentContext = null;
  IQBPluginService jdField_a_of_type_ComTencentCommonPluginIQBPluginService = null;
  private QBPluginProxy jdField_a_of_type_ComTencentCommonPluginQBPluginProxy = null;
  private ArrayList<IBindPluginCallback> jdField_a_of_type_JavaUtilArrayList = null;
  
  public QBPluginFactory(Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext.getApplicationContext();
  }
  
  public static QBPluginFactory getInstance(Context paramContext)
  {
    if (mInstance == null) {
      mInstance = new QBPluginFactory(paramContext);
    }
    return mInstance;
  }
  
  @Deprecated
  public void bindPluginService(IBindPluginCallback paramIBindPluginCallback, int paramInt)
  {
    synchronized (this.jdField_a_of_type_JavaUtilArrayList)
    {
      if (!this.jdField_a_of_type_JavaUtilArrayList.contains(paramIBindPluginCallback)) {
        this.jdField_a_of_type_JavaUtilArrayList.add(paramIBindPluginCallback);
      }
      this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy.a(this.jdField_a_of_type_AndroidContentContext, paramInt);
      return;
    }
  }
  
  public QBPluginProxy createQBPluginProxy()
  {
    return this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy;
  }
  
  public void onBindPluginSuccess(QBPluginProxy paramQBPluginProxy)
  {
    synchronized (this.jdField_a_of_type_JavaUtilArrayList)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("onBindPluginSuccess ,pluginProxy: ");
      localStringBuilder.append(paramQBPluginProxy);
      localStringBuilder.append(",Size=");
      localStringBuilder.append(this.jdField_a_of_type_JavaUtilArrayList.size());
      FLogger.d("QBPluginFactory", localStringBuilder.toString());
      int i = 0;
      while (i < this.jdField_a_of_type_JavaUtilArrayList.size())
      {
        ((IBindPluginCallback)this.jdField_a_of_type_JavaUtilArrayList.get(i)).onBindPluginSuccess(paramQBPluginProxy);
        i += 1;
      }
      return;
    }
  }
  
  public void onPluignServiceDisconnected()
  {
    synchronized (this.jdField_a_of_type_JavaUtilArrayList)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("onBindPluignFailed size=");
      localStringBuilder.append(this.jdField_a_of_type_JavaUtilArrayList.size());
      FLogger.d("QBPluginFactory", localStringBuilder.toString());
      int i = 0;
      while (i < this.jdField_a_of_type_JavaUtilArrayList.size())
      {
        ((IBindPluginCallback)this.jdField_a_of_type_JavaUtilArrayList.get(i)).onBindPluignFailed();
        i += 1;
      }
      return;
    }
  }
  
  public void setLocalPluginServiceImpl(IQBPluginService paramIQBPluginService)
  {
    this.jdField_a_of_type_ComTencentCommonPluginQBPluginProxy.setLocalPluginServiceImpl(paramIQBPluginService);
  }
  
  public static abstract interface IBindPluginCallback
  {
    public abstract void onBindPluginSuccess(QBPluginProxy paramQBPluginProxy);
    
    public abstract void onBindPluignFailed();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\plugin\QBPluginFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */