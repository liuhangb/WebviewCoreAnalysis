package org.chromium.mojo.bindings.pipecontrol;

import org.chromium.mojo.bindings.BindingsHelper;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Encoder;
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
  
  protected final void encode(Encoder paramEncoder)
  {
    paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader).a(this.jdField_a_of_type_OrgChromiumMojoBindingsPipecontrolRunOrClosePipeInput, 8, false);
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
    return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsPipecontrolRunOrClosePipeInput, ((RunOrClosePipeMessageParams)paramObject).jdField_a_of_type_OrgChromiumMojoBindingsPipecontrolRunOrClosePipeInput);
  }
  
  public int hashCode()
  {
    return (getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsPipecontrolRunOrClosePipeInput);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\pipecontrol\RunOrClosePipeMessageParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */