package org.chromium.service_manager.mojom;

import org.chromium.mojo.bindings.BindingsHelper;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Decoder;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Struct;

public final class RunningServiceInfo
  extends Struct
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
  private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(24, 0) };
  public int a;
  public Identity a;
  public int b;
  
  public RunningServiceInfo()
  {
    this(0);
  }
  
  private RunningServiceInfo(int paramInt)
  {
    super(24, paramInt);
  }
  
  public static RunningServiceInfo a(Decoder paramDecoder)
  {
    if (paramDecoder == null) {
      return null;
    }
    paramDecoder.a();
    try
    {
      DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
      RunningServiceInfo localRunningServiceInfo = new RunningServiceInfo(localDataHeader.b);
      if (localDataHeader.b >= 0) {
        localRunningServiceInfo.jdField_a_of_type_Int = paramDecoder.a(8);
      }
      if (localDataHeader.b >= 0) {
        localRunningServiceInfo.b = paramDecoder.a(12);
      }
      if (localDataHeader.b >= 0) {
        localRunningServiceInfo.jdField_a_of_type_OrgChromiumService_managerMojomIdentity = Identity.a(paramDecoder.a(16, false));
      }
      return localRunningServiceInfo;
    }
    finally
    {
      paramDecoder.b();
    }
  }
  
  protected final void encode(Encoder paramEncoder)
  {
    paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
    paramEncoder.a(this.jdField_a_of_type_Int, 8);
    paramEncoder.a(this.b, 12);
    paramEncoder.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, 16, false);
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
    paramObject = (RunningServiceInfo)paramObject;
    if (this.jdField_a_of_type_Int != ((RunningServiceInfo)paramObject).jdField_a_of_type_Int) {
      return false;
    }
    if (!BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity, ((RunningServiceInfo)paramObject).jdField_a_of_type_OrgChromiumService_managerMojomIdentity)) {
      return false;
    }
    return this.b == ((RunningServiceInfo)paramObject).b;
  }
  
  public int hashCode()
  {
    return (((getClass().hashCode() + 31) * 31 + BindingsHelper.b(this.jdField_a_of_type_Int)) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumService_managerMojomIdentity)) * 31 + BindingsHelper.b(this.b);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\service_manager\mojom\RunningServiceInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */