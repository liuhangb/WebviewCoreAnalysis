package org.chromium.mojo.bindings.interfacecontrol;

import org.chromium.mojo.bindings.BindingsHelper;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Decoder;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Union;

public final class RunInput
  extends Union
{
  private int jdField_a_of_type_Int = -1;
  private FlushForTesting jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolFlushForTesting;
  private QueryVersion jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolQueryVersion;
  
  public static final RunInput decode(Decoder paramDecoder, int paramInt)
  {
    DataHeader localDataHeader = paramDecoder.a(paramInt);
    if (localDataHeader.jdField_a_of_type_Int == 0) {
      return null;
    }
    RunInput localRunInput = new RunInput();
    switch (localDataHeader.b)
    {
    default: 
      return localRunInput;
    case 1: 
      localRunInput.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolFlushForTesting = FlushForTesting.a(paramDecoder.a(paramInt + 8, false));
      localRunInput.jdField_a_of_type_Int = 1;
      return localRunInput;
    }
    localRunInput.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolQueryVersion = QueryVersion.a(paramDecoder.a(paramInt + 8, false));
    localRunInput.jdField_a_of_type_Int = 0;
    return localRunInput;
  }
  
  public int a()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public void a(QueryVersion paramQueryVersion)
  {
    this.jdField_a_of_type_Int = 0;
    this.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolQueryVersion = paramQueryVersion;
  }
  
  protected final void encode(Encoder paramEncoder, int paramInt)
  {
    paramEncoder.a(16, paramInt);
    paramEncoder.a(this.jdField_a_of_type_Int, paramInt + 4);
    switch (this.jdField_a_of_type_Int)
    {
    default: 
      return;
    case 1: 
      paramEncoder.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolFlushForTesting, paramInt + 8, false);
      return;
    }
    paramEncoder.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolQueryVersion, paramInt + 8, false);
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    if (getClass() != paramObject.getClass()) {
      return false;
    }
    paramObject = (RunInput)paramObject;
    int i = this.jdField_a_of_type_Int;
    if (i != ((RunInput)paramObject).jdField_a_of_type_Int) {
      return false;
    }
    switch (i)
    {
    default: 
      return false;
    case 1: 
      return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolFlushForTesting, ((RunInput)paramObject).jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolFlushForTesting);
    }
    return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolQueryVersion, ((RunInput)paramObject).jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolQueryVersion);
  }
  
  public int hashCode()
  {
    int i = (getClass().hashCode() + 31) * 31 + BindingsHelper.b(this.jdField_a_of_type_Int);
    switch (this.jdField_a_of_type_Int)
    {
    default: 
      return i;
    case 1: 
      return i * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolFlushForTesting);
    }
    return i * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolQueryVersion);
  }
  
  public static final class Tag {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\interfacecontrol\RunInput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */