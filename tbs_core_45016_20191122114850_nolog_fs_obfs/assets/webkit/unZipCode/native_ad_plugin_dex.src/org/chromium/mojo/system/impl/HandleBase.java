package org.chromium.mojo.system.impl;

import org.chromium.mojo.system.Core;
import org.chromium.mojo.system.Core.HandleSignalsState;
import org.chromium.mojo.system.Handle;
import org.chromium.mojo.system.UntypedHandle;

abstract class HandleBase
  implements Handle
{
  private int a;
  protected CoreImpl a;
  
  HandleBase(CoreImpl paramCoreImpl, int paramInt)
  {
    this.jdField_a_of_type_OrgChromiumMojoSystemImplCoreImpl = paramCoreImpl;
    this.jdField_a_of_type_Int = paramInt;
  }
  
  protected HandleBase(HandleBase paramHandleBase)
  {
    this.jdField_a_of_type_OrgChromiumMojoSystemImplCoreImpl = paramHandleBase.jdField_a_of_type_OrgChromiumMojoSystemImplCoreImpl;
    int i = paramHandleBase.jdField_a_of_type_Int;
    paramHandleBase.jdField_a_of_type_Int = 0;
    this.jdField_a_of_type_Int = i;
  }
  
  int a()
  {
    return this.jdField_a_of_type_Int;
  }
  
  void a()
  {
    this.jdField_a_of_type_Int = 0;
  }
  
  public void close()
  {
    int i = this.jdField_a_of_type_Int;
    if (i != 0)
    {
      this.jdField_a_of_type_Int = 0;
      this.jdField_a_of_type_OrgChromiumMojoSystemImplCoreImpl.a(i);
    }
  }
  
  protected final void finalize()
    throws Throwable
  {
    if (isValid()) {
      this.jdField_a_of_type_OrgChromiumMojoSystemImplCoreImpl.a(this.jdField_a_of_type_Int);
    }
    super.finalize();
  }
  
  public Core getCore()
  {
    return this.jdField_a_of_type_OrgChromiumMojoSystemImplCoreImpl;
  }
  
  public boolean isValid()
  {
    return this.jdField_a_of_type_Int != 0;
  }
  
  public Core.HandleSignalsState querySignalsState()
  {
    return this.jdField_a_of_type_OrgChromiumMojoSystemImplCoreImpl.a(this.jdField_a_of_type_Int);
  }
  
  public int releaseNativeHandle()
  {
    int i = this.jdField_a_of_type_Int;
    this.jdField_a_of_type_Int = 0;
    return i;
  }
  
  public UntypedHandle toUntypedHandle()
  {
    return new UntypedHandleImpl(this);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\system\impl\HandleBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */