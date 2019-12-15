package org.chromium.device.mojom;

import org.chromium.mojo.bindings.BindingsHelper;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Decoder;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Struct;

public final class BatteryStatus
  extends Struct
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
  private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(40, 0) };
  public double a;
  public boolean a;
  public double b = Double.POSITIVE_INFINITY;
  public double c = 1.0D;
  
  public BatteryStatus()
  {
    this(0);
  }
  
  private BatteryStatus(int paramInt)
  {
    super(40, paramInt);
    this.jdField_a_of_type_Boolean = true;
    this.jdField_a_of_type_Double = 0.0D;
  }
  
  public static BatteryStatus a(Decoder paramDecoder)
  {
    if (paramDecoder == null) {
      return null;
    }
    paramDecoder.a();
    try
    {
      DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
      BatteryStatus localBatteryStatus = new BatteryStatus(localDataHeader.b);
      if (localDataHeader.b >= 0) {
        localBatteryStatus.jdField_a_of_type_Boolean = paramDecoder.a(8, 0);
      }
      if (localDataHeader.b >= 0) {
        localBatteryStatus.jdField_a_of_type_Double = paramDecoder.a(16);
      }
      if (localDataHeader.b >= 0) {
        localBatteryStatus.b = paramDecoder.a(24);
      }
      if (localDataHeader.b >= 0) {
        localBatteryStatus.c = paramDecoder.a(32);
      }
      return localBatteryStatus;
    }
    finally
    {
      paramDecoder.b();
    }
  }
  
  protected final void encode(Encoder paramEncoder)
  {
    paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
    paramEncoder.a(this.jdField_a_of_type_Boolean, 8, 0);
    paramEncoder.a(this.jdField_a_of_type_Double, 16);
    paramEncoder.a(this.b, 24);
    paramEncoder.a(this.c, 32);
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
    paramObject = (BatteryStatus)paramObject;
    if (this.jdField_a_of_type_Boolean != ((BatteryStatus)paramObject).jdField_a_of_type_Boolean) {
      return false;
    }
    if (this.jdField_a_of_type_Double != ((BatteryStatus)paramObject).jdField_a_of_type_Double) {
      return false;
    }
    if (this.b != ((BatteryStatus)paramObject).b) {
      return false;
    }
    return this.c == ((BatteryStatus)paramObject).c;
  }
  
  public int hashCode()
  {
    return ((((getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_Boolean)) * 31 + BindingsHelper.a(this.jdField_a_of_type_Double)) * 31 + BindingsHelper.a(this.b)) * 31 + BindingsHelper.a(this.c);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\device\mojom\BatteryStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */