package org.chromium.mojo.bindings.interfacecontrol;

import org.chromium.mojo.bindings.BindingsHelper;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Decoder;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Union;

public final class RunOutput
  extends Union
{
  private int jdField_a_of_type_Int = -1;
  private QueryVersionResult jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolQueryVersionResult;
  
  public static final RunOutput decode(Decoder paramDecoder, int paramInt)
  {
    DataHeader localDataHeader = paramDecoder.a(paramInt);
    if (localDataHeader.jdField_a_of_type_Int == 0) {
      return null;
    }
    RunOutput localRunOutput = new RunOutput();
    if (localDataHeader.b != 0) {
      return localRunOutput;
    }
    localRunOutput.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolQueryVersionResult = QueryVersionResult.a(paramDecoder.a(paramInt + 8, false));
    localRunOutput.jdField_a_of_type_Int = 0;
    return localRunOutput;
  }
  
  public int a()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public QueryVersionResult a()
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Int != 0)) {
      throw new AssertionError();
    }
    return this.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolQueryVersionResult;
  }
  
  public void a(QueryVersionResult paramQueryVersionResult)
  {
    this.jdField_a_of_type_Int = 0;
    this.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolQueryVersionResult = paramQueryVersionResult;
  }
  
  protected final void encode(Encoder paramEncoder, int paramInt)
  {
    paramEncoder.a(16, paramInt);
    paramEncoder.a(this.jdField_a_of_type_Int, paramInt + 4);
    if (this.jdField_a_of_type_Int != 0) {
      return;
    }
    paramEncoder.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolQueryVersionResult, paramInt + 8, false);
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
    paramObject = (RunOutput)paramObject;
    int i = this.jdField_a_of_type_Int;
    if (i != ((RunOutput)paramObject).jdField_a_of_type_Int) {
      return false;
    }
    if (i != 0) {
      return false;
    }
    return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolQueryVersionResult, ((RunOutput)paramObject).jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolQueryVersionResult);
  }
  
  public int hashCode()
  {
    int i = (getClass().hashCode() + 31) * 31 + BindingsHelper.b(this.jdField_a_of_type_Int);
    if (this.jdField_a_of_type_Int != 0) {
      return i;
    }
    return i * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoBindingsInterfacecontrolQueryVersionResult);
  }
  
  public static final class Tag {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\interfacecontrol\RunOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */