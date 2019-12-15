package org.chromium.mojo.bindings.interfacecontrol;

import org.chromium.mojo.bindings.BindingsHelper;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Decoder;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Message;
import org.chromium.mojo.bindings.Struct;

public final class RunMessageParams
  extends Struct
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
  private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(24, 0) };
  public RunInput a;
  
  public RunMessageParams()
  {
    this(0);
  }
  
  private RunMessageParams(int paramInt)
  {
    super(24, paramInt);
  }
  
  public static RunMessageParams a(Decoder paramDecoder)
  {
    if (paramDecoder == null) {
      return null;
    }
    paramDecoder.a();
    try
    {
      DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
      RunMessageParams localRunMessageParams = new RunMessageParams(localDataHeader.b);
      if (localDataHeader.b >= 0) {
        localRunMessageParams.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolRunInput = RunInput.decode(paramDecoder, 8);
      }
      return localRunMessageParams;
    }
    finally
    {
      paramDecoder.b();
    }
  }
  
  public static RunMessageParams a(Message paramMessage)
  {
    return a(new Decoder(paramMessage));
  }
  
  protected final void encode(Encoder paramEncoder)
  {
    paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader).a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolRunInput, 8, false);
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
    paramObject = (RunMessageParams)paramObject;
    return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolRunInput, ((RunMessageParams)paramObject).jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolRunInput);
  }
  
  public int hashCode()
  {
    return (getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolRunInput);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\interfacecontrol\RunMessageParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */