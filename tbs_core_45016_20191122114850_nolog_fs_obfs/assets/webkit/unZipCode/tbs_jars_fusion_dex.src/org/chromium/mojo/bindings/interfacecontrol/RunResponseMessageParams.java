package org.chromium.mojo.bindings.interfacecontrol;

import org.chromium.mojo.bindings.BindingsHelper;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Decoder;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Message;
import org.chromium.mojo.bindings.Struct;

public final class RunResponseMessageParams
  extends Struct
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
  private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(24, 0) };
  public RunOutput a;
  
  public RunResponseMessageParams()
  {
    this(0);
  }
  
  private RunResponseMessageParams(int paramInt)
  {
    super(24, paramInt);
  }
  
  public static RunResponseMessageParams a(Decoder paramDecoder)
  {
    if (paramDecoder == null) {
      return null;
    }
    paramDecoder.a();
    try
    {
      DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
      RunResponseMessageParams localRunResponseMessageParams = new RunResponseMessageParams(localDataHeader.b);
      if (localDataHeader.b >= 0) {
        localRunResponseMessageParams.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolRunOutput = RunOutput.decode(paramDecoder, 8);
      }
      return localRunResponseMessageParams;
    }
    finally
    {
      paramDecoder.b();
    }
  }
  
  public static RunResponseMessageParams a(Message paramMessage)
  {
    return a(new Decoder(paramMessage));
  }
  
  protected final void encode(Encoder paramEncoder)
  {
    paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader).a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolRunOutput, 8, true);
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
    paramObject = (RunResponseMessageParams)paramObject;
    return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolRunOutput, ((RunResponseMessageParams)paramObject).jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolRunOutput);
  }
  
  public int hashCode()
  {
    return (getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolRunOutput);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\interfacecontrol\RunResponseMessageParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */