package org.chromium.media.mojom;

import org.chromium.gfx.mojom.Rect;
import org.chromium.mojo.bindings.BindingsHelper;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Decoder;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Struct;
import org.chromium.mojo.common.mojom.UnguessableToken;

public final class AndroidOverlayConfig
  extends Struct
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
  private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(32, 0) };
  public Rect a;
  public UnguessableToken a;
  public boolean a;
  public boolean b;
  
  public AndroidOverlayConfig()
  {
    this(0);
  }
  
  private AndroidOverlayConfig(int paramInt)
  {
    super(32, paramInt);
  }
  
  public static AndroidOverlayConfig a(Decoder paramDecoder)
  {
    if (paramDecoder == null) {
      return null;
    }
    paramDecoder.a();
    try
    {
      DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
      AndroidOverlayConfig localAndroidOverlayConfig = new AndroidOverlayConfig(localDataHeader.b);
      if (localDataHeader.b >= 0) {
        localAndroidOverlayConfig.jdField_a_of_type_OrgChromiumMojoCommonMojomUnguessableToken = UnguessableToken.a(paramDecoder.a(8, false));
      }
      if (localDataHeader.b >= 0) {
        localAndroidOverlayConfig.jdField_a_of_type_OrgChromiumGfxMojomRect = Rect.a(paramDecoder.a(16, false));
      }
      if (localDataHeader.b >= 0) {
        localAndroidOverlayConfig.jdField_a_of_type_Boolean = paramDecoder.a(24, 0);
      }
      if (localDataHeader.b >= 0) {
        localAndroidOverlayConfig.b = paramDecoder.a(24, 1);
      }
      return localAndroidOverlayConfig;
    }
    finally
    {
      paramDecoder.b();
    }
  }
  
  protected final void encode(Encoder paramEncoder)
  {
    paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
    paramEncoder.a(this.jdField_a_of_type_OrgChromiumMojoCommonMojomUnguessableToken, 8, false);
    paramEncoder.a(this.jdField_a_of_type_OrgChromiumGfxMojomRect, 16, false);
    paramEncoder.a(this.jdField_a_of_type_Boolean, 24, 0);
    paramEncoder.a(this.b, 24, 1);
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
    paramObject = (AndroidOverlayConfig)paramObject;
    if (!BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoCommonMojomUnguessableToken, ((AndroidOverlayConfig)paramObject).jdField_a_of_type_OrgChromiumMojoCommonMojomUnguessableToken)) {
      return false;
    }
    if (!BindingsHelper.a(this.jdField_a_of_type_OrgChromiumGfxMojomRect, ((AndroidOverlayConfig)paramObject).jdField_a_of_type_OrgChromiumGfxMojomRect)) {
      return false;
    }
    if (this.jdField_a_of_type_Boolean != ((AndroidOverlayConfig)paramObject).jdField_a_of_type_Boolean) {
      return false;
    }
    return this.b == ((AndroidOverlayConfig)paramObject).b;
  }
  
  public int hashCode()
  {
    return ((((getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumMojoCommonMojomUnguessableToken)) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumGfxMojomRect)) * 31 + BindingsHelper.a(this.jdField_a_of_type_Boolean)) * 31 + BindingsHelper.a(this.b);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\mojom\AndroidOverlayConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */