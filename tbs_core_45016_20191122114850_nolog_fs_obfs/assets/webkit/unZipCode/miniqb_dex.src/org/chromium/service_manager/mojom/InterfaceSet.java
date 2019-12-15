package org.chromium.service_manager.mojom;

import java.util.Arrays;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Struct;

public final class InterfaceSet
  extends Struct
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
  private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(16, 0) };
  public String[] a;
  
  public InterfaceSet()
  {
    this(0);
  }
  
  private InterfaceSet(int paramInt)
  {
    super(16, paramInt);
  }
  
  protected final void encode(Encoder paramEncoder)
  {
    paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
    String[] arrayOfString = this.jdField_a_of_type_ArrayOfJavaLangString;
    if (arrayOfString == null)
    {
      paramEncoder.a(8, false);
      return;
    }
    paramEncoder = paramEncoder.a(arrayOfString.length, 8, -1);
    int i = 0;
    for (;;)
    {
      arrayOfString = this.jdField_a_of_type_ArrayOfJavaLangString;
      if (i >= arrayOfString.length) {
        break;
      }
      paramEncoder.a(arrayOfString[i], i * 8 + 8, false);
      i += 1;
    }
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
    paramObject = (InterfaceSet)paramObject;
    return Arrays.deepEquals(this.jdField_a_of_type_ArrayOfJavaLangString, ((InterfaceSet)paramObject).jdField_a_of_type_ArrayOfJavaLangString);
  }
  
  public int hashCode()
  {
    return (getClass().hashCode() + 31) * 31 + Arrays.deepHashCode(this.jdField_a_of_type_ArrayOfJavaLangString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\service_manager\mojom\InterfaceSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */