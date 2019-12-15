package org.chromium.mojo.bindings.interfacecontrol;

import org.chromium.mojo.bindings.BindingsHelper;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Decoder;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Message;
import org.chromium.mojo.bindings.Struct;

public final class RunOrClosePipeMessageParams
  extends Struct
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
  private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(24, 0) };
  public RunOrClosePipeInput a;
  
  public RunOrClosePipeMessageParams()
  {
    this(0);
  }
  
  private RunOrClosePipeMessageParams(int paramInt)
  {
    super(24, paramInt);
  }
  
  public static RunOrClosePipeMessageParams a(Decoder paramDecoder)
  {
    if (paramDecoder == null) {
      return null;
    }
    paramDecoder.a();
    try
    {
      DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
      RunOrClosePipeMessageParams localRunOrClosePipeMessageParams = new RunOrClosePipeMessageParams(localDataHeader.b);
      if (localDataHeader.b >= 0) {
        localRunOrClosePipeMessageParams.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolRunOrClosePipeInput = RunOrClosePipeInput.decode(paramDecoder, 8);
      }
      return localRunOrClosePipeMessageParams;
    }
    finally
    {
      paramDecoder.b();
    }
  }
  
  public static RunOrClosePipeMessageParams a(Message paramMessage)
  {
    return a(new Decoder(paramMessage));
  }
  
  protected final void encode(Encoder paramEncoder)
  {
    paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader).a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolRunOrClosePipeInput, 8, false);
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
    paramObject = (RunOrClosePipeMessageParams)paramObject;
    return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolRunOrClosePipeInput, ((RunOrClosePipeMessageParams)paramObject).jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolRunOrClosePipeInput);
  }
  
  public int hashCode()
  {
    return (getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolRunOrClosePipeInput);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\interfacecontrol\RunOrClosePipeMessageParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */