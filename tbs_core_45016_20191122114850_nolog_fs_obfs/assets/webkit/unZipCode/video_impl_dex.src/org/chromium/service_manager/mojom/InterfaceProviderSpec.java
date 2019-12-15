package org.chromium.service_manager.mojom;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.chromium.mojo.bindings.BindingsHelper;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Struct;

public final class InterfaceProviderSpec
  extends Struct
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
  private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(24, 0) };
  public Map<String, InterfaceSet> a;
  public Map<String, CapabilitySet> b;
  
  public InterfaceProviderSpec()
  {
    this(0);
  }
  
  private InterfaceProviderSpec(int paramInt)
  {
    super(24, paramInt);
  }
  
  protected final void encode(Encoder paramEncoder)
  {
    paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
    Object localObject4;
    if (this.jdField_a_of_type_JavaUtilMap == null)
    {
      paramEncoder.a(8, false);
    }
    else
    {
      localObject2 = paramEncoder.a(8);
      i = this.jdField_a_of_type_JavaUtilMap.size();
      localObject3 = new String[i];
      localObject1 = new InterfaceSet[i];
      localObject4 = this.jdField_a_of_type_JavaUtilMap.entrySet().iterator();
      i = 0;
      while (((Iterator)localObject4).hasNext())
      {
        Map.Entry localEntry = (Map.Entry)((Iterator)localObject4).next();
        localObject3[i] = ((String)localEntry.getKey());
        localObject1[i] = ((InterfaceSet)localEntry.getValue());
        i += 1;
      }
      localObject4 = ((Encoder)localObject2).a(localObject3.length, 8, -1);
      i = 0;
      while (i < localObject3.length)
      {
        ((Encoder)localObject4).a(localObject3[i], i * 8 + 8, false);
        i += 1;
      }
      localObject2 = ((Encoder)localObject2).a(localObject1.length, 16, -1);
      i = 0;
      while (i < localObject1.length)
      {
        ((Encoder)localObject2).a(localObject1[i], i * 8 + 8, false);
        i += 1;
      }
    }
    if (this.b == null)
    {
      paramEncoder.a(16, false);
      return;
    }
    Object localObject1 = paramEncoder.a(16);
    int i = this.b.size();
    Object localObject2 = new String[i];
    paramEncoder = new CapabilitySet[i];
    Object localObject3 = this.b.entrySet().iterator();
    i = 0;
    while (((Iterator)localObject3).hasNext())
    {
      localObject4 = (Map.Entry)((Iterator)localObject3).next();
      localObject2[i] = ((String)((Map.Entry)localObject4).getKey());
      paramEncoder[i] = ((CapabilitySet)((Map.Entry)localObject4).getValue());
      i += 1;
    }
    localObject3 = ((Encoder)localObject1).a(localObject2.length, 8, -1);
    i = 0;
    while (i < localObject2.length)
    {
      ((Encoder)localObject3).a(localObject2[i], i * 8 + 8, false);
      i += 1;
    }
    localObject1 = ((Encoder)localObject1).a(paramEncoder.length, 16, -1);
    i = 0;
    while (i < paramEncoder.length)
    {
      ((Encoder)localObject1).a(paramEncoder[i], i * 8 + 8, false);
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
    paramObject = (InterfaceProviderSpec)paramObject;
    if (!BindingsHelper.a(this.jdField_a_of_type_JavaUtilMap, ((InterfaceProviderSpec)paramObject).jdField_a_of_type_JavaUtilMap)) {
      return false;
    }
    return BindingsHelper.a(this.b, ((InterfaceProviderSpec)paramObject).b);
  }
  
  public int hashCode()
  {
    return ((getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_JavaUtilMap)) * 31 + BindingsHelper.a(this.b);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\service_manager\mojom\InterfaceProviderSpec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */