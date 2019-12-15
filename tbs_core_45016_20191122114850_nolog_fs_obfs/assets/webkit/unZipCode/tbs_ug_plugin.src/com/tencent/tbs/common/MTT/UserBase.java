package com.tencent.tbs.common.MTT;

import android.content.Context;
import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import com.tencent.common.http.Apn;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.baseinfo.TbsUserInfo;
import com.tencent.tbs.common.lbs.LbsManager;
import com.tencent.tbs.common.utils.DeviceUtils;
import com.tencent.tbs.common.utils.TbsInfoUtils;
import com.tencent.tbs.common.wup.MultiWUPRequest;
import com.tencent.tbs.common.wup.WUPRequest;
import com.tencent.tbs.common.wup.WUPTaskProxy;
import java.util.ArrayList;
import java.util.Collection;

public final class UserBase
  extends JceStruct
  implements IWUPRequestCallBack, Cloneable
{
  static final String APP_QB = "com.tencent.mtt";
  static final String APP_QQ = "com.tencent.mobileqq";
  static final String APP_WX = "com.tencent.mm";
  private static final String KEY_NAME_NA = "N/A";
  private static final byte REQ_ID_DEVICE_INFO = 1;
  private static final byte REQ_ID_SOFT_INFO = 2;
  private static final byte REQ_ID_USAGE_INFO = 3;
  private static final String TAG = "UserBase";
  static byte[] cache_sGUID;
  static byte[] cache_sMac;
  static byte[] cache_vLBSKeyData;
  static ArrayList<Long> cache_vWifiMacs;
  private static UserBase mInstance;
  public boolean bSave = true;
  public int iLanguageType = 200;
  public short iMCC = 0;
  public short iMNC = 0;
  public int iServerVer = 1;
  private Object mUBFileLock = new Object();
  public String sAPN = "";
  public String sAdId = "";
  public String sCellNumber = "";
  public String sCellid = "";
  public String sCellphone = "";
  public String sChannel = "";
  public String sFirstChannel = "";
  public byte[] sGUID = new byte[16];
  public String sIMEI = "";
  public String sLAC = "";
  public String sLC = "";
  public byte[] sMac = new byte[1];
  public String sQUA = "";
  public String sUA = "";
  public String sUin = "";
  public String sVenderId = "";
  public byte[] vLBSKeyData = new byte[1];
  public ArrayList<Long> vWifiMacs = new ArrayList();
  
  public UserBase()
  {
    try
    {
      Object localObject1 = TbsBaseModuleShell.getContext();
      DeviceUtils.getIMEI((Context)localObject1);
      DeviceUtils.getIMSI((Context)localObject1);
      localObject1 = GUIDFactory.getInstance().getByteGuid();
      if (localObject1.length > 0) {
        this.sGUID = ((byte[])localObject1);
      } else {
        this.sGUID = new byte[16];
      }
      this.sLC = TbsInfoUtils.getLC();
      this.sQUA = TbsInfoUtils.getQUA();
      this.iServerVer = 2;
      this.sChannel = TbsUserInfo.getCurrentChannelID();
      this.sFirstChannel = TbsUserInfo.getInstance().getOriChannelID();
      this.sAPN = Apn.getApnName(Apn.getApnTypeS());
      localObject1 = null;
      try
      {
        Object localObject2 = new int[2];
        localObject2[0] = -1;
        localObject2[1] = -1;
        String[] arrayOfString = new String[2];
        arrayOfString[0] = "N/A";
        arrayOfString[1] = "N/A";
        boolean bool = TbsBaseModuleShell.sServiceMode;
        this.iMNC = ((short)localObject2[0]);
        this.iMCC = ((short)localObject2[1]);
        this.sLAC = arrayOfString[0];
        this.sCellid = arrayOfString[1];
        localObject2 = DeviceUtils.getMacAddress();
        localObject1 = localObject2;
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
      if (localObject1 != null) {
        this.sMac = ((byte[])localObject1);
      }
      this.sUin = TbsBaseModuleShell.UIN;
      return;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
      return;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
  }
  
  public UserBase(String paramString1, byte[] paramArrayOfByte1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt1, boolean paramBoolean, String paramString7, String paramString8, String paramString9, int paramInt2, short paramShort1, short paramShort2, String paramString10, String paramString11, byte[] paramArrayOfByte2, ArrayList<Long> paramArrayList, byte[] paramArrayOfByte3, String paramString12, String paramString13, String paramString14)
  {
    this.sIMEI = paramString1;
    this.sGUID = paramArrayOfByte1;
    this.sQUA = paramString2;
    this.sLC = paramString3;
    this.sCellphone = paramString4;
    this.sUin = paramString5;
    this.sCellid = paramString6;
    this.iServerVer = paramInt1;
    this.bSave = paramBoolean;
    this.sChannel = paramString7;
    this.sLAC = paramString8;
    this.sUA = paramString9;
    this.iLanguageType = paramInt2;
    this.iMCC = paramShort1;
    this.iMNC = paramShort2;
    this.sAPN = paramString10;
    this.sCellNumber = paramString11;
    this.sMac = paramArrayOfByte2;
    this.vWifiMacs = paramArrayList;
    this.vLBSKeyData = paramArrayOfByte3;
    this.sVenderId = paramString12;
    this.sAdId = paramString13;
    this.sFirstChannel = paramString14;
  }
  
  public UserBase(boolean paramBoolean) {}
  
  public static UserBase getInstance()
  {
    if (mInstance == null) {
      mInstance = new UserBase();
    }
    return mInstance;
  }
  
  public void checkUBChanged()
  {
    Object localObject = load(TbsBaseModuleShell.getCallerContext());
    String[] arrayOfString = new String[3];
    int i = 0;
    arrayOfString[0] = "com.tencent.mtt";
    arrayOfString[1] = "com.tencent.mm";
    arrayOfString[2] = "com.tencent.mobileqq";
    while (i < arrayOfString.length)
    {
      if (TbsBaseModuleShell.getCallerContext().getPackageName().equalsIgnoreCase(arrayOfString[i])) {
        break label64;
      }
      i = (byte)(i + 1);
    }
    i = -1;
    label64:
    if (i != -1)
    {
      if (localObject.equals(mInstance)) {
        return;
      }
      localObject = new MultiWUPRequest();
      ((MultiWUPRequest)localObject).addWUPRequest(getDeviceInfoRequest());
      ((MultiWUPRequest)localObject).addWUPRequest(getSoftInfoRequest());
      ((MultiWUPRequest)localObject).addWUPRequest(getUsageInfoRequest());
      ((MultiWUPRequest)localObject).setRequestName("multi_task_profile");
      WUPTaskProxy.send((MultiWUPRequest)localObject);
      mInstance.save();
      return;
    }
  }
  
  public Object clone()
  {
    try
    {
      Object localObject = super.clone();
      return localObject;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      for (;;) {}
    }
    return null;
  }
  
  public WUPRequest getDeviceInfoRequest()
  {
    Object localObject1 = new DeviceInfo();
    Object localObject2 = mInstance;
    ((DeviceInfo)localObject1).sImei = ((UserBase)localObject2).sIMEI;
    ((DeviceInfo)localObject1).sImsi = ((UserBase)localObject2).sCellphone;
    ((DeviceInfo)localObject1).sPhoneNumber = "";
    ((DeviceInfo)localObject1).vMac = ((UserBase)localObject2).sMac;
    ((DeviceInfo)localObject1).iScreenWidth = DeviceUtils.getWidth(TbsBaseModuleShell.getCallerContext());
    ((DeviceInfo)localObject1).iScreenHeight = DeviceUtils.getHeight(TbsBaseModuleShell.getCallerContext());
    localObject2 = new DeviceInfoReq();
    ((DeviceInfoReq)localObject2).vGuid = GUIDFactory.getInstance().getByteGuid();
    ((DeviceInfoReq)localObject2).stDeviceInfo = ((DeviceInfo)localObject1);
    localObject1 = new WUPRequest("CMD_DEVICE_INFO", "deviceInfo");
    ((WUPRequest)localObject1).put("req", localObject2);
    ((WUPRequest)localObject1).setType((byte)1);
    ((WUPRequest)localObject1).setRequestCallBack(this);
    return (WUPRequest)localObject1;
  }
  
  public WUPRequest getSoftInfoRequest()
  {
    Object localObject1 = new SoftInfo();
    ((SoftInfo)localObject1).sLc = TbsInfoUtils.getLC();
    Object localObject2 = mInstance;
    ((SoftInfo)localObject1).sCurChannel = ((UserBase)localObject2).sChannel;
    ((SoftInfo)localObject1).sOriChannel = ((UserBase)localObject2).sFirstChannel;
    ((SoftInfo)localObject1).iServerVer = 2;
    localObject2 = new SoftInfoReq();
    ((SoftInfoReq)localObject2).vGuid = GUIDFactory.getInstance().getByteGuid();
    ((SoftInfoReq)localObject2).stSoftInfo = ((SoftInfo)localObject1);
    localObject1 = new WUPRequest("CMD_SOFT_INFO", "softInfo");
    ((WUPRequest)localObject1).put("req", localObject2);
    ((WUPRequest)localObject1).setType((byte)2);
    ((WUPRequest)localObject1).setRequestCallBack(this);
    return (WUPRequest)localObject1;
  }
  
  public WUPRequest getUsageInfoRequest()
  {
    Object localObject2 = new int[2];
    Object tmp5_4 = localObject2;
    tmp5_4[0] = -1;
    Object tmp9_5 = tmp5_4;
    tmp9_5[1] = -1;
    tmp9_5;
    String[] arrayOfString = new String[2];
    arrayOfString[0] = "N/A";
    arrayOfString[1] = "N/A";
    Object localObject1 = new UsageInfo();
    ((UsageInfo)localObject1).sCellId = arrayOfString[1];
    ((UsageInfo)localObject1).sLac = arrayOfString[0];
    ((UsageInfo)localObject1).iMcc = ((short)localObject2[1]);
    ((UsageInfo)localObject1).iMnc = ((short)localObject2[0]);
    ((UsageInfo)localObject1).vWifiMacs = LbsManager.getWifiMac();
    ((UsageInfo)localObject1).sApn = Apn.getApnName(Apn.getApnTypeS());
    ((UsageInfo)localObject1).vLbsKeyData = null;
    localObject2 = new UsageInfoReq();
    ((UsageInfoReq)localObject2).vGuid = GUIDFactory.getInstance().getByteGuid();
    ((UsageInfoReq)localObject2).stUsageInfo = ((UsageInfo)localObject1);
    localObject1 = new WUPRequest("CMD_USAGE_INFO", "usageInfo");
    ((WUPRequest)localObject1).put("req", localObject2);
    ((WUPRequest)localObject1).setType((byte)3);
    ((WUPRequest)localObject1).setRequestCallBack(this);
    return (WUPRequest)localObject1;
  }
  
  /* Error */
  public UserBase load(Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 136	com/tencent/tbs/common/MTT/UserBase:mUBFileLock	Ljava/lang/Object;
    //   4: astore 11
    //   6: aload 11
    //   8: monitorenter
    //   9: new 2	com/tencent/tbs/common/MTT/UserBase
    //   12: dup
    //   13: invokespecial 214	com/tencent/tbs/common/MTT/UserBase:<init>	()V
    //   16: astore 12
    //   18: aload 12
    //   20: invokevirtual 406	com/tencent/tbs/common/MTT/UserBase:reSet	()V
    //   23: aload_1
    //   24: invokestatic 412	com/tencent/tbs/common/utils/TbsFileUtils:getUserBaseFile	(Landroid/content/Context;)Ljava/io/File;
    //   27: astore 6
    //   29: aload 6
    //   31: ifnull +574 -> 605
    //   34: aload 6
    //   36: invokevirtual 418	java/io/File:exists	()Z
    //   39: istore 5
    //   41: iload 5
    //   43: ifeq +562 -> 605
    //   46: aconst_null
    //   47: astore 8
    //   49: aconst_null
    //   50: astore 9
    //   52: aconst_null
    //   53: astore 10
    //   55: aconst_null
    //   56: astore_1
    //   57: new 420	java/io/DataInputStream
    //   60: dup
    //   61: new 422	java/io/BufferedInputStream
    //   64: dup
    //   65: aload 6
    //   67: invokestatic 428	com/tencent/common/utils/FileUtils:openInputStream	(Ljava/io/File;)Ljava/io/FileInputStream;
    //   70: invokespecial 431	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   73: invokespecial 432	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
    //   76: astore 6
    //   78: aload 6
    //   80: bipush 16
    //   82: invokestatic 436	com/tencent/common/utils/FileUtils:read	(Ljava/io/InputStream;I)Ljava/nio/ByteBuffer;
    //   85: astore_1
    //   86: aload 12
    //   88: aload_1
    //   89: invokevirtual 441	java/nio/ByteBuffer:position	()I
    //   92: newarray <illegal type>
    //   94: putfield 86	com/tencent/tbs/common/MTT/UserBase:sGUID	[B
    //   97: iconst_0
    //   98: istore_3
    //   99: aload_1
    //   100: iconst_0
    //   101: invokevirtual 444	java/nio/ByteBuffer:position	(I)Ljava/nio/Buffer;
    //   104: pop
    //   105: aload_1
    //   106: aload 12
    //   108: getfield 86	com/tencent/tbs/common/MTT/UserBase:sGUID	[B
    //   111: invokevirtual 448	java/nio/ByteBuffer:get	([B)Ljava/nio/ByteBuffer;
    //   114: pop
    //   115: invokestatic 451	com/tencent/common/utils/FileUtils:getInstance	()Lcom/tencent/common/utils/FileUtils;
    //   118: aload_1
    //   119: invokevirtual 455	com/tencent/common/utils/FileUtils:releaseByteBuffer	(Ljava/nio/ByteBuffer;)Z
    //   122: pop
    //   123: aload 12
    //   125: aload 6
    //   127: invokevirtual 458	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   130: putfield 84	com/tencent/tbs/common/MTT/UserBase:sIMEI	Ljava/lang/String;
    //   133: aload 12
    //   135: aload 6
    //   137: invokevirtual 458	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   140: putfield 88	com/tencent/tbs/common/MTT/UserBase:sQUA	Ljava/lang/String;
    //   143: aload 12
    //   145: aload 6
    //   147: invokevirtual 458	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   150: putfield 90	com/tencent/tbs/common/MTT/UserBase:sLC	Ljava/lang/String;
    //   153: aload 12
    //   155: aload 6
    //   157: invokevirtual 458	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   160: putfield 92	com/tencent/tbs/common/MTT/UserBase:sCellphone	Ljava/lang/String;
    //   163: aload 12
    //   165: aload 6
    //   167: invokevirtual 458	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   170: putfield 94	com/tencent/tbs/common/MTT/UserBase:sUin	Ljava/lang/String;
    //   173: aload 12
    //   175: aload 6
    //   177: invokevirtual 458	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   180: putfield 96	com/tencent/tbs/common/MTT/UserBase:sCellid	Ljava/lang/String;
    //   183: aload 12
    //   185: aload 6
    //   187: invokevirtual 458	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   190: putfield 102	com/tencent/tbs/common/MTT/UserBase:sChannel	Ljava/lang/String;
    //   193: aload 12
    //   195: aload 6
    //   197: invokevirtual 458	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   200: putfield 104	com/tencent/tbs/common/MTT/UserBase:sLAC	Ljava/lang/String;
    //   203: aload 12
    //   205: aload 6
    //   207: invokevirtual 458	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   210: putfield 106	com/tencent/tbs/common/MTT/UserBase:sUA	Ljava/lang/String;
    //   213: aload 12
    //   215: aload 6
    //   217: invokevirtual 458	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   220: putfield 114	com/tencent/tbs/common/MTT/UserBase:sAPN	Ljava/lang/String;
    //   223: aload 12
    //   225: aload 6
    //   227: invokevirtual 458	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   230: putfield 116	com/tencent/tbs/common/MTT/UserBase:sCellNumber	Ljava/lang/String;
    //   233: aload 12
    //   235: aload 6
    //   237: invokevirtual 458	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   240: putfield 127	com/tencent/tbs/common/MTT/UserBase:sVenderId	Ljava/lang/String;
    //   243: aload 12
    //   245: aload 6
    //   247: invokevirtual 458	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   250: putfield 129	com/tencent/tbs/common/MTT/UserBase:sAdId	Ljava/lang/String;
    //   253: aload 12
    //   255: aload 6
    //   257: invokevirtual 458	java/io/DataInputStream:readUTF	()Ljava/lang/String;
    //   260: putfield 131	com/tencent/tbs/common/MTT/UserBase:sFirstChannel	Ljava/lang/String;
    //   263: aload 12
    //   265: aload 6
    //   267: invokevirtual 461	java/io/DataInputStream:readInt	()I
    //   270: putfield 98	com/tencent/tbs/common/MTT/UserBase:iServerVer	I
    //   273: aload 12
    //   275: aload 6
    //   277: invokevirtual 464	java/io/DataInputStream:readBoolean	()Z
    //   280: putfield 100	com/tencent/tbs/common/MTT/UserBase:bSave	Z
    //   283: aload 12
    //   285: aload 6
    //   287: invokevirtual 461	java/io/DataInputStream:readInt	()I
    //   290: putfield 108	com/tencent/tbs/common/MTT/UserBase:iLanguageType	I
    //   293: aload 12
    //   295: aload 6
    //   297: invokevirtual 468	java/io/DataInputStream:readShort	()S
    //   300: putfield 110	com/tencent/tbs/common/MTT/UserBase:iMCC	S
    //   303: aload 12
    //   305: aload 6
    //   307: invokevirtual 468	java/io/DataInputStream:readShort	()S
    //   310: putfield 112	com/tencent/tbs/common/MTT/UserBase:iMNC	S
    //   313: aload 6
    //   315: invokevirtual 461	java/io/DataInputStream:readInt	()I
    //   318: istore 4
    //   320: new 120	java/util/ArrayList
    //   323: dup
    //   324: invokespecial 121	java/util/ArrayList:<init>	()V
    //   327: astore_1
    //   328: iconst_0
    //   329: istore_2
    //   330: iload_2
    //   331: iload 4
    //   333: if_icmpge +23 -> 356
    //   336: aload_1
    //   337: aload 6
    //   339: invokevirtual 472	java/io/DataInputStream:readLong	()J
    //   342: invokestatic 478	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   345: invokevirtual 481	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   348: pop
    //   349: iload_2
    //   350: iconst_1
    //   351: iadd
    //   352: istore_2
    //   353: goto -23 -> 330
    //   356: aload 12
    //   358: aload_1
    //   359: putfield 123	com/tencent/tbs/common/MTT/UserBase:vWifiMacs	Ljava/util/ArrayList;
    //   362: aload 6
    //   364: invokevirtual 461	java/io/DataInputStream:readInt	()I
    //   367: istore 4
    //   369: iload 4
    //   371: newarray <illegal type>
    //   373: astore_1
    //   374: iconst_0
    //   375: istore_2
    //   376: iload_2
    //   377: iload 4
    //   379: if_icmpge +18 -> 397
    //   382: aload_1
    //   383: iload_2
    //   384: aload 6
    //   386: invokevirtual 485	java/io/DataInputStream:readByte	()B
    //   389: bastore
    //   390: iload_2
    //   391: iconst_1
    //   392: iadd
    //   393: istore_2
    //   394: goto -18 -> 376
    //   397: aload 12
    //   399: aload_1
    //   400: putfield 118	com/tencent/tbs/common/MTT/UserBase:sMac	[B
    //   403: aload 6
    //   405: invokevirtual 461	java/io/DataInputStream:readInt	()I
    //   408: istore 4
    //   410: iload 4
    //   412: newarray <illegal type>
    //   414: astore_1
    //   415: iload_3
    //   416: istore_2
    //   417: iload_2
    //   418: iload 4
    //   420: if_icmpge +18 -> 438
    //   423: aload_1
    //   424: iload_2
    //   425: aload 6
    //   427: invokevirtual 485	java/io/DataInputStream:readByte	()B
    //   430: bastore
    //   431: iload_2
    //   432: iconst_1
    //   433: iadd
    //   434: istore_2
    //   435: goto -18 -> 417
    //   438: aload 12
    //   440: aload_1
    //   441: putfield 125	com/tencent/tbs/common/MTT/UserBase:vLBSKeyData	[B
    //   444: aload 6
    //   446: invokevirtual 488	java/io/DataInputStream:close	()V
    //   449: goto +156 -> 605
    //   452: astore_1
    //   453: aload_1
    //   454: invokevirtual 489	java/io/IOException:printStackTrace	()V
    //   457: goto +148 -> 605
    //   460: astore_1
    //   461: goto +122 -> 583
    //   464: astore 7
    //   466: goto +30 -> 496
    //   469: astore 7
    //   471: goto +56 -> 527
    //   474: astore 7
    //   476: goto +82 -> 558
    //   479: astore 7
    //   481: aload_1
    //   482: astore 6
    //   484: aload 7
    //   486: astore_1
    //   487: goto +96 -> 583
    //   490: astore 7
    //   492: aload 8
    //   494: astore 6
    //   496: aload 6
    //   498: astore_1
    //   499: aload 7
    //   501: invokevirtual 490	java/lang/OutOfMemoryError:printStackTrace	()V
    //   504: aload 6
    //   506: ifnull +99 -> 605
    //   509: aload 6
    //   511: invokevirtual 488	java/io/DataInputStream:close	()V
    //   514: goto +91 -> 605
    //   517: astore_1
    //   518: goto -65 -> 453
    //   521: astore 7
    //   523: aload 9
    //   525: astore 6
    //   527: aload 6
    //   529: astore_1
    //   530: aload 7
    //   532: invokevirtual 202	java/lang/Exception:printStackTrace	()V
    //   535: aload 6
    //   537: ifnull +68 -> 605
    //   540: aload 6
    //   542: invokevirtual 488	java/io/DataInputStream:close	()V
    //   545: goto +60 -> 605
    //   548: astore_1
    //   549: goto -96 -> 453
    //   552: astore 7
    //   554: aload 10
    //   556: astore 6
    //   558: aload 6
    //   560: astore_1
    //   561: aload 7
    //   563: invokevirtual 489	java/io/IOException:printStackTrace	()V
    //   566: aload 6
    //   568: ifnull +37 -> 605
    //   571: aload 6
    //   573: invokevirtual 488	java/io/DataInputStream:close	()V
    //   576: goto +29 -> 605
    //   579: astore_1
    //   580: goto -127 -> 453
    //   583: aload 6
    //   585: ifnull +18 -> 603
    //   588: aload 6
    //   590: invokevirtual 488	java/io/DataInputStream:close	()V
    //   593: goto +10 -> 603
    //   596: astore 6
    //   598: aload 6
    //   600: invokevirtual 489	java/io/IOException:printStackTrace	()V
    //   603: aload_1
    //   604: athrow
    //   605: aload 11
    //   607: monitorexit
    //   608: aload 12
    //   610: areturn
    //   611: astore_1
    //   612: aload 11
    //   614: monitorexit
    //   615: aload_1
    //   616: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	617	0	this	UserBase
    //   0	617	1	paramContext	Context
    //   329	106	2	i	int
    //   98	318	3	j	int
    //   318	103	4	k	int
    //   39	3	5	bool	boolean
    //   27	562	6	localObject1	Object
    //   596	3	6	localIOException1	java.io.IOException
    //   464	1	7	localOutOfMemoryError1	OutOfMemoryError
    //   469	1	7	localException1	Exception
    //   474	1	7	localIOException2	java.io.IOException
    //   479	6	7	localObject2	Object
    //   490	10	7	localOutOfMemoryError2	OutOfMemoryError
    //   521	10	7	localException2	Exception
    //   552	10	7	localIOException3	java.io.IOException
    //   47	446	8	localObject3	Object
    //   50	474	9	localObject4	Object
    //   53	502	10	localObject5	Object
    //   4	609	11	localObject6	Object
    //   16	593	12	localUserBase	UserBase
    // Exception table:
    //   from	to	target	type
    //   444	449	452	java/io/IOException
    //   78	97	460	finally
    //   99	328	460	finally
    //   336	349	460	finally
    //   356	374	460	finally
    //   382	390	460	finally
    //   397	415	460	finally
    //   423	431	460	finally
    //   438	444	460	finally
    //   78	97	464	java/lang/OutOfMemoryError
    //   99	328	464	java/lang/OutOfMemoryError
    //   336	349	464	java/lang/OutOfMemoryError
    //   356	374	464	java/lang/OutOfMemoryError
    //   382	390	464	java/lang/OutOfMemoryError
    //   397	415	464	java/lang/OutOfMemoryError
    //   423	431	464	java/lang/OutOfMemoryError
    //   438	444	464	java/lang/OutOfMemoryError
    //   78	97	469	java/lang/Exception
    //   99	328	469	java/lang/Exception
    //   336	349	469	java/lang/Exception
    //   356	374	469	java/lang/Exception
    //   382	390	469	java/lang/Exception
    //   397	415	469	java/lang/Exception
    //   423	431	469	java/lang/Exception
    //   438	444	469	java/lang/Exception
    //   78	97	474	java/io/IOException
    //   99	328	474	java/io/IOException
    //   336	349	474	java/io/IOException
    //   356	374	474	java/io/IOException
    //   382	390	474	java/io/IOException
    //   397	415	474	java/io/IOException
    //   423	431	474	java/io/IOException
    //   438	444	474	java/io/IOException
    //   57	78	479	finally
    //   499	504	479	finally
    //   530	535	479	finally
    //   561	566	479	finally
    //   57	78	490	java/lang/OutOfMemoryError
    //   509	514	517	java/io/IOException
    //   57	78	521	java/lang/Exception
    //   540	545	548	java/io/IOException
    //   57	78	552	java/io/IOException
    //   571	576	579	java/io/IOException
    //   588	593	596	java/io/IOException
    //   9	29	611	finally
    //   34	41	611	finally
    //   444	449	611	finally
    //   453	457	611	finally
    //   509	514	611	finally
    //   540	545	611	finally
    //   571	576	611	finally
    //   588	593	611	finally
    //   598	603	611	finally
    //   603	605	611	finally
    //   605	608	611	finally
    //   612	615	611	finally
  }
  
  public void onWUPTaskFail(WUPRequestBase paramWUPRequestBase)
  {
    if (paramWUPRequestBase != null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("wup task fail : ");
      localStringBuilder.append(paramWUPRequestBase.getType());
      LogUtils.d("UserBase", localStringBuilder.toString());
      switch (paramWUPRequestBase.getType())
      {
      default: 
        return;
      case 3: 
        LogUtils.d("UserBase", "REQ_ID_USAGE_INFO");
        return;
      case 2: 
        LogUtils.d("UserBase", "REQ_ID_SOFT_INFO");
        return;
      }
      LogUtils.d("UserBase", "REQ_ID_DEVICE_INFO");
    }
  }
  
  public void onWUPTaskSuccess(WUPRequestBase paramWUPRequestBase, WUPResponseBase paramWUPResponseBase)
  {
    if (paramWUPRequestBase != null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("wup task succ : ");
      localStringBuilder.append(paramWUPRequestBase.getType());
      LogUtils.d("UserBase", localStringBuilder.toString());
      switch (paramWUPRequestBase.getType())
      {
      default: 
        
      case 3: 
        LogUtils.d("UserBase", "REQ_ID_USAGE_INFO");
        if (paramWUPResponseBase == null) {}
        break;
      case 2: 
        LogUtils.d("UserBase", "REQ_ID_SOFT_INFO");
        if (paramWUPResponseBase == null) {}
        break;
      case 1: 
        LogUtils.d("UserBase", "REQ_ID_DEVICE_INFO");
        if (paramWUPResponseBase == null) {}
        break;
      }
    }
  }
  
  public void reSet()
  {
    this.sIMEI = "";
    this.sGUID = new byte[16];
    this.sQUA = "";
    this.sLC = "";
    this.sCellphone = "";
    this.sUin = "";
    this.sCellid = "";
    this.iServerVer = 0;
    this.bSave = false;
    this.sChannel = "";
    this.sLAC = "";
    this.sUA = "";
    this.iLanguageType = 0;
    this.iMCC = 0;
    this.iMNC = 0;
    this.sAPN = "";
    this.sCellNumber = "";
    this.sMac = new byte[1];
    this.vWifiMacs = new ArrayList();
    this.vLBSKeyData = new byte[1];
    this.sVenderId = "";
    this.sAdId = "";
    this.sFirstChannel = "";
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sIMEI = paramJceInputStream.readString(0, true);
    if (cache_sGUID == null)
    {
      cache_sGUID = (byte[])new byte[1];
      ((byte[])cache_sGUID)[0] = 0;
    }
    this.sGUID = ((byte[])paramJceInputStream.read(cache_sGUID, 1, true));
    this.sQUA = paramJceInputStream.readString(2, true);
    this.sLC = paramJceInputStream.readString(3, true);
    this.sCellphone = paramJceInputStream.readString(4, true);
    this.sUin = paramJceInputStream.readString(5, true);
    this.sCellid = paramJceInputStream.readString(6, false);
    this.iServerVer = paramJceInputStream.read(this.iServerVer, 7, false);
    this.bSave = paramJceInputStream.read(this.bSave, 8, false);
    this.sChannel = paramJceInputStream.readString(9, false);
    this.sLAC = paramJceInputStream.readString(10, false);
    this.sUA = paramJceInputStream.readString(11, false);
    this.iLanguageType = paramJceInputStream.read(this.iLanguageType, 12, false);
    this.iMCC = paramJceInputStream.read(this.iMCC, 13, false);
    this.iMNC = paramJceInputStream.read(this.iMNC, 14, false);
    this.sAPN = paramJceInputStream.readString(15, false);
    this.sCellNumber = paramJceInputStream.readString(16, false);
    if (cache_sMac == null)
    {
      cache_sMac = (byte[])new byte[1];
      ((byte[])cache_sMac)[0] = 0;
    }
    this.sMac = ((byte[])paramJceInputStream.read(cache_sMac, 17, false));
    if (cache_vWifiMacs == null)
    {
      cache_vWifiMacs = new ArrayList();
      cache_vWifiMacs.add(Long.valueOf(0L));
    }
    this.vWifiMacs = ((ArrayList)paramJceInputStream.read(cache_vWifiMacs, 19, false));
    if (cache_vLBSKeyData == null)
    {
      cache_vLBSKeyData = (byte[])new byte[1];
      ((byte[])cache_vLBSKeyData)[0] = 0;
    }
    this.vLBSKeyData = ((byte[])paramJceInputStream.read(cache_vLBSKeyData, 20, false));
    this.sVenderId = paramJceInputStream.readString(21, false);
    this.sAdId = paramJceInputStream.readString(22, false);
    this.sFirstChannel = paramJceInputStream.readString(23, false);
  }
  
  /* Error */
  public void save()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 136	com/tencent/tbs/common/MTT/UserBase:mUBFileLock	Ljava/lang/Object;
    //   4: astore 8
    //   6: aload 8
    //   8: monitorenter
    //   9: invokestatic 142	com/tencent/tbs/common/baseinfo/TbsBaseModuleShell:getContext	()Landroid/content/Context;
    //   12: invokestatic 412	com/tencent/tbs/common/utils/TbsFileUtils:getUserBaseFile	(Landroid/content/Context;)Ljava/io/File;
    //   15: astore 9
    //   17: aconst_null
    //   18: astore 6
    //   20: aconst_null
    //   21: astore 7
    //   23: aconst_null
    //   24: astore 5
    //   26: aload 5
    //   28: astore 4
    //   30: aload 9
    //   32: invokevirtual 418	java/io/File:exists	()Z
    //   35: ifne +13 -> 48
    //   38: aload 5
    //   40: astore 4
    //   42: aload 9
    //   44: invokevirtual 559	java/io/File:createNewFile	()Z
    //   47: pop
    //   48: aload 5
    //   50: astore 4
    //   52: new 561	java/io/DataOutputStream
    //   55: dup
    //   56: new 563	java/io/BufferedOutputStream
    //   59: dup
    //   60: aload 9
    //   62: invokestatic 567	com/tencent/common/utils/FileUtils:openOutputStream	(Ljava/io/File;)Ljava/io/FileOutputStream;
    //   65: invokespecial 570	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   68: invokespecial 571	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   71: astore 5
    //   73: aload_0
    //   74: getfield 86	com/tencent/tbs/common/MTT/UserBase:sGUID	[B
    //   77: astore 4
    //   79: aload_0
    //   80: getfield 86	com/tencent/tbs/common/MTT/UserBase:sGUID	[B
    //   83: arraylength
    //   84: istore_1
    //   85: iconst_0
    //   86: istore_2
    //   87: aload 5
    //   89: aload 4
    //   91: iconst_0
    //   92: iload_1
    //   93: invokevirtual 575	java/io/DataOutputStream:write	([BII)V
    //   96: aload 5
    //   98: aload_0
    //   99: getfield 84	com/tencent/tbs/common/MTT/UserBase:sIMEI	Ljava/lang/String;
    //   102: invokevirtual 578	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   105: aload 5
    //   107: aload_0
    //   108: getfield 88	com/tencent/tbs/common/MTT/UserBase:sQUA	Ljava/lang/String;
    //   111: invokevirtual 578	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   114: aload 5
    //   116: aload_0
    //   117: getfield 90	com/tencent/tbs/common/MTT/UserBase:sLC	Ljava/lang/String;
    //   120: invokevirtual 578	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   123: aload 5
    //   125: aload_0
    //   126: getfield 92	com/tencent/tbs/common/MTT/UserBase:sCellphone	Ljava/lang/String;
    //   129: invokevirtual 578	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   132: aload 5
    //   134: aload_0
    //   135: getfield 94	com/tencent/tbs/common/MTT/UserBase:sUin	Ljava/lang/String;
    //   138: invokevirtual 578	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   141: aload 5
    //   143: aload_0
    //   144: getfield 96	com/tencent/tbs/common/MTT/UserBase:sCellid	Ljava/lang/String;
    //   147: invokevirtual 578	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   150: aload 5
    //   152: aload_0
    //   153: getfield 102	com/tencent/tbs/common/MTT/UserBase:sChannel	Ljava/lang/String;
    //   156: invokevirtual 578	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   159: aload 5
    //   161: aload_0
    //   162: getfield 104	com/tencent/tbs/common/MTT/UserBase:sLAC	Ljava/lang/String;
    //   165: invokevirtual 578	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   168: aload 5
    //   170: aload_0
    //   171: getfield 106	com/tencent/tbs/common/MTT/UserBase:sUA	Ljava/lang/String;
    //   174: invokevirtual 578	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   177: aload 5
    //   179: aload_0
    //   180: getfield 114	com/tencent/tbs/common/MTT/UserBase:sAPN	Ljava/lang/String;
    //   183: invokevirtual 578	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   186: aload 5
    //   188: aload_0
    //   189: getfield 116	com/tencent/tbs/common/MTT/UserBase:sCellNumber	Ljava/lang/String;
    //   192: invokevirtual 578	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   195: aload 5
    //   197: aload_0
    //   198: getfield 127	com/tencent/tbs/common/MTT/UserBase:sVenderId	Ljava/lang/String;
    //   201: invokevirtual 578	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   204: aload 5
    //   206: aload_0
    //   207: getfield 129	com/tencent/tbs/common/MTT/UserBase:sAdId	Ljava/lang/String;
    //   210: invokevirtual 578	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   213: aload 5
    //   215: aload_0
    //   216: getfield 131	com/tencent/tbs/common/MTT/UserBase:sFirstChannel	Ljava/lang/String;
    //   219: invokevirtual 578	java/io/DataOutputStream:writeUTF	(Ljava/lang/String;)V
    //   222: aload 5
    //   224: aload_0
    //   225: getfield 98	com/tencent/tbs/common/MTT/UserBase:iServerVer	I
    //   228: invokevirtual 582	java/io/DataOutputStream:writeInt	(I)V
    //   231: aload 5
    //   233: aload_0
    //   234: getfield 100	com/tencent/tbs/common/MTT/UserBase:bSave	Z
    //   237: invokevirtual 585	java/io/DataOutputStream:writeBoolean	(Z)V
    //   240: aload 5
    //   242: aload_0
    //   243: getfield 108	com/tencent/tbs/common/MTT/UserBase:iLanguageType	I
    //   246: invokevirtual 582	java/io/DataOutputStream:writeInt	(I)V
    //   249: aload 5
    //   251: aload_0
    //   252: getfield 110	com/tencent/tbs/common/MTT/UserBase:iMCC	S
    //   255: invokevirtual 588	java/io/DataOutputStream:writeShort	(I)V
    //   258: aload 5
    //   260: aload_0
    //   261: getfield 112	com/tencent/tbs/common/MTT/UserBase:iMNC	S
    //   264: invokevirtual 588	java/io/DataOutputStream:writeShort	(I)V
    //   267: aload 5
    //   269: aload_0
    //   270: getfield 123	com/tencent/tbs/common/MTT/UserBase:vWifiMacs	Ljava/util/ArrayList;
    //   273: invokevirtual 591	java/util/ArrayList:size	()I
    //   276: invokevirtual 582	java/io/DataOutputStream:writeInt	(I)V
    //   279: aload_0
    //   280: getfield 123	com/tencent/tbs/common/MTT/UserBase:vWifiMacs	Ljava/util/ArrayList;
    //   283: invokevirtual 595	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   286: astore 4
    //   288: aload 4
    //   290: invokeinterface 600 1 0
    //   295: ifeq +24 -> 319
    //   298: aload 5
    //   300: aload 4
    //   302: invokeinterface 603 1 0
    //   307: checkcast 474	java/lang/Long
    //   310: invokevirtual 606	java/lang/Long:longValue	()J
    //   313: invokevirtual 610	java/io/DataOutputStream:writeLong	(J)V
    //   316: goto -28 -> 288
    //   319: aload 5
    //   321: aload_0
    //   322: getfield 118	com/tencent/tbs/common/MTT/UserBase:sMac	[B
    //   325: arraylength
    //   326: invokevirtual 582	java/io/DataOutputStream:writeInt	(I)V
    //   329: aload_0
    //   330: getfield 118	com/tencent/tbs/common/MTT/UserBase:sMac	[B
    //   333: astore 4
    //   335: aload 4
    //   337: arraylength
    //   338: istore_3
    //   339: iconst_0
    //   340: istore_1
    //   341: iload_1
    //   342: iload_3
    //   343: if_icmpge +19 -> 362
    //   346: aload 5
    //   348: aload 4
    //   350: iload_1
    //   351: baload
    //   352: invokevirtual 613	java/io/DataOutputStream:writeByte	(I)V
    //   355: iload_1
    //   356: iconst_1
    //   357: iadd
    //   358: istore_1
    //   359: goto -18 -> 341
    //   362: aload 5
    //   364: aload_0
    //   365: getfield 125	com/tencent/tbs/common/MTT/UserBase:vLBSKeyData	[B
    //   368: arraylength
    //   369: invokevirtual 582	java/io/DataOutputStream:writeInt	(I)V
    //   372: aload_0
    //   373: getfield 125	com/tencent/tbs/common/MTT/UserBase:vLBSKeyData	[B
    //   376: astore 4
    //   378: aload 4
    //   380: arraylength
    //   381: istore_3
    //   382: iload_2
    //   383: istore_1
    //   384: iload_1
    //   385: iload_3
    //   386: if_icmpge +19 -> 405
    //   389: aload 5
    //   391: aload 4
    //   393: iload_1
    //   394: baload
    //   395: invokevirtual 613	java/io/DataOutputStream:writeByte	(I)V
    //   398: iload_1
    //   399: iconst_1
    //   400: iadd
    //   401: istore_1
    //   402: goto -18 -> 384
    //   405: aload 5
    //   407: invokevirtual 614	java/io/DataOutputStream:close	()V
    //   410: goto +111 -> 521
    //   413: astore 4
    //   415: aload 4
    //   417: invokevirtual 489	java/io/IOException:printStackTrace	()V
    //   420: goto +101 -> 521
    //   423: astore 4
    //   425: goto +100 -> 525
    //   428: astore 6
    //   430: goto +31 -> 461
    //   433: astore 6
    //   435: goto +59 -> 494
    //   438: astore 6
    //   440: aload 4
    //   442: astore 5
    //   444: aload 6
    //   446: astore 4
    //   448: goto +77 -> 525
    //   451: astore 4
    //   453: aload 6
    //   455: astore 5
    //   457: aload 4
    //   459: astore 6
    //   461: aload 5
    //   463: astore 4
    //   465: aload 6
    //   467: invokevirtual 202	java/lang/Exception:printStackTrace	()V
    //   470: aload 5
    //   472: ifnull +49 -> 521
    //   475: aload 5
    //   477: invokevirtual 614	java/io/DataOutputStream:close	()V
    //   480: goto +41 -> 521
    //   483: astore 4
    //   485: goto -70 -> 415
    //   488: astore 6
    //   490: aload 7
    //   492: astore 5
    //   494: aload 5
    //   496: astore 4
    //   498: aload 6
    //   500: invokevirtual 489	java/io/IOException:printStackTrace	()V
    //   503: aload 5
    //   505: ifnull +16 -> 521
    //   508: aload 5
    //   510: invokevirtual 614	java/io/DataOutputStream:close	()V
    //   513: goto +8 -> 521
    //   516: astore 4
    //   518: goto -103 -> 415
    //   521: aload 8
    //   523: monitorexit
    //   524: return
    //   525: aload 5
    //   527: ifnull +18 -> 545
    //   530: aload 5
    //   532: invokevirtual 614	java/io/DataOutputStream:close	()V
    //   535: goto +10 -> 545
    //   538: astore 5
    //   540: aload 5
    //   542: invokevirtual 489	java/io/IOException:printStackTrace	()V
    //   545: aload 4
    //   547: athrow
    //   548: astore 4
    //   550: aload 8
    //   552: monitorexit
    //   553: aload 4
    //   555: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	556	0	this	UserBase
    //   84	318	1	i	int
    //   86	297	2	j	int
    //   338	49	3	k	int
    //   28	364	4	localObject1	Object
    //   413	3	4	localIOException1	java.io.IOException
    //   423	18	4	localObject2	Object
    //   446	1	4	localObject3	Object
    //   451	7	4	localException1	Exception
    //   463	1	4	localObject4	Object
    //   483	1	4	localIOException2	java.io.IOException
    //   496	1	4	localObject5	Object
    //   516	30	4	localIOException3	java.io.IOException
    //   548	6	4	localObject6	Object
    //   24	507	5	localObject7	Object
    //   538	3	5	localIOException4	java.io.IOException
    //   18	1	6	localObject8	Object
    //   428	1	6	localException2	Exception
    //   433	1	6	localIOException5	java.io.IOException
    //   438	16	6	localObject9	Object
    //   459	7	6	localObject10	Object
    //   488	11	6	localIOException6	java.io.IOException
    //   21	470	7	localObject11	Object
    //   4	547	8	localObject12	Object
    //   15	46	9	localFile	java.io.File
    // Exception table:
    //   from	to	target	type
    //   405	410	413	java/io/IOException
    //   73	85	423	finally
    //   87	288	423	finally
    //   288	316	423	finally
    //   319	339	423	finally
    //   346	355	423	finally
    //   362	382	423	finally
    //   389	398	423	finally
    //   73	85	428	java/lang/Exception
    //   87	288	428	java/lang/Exception
    //   288	316	428	java/lang/Exception
    //   319	339	428	java/lang/Exception
    //   346	355	428	java/lang/Exception
    //   362	382	428	java/lang/Exception
    //   389	398	428	java/lang/Exception
    //   73	85	433	java/io/IOException
    //   87	288	433	java/io/IOException
    //   288	316	433	java/io/IOException
    //   319	339	433	java/io/IOException
    //   346	355	433	java/io/IOException
    //   362	382	433	java/io/IOException
    //   389	398	433	java/io/IOException
    //   30	38	438	finally
    //   42	48	438	finally
    //   52	73	438	finally
    //   465	470	438	finally
    //   498	503	438	finally
    //   30	38	451	java/lang/Exception
    //   42	48	451	java/lang/Exception
    //   52	73	451	java/lang/Exception
    //   475	480	483	java/io/IOException
    //   30	38	488	java/io/IOException
    //   42	48	488	java/io/IOException
    //   52	73	488	java/io/IOException
    //   508	513	516	java/io/IOException
    //   530	535	538	java/io/IOException
    //   9	17	548	finally
    //   405	410	548	finally
    //   415	420	548	finally
    //   475	480	548	finally
    //   508	513	548	finally
    //   521	524	548	finally
    //   530	535	548	finally
    //   540	545	548	finally
    //   545	548	548	finally
    //   550	553	548	finally
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.sIMEI, 0);
    Object localObject = this.sGUID;
    if (localObject != null) {
      paramJceOutputStream.write((byte[])localObject, 1);
    }
    paramJceOutputStream.write(this.sQUA, 2);
    paramJceOutputStream.write(this.sLC, 3);
    paramJceOutputStream.write(this.sCellphone, 4);
    paramJceOutputStream.write(this.sUin, 5);
    localObject = this.sCellid;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 6);
    }
    paramJceOutputStream.write(this.iServerVer, 7);
    paramJceOutputStream.write(this.bSave, 8);
    localObject = this.sChannel;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 9);
    }
    localObject = this.sLAC;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 10);
    }
    localObject = this.sUA;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 11);
    }
    paramJceOutputStream.write(this.iLanguageType, 12);
    paramJceOutputStream.write(this.iMCC, 13);
    paramJceOutputStream.write(this.iMNC, 14);
    localObject = this.sAPN;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 15);
    }
    localObject = this.sCellNumber;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 16);
    }
    localObject = this.sMac;
    if (localObject != null) {
      paramJceOutputStream.write((byte[])localObject, 17);
    }
    localObject = this.vWifiMacs;
    if (localObject != null) {
      paramJceOutputStream.write((Collection)localObject, 19);
    }
    localObject = this.vLBSKeyData;
    if (localObject != null) {
      paramJceOutputStream.write((byte[])localObject, 20);
    }
    localObject = this.sVenderId;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 21);
    }
    localObject = this.sAdId;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 22);
    }
    localObject = this.sFirstChannel;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 23);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\UserBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */