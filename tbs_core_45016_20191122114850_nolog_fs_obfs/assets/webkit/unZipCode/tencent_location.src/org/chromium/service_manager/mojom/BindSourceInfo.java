package org.chromium.service_manager.mojom;

import org.chromium.mojo.bindings.BindingsHelper;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Decoder;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Struct;

public final class BindSourceInfo
  extends Struct
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
  private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(24, 0) };
  public CapabilitySet a;
  public Identity a;
  
  public BindSourceInfo()
  {
    this(0);
  }
  
  private BindSourceInfo(int paramInt)
  {
    super(24, paramInt);
  }
  
  public static BindSourceInfo a(Decoder paramDecoder)
  {
    if (paramDecoder == null) {
      return null;
    }
    paramDecoder.a();
    try
    {
      DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
      BindSourceInfo localBindSourceInfo = new BindSourceInfo(localDataHeader.b);
      if (localDataHeader.b >= 0) {
        localBindSourceInfo.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = Identity.a(paramDecoder.a(8, false));
      }
      if (localDataHeader.b >= 0) {
        localBindSourceInfo.jdField_a_of_type_OrgChromiumService_managerMojomCapabilitySet = CapabilitySet.a(paramDecoder.a(16, false));
      }
      return localBindSourceInfo;
    }
    finally
    {
      paramDecoder.b();
    }
  }
  
  protected final void encode(Encoder paramEncoder)
  {
    paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
    paramEncoder.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, 8, false);
    paramEncoder.a(this.jdField_a_of_type_OrgChromiumService_managerMojomCapabilitySet, 16, false);
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
    paramObject = (BindSourceInfo)paramObject;
    if (!BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, ((BindSourceInfo)paramObject).jdField_a_of_type_OrgChromiumService_managerMojomIdentity)) {
      return false;
    }
    return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomCapabilitySet, ((BindSourceInfo)paramObject).jdField_a_of_type_OrgChromiumService_managerMojomCapabilitySet);
  }
  
  public int hashCode()
  {
    return ((getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity)) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomCapabilitySet);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\service_manager\mojom\BindSourceInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */