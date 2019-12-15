package com.tencent.smtt.aladdin;

import com.tencent.smtt.export.external.easel.interfaces.ILoader;
import com.tencent.smtt.export.external.easel.interfaces.ILoader.Delegate;
import com.tencent.smtt.net.HttpTask.Listener;
import com.tencent.smtt.net.HttpTask.b;

public class a
  implements ILoader, HttpTask.Listener
{
  protected ILoader.Delegate a;
  
  static
  {
    jdField_a_of_type_Boolean = a.class.desiredAssertionStatus() ^ true;
  }
  
  public void onTaskFailed(int paramInt)
  {
    ILoader.Delegate localDelegate = this.jdField_a_of_type_ComTencentSmttExportExternalEaselInterfacesILoader$Delegate;
    if (localDelegate != null) {
      localDelegate.didFail();
    }
  }
  
  public void onTaskSuccess(HttpTask.b paramb)
  {
    ILoader.Delegate localDelegate = this.jdField_a_of_type_ComTencentSmttExportExternalEaselInterfacesILoader$Delegate;
    if (localDelegate != null)
    {
      localDelegate.didReceiveResponse(paramb.jdField_a_of_type_Int);
      this.jdField_a_of_type_ComTencentSmttExportExternalEaselInterfacesILoader$Delegate.didReceiveData(paramb.jdField_a_of_type_ArrayOfByte, paramb.jdField_a_of_type_ArrayOfByte.length);
      this.jdField_a_of_type_ComTencentSmttExportExternalEaselInterfacesILoader$Delegate.didFinishLoading();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\aladdin\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */