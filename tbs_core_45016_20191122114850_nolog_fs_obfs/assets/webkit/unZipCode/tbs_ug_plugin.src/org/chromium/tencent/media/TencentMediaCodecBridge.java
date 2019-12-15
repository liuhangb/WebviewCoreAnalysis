package org.chromium.tencent.media;

import android.annotation.TargetApi;
import android.media.AudioTrack;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodec.CryptoException;
import android.media.MediaCodec.CryptoInfo;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.Surface;
import java.nio.ByteBuffer;
import org.chromium.base.Log;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;
import org.chromium.media.BitrateAdjuster;
import org.chromium.media.MediaCodecUtil;
import org.chromium.media.MediaCodecUtil.CodecCreationInfo;

@JNINamespace("media")
class TencentMediaCodecBridge
{
  private static final int BITRATE_ADJUSTMENT_FPS = 30;
  private static final String KEY_CROP_BOTTOM = "crop-bottom";
  private static final String KEY_CROP_LEFT = "crop-left";
  private static final String KEY_CROP_RIGHT = "crop-right";
  private static final String KEY_CROP_TOP = "crop-top";
  private static final int MAXIMUM_INITIAL_FPS = 30;
  private static final long MAX_PRESENTATION_TIMESTAMP_SHIFT_US = 100000L;
  private static final int MEDIA_CODEC_ABORT = 24;
  private static final int MEDIA_CODEC_DEQUEUE_INPUT_AGAIN_LATER = 20;
  private static final int MEDIA_CODEC_DEQUEUE_OUTPUT_AGAIN_LATER = 21;
  private static final int MEDIA_CODEC_ERROR = 5;
  private static final int MEDIA_CODEC_INPUT_END_OF_STREAM = 22;
  private static final int MEDIA_CODEC_NO_KEY = 4;
  private static final int MEDIA_CODEC_OK = 0;
  private static final int MEDIA_CODEC_OUTPUT_BUFFERS_CHANGED = 2;
  private static final int MEDIA_CODEC_OUTPUT_END_OF_STREAM = 23;
  private static final int MEDIA_CODEC_OUTPUT_FORMAT_CHANGED = 3;
  private static final int MEDIA_CODEC_TRY_AGAIN_LATER = 1;
  private static final int MEDIA_CODEC_UNKNOWN_CIPHER_MODE = -1;
  private static final int MEDIA_ENCRYPTION_SCHEME_CIPHER_MODE_AES_CBC = 2;
  private static final int MEDIA_ENCRYPTION_SCHEME_CIPHER_MODE_AES_CTR = 1;
  private static final int MEDIA_ENCRYPTION_SCHEME_CIPHER_MODE_UNENCRYPTED = 0;
  private static final int PCM16_BYTES_PER_SAMPLE = 2;
  private static final String TAG = "cr_TencentMediaCodecBridge";
  public BitrateAdjuster bitrateAdjuster = BitrateAdjuster.jdField_a_of_type_OrgChromiumMediaBitrateAdjuster;
  private boolean mAdaptivePlaybackSupported;
  private AudioTrack mAudioTrack;
  private boolean mFlushed;
  private ByteBuffer[] mInputBuffers;
  private long mLastPresentationTimeUs;
  private MediaCodec mMediaCodec;
  private String mMime;
  private ByteBuffer[] mOutputBuffers;
  private byte[] mPendingAudioBuffer;
  
  private TencentMediaCodecBridge(MediaCodec paramMediaCodec, String paramString, boolean paramBoolean, BitrateAdjuster paramBitrateAdjuster)
  {
    this.mMediaCodec = paramMediaCodec;
    this.mMime = paramString;
    this.mLastPresentationTimeUs = 0L;
    this.mFlushed = true;
    this.mAdaptivePlaybackSupported = paramBoolean;
    this.bitrateAdjuster = paramBitrateAdjuster;
  }
  
  @CalledByNative
  private boolean configureAudio(MediaFormat paramMediaFormat, MediaCrypto paramMediaCrypto, int paramInt, boolean paramBoolean)
  {
    try
    {
      this.mMediaCodec.configure(paramMediaFormat, null, paramMediaCrypto, paramInt);
      if (paramBoolean)
      {
        paramBoolean = createAudioTrack(paramMediaFormat.getInteger("sample-rate"), paramMediaFormat.getInteger("channel-count"));
        if (!paramBoolean) {
          return false;
        }
      }
      return true;
    }
    catch (Exception paramMediaFormat)
    {
      Log.e("cr_TencentMediaCodecBridge", "Cannot configure the audio codec", new Object[] { paramMediaFormat });
      return false;
    }
    catch (MediaCodec.CryptoException paramMediaFormat)
    {
      Log.e("cr_TencentMediaCodecBridge", "Cannot configure the audio codec: DRM error", new Object[] { paramMediaFormat });
      return false;
    }
    catch (IllegalStateException paramMediaFormat)
    {
      Log.e("cr_TencentMediaCodecBridge", "Cannot configure the audio codec", new Object[] { paramMediaFormat });
      return false;
    }
    catch (IllegalArgumentException paramMediaFormat)
    {
      Log.e("cr_TencentMediaCodecBridge", "Cannot configure the audio codec", new Object[] { paramMediaFormat });
    }
    return false;
  }
  
  @CalledByNative
  private boolean configureVideo(MediaFormat paramMediaFormat, Surface paramSurface, MediaCrypto paramMediaCrypto, int paramInt, boolean paramBoolean)
  {
    if (!paramBoolean) {}
    try
    {
      this.mAdaptivePlaybackSupported = false;
      if (this.mAdaptivePlaybackSupported)
      {
        paramMediaFormat.setInteger("max-width", paramMediaFormat.getInteger("width"));
        paramMediaFormat.setInteger("max-height", paramMediaFormat.getInteger("height"));
      }
      maybeSetMaxInputSize(paramMediaFormat);
      this.mMediaCodec.configure(paramMediaFormat, paramSurface, paramMediaCrypto, paramInt);
      return true;
    }
    catch (Exception paramMediaFormat)
    {
      Log.e("cr_TencentMediaCodecBridge", "Cannot configure the video codec", new Object[] { paramMediaFormat });
      return false;
    }
    catch (MediaCodec.CryptoException paramMediaFormat)
    {
      Log.e("cr_TencentMediaCodecBridge", "Cannot configure the video codec: DRM error", new Object[] { paramMediaFormat });
      return false;
    }
    catch (IllegalStateException paramMediaFormat)
    {
      Log.e("cr_TencentMediaCodecBridge", "Cannot configure the video codec", new Object[] { paramMediaFormat });
      return false;
    }
    catch (IllegalArgumentException paramMediaFormat)
    {
      Log.e("cr_TencentMediaCodecBridge", "Cannot configure the video codec, wrong format or surface", new Object[] { paramMediaFormat });
    }
    return false;
  }
  
  @CalledByNative
  private static TencentMediaCodecBridge create(String paramString, boolean paramBoolean1, int paramInt, boolean paramBoolean2)
  {
    Object localObject = new MediaCodecUtil.CodecCreationInfo();
    if (paramInt == 1) {}
    try
    {
      localCodecCreationInfo = MediaCodecUtil.a(paramString);
      localObject = localCodecCreationInfo;
    }
    catch (Exception localException)
    {
      MediaCodecUtil.CodecCreationInfo localCodecCreationInfo;
      Log.e("cr_TencentMediaCodecBridge", "Failed to create MediaCodec: %s, isSecure: %s, direction: %d", new Object[] { paramString, Boolean.valueOf(paramBoolean1), Integer.valueOf(paramInt), localException });
    }
    localCodecCreationInfo = MediaCodecUtil.a(paramString, paramBoolean1, paramBoolean2);
    localObject = localCodecCreationInfo;
    if (((MediaCodecUtil.CodecCreationInfo)localObject).jdField_a_of_type_AndroidMediaMediaCodec == null) {
      return null;
    }
    return new TencentMediaCodecBridge(((MediaCodecUtil.CodecCreationInfo)localObject).jdField_a_of_type_AndroidMediaMediaCodec, paramString, ((MediaCodecUtil.CodecCreationInfo)localObject).jdField_a_of_type_Boolean, ((MediaCodecUtil.CodecCreationInfo)localObject).jdField_a_of_type_OrgChromiumMediaBitrateAdjuster);
  }
  
  @CalledByNative
  private static MediaFormat createAudioFormat(String paramString, int paramInt1, int paramInt2)
  {
    return MediaFormat.createAudioFormat(paramString, paramInt1, paramInt2);
  }
  
  @CalledByNative
  private boolean createAudioTrack(int paramInt1, int paramInt2)
  {
    int i = getAudioFormat(paramInt2);
    double d = AudioTrack.getMinBufferSize(paramInt1, i, 2) / 2 / paramInt2;
    Double.isNaN(d);
    int j = (int)(d * 1.5D);
    try
    {
      if (this.mAudioTrack != null) {
        this.mAudioTrack.release();
      }
      this.mAudioTrack = new AudioTrack(3, paramInt1, i, 2, j * 2 * paramInt2, 1);
      if (this.mAudioTrack.getState() == 0)
      {
        Log.e("cr_TencentMediaCodecBridge", "Cannot create AudioTrack", new Object[0]);
        this.mAudioTrack = null;
        return false;
      }
      return true;
    }
    finally {}
  }
  
  @CalledByNative
  private static MediaFormat createVideoDecoderFormat(String paramString, int paramInt1, int paramInt2)
  {
    return MediaFormat.createVideoFormat(paramString, paramInt1, paramInt2);
  }
  
  @CalledByNative
  private MediaFormat createVideoEncoderFormat(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    BitrateAdjuster localBitrateAdjuster1 = this.bitrateAdjuster;
    BitrateAdjuster localBitrateAdjuster2 = BitrateAdjuster.b;
    int i = 30;
    if (localBitrateAdjuster1 == localBitrateAdjuster2) {
      paramInt4 = i;
    } else {
      paramInt4 = Math.min(paramInt4, 30);
    }
    paramString = MediaFormat.createVideoFormat(paramString, paramInt1, paramInt2);
    paramString.setInteger("bitrate", paramInt3);
    paramString.setInteger("frame-rate", paramInt4);
    paramString.setInteger("i-frame-interval", paramInt5);
    paramString.setInteger("color-format", paramInt6);
    return paramString;
  }
  
  @CalledByNative
  private DequeueInputResult dequeueInputBuffer(long paramLong)
  {
    int j = -1;
    int i = 5;
    int k;
    try
    {
      k = this.mMediaCodec.dequeueInputBuffer(paramLong);
      if (k < 0) {
        break label103;
      }
      j = k;
      i = 0;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder;
      Log.e("cr_TencentMediaCodecBridge", "Failed to dequeue input buffer", new Object[] { localException });
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("Unexpected index_or_status: ");
    localStringBuilder.append(k);
    Log.e("cr_TencentMediaCodecBridge", localStringBuilder.toString(), new Object[0]);
    for (;;)
    {
      return new DequeueInputResult(i, j, null);
      label103:
      if (k != -1) {
        break;
      }
      i = 20;
    }
  }
  
  @CalledByNative
  private DequeueOutputResult dequeueOutputBuffer(long paramLong)
  {
    MediaCodec.BufferInfo localBufferInfo = new MediaCodec.BufferInfo();
    int j = -1;
    int i = 5;
    for (;;)
    {
      int k;
      try
      {
        k = this.mMediaCodec.dequeueOutputBuffer(localBufferInfo, paramLong);
        if (localBufferInfo.presentationTimeUs < this.mLastPresentationTimeUs) {
          localBufferInfo.presentationTimeUs = this.mLastPresentationTimeUs;
        }
        this.mLastPresentationTimeUs = localBufferInfo.presentationTimeUs;
        if (k >= 0)
        {
          j = k;
          i = 0;
        }
        else if (k == -3)
        {
          this.mOutputBuffers = this.mMediaCodec.getOutputBuffers();
          i = 2;
        }
        else
        {
          if (k != -2) {
            break label213;
          }
          this.mMediaCodec.getOutputFormat();
          i = 3;
          continue;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Unexpected index_or_status: ");
          localStringBuilder.append(k);
          Log.e("cr_TencentMediaCodecBridge", localStringBuilder.toString(), new Object[0]);
        }
      }
      catch (IllegalStateException localIllegalStateException)
      {
        Log.e("cr_TencentMediaCodecBridge", "Failed to dequeue output buffer", new Object[] { localIllegalStateException });
        i = 5;
        j = -1;
      }
      return new DequeueOutputResult(i, j, localBufferInfo.flags, localBufferInfo.offset, localBufferInfo.presentationTimeUs, localBufferInfo.size, null);
      label213:
      if (k == -1) {
        i = 21;
      }
    }
  }
  
  @CalledByNative
  private int flush()
  {
    try
    {
      this.mFlushed = true;
      this.mMediaCodec.flush();
      return 0;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Log.e("cr_TencentMediaCodecBridge", "Failed to flush MediaCodec", new Object[] { localIllegalStateException });
    }
    return 5;
  }
  
  private int getAudioFormat(int paramInt)
  {
    if (paramInt != 4)
    {
      if (paramInt != 6)
      {
        if (paramInt != 8)
        {
          switch (paramInt)
          {
          default: 
            return 1;
          case 2: 
            return 12;
          }
          return 4;
        }
        if (Build.VERSION.SDK_INT >= 23) {
          return 6396;
        }
        return 1020;
      }
      return 252;
    }
    return 204;
  }
  
  @CalledByNative
  private ByteBuffer getInputBuffer(int paramInt)
  {
    if (Build.VERSION.SDK_INT > 19) {
      try
      {
        ByteBuffer localByteBuffer = this.mMediaCodec.getInputBuffer(paramInt);
        return localByteBuffer;
      }
      catch (IllegalStateException localIllegalStateException)
      {
        Log.e("cr_TencentMediaCodecBridge", "Failed to get input buffer", new Object[] { localIllegalStateException });
        return null;
      }
    }
    return this.mInputBuffers[paramInt];
  }
  
  @TargetApi(19)
  @CalledByNative
  private String getName()
  {
    try
    {
      String str = this.mMediaCodec.getName();
      return str;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Log.e("cr_TencentMediaCodecBridge", "Cannot get codec name", new Object[] { localIllegalStateException });
    }
    return "unknown";
  }
  
  @CalledByNative
  private ByteBuffer getOutputBuffer(int paramInt)
  {
    if (Build.VERSION.SDK_INT > 19) {
      try
      {
        ByteBuffer localByteBuffer = this.mMediaCodec.getOutputBuffer(paramInt);
        return localByteBuffer;
      }
      catch (IllegalStateException localIllegalStateException)
      {
        Log.e("cr_TencentMediaCodecBridge", "Failed to get output buffer", new Object[] { localIllegalStateException });
        return null;
      }
    }
    return this.mOutputBuffers[paramInt];
  }
  
  @CalledByNative
  private GetOutputFormatResult getOutputFormat()
  {
    int i = 0;
    MediaFormat localMediaFormat2;
    try
    {
      MediaFormat localMediaFormat1 = this.mMediaCodec.getOutputFormat();
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Log.e("cr_TencentMediaCodecBridge", "Failed to get output format", new Object[] { localIllegalStateException });
      i = 5;
      localMediaFormat2 = null;
    }
    return new GetOutputFormatResult(i, localMediaFormat2, null);
  }
  
  @CalledByNative
  private boolean isAdaptivePlaybackSupported(int paramInt1, int paramInt2)
  {
    return this.mAdaptivePlaybackSupported;
  }
  
  private void maybeSetMaxInputSize(MediaFormat paramMediaFormat)
  {
    if (paramMediaFormat.containsKey("max-input-size")) {
      return;
    }
    int i = paramMediaFormat.getInteger("height");
    int j = i;
    if (this.mAdaptivePlaybackSupported)
    {
      j = i;
      if (paramMediaFormat.containsKey("max-height")) {
        j = Math.max(i, paramMediaFormat.getInteger("max-height"));
      }
    }
    i = paramMediaFormat.getInteger("width");
    int k = i;
    if (this.mAdaptivePlaybackSupported)
    {
      k = i;
      if (paramMediaFormat.containsKey("max-width")) {
        k = Math.max(j, paramMediaFormat.getInteger("max-width"));
      }
    }
    String str = paramMediaFormat.getString("mime");
    i = -1;
    switch (str.hashCode())
    {
    default: 
      break;
    case 1599127257: 
      if (str.equals("video/x-vnd.on2.vp9")) {
        i = 3;
      }
      break;
    case 1599127256: 
      if (str.equals("video/x-vnd.on2.vp8")) {
        i = 1;
      }
      break;
    case 1331836730: 
      if (str.equals("video/avc")) {
        i = 0;
      }
      break;
    case -1662541442: 
      if (str.equals("video/hevc")) {
        i = 2;
      }
      break;
    }
    switch (i)
    {
    default: 
      return;
    case 2: 
    case 3: 
      j = k * j;
      i = 4;
      break;
    case 1: 
      j = k * j;
      i = 2;
      break;
    case 0: 
      if ("BRAVIA 4K 2015".equals(Build.MODEL)) {
        return;
      }
      j = (k + 15) / 16 * ((j + 15) / 16) * 16 * 16;
      i = 2;
    }
    paramMediaFormat.setInteger("max-input-size", j * 3 / (i * 2));
  }
  
  @CalledByNative
  private long playOutputBuffer(byte[] paramArrayOfByte, boolean paramBoolean)
  {
    try
    {
      if (this.mAudioTrack == null) {
        return 0L;
      }
      if (paramBoolean)
      {
        this.mPendingAudioBuffer = paramArrayOfByte;
        return 0L;
      }
      if (3 != this.mAudioTrack.getPlayState()) {
        this.mAudioTrack.play();
      }
      if (this.mPendingAudioBuffer != null)
      {
        this.mAudioTrack.write(this.mPendingAudioBuffer, 0, this.mPendingAudioBuffer.length);
        i = this.mPendingAudioBuffer.length;
        this.mPendingAudioBuffer = null;
      }
      this.mAudioTrack.write(paramArrayOfByte, 0, paramArrayOfByte.length);
      int i = paramArrayOfByte.length;
      long l = this.mAudioTrack.getPlaybackHeadPosition();
      return 0xFFFFFFFF & l;
    }
    finally {}
  }
  
  @CalledByNative
  private int queueInputBuffer(int paramInt1, int paramInt2, int paramInt3, long paramLong, int paramInt4)
  {
    resetLastPresentationTimeIfNeeded(paramLong);
    try
    {
      this.mMediaCodec.queueInputBuffer(paramInt1, paramInt2, paramInt3, paramLong, paramInt4);
      return 0;
    }
    catch (Exception localException)
    {
      Log.e("cr_TencentMediaCodecBridge", "Failed to queue input buffer", new Object[] { localException });
    }
    return 5;
  }
  
  @CalledByNative
  private int queueSecureInputBuffer(int paramInt1, int paramInt2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, long paramLong)
  {
    resetLastPresentationTimeIfNeeded(paramLong);
    for (;;)
    {
      int i;
      try
      {
        i = translateCipherModeValue(paramInt4);
        if (i != -1) {
          break label229;
        }
        return 5;
      }
      catch (IllegalStateException paramArrayOfByte1)
      {
        MediaCodec.CryptoInfo localCryptoInfo;
        paramArrayOfByte2 = new StringBuilder();
        paramArrayOfByte2.append("Failed to queue secure input buffer, IllegalStateException ");
        paramArrayOfByte2.append(paramArrayOfByte1);
        Log.e("cr_TencentMediaCodecBridge", paramArrayOfByte2.toString(), new Object[0]);
        return 5;
      }
      catch (MediaCodec.CryptoException paramArrayOfByte1)
      {
        if (paramArrayOfByte1.getErrorCode() != 1) {
          continue;
        }
        return 4;
        paramArrayOfByte2 = new StringBuilder();
        paramArrayOfByte2.append("Failed to queue secure input buffer, CryptoException with error code ");
        paramArrayOfByte2.append(paramArrayOfByte1.getErrorCode());
        Log.e("cr_TencentMediaCodecBridge", paramArrayOfByte2.toString(), new Object[0]);
        return 5;
      }
      if ((paramInt4 != 0) && (!MediaCodecUtil.platformSupportsCbcsEncryption(Build.VERSION.SDK_INT)))
      {
        Log.e("cr_TencentMediaCodecBridge", "Encryption scheme 'cbcs' not supported on this platform.", new Object[0]);
        return 5;
      }
      localCryptoInfo = new MediaCodec.CryptoInfo();
      localCryptoInfo.set(paramInt3, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfByte2, paramArrayOfByte1, i);
      if ((paramInt5 != 0) && (paramInt6 != 0)) {
        if (paramInt4 != 0)
        {
          MediaCodecUtil.a(localCryptoInfo, paramInt5, paramInt6);
        }
        else
        {
          Log.e("cr_TencentMediaCodecBridge", "Pattern encryption only supported for 'cbcs' scheme (CBC mode).", new Object[0]);
          return 5;
        }
      }
      this.mMediaCodec.queueSecureInputBuffer(paramInt1, paramInt2, localCryptoInfo, paramLong, 0);
      return 0;
      label229:
      if (i == 2) {
        paramInt4 = 1;
      } else {
        paramInt4 = 0;
      }
    }
  }
  
  @CalledByNative
  private void release()
  {
    try
    {
      if (Build.VERSION.SDK_INT >= 18) {
        this.mMediaCodec.getName();
      }
      this.mMediaCodec.release();
      try
      {
        if (this.mAudioTrack != null)
        {
          this.mAudioTrack.release();
          this.mAudioTrack = null;
        }
      }
      finally {}
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Log.e("cr_TencentMediaCodecBridge", "Cannot release media codec", new Object[] { localIllegalStateException });
      this.mMediaCodec = null;
    }
  }
  
  @CalledByNative
  private void releaseOutputBuffer(int paramInt, boolean paramBoolean)
  {
    try
    {
      this.mMediaCodec.releaseOutputBuffer(paramInt, paramBoolean);
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Log.e("cr_TencentMediaCodecBridge", "Failed to release output buffer", new Object[] { localIllegalStateException });
    }
  }
  
  @TargetApi(19)
  @CalledByNative
  private void requestKeyFrameSoon()
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("request-sync", 0);
    try
    {
      this.mMediaCodec.setParameters(localBundle);
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Log.e("cr_TencentMediaCodecBridge", "Failed to set MediaCodec parameters", new Object[] { localIllegalStateException });
    }
  }
  
  private void resetLastPresentationTimeIfNeeded(long paramLong)
  {
    if (this.mFlushed)
    {
      this.mLastPresentationTimeUs = Math.max(paramLong - 100000L, 0L);
      this.mFlushed = false;
    }
  }
  
  @CalledByNative
  private static void setCodecSpecificData(MediaFormat paramMediaFormat, int paramInt, byte[] paramArrayOfByte)
  {
    String str;
    switch (paramInt)
    {
    default: 
      str = null;
      break;
    case 2: 
      str = "csd-2";
      break;
    case 1: 
      str = "csd-1";
      break;
    case 0: 
      str = "csd-0";
    }
    if (str != null) {
      paramMediaFormat.setByteBuffer(str, ByteBuffer.wrap(paramArrayOfByte));
    }
  }
  
  @CalledByNative
  private static void setFrameHasADTSHeader(MediaFormat paramMediaFormat)
  {
    paramMediaFormat.setInteger("is-adts", 1);
  }
  
  @TargetApi(23)
  @CalledByNative
  private boolean setSurface(Surface paramSurface)
  {
    try
    {
      this.mMediaCodec.setOutputSurface(paramSurface);
      return true;
    }
    catch (IllegalStateException paramSurface) {}catch (IllegalArgumentException paramSurface) {}
    Log.e("cr_TencentMediaCodecBridge", "Cannot set output surface", new Object[] { paramSurface });
    return false;
  }
  
  @TargetApi(19)
  @CalledByNative
  private void setVideoBitrate(int paramInt1, int paramInt2)
  {
    int i = paramInt1;
    if (this.bitrateAdjuster == BitrateAdjuster.b)
    {
      i = paramInt1;
      if (paramInt2 > 0) {
        i = paramInt1 * 30 / paramInt2;
      }
    }
    Bundle localBundle = new Bundle();
    localBundle.putInt("video-bitrate", i);
    try
    {
      this.mMediaCodec.setParameters(localBundle);
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Log.e("cr_TencentMediaCodecBridge", "Failed to set MediaCodec parameters", new Object[] { localIllegalStateException });
    }
  }
  
  @CalledByNative
  private void setVolume(double paramDouble)
  {
    try
    {
      if (this.mAudioTrack != null)
      {
        AudioTrack localAudioTrack = this.mAudioTrack;
        float f = (float)paramDouble;
        localAudioTrack.setStereoVolume(f, f);
      }
      return;
    }
    finally {}
  }
  
  @CalledByNative
  private boolean start()
  {
    try
    {
      this.mMediaCodec.start();
      if (Build.VERSION.SDK_INT <= 19)
      {
        this.mInputBuffers = this.mMediaCodec.getInputBuffers();
        this.mOutputBuffers = this.mMediaCodec.getOutputBuffers();
      }
      return true;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      Log.e("cr_TencentMediaCodecBridge", "Cannot start the media codec", new Object[] { localIllegalArgumentException });
      return false;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Log.e("cr_TencentMediaCodecBridge", "Cannot start the media codec", new Object[] { localIllegalStateException });
    }
    return false;
  }
  
  @CalledByNative
  private void stop()
  {
    try
    {
      this.mMediaCodec.stop();
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Log.e("cr_TencentMediaCodecBridge", "Failed to stop MediaCodec", new Object[] { localIllegalStateException });
    }
  }
  
  private int translateCipherModeValue(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unsupported cipher mode: ");
      localStringBuilder.append(paramInt);
      Log.e("cr_TencentMediaCodecBridge", localStringBuilder.toString(), new Object[0]);
      return -1;
    case 2: 
      return 2;
    case 1: 
      return 1;
    }
    return 0;
  }
  
  @MainDex
  private static class DequeueInputResult
  {
    private final int mIndex;
    private final int mStatus;
    
    private DequeueInputResult(int paramInt1, int paramInt2)
    {
      this.mStatus = paramInt1;
      this.mIndex = paramInt2;
    }
    
    @CalledByNative("DequeueInputResult")
    private int index()
    {
      return this.mIndex;
    }
    
    @CalledByNative("DequeueInputResult")
    private int status()
    {
      return this.mStatus;
    }
  }
  
  @MainDex
  private static class DequeueOutputResult
  {
    private final int mFlags;
    private final int mIndex;
    private final int mNumBytes;
    private final int mOffset;
    private final long mPresentationTimeMicroseconds;
    private final int mStatus;
    
    private DequeueOutputResult(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong, int paramInt5)
    {
      this.mStatus = paramInt1;
      this.mIndex = paramInt2;
      this.mFlags = paramInt3;
      this.mOffset = paramInt4;
      this.mPresentationTimeMicroseconds = paramLong;
      this.mNumBytes = paramInt5;
    }
    
    @CalledByNative("DequeueOutputResult")
    private int flags()
    {
      return this.mFlags;
    }
    
    @CalledByNative("DequeueOutputResult")
    private int index()
    {
      return this.mIndex;
    }
    
    @CalledByNative("DequeueOutputResult")
    private int numBytes()
    {
      return this.mNumBytes;
    }
    
    @CalledByNative("DequeueOutputResult")
    private int offset()
    {
      return this.mOffset;
    }
    
    @CalledByNative("DequeueOutputResult")
    private long presentationTimeMicroseconds()
    {
      return this.mPresentationTimeMicroseconds;
    }
    
    @CalledByNative("DequeueOutputResult")
    private int status()
    {
      return this.mStatus;
    }
  }
  
  @MainDex
  private static class GetOutputFormatResult
  {
    private final MediaFormat mFormat;
    private final int mStatus;
    
    private GetOutputFormatResult(int paramInt, MediaFormat paramMediaFormat)
    {
      this.mStatus = paramInt;
      this.mFormat = paramMediaFormat;
    }
    
    @CalledByNative("GetOutputFormatResult")
    private int channelCount()
    {
      return this.mFormat.getInteger("channel-count");
    }
    
    private boolean formatHasCropValues()
    {
      return (this.mFormat.containsKey("crop-right")) && (this.mFormat.containsKey("crop-left")) && (this.mFormat.containsKey("crop-bottom")) && (this.mFormat.containsKey("crop-top"));
    }
    
    @CalledByNative("GetOutputFormatResult")
    private int height()
    {
      if (formatHasCropValues()) {
        return this.mFormat.getInteger("crop-bottom") - this.mFormat.getInteger("crop-top") + 1;
      }
      return this.mFormat.getInteger("height");
    }
    
    @CalledByNative("GetOutputFormatResult")
    private int sampleRate()
    {
      return this.mFormat.getInteger("sample-rate");
    }
    
    @CalledByNative("GetOutputFormatResult")
    private int status()
    {
      return this.mStatus;
    }
    
    @CalledByNative("GetOutputFormatResult")
    private int width()
    {
      if (formatHasCropValues()) {
        return this.mFormat.getInteger("crop-right") - this.mFormat.getInteger("crop-left") + 1;
      }
      return this.mFormat.getInteger("width");
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\media\TencentMediaCodecBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */