package com.tencent.mtt.base.task;

import android.util.Log;
import com.tencent.basesupport.FLogger;
import com.tencent.common.http.HttpConnectionPool;
import com.tencent.common.http.IRequstIntercepter;
import com.tencent.common.http.MttRequestBase;
import com.tencent.common.http.RequesterFactory;
import com.tencent.mtt.base.image.SharpP;
import com.tencent.mtt.base.image.SharpP.ISharpDecorder;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

public class PictureTask
  extends Task
  implements IRequstIntercepter
{
  private static int jdField_a_of_type_Int = 6;
  private static final HttpConnectionPool jdField_a_of_type_ComTencentCommonHttpHttpConnectionPool = new HttpConnectionPool(6, 40L, TimeUnit.SECONDS);
  private static int jdField_b_of_type_Int = 2;
  private static int jdField_c_of_type_Int = 30000;
  private long jdField_a_of_type_Long = 0L;
  private String jdField_a_of_type_JavaLangString;
  private boolean jdField_a_of_type_Boolean = false;
  private byte[] jdField_a_of_type_ArrayOfByte;
  private long jdField_b_of_type_Long = 0L;
  private boolean jdField_b_of_type_Boolean = false;
  private long jdField_c_of_type_Long;
  private volatile boolean jdField_c_of_type_Boolean = false;
  public Object mBindObject;
  public Integer mStatusCode;
  public Throwable mThrowable;
  
  public PictureTask(String paramString1, TaskObserver paramTaskObserver, boolean paramBoolean, String paramString2, byte paramByte)
  {
    setTaskType((byte)-1);
    this.jdField_a_of_type_JavaLangString = paramString1;
    this.mMttRequest = RequesterFactory.getMttRequestBase();
    this.mMttRequest.setUrl(paramString1);
    this.mMttRequest.setMethod(paramByte);
    this.mMttRequest.mForceNoReferer = paramBoolean;
    this.mMttRequest.setRequestType((byte)106);
    if (!paramBoolean) {
      this.mMttRequest.setReferer(paramString2);
    }
    addObserver(paramTaskObserver);
  }
  
  public void acceptSharpP(boolean paramBoolean)
  {
    if (this.jdField_a_of_type_Boolean == paramBoolean) {
      return;
    }
    Object localObject;
    if (paramBoolean)
    {
      if (SharpP.queryDecoder() == null)
      {
        Log.e("PictureTask", "SharpP decoder not implemented", new IllegalAccessException("need implement ISharpDecorder"));
        return;
      }
      if (this.jdField_a_of_type_JavaLangString == null)
      {
        Log.e("PictureTask", "should set url first before enabling SharpP", new IllegalStateException("url is empty"));
        return;
      }
      if (!SharpP.queryDecoder().support(this.jdField_a_of_type_JavaLangString))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("sharpP not currently supported for ");
        ((StringBuilder)localObject).append(this.jdField_a_of_type_JavaLangString);
        ((StringBuilder)localObject).toString();
        return;
      }
      if (SharpP.isDisabled(this.jdField_a_of_type_JavaLangString))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("sharpP is disabled for url ");
        ((StringBuilder)localObject).append(this.jdField_a_of_type_JavaLangString);
        ((StringBuilder)localObject).toString();
        return;
      }
    }
    String str = this.mMttRequest.getHeader("Accept");
    if (str == null)
    {
      Log.e("PictureTask", "should set Accept header before enabling SharpP", new IllegalStateException("Accept is empty"));
      return;
    }
    this.jdField_a_of_type_Boolean = paramBoolean;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("sharpP is ");
    if (paramBoolean) {
      localObject = "on";
    } else {
      localObject = "off";
    }
    localStringBuilder.append((String)localObject);
    localStringBuilder.append(" for ");
    localStringBuilder.append(this.jdField_a_of_type_JavaLangString);
    localStringBuilder.toString();
    if (paramBoolean)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(str);
      ((StringBuilder)localObject).append(",image/sharpp");
      localObject = ((StringBuilder)localObject).toString();
    }
    else
    {
      localObject = str.replaceAll(",image/sharpp", "");
    }
    this.mMttRequest.replaceHeader("Accept", (String)localObject);
    SharpP.report("1", this.jdField_a_of_type_JavaLangString, new String[0]);
    SharpP.acc(new String[] { "RQ" });
  }
  
  public PictureTask addHeader(String paramString1, String paramString2)
  {
    this.mMttRequest.addHeader(paramString1, paramString2);
    return this;
  }
  
  public void cancel()
  {
    super.cancel();
    FLogger.d("PictureTask", "cancel");
    this.mCanceled = true;
    setMttResponse(null);
    this.mStatus = 6;
    fireObserverEvent(this.mStatus);
  }
  
  /* Error */
  public void doRun()
  {
    // Byte code:
    //   0: new 133	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 134	java/lang/StringBuilder:<init>	()V
    //   7: astore 13
    //   9: aload 13
    //   11: ldc -18
    //   13: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   16: pop
    //   17: aload 13
    //   19: aload_0
    //   20: invokevirtual 241	com/tencent/mtt/base/task/PictureTask:getTaskUrl	()Ljava/lang/String;
    //   23: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   26: pop
    //   27: ldc 104
    //   29: aload 13
    //   31: invokevirtual 144	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   34: invokestatic 206	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   37: aload_0
    //   38: iconst_1
    //   39: putfield 56	com/tencent/mtt/base/task/PictureTask:jdField_c_of_type_Boolean	Z
    //   42: invokestatic 247	java/lang/System:currentTimeMillis	()J
    //   45: lstore 7
    //   47: iconst_0
    //   48: istore 6
    //   50: iconst_1
    //   51: istore 4
    //   53: iconst_0
    //   54: istore_3
    //   55: iconst_0
    //   56: istore_2
    //   57: iload 4
    //   59: ifeq +1426 -> 1485
    //   62: aload_0
    //   63: invokevirtual 250	com/tencent/mtt/base/task/PictureTask:closeQuietly	()V
    //   66: aload_0
    //   67: getfield 72	com/tencent/mtt/base/task/PictureTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
    //   70: invokevirtual 253	com/tencent/common/http/MttRequestBase:getUrl	()Ljava/lang/String;
    //   73: astore 17
    //   75: aload_0
    //   76: iconst_0
    //   77: invokestatic 257	com/tencent/common/http/RequesterFactory:getRequester	(I)Lcom/tencent/common/http/Requester;
    //   80: putfield 261	com/tencent/mtt/base/task/PictureTask:mRequester	Lcom/tencent/common/http/Requester;
    //   83: aload_0
    //   84: getfield 261	com/tencent/mtt/base/task/PictureTask:mRequester	Lcom/tencent/common/http/Requester;
    //   87: aload_0
    //   88: invokevirtual 267	com/tencent/common/http/Requester:setIntercepter	(Lcom/tencent/common/http/IRequstIntercepter;)V
    //   91: aload_0
    //   92: iconst_4
    //   93: invokevirtual 270	com/tencent/mtt/base/task/PictureTask:removeTaskAttr	(I)V
    //   96: ldc 104
    //   98: ldc_w 272
    //   101: invokestatic 206	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   104: new 133	java/lang/StringBuilder
    //   107: dup
    //   108: invokespecial 134	java/lang/StringBuilder:<init>	()V
    //   111: astore 13
    //   113: aload 13
    //   115: ldc_w 274
    //   118: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: pop
    //   122: aload 13
    //   124: aload_0
    //   125: invokevirtual 277	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   128: pop
    //   129: ldc 104
    //   131: aload 13
    //   133: invokevirtual 144	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   136: invokestatic 206	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   139: new 133	java/lang/StringBuilder
    //   142: dup
    //   143: invokespecial 134	java/lang/StringBuilder:<init>	()V
    //   146: astore 13
    //   148: aload 13
    //   150: ldc_w 279
    //   153: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   156: pop
    //   157: aload 13
    //   159: aload_0
    //   160: getfield 72	com/tencent/mtt/base/task/PictureTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
    //   163: invokevirtual 253	com/tencent/common/http/MttRequestBase:getUrl	()Ljava/lang/String;
    //   166: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   169: pop
    //   170: aload 13
    //   172: ldc_w 281
    //   175: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   178: pop
    //   179: ldc 104
    //   181: aload 13
    //   183: invokevirtual 144	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   186: invokestatic 206	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   189: aload_0
    //   190: getfield 284	com/tencent/mtt/base/task/PictureTask:mApn	I
    //   193: iconst_4
    //   194: if_icmpne +16 -> 210
    //   197: aload_0
    //   198: getfield 72	com/tencent/mtt/base/task/PictureTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
    //   201: sipush 5000
    //   204: invokevirtual 287	com/tencent/common/http/MttRequestBase:setConnectTimeout	(I)V
    //   207: goto +13 -> 220
    //   210: aload_0
    //   211: getfield 72	com/tencent/mtt/base/task/PictureTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
    //   214: sipush 10000
    //   217: invokevirtual 287	com/tencent/common/http/MttRequestBase:setConnectTimeout	(I)V
    //   220: aload_0
    //   221: getfield 261	com/tencent/mtt/base/task/PictureTask:mRequester	Lcom/tencent/common/http/Requester;
    //   224: aload_0
    //   225: getfield 72	com/tencent/mtt/base/task/PictureTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
    //   228: invokevirtual 291	com/tencent/common/http/Requester:execute	(Lcom/tencent/common/http/MttRequestBase;)Lcom/tencent/common/http/MttResponse;
    //   231: astore 13
    //   233: aload_0
    //   234: aload 13
    //   236: invokevirtual 213	com/tencent/mtt/base/task/PictureTask:setMttResponse	(Lcom/tencent/common/http/MttResponse;)V
    //   239: aload_0
    //   240: aload 13
    //   242: invokevirtual 296	com/tencent/common/http/MttResponse:getContentLength	()J
    //   245: putfield 48	com/tencent/mtt/base/task/PictureTask:jdField_a_of_type_Long	J
    //   248: iconst_0
    //   249: istore_1
    //   250: goto +240 -> 490
    //   253: astore 14
    //   255: goto +36 -> 291
    //   258: astore 14
    //   260: goto +73 -> 333
    //   263: astore 14
    //   265: goto +118 -> 383
    //   268: astore 14
    //   270: goto +152 -> 422
    //   273: astore 14
    //   275: goto +185 -> 460
    //   278: astore 13
    //   280: iload 6
    //   282: istore_1
    //   283: goto +1181 -> 1464
    //   286: astore 14
    //   288: aconst_null
    //   289: astore 13
    //   291: aload_0
    //   292: aload 14
    //   294: putfield 298	com/tencent/mtt/base/task/PictureTask:mThrowable	Ljava/lang/Throwable;
    //   297: aload 14
    //   299: invokevirtual 301	java/lang/Throwable:printStackTrace	()V
    //   302: aload_0
    //   303: getfield 261	com/tencent/mtt/base/task/PictureTask:mRequester	Lcom/tencent/common/http/Requester;
    //   306: ifnull +10 -> 316
    //   309: aload_0
    //   310: getfield 261	com/tencent/mtt/base/task/PictureTask:mRequester	Lcom/tencent/common/http/Requester;
    //   313: invokevirtual 304	com/tencent/common/http/Requester:abort	()V
    //   316: iconst_m1
    //   317: istore_1
    //   318: goto +172 -> 490
    //   321: astore 13
    //   323: iconst_m1
    //   324: istore_1
    //   325: goto +1139 -> 1464
    //   328: astore 14
    //   330: aconst_null
    //   331: astore 13
    //   333: aload_0
    //   334: aload 14
    //   336: putfield 298	com/tencent/mtt/base/task/PictureTask:mThrowable	Ljava/lang/Throwable;
    //   339: ldc 104
    //   341: ldc_w 306
    //   344: invokestatic 206	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   347: aload 14
    //   349: invokevirtual 307	java/io/IOException:printStackTrace	()V
    //   352: aload_0
    //   353: getfield 261	com/tencent/mtt/base/task/PictureTask:mRequester	Lcom/tencent/common/http/Requester;
    //   356: ifnull +10 -> 366
    //   359: aload_0
    //   360: getfield 261	com/tencent/mtt/base/task/PictureTask:mRequester	Lcom/tencent/common/http/Requester;
    //   363: invokevirtual 304	com/tencent/common/http/Requester:abort	()V
    //   366: iconst_2
    //   367: istore_1
    //   368: goto +122 -> 490
    //   371: astore 13
    //   373: iconst_2
    //   374: istore_1
    //   375: goto +1089 -> 1464
    //   378: astore 14
    //   380: aconst_null
    //   381: astore 13
    //   383: aload_0
    //   384: aload 14
    //   386: putfield 298	com/tencent/mtt/base/task/PictureTask:mThrowable	Ljava/lang/Throwable;
    //   389: ldc 104
    //   391: ldc_w 309
    //   394: invokestatic 206	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   397: aload_0
    //   398: getfield 261	com/tencent/mtt/base/task/PictureTask:mRequester	Lcom/tencent/common/http/Requester;
    //   401: ifnull +10 -> 411
    //   404: aload_0
    //   405: getfield 261	com/tencent/mtt/base/task/PictureTask:mRequester	Lcom/tencent/common/http/Requester;
    //   408: invokevirtual 304	com/tencent/common/http/Requester:abort	()V
    //   411: bipush 8
    //   413: istore_1
    //   414: goto +76 -> 490
    //   417: astore 14
    //   419: aconst_null
    //   420: astore 13
    //   422: aload_0
    //   423: aload 14
    //   425: putfield 298	com/tencent/mtt/base/task/PictureTask:mThrowable	Ljava/lang/Throwable;
    //   428: ldc 104
    //   430: ldc_w 311
    //   433: invokestatic 206	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   436: aload_0
    //   437: getfield 261	com/tencent/mtt/base/task/PictureTask:mRequester	Lcom/tencent/common/http/Requester;
    //   440: ifnull +10 -> 450
    //   443: aload_0
    //   444: getfield 261	com/tencent/mtt/base/task/PictureTask:mRequester	Lcom/tencent/common/http/Requester;
    //   447: invokevirtual 304	com/tencent/common/http/Requester:abort	()V
    //   450: iconst_5
    //   451: istore_1
    //   452: goto +38 -> 490
    //   455: astore 14
    //   457: aconst_null
    //   458: astore 13
    //   460: aload_0
    //   461: aload 14
    //   463: putfield 298	com/tencent/mtt/base/task/PictureTask:mThrowable	Ljava/lang/Throwable;
    //   466: ldc 104
    //   468: ldc_w 313
    //   471: invokestatic 206	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   474: aload_0
    //   475: getfield 261	com/tencent/mtt/base/task/PictureTask:mRequester	Lcom/tencent/common/http/Requester;
    //   478: ifnull +10 -> 488
    //   481: aload_0
    //   482: getfield 261	com/tencent/mtt/base/task/PictureTask:mRequester	Lcom/tencent/common/http/Requester;
    //   485: invokevirtual 304	com/tencent/common/http/Requester:abort	()V
    //   488: iconst_3
    //   489: istore_1
    //   490: aload 13
    //   492: ifnonnull +9 -> 501
    //   495: aconst_null
    //   496: astore 14
    //   498: goto +10 -> 508
    //   501: aload 13
    //   503: invokevirtual 317	com/tencent/common/http/MttResponse:getStatusCode	()Ljava/lang/Integer;
    //   506: astore 14
    //   508: aload_0
    //   509: aload 14
    //   511: putfield 319	com/tencent/mtt/base/task/PictureTask:mStatusCode	Ljava/lang/Integer;
    //   514: iload_1
    //   515: istore 5
    //   517: iload_1
    //   518: ifne +14 -> 532
    //   521: iload_1
    //   522: istore 5
    //   524: aload 14
    //   526: ifnonnull +6 -> 532
    //   529: iconst_m1
    //   530: istore 5
    //   532: new 133	java/lang/StringBuilder
    //   535: dup
    //   536: invokespecial 134	java/lang/StringBuilder:<init>	()V
    //   539: astore 15
    //   541: aload 15
    //   543: ldc_w 321
    //   546: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   549: pop
    //   550: aload 15
    //   552: iload 5
    //   554: invokevirtual 324	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   557: pop
    //   558: ldc 104
    //   560: aload 15
    //   562: invokevirtual 144	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   565: invokestatic 206	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   568: new 133	java/lang/StringBuilder
    //   571: dup
    //   572: invokespecial 134	java/lang/StringBuilder:<init>	()V
    //   575: astore 15
    //   577: aload 15
    //   579: ldc_w 326
    //   582: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   585: pop
    //   586: aload 15
    //   588: aload 14
    //   590: invokevirtual 277	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   593: pop
    //   594: ldc 104
    //   596: aload 15
    //   598: invokevirtual 144	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   601: invokestatic 206	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   604: aload_0
    //   605: iload 5
    //   607: putfield 329	com/tencent/mtt/base/task/PictureTask:mErrorCode	I
    //   610: aload_0
    //   611: getfield 209	com/tencent/mtt/base/task/PictureTask:mCanceled	Z
    //   614: ifeq +26 -> 640
    //   617: aload_0
    //   618: invokevirtual 250	com/tencent/mtt/base/task/PictureTask:closeQuietly	()V
    //   621: aload_0
    //   622: iconst_5
    //   623: invokevirtual 332	com/tencent/mtt/base/task/PictureTask:setStatus	(B)V
    //   626: aload_0
    //   627: aload_0
    //   628: getfield 217	com/tencent/mtt/base/task/PictureTask:mStatus	B
    //   631: invokevirtual 221	com/tencent/mtt/base/task/PictureTask:fireObserverEvent	(I)V
    //   634: aload_0
    //   635: iconst_0
    //   636: putfield 56	com/tencent/mtt/base/task/PictureTask:jdField_c_of_type_Boolean	Z
    //   639: return
    //   640: iload 5
    //   642: iconst_1
    //   643: if_icmpeq +111 -> 754
    //   646: iload 5
    //   648: ifne +17 -> 665
    //   651: aload 14
    //   653: ifnull +12 -> 665
    //   656: aload 14
    //   658: invokevirtual 338	java/lang/Integer:intValue	()I
    //   661: iconst_m1
    //   662: if_icmpne +92 -> 754
    //   665: invokestatic 247	java/lang/System:currentTimeMillis	()J
    //   668: lstore 9
    //   670: iload 5
    //   672: bipush 8
    //   674: if_icmpeq +80 -> 754
    //   677: iload_3
    //   678: getstatic 340	com/tencent/mtt/base/task/PictureTask:jdField_b_of_type_Int	I
    //   681: if_icmpge +73 -> 754
    //   684: lload 9
    //   686: lload 7
    //   688: lsub
    //   689: l2i
    //   690: getstatic 342	com/tencent/mtt/base/task/PictureTask:jdField_c_of_type_Int	I
    //   693: if_icmpge +61 -> 754
    //   696: ldc2_w 343
    //   699: invokestatic 350	java/lang/Thread:sleep	(J)V
    //   702: goto +10 -> 712
    //   705: astore 13
    //   707: aload 13
    //   709: invokevirtual 351	java/lang/Exception:printStackTrace	()V
    //   712: iload_3
    //   713: iconst_1
    //   714: iadd
    //   715: istore_3
    //   716: new 133	java/lang/StringBuilder
    //   719: dup
    //   720: invokespecial 134	java/lang/StringBuilder:<init>	()V
    //   723: astore 13
    //   725: aload 13
    //   727: ldc_w 353
    //   730: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   733: pop
    //   734: aload 13
    //   736: iload_3
    //   737: invokevirtual 324	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   740: pop
    //   741: ldc 104
    //   743: aload 13
    //   745: invokevirtual 144	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   748: invokestatic 206	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   751: goto -694 -> 57
    //   754: aload 14
    //   756: ifnull +24 -> 780
    //   759: aload 14
    //   761: invokevirtual 338	java/lang/Integer:intValue	()I
    //   764: sipush 200
    //   767: if_icmpne +13 -> 780
    //   770: aload 13
    //   772: invokevirtual 357	com/tencent/common/http/MttResponse:getInputStream	()Lcom/tencent/common/http/MttInputStream;
    //   775: astore 16
    //   777: goto +128 -> 905
    //   780: aload 14
    //   782: ifnull +120 -> 902
    //   785: aload 14
    //   787: invokevirtual 338	java/lang/Integer:intValue	()I
    //   790: sipush 300
    //   793: if_icmplt +109 -> 902
    //   796: aload 14
    //   798: invokevirtual 338	java/lang/Integer:intValue	()I
    //   801: sipush 307
    //   804: if_icmpgt +98 -> 902
    //   807: aload 13
    //   809: invokevirtual 360	com/tencent/common/http/MttResponse:getLocation	()Ljava/lang/String;
    //   812: astore 14
    //   814: new 133	java/lang/StringBuilder
    //   817: dup
    //   818: invokespecial 134	java/lang/StringBuilder:<init>	()V
    //   821: astore 15
    //   823: aload 15
    //   825: ldc_w 362
    //   828: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   831: pop
    //   832: aload 15
    //   834: aload 14
    //   836: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   839: pop
    //   840: ldc 104
    //   842: aload 15
    //   844: invokevirtual 144	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   847: invokestatic 206	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   850: aload 14
    //   852: ifnull +44 -> 896
    //   855: iload_2
    //   856: getstatic 364	com/tencent/mtt/base/task/PictureTask:jdField_a_of_type_Int	I
    //   859: if_icmpge +43 -> 902
    //   862: aload_0
    //   863: getfield 72	com/tencent/mtt/base/task/PictureTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
    //   866: aload_0
    //   867: getfield 72	com/tencent/mtt/base/task/PictureTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
    //   870: invokevirtual 253	com/tencent/common/http/MttRequestBase:getUrl	()Ljava/lang/String;
    //   873: aload 14
    //   875: invokestatic 369	com/tencent/common/utils/UrlUtils:resolveBase	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   878: invokevirtual 78	com/tencent/common/http/MttRequestBase:setUrl	(Ljava/lang/String;)V
    //   881: aload_0
    //   882: getfield 72	com/tencent/mtt/base/task/PictureTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
    //   885: iconst_0
    //   886: invokevirtual 81	com/tencent/common/http/MttRequestBase:setMethod	(B)V
    //   889: iload_2
    //   890: iconst_1
    //   891: iadd
    //   892: istore_2
    //   893: goto -836 -> 57
    //   896: aload_0
    //   897: iconst_0
    //   898: putfield 56	com/tencent/mtt/base/task/PictureTask:jdField_c_of_type_Boolean	Z
    //   901: return
    //   902: aconst_null
    //   903: astore 16
    //   905: aload_0
    //   906: iconst_1
    //   907: putfield 217	com/tencent/mtt/base/task/PictureTask:mStatus	B
    //   910: aload_0
    //   911: getfield 209	com/tencent/mtt/base/task/PictureTask:mCanceled	Z
    //   914: ifeq +26 -> 940
    //   917: aload_0
    //   918: invokevirtual 250	com/tencent/mtt/base/task/PictureTask:closeQuietly	()V
    //   921: aload_0
    //   922: iconst_5
    //   923: putfield 217	com/tencent/mtt/base/task/PictureTask:mStatus	B
    //   926: aload_0
    //   927: aload_0
    //   928: getfield 217	com/tencent/mtt/base/task/PictureTask:mStatus	B
    //   931: invokevirtual 221	com/tencent/mtt/base/task/PictureTask:fireObserverEvent	(I)V
    //   934: aload_0
    //   935: iconst_0
    //   936: putfield 56	com/tencent/mtt/base/task/PictureTask:jdField_c_of_type_Boolean	Z
    //   939: return
    //   940: aload_0
    //   941: getfield 52	com/tencent/mtt/base/task/PictureTask:jdField_a_of_type_Boolean	Z
    //   944: ifeq +124 -> 1068
    //   947: aload 13
    //   949: ifnull +13 -> 962
    //   952: aload 13
    //   954: invokevirtual 373	com/tencent/common/http/MttResponse:getContentType	()Lcom/tencent/common/http/ContentType;
    //   957: astore 14
    //   959: goto +6 -> 965
    //   962: aconst_null
    //   963: astore 14
    //   965: new 133	java/lang/StringBuilder
    //   968: dup
    //   969: invokespecial 134	java/lang/StringBuilder:<init>	()V
    //   972: astore 15
    //   974: aload 15
    //   976: ldc_w 375
    //   979: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   982: pop
    //   983: aload 15
    //   985: aload 14
    //   987: invokevirtual 277	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   990: pop
    //   991: aload 15
    //   993: ldc_w 377
    //   996: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   999: pop
    //   1000: aload 15
    //   1002: aload 17
    //   1004: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1007: pop
    //   1008: aload 15
    //   1010: invokevirtual 144	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1013: pop
    //   1014: aload 14
    //   1016: ifnull +45 -> 1061
    //   1019: ldc_w 379
    //   1022: aload 14
    //   1024: getfield 384	com/tencent/common/http/ContentType:mTypeValue	Ljava/lang/String;
    //   1027: invokevirtual 388	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1030: ifeq +31 -> 1061
    //   1033: ldc_w 390
    //   1036: aload 17
    //   1038: iconst_0
    //   1039: anewarray 173	java/lang/String
    //   1042: invokestatic 187	com/tencent/mtt/base/image/SharpP:report	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V
    //   1045: iconst_1
    //   1046: anewarray 173	java/lang/String
    //   1049: dup
    //   1050: iconst_0
    //   1051: ldc_w 392
    //   1054: aastore
    //   1055: invokestatic 193	com/tencent/mtt/base/image/SharpP:acc	([Ljava/lang/String;)V
    //   1058: goto +10 -> 1068
    //   1061: aload_0
    //   1062: getfield 62	com/tencent/mtt/base/task/PictureTask:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1065: invokestatic 395	com/tencent/mtt/base/image/SharpP:disable	(Ljava/lang/String;)V
    //   1068: aload 16
    //   1070: ifnull +315 -> 1385
    //   1073: new 397	java/io/ByteArrayOutputStream
    //   1076: dup
    //   1077: invokespecial 398	java/io/ByteArrayOutputStream:<init>	()V
    //   1080: astore 18
    //   1082: aload 13
    //   1084: astore 14
    //   1086: aload 13
    //   1088: astore 15
    //   1090: aload_0
    //   1091: aload 16
    //   1093: aload_0
    //   1094: getfield 62	com/tencent/mtt/base/task/PictureTask:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1097: invokevirtual 402	com/tencent/mtt/base/task/PictureTask:onResponse	(Ljava/io/InputStream;Ljava/lang/String;)Z
    //   1100: ifne +207 -> 1307
    //   1103: aload 13
    //   1105: astore 14
    //   1107: aload 13
    //   1109: astore 15
    //   1111: sipush 4096
    //   1114: newarray <illegal type>
    //   1116: astore 19
    //   1118: aload 13
    //   1120: astore 14
    //   1122: aload 13
    //   1124: astore 15
    //   1126: aload 16
    //   1128: aload 19
    //   1130: iconst_0
    //   1131: aload 19
    //   1133: arraylength
    //   1134: invokevirtual 408	com/tencent/common/http/MttInputStream:read	([BII)I
    //   1137: istore_1
    //   1138: iload_1
    //   1139: ifle +95 -> 1234
    //   1142: aload 13
    //   1144: astore 14
    //   1146: aload 13
    //   1148: astore 15
    //   1150: aload 18
    //   1152: aload 19
    //   1154: iconst_0
    //   1155: iload_1
    //   1156: invokevirtual 412	java/io/ByteArrayOutputStream:write	([BII)V
    //   1159: aload 13
    //   1161: astore 14
    //   1163: aload 13
    //   1165: astore 15
    //   1167: aload_0
    //   1168: getfield 50	com/tencent/mtt/base/task/PictureTask:jdField_b_of_type_Long	J
    //   1171: lstore 9
    //   1173: iload_1
    //   1174: i2l
    //   1175: lstore 11
    //   1177: aload_0
    //   1178: lload 9
    //   1180: lload 11
    //   1182: ladd
    //   1183: putfield 50	com/tencent/mtt/base/task/PictureTask:jdField_b_of_type_Long	J
    //   1186: invokestatic 247	java/lang/System:currentTimeMillis	()J
    //   1189: lstore 9
    //   1191: aload_0
    //   1192: getfield 209	com/tencent/mtt/base/task/PictureTask:mCanceled	Z
    //   1195: ifne +306 -> 1501
    //   1198: lload 9
    //   1200: aload_0
    //   1201: getfield 414	com/tencent/mtt/base/task/PictureTask:jdField_c_of_type_Long	J
    //   1204: lsub
    //   1205: ldc2_w 415
    //   1208: lcmp
    //   1209: ifle +292 -> 1501
    //   1212: aload_0
    //   1213: lload 9
    //   1215: putfield 414	com/tencent/mtt/base/task/PictureTask:jdField_c_of_type_Long	J
    //   1218: aload_0
    //   1219: iconst_2
    //   1220: putfield 217	com/tencent/mtt/base/task/PictureTask:mStatus	B
    //   1223: aload_0
    //   1224: aload_0
    //   1225: getfield 217	com/tencent/mtt/base/task/PictureTask:mStatus	B
    //   1228: invokevirtual 221	com/tencent/mtt/base/task/PictureTask:fireObserverEvent	(I)V
    //   1231: goto +270 -> 1501
    //   1234: aload 13
    //   1236: astore 14
    //   1238: new 133	java/lang/StringBuilder
    //   1241: dup
    //   1242: invokespecial 134	java/lang/StringBuilder:<init>	()V
    //   1245: astore 15
    //   1247: aload 15
    //   1249: ldc_w 418
    //   1252: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1255: pop
    //   1256: aload 15
    //   1258: aload_0
    //   1259: getfield 62	com/tencent/mtt/base/task/PictureTask:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1262: invokevirtual 140	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1265: pop
    //   1266: ldc 104
    //   1268: aload 15
    //   1270: invokevirtual 144	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1273: invokestatic 206	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   1276: aload 18
    //   1278: invokevirtual 421	java/io/ByteArrayOutputStream:flush	()V
    //   1281: aload_0
    //   1282: aload 18
    //   1284: invokevirtual 425	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   1287: putfield 427	com/tencent/mtt/base/task/PictureTask:jdField_a_of_type_ArrayOfByte	[B
    //   1290: aload 18
    //   1292: invokevirtual 430	java/io/ByteArrayOutputStream:close	()V
    //   1295: aload 14
    //   1297: astore 13
    //   1299: goto +8 -> 1307
    //   1302: astore 14
    //   1304: goto +47 -> 1351
    //   1307: aload_0
    //   1308: invokevirtual 250	com/tencent/mtt/base/task/PictureTask:closeQuietly	()V
    //   1311: iconst_1
    //   1312: istore_1
    //   1313: goto +74 -> 1387
    //   1316: astore 13
    //   1318: goto +60 -> 1378
    //   1321: aload 14
    //   1323: astore 13
    //   1325: ldc 104
    //   1327: ldc_w 432
    //   1330: invokestatic 206	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   1333: aload 18
    //   1335: invokevirtual 430	java/io/ByteArrayOutputStream:close	()V
    //   1338: goto +33 -> 1371
    //   1341: astore 14
    //   1343: aload 14
    //   1345: invokevirtual 301	java/lang/Throwable:printStackTrace	()V
    //   1348: goto +23 -> 1371
    //   1351: aload 14
    //   1353: invokevirtual 351	java/lang/Exception:printStackTrace	()V
    //   1356: aload 18
    //   1358: invokevirtual 430	java/io/ByteArrayOutputStream:close	()V
    //   1361: goto +10 -> 1371
    //   1364: astore 14
    //   1366: aload 14
    //   1368: invokevirtual 301	java/lang/Throwable:printStackTrace	()V
    //   1371: aload_0
    //   1372: invokevirtual 250	com/tencent/mtt/base/task/PictureTask:closeQuietly	()V
    //   1375: goto +10 -> 1385
    //   1378: aload_0
    //   1379: invokevirtual 250	com/tencent/mtt/base/task/PictureTask:closeQuietly	()V
    //   1382: aload 13
    //   1384: athrow
    //   1385: iconst_0
    //   1386: istore_1
    //   1387: aload_0
    //   1388: invokestatic 247	java/lang/System:currentTimeMillis	()J
    //   1391: lload 7
    //   1393: lsub
    //   1394: l2i
    //   1395: putfield 435	com/tencent/mtt/base/task/PictureTask:mWasteTime	I
    //   1398: iload_1
    //   1399: ifeq +11 -> 1410
    //   1402: aload_0
    //   1403: iconst_3
    //   1404: putfield 217	com/tencent/mtt/base/task/PictureTask:mStatus	B
    //   1407: goto +8 -> 1415
    //   1410: aload_0
    //   1411: iconst_5
    //   1412: putfield 217	com/tencent/mtt/base/task/PictureTask:mStatus	B
    //   1415: getstatic 441	com/tencent/mtt/base/task/ITaskResult:PROXY	Lcom/tencent/basesupport/ModuleProxy;
    //   1418: invokevirtual 447	com/tencent/basesupport/ModuleProxy:get	()Ljava/lang/Object;
    //   1421: checkcast 437	com/tencent/mtt/base/task/ITaskResult
    //   1424: astore 14
    //   1426: aload 14
    //   1428: ifnull +22 -> 1450
    //   1431: aload 14
    //   1433: aload_0
    //   1434: getfield 427	com/tencent/mtt/base/task/PictureTask:jdField_a_of_type_ArrayOfByte	[B
    //   1437: aload 13
    //   1439: aload_0
    //   1440: getfield 62	com/tencent/mtt/base/task/PictureTask:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1443: aload 17
    //   1445: invokeinterface 451 5 0
    //   1450: aload_0
    //   1451: aload_0
    //   1452: getfield 217	com/tencent/mtt/base/task/PictureTask:mStatus	B
    //   1455: invokevirtual 221	com/tencent/mtt/base/task/PictureTask:fireObserverEvent	(I)V
    //   1458: iconst_0
    //   1459: istore 4
    //   1461: goto -1404 -> 57
    //   1464: iload_1
    //   1465: ifeq +17 -> 1482
    //   1468: aload_0
    //   1469: getfield 261	com/tencent/mtt/base/task/PictureTask:mRequester	Lcom/tencent/common/http/Requester;
    //   1472: ifnull +10 -> 1482
    //   1475: aload_0
    //   1476: getfield 261	com/tencent/mtt/base/task/PictureTask:mRequester	Lcom/tencent/common/http/Requester;
    //   1479: invokevirtual 304	com/tencent/common/http/Requester:abort	()V
    //   1482: aload 13
    //   1484: athrow
    //   1485: aload_0
    //   1486: iconst_0
    //   1487: putfield 56	com/tencent/mtt/base/task/PictureTask:jdField_c_of_type_Boolean	Z
    //   1490: return
    //   1491: astore 13
    //   1493: goto -172 -> 1321
    //   1496: astore 14
    //   1498: goto -173 -> 1325
    //   1501: goto -383 -> 1118
    //   1504: astore 14
    //   1506: aload 15
    //   1508: astore 13
    //   1510: goto -159 -> 1351
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1513	0	this	PictureTask
    //   249	1216	1	i	int
    //   56	837	2	j	int
    //   54	683	3	k	int
    //   51	1409	4	m	int
    //   515	160	5	n	int
    //   48	233	6	i1	int
    //   45	1347	7	l1	long
    //   668	546	9	l2	long
    //   1175	6	11	l3	long
    //   7	234	13	localObject1	Object
    //   278	1	13	localObject2	Object
    //   289	1	13	localObject3	Object
    //   321	1	13	localObject4	Object
    //   331	1	13	localObject5	Object
    //   371	1	13	localObject6	Object
    //   381	121	13	localObject7	Object
    //   705	3	13	localException1	Exception
    //   723	575	13	localObject8	Object
    //   1316	1	13	localObject9	Object
    //   1323	160	13	localObject10	Object
    //   1491	1	13	localOutOfMemoryError1	OutOfMemoryError
    //   1508	1	13	localObject11	Object
    //   253	1	14	localThrowable1	Throwable
    //   258	1	14	localIOException1	java.io.IOException
    //   263	1	14	localFileNotFoundException1	java.io.FileNotFoundException
    //   268	1	14	localSocketTimeoutException1	java.net.SocketTimeoutException
    //   273	1	14	localUnknownHostException1	java.net.UnknownHostException
    //   286	12	14	localThrowable2	Throwable
    //   328	20	14	localIOException2	java.io.IOException
    //   378	7	14	localFileNotFoundException2	java.io.FileNotFoundException
    //   417	7	14	localSocketTimeoutException2	java.net.SocketTimeoutException
    //   455	7	14	localUnknownHostException2	java.net.UnknownHostException
    //   496	800	14	localObject12	Object
    //   1302	20	14	localException2	Exception
    //   1341	11	14	localThrowable3	Throwable
    //   1364	3	14	localThrowable4	Throwable
    //   1424	8	14	localITaskResult	ITaskResult
    //   1496	1	14	localOutOfMemoryError2	OutOfMemoryError
    //   1504	1	14	localException3	Exception
    //   539	968	15	localObject13	Object
    //   775	352	16	localMttInputStream	com.tencent.common.http.MttInputStream
    //   73	1371	17	str	String
    //   1080	277	18	localByteArrayOutputStream	java.io.ByteArrayOutputStream
    //   1116	37	19	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   233	248	253	java/lang/Throwable
    //   233	248	258	java/io/IOException
    //   233	248	263	java/io/FileNotFoundException
    //   233	248	268	java/net/SocketTimeoutException
    //   233	248	273	java/net/UnknownHostException
    //   189	207	278	finally
    //   210	220	278	finally
    //   220	233	278	finally
    //   233	248	278	finally
    //   291	297	278	finally
    //   333	347	278	finally
    //   383	397	278	finally
    //   422	436	278	finally
    //   460	474	278	finally
    //   189	207	286	java/lang/Throwable
    //   210	220	286	java/lang/Throwable
    //   220	233	286	java/lang/Throwable
    //   297	302	321	finally
    //   189	207	328	java/io/IOException
    //   210	220	328	java/io/IOException
    //   220	233	328	java/io/IOException
    //   347	352	371	finally
    //   189	207	378	java/io/FileNotFoundException
    //   210	220	378	java/io/FileNotFoundException
    //   220	233	378	java/io/FileNotFoundException
    //   189	207	417	java/net/SocketTimeoutException
    //   210	220	417	java/net/SocketTimeoutException
    //   220	233	417	java/net/SocketTimeoutException
    //   189	207	455	java/net/UnknownHostException
    //   210	220	455	java/net/UnknownHostException
    //   220	233	455	java/net/UnknownHostException
    //   696	702	705	java/lang/Exception
    //   1177	1231	1302	java/lang/Exception
    //   1238	1295	1302	java/lang/Exception
    //   1090	1103	1316	finally
    //   1111	1118	1316	finally
    //   1126	1138	1316	finally
    //   1150	1159	1316	finally
    //   1167	1173	1316	finally
    //   1177	1231	1316	finally
    //   1238	1295	1316	finally
    //   1325	1333	1316	finally
    //   1333	1338	1316	finally
    //   1343	1348	1316	finally
    //   1351	1356	1316	finally
    //   1356	1361	1316	finally
    //   1366	1371	1316	finally
    //   1333	1338	1341	java/lang/Throwable
    //   1356	1361	1364	java/lang/Throwable
    //   1090	1103	1491	java/lang/OutOfMemoryError
    //   1111	1118	1491	java/lang/OutOfMemoryError
    //   1126	1138	1491	java/lang/OutOfMemoryError
    //   1150	1159	1491	java/lang/OutOfMemoryError
    //   1167	1173	1491	java/lang/OutOfMemoryError
    //   1177	1231	1496	java/lang/OutOfMemoryError
    //   1238	1295	1496	java/lang/OutOfMemoryError
    //   1090	1103	1504	java/lang/Exception
    //   1111	1118	1504	java/lang/Exception
    //   1126	1138	1504	java/lang/Exception
    //   1150	1159	1504	java/lang/Exception
    //   1167	1173	1504	java/lang/Exception
  }
  
  public int getProgress()
  {
    long l = this.jdField_a_of_type_Long;
    if (l > 0L) {
      return (int)(this.jdField_b_of_type_Long * 100L / l);
    }
    return -1;
  }
  
  public String getReportString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("url:");
    localStringBuilder.append(getTaskUrl());
    localStringBuilder.append(" fail:");
    localStringBuilder.append(getFailReason());
    localStringBuilder.append(" taskStatus");
    localStringBuilder.append(getStatus());
    return localStringBuilder.toString();
  }
  
  public byte[] getResponseData()
  {
    return this.jdField_a_of_type_ArrayOfByte;
  }
  
  public String getTaskUrl()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public boolean hasStart()
  {
    return this.jdField_c_of_type_Boolean;
  }
  
  public void onIntercept(HttpURLConnection paramHttpURLConnection)
  {
    if (paramHttpURLConnection != null)
    {
      if (!this.jdField_b_of_type_Boolean) {
        return;
      }
      boolean bool2 = false;
      HttpConnectionPool localHttpConnectionPool = jdField_a_of_type_ComTencentCommonHttpHttpConnectionPool;
      boolean bool1 = bool2;
      if (localHttpConnectionPool != null)
      {
        bool1 = bool2;
        if (localHttpConnectionPool.isValid()) {
          bool1 = localHttpConnectionPool.attachConnection(paramHttpURLConnection);
        }
      }
      if (!bool1) {
        setConnectionClose();
      }
      return;
    }
  }
  
  protected boolean onResponse(InputStream paramInputStream)
    throws Exception
  {
    return false;
  }
  
  protected boolean onResponse(InputStream paramInputStream, String paramString)
    throws Exception
  {
    return onResponse(paramInputStream);
  }
  
  public void setKeepAliveEnable(boolean paramBoolean)
  {
    if ((paramBoolean) && (jdField_a_of_type_ComTencentCommonHttpHttpConnectionPool.isValid())) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    }
    this.jdField_b_of_type_Boolean = paramBoolean;
    if (paramBoolean)
    {
      this.mMttRequest.addHeader("Connection", "keep-alive");
      return;
    }
    super.setConnectionClose();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\task\PictureTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */