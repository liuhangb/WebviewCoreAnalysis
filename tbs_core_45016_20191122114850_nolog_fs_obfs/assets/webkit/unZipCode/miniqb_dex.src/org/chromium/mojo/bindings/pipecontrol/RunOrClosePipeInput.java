package org.chromium.mojo.bindings.pipecontrol;

import org.chromium.mojo.bindings.BindingsHelper;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Decoder;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Union;

public final class RunOrClosePipeInput
  extends Union
{
  private int jdField_a_of_type_Int = -1;
  private PeerAssociatedEndpointClosedEvent jdField_a_of_type_OrgChromiumMojoBindingsPipecontrolPeerAssociatedEndpointClosedEvent;
  
  public static final RunOrClosePipeInput decode(Decoder paramDecoder, int paramInt)
  {
    DataHeader localDataHeader = paramDecoder.a(paramInt);
    if (localDataHeader.jdField_a_of_type_Int == 0) {
      return null;
    }
    RunOrClosePipeInput localRunOrClosePipeInput = new RunOrClosePipeInput();
    if (localDataHeader.b != 0) {
      return localRunOrClosePipeInput;
    }
    localRunOrClosePipeInput.jdField_a_of_type_OrgChromiumMojoBindingsPipecontrolPeerAssociatedEndpointClosedEvent = PeerAssociatedEndpointClosedEvent.a(paramDecoder.a(paramInt + 8, false));
    localRunOrClosePipeInput.jdField_a_of_type_Int = 0;
    return localRunOrClosePipeInput;
  }
  
  protected final void encode(Encoder paramEncoder, int paramInt)
  {
    paramEncoder.a(16, paramInt);
    paramEncoder.a(this.jdField_a_of_type_Int, paramInt + 4);
    if (this.jdField_a_of_type_Int != 0) {
      return;
    }
    paramEncoder.a(this.jdField_a_of_type_OrgChromiumMojoBindingsPipecontrolPeerAssociatedEndpointClosedEvent, paramInt + 8, false);
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
    paramObject = (RunOrClosePipeInput)paramObject;
    int i = this.jdField_a_of_type_Int;
    if (i != ((RunOrClosePipeInput)paramObject).jdField_a_of_type_Int) {
      return false;
    }
    if (i != 0) {
      return false;
    }
    return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsPipecontrolPeerAssociatedEndpointClosedEvent, ((RunOrClosePipeInput)paramObject).jdField_a_of_type_OrgChromiumMojoBindingsPipecontrolPeerAssociatedEndpointClosedEvent);
  }
  
  public int hashCode()
  {
    int i = (getClass().hashCode() + 31) * 31 + BindingsHelper.b(this.jdField_a_of_type_Int);
    if (this.jdField_a_of_type_Int != 0) {
      return i;
    }
    return i * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsPipecontrolPeerAssociatedEndpointClosedEvent);
  }
  
  public static final class Tag {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\pipecontrol\RunOrClosePipeInput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */