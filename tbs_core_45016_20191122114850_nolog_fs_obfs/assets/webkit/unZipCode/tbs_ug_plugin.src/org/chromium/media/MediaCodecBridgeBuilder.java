package org.chromium.media;

import android.media.MediaCrypto;
import android.view.Surface;
import org.chromium.base.Log;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("media")
class MediaCodecBridgeBuilder
{
  @CalledByNative
  static MediaCodecBridge createAudioDecoder(String paramString, MediaCrypto paramMediaCrypto, int paramInt1, int paramInt2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, boolean paramBoolean)
  {
    Object localObject = new MediaCodecUtil.CodecCreationInfo();
    try
    {
      MediaCodecUtil.CodecCreationInfo localCodecCreationInfo = MediaCodecUtil.a(paramString, 0, paramMediaCrypto);
      localObject = localCodecCreationInfo;
    }
    catch (Exception localException)
    {
      Log.e("cr_MediaCodecBridge", "Failed to create MediaCodec audio decoder: %s", new Object[] { paramString, localException });
    }
    if (((MediaCodecUtil.CodecCreationInfo)localObject).jdField_a_of_type_AndroidMediaMediaCodec == null) {
      return null;
    }
    localObject = new MediaCodecBridge(((MediaCodecUtil.CodecCreationInfo)localObject).jdField_a_of_type_AndroidMediaMediaCodec, ((MediaCodecUtil.CodecCreationInfo)localObject).jdField_a_of_type_OrgChromiumMediaBitrateAdjuster);
    if (!((MediaCodecBridge)localObject).a(MediaFormatBuilder.a(paramString, paramInt1, paramInt2, new byte[][] { paramArrayOfByte1, paramArrayOfByte2, paramArrayOfByte3 }, paramBoolean), paramMediaCrypto, 0)) {
      return null;
    }
    if (!((MediaCodecBridge)localObject).a())
    {
      ((MediaCodecBridge)localObject).release();
      return null;
    }
    return (MediaCodecBridge)localObject;
  }
  
  @CalledByNative
  static MediaCodecBridge createVideoDecoder(String paramString, int paramInt1, MediaCrypto paramMediaCrypto, int paramInt2, int paramInt3, Surface paramSurface, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, HdrMetadata paramHdrMetadata, boolean paramBoolean)
  {
    Object localObject = new MediaCodecUtil.CodecCreationInfo();
    try
    {
      MediaCodecUtil.CodecCreationInfo localCodecCreationInfo = MediaCodecUtil.a(paramString, paramInt1, paramMediaCrypto);
      localObject = localCodecCreationInfo;
    }
    catch (Exception localException)
    {
      Log.e("cr_MediaCodecBridge", "Failed to create MediaCodec video decoder: %s, codecType: %d", new Object[] { paramString, Integer.valueOf(paramInt1), localException });
    }
    if (((MediaCodecUtil.CodecCreationInfo)localObject).jdField_a_of_type_AndroidMediaMediaCodec == null) {
      return null;
    }
    MediaCodecBridge localMediaCodecBridge = new MediaCodecBridge(((MediaCodecUtil.CodecCreationInfo)localObject).jdField_a_of_type_AndroidMediaMediaCodec, ((MediaCodecUtil.CodecCreationInfo)localObject).jdField_a_of_type_OrgChromiumMediaBitrateAdjuster);
    if ((((MediaCodecUtil.CodecCreationInfo)localObject).jdField_a_of_type_Boolean) && (paramBoolean)) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    }
    if (!localMediaCodecBridge.a(MediaFormatBuilder.a(paramString, paramInt2, paramInt3, new byte[][] { paramArrayOfByte1, paramArrayOfByte2 }, paramHdrMetadata, paramBoolean), paramSurface, paramMediaCrypto, 0)) {
      return null;
    }
    if (!localMediaCodecBridge.a())
    {
      localMediaCodecBridge.release();
      return null;
    }
    return localMediaCodecBridge;
  }
  
  @CalledByNative
  static MediaCodecBridge createVideoEncoder(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    Object localObject1 = new MediaCodecUtil.CodecCreationInfo();
    try
    {
      MediaCodecUtil.CodecCreationInfo localCodecCreationInfo = MediaCodecUtil.a(paramString);
      localObject1 = localCodecCreationInfo;
    }
    catch (Exception localException)
    {
      Log.e("cr_MediaCodecBridge", "Failed to create MediaCodec video encoder: %s", new Object[] { paramString, localException });
    }
    if (((MediaCodecUtil.CodecCreationInfo)localObject1).jdField_a_of_type_AndroidMediaMediaCodec == null) {
      return null;
    }
    Object localObject2;
    if (paramString.equals("video/avc")) {
      localObject2 = new MediaCodecEncoder(((MediaCodecUtil.CodecCreationInfo)localObject1).jdField_a_of_type_AndroidMediaMediaCodec, ((MediaCodecUtil.CodecCreationInfo)localObject1).jdField_a_of_type_OrgChromiumMediaBitrateAdjuster);
    } else {
      localObject2 = new MediaCodecBridge(((MediaCodecUtil.CodecCreationInfo)localObject1).jdField_a_of_type_AndroidMediaMediaCodec, ((MediaCodecUtil.CodecCreationInfo)localObject1).jdField_a_of_type_OrgChromiumMediaBitrateAdjuster);
    }
    if (!((MediaCodecBridge)localObject2).a(MediaFormatBuilder.a(paramString, paramInt1, paramInt2, paramInt3, ((MediaCodecUtil.CodecCreationInfo)localObject1).jdField_a_of_type_OrgChromiumMediaBitrateAdjuster.getInitialFrameRate(paramInt4), paramInt5, paramInt6, ((MediaCodecUtil.CodecCreationInfo)localObject1).jdField_a_of_type_Boolean), null, null, 1)) {
      return null;
    }
    if (!((MediaCodecBridge)localObject2).a())
    {
      ((MediaCodecBridge)localObject2).release();
      return null;
    }
    return (MediaCodecBridge)localObject2;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\MediaCodecBridgeBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */