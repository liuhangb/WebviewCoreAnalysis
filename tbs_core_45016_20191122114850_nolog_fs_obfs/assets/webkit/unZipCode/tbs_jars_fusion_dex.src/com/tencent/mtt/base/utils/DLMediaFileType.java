package com.tencent.mtt.base.utils;

import android.util.SparseArray;

public class DLMediaFileType
{
  public static final byte TYPE_ALL = 9;
  public static final byte TYPE_ALL_FILE = 11;
  public static final byte TYPE_APK = 1;
  public static final byte TYPE_DIRECTORY = 8;
  public static final byte TYPE_DOCUMENT = 5;
  public static final byte TYPE_MOVIE = 3;
  public static final byte TYPE_MUSIC = 4;
  public static final byte TYPE_OTHER = 7;
  public static final byte TYPE_PICTURE = 2;
  public static final byte TYPE_UNKNOWN = 0;
  public static final byte TYPE_ZIP = 6;
  private static SparseArray<FileExtType> a;
  public static String[] mDownloadDirs = new String[10];
  
  static
  {
    String[] arrayOfString = mDownloadDirs;
    arrayOfString[7] = "其他";
    arrayOfString[1] = "安装包";
    arrayOfString[2] = "图片收藏";
    arrayOfString[3] = "视频";
    arrayOfString[4] = "音乐";
    arrayOfString[5] = "文档";
    arrayOfString[6] = "其他";
    arrayOfString[8] = null;
    arrayOfString[0] = null;
    arrayOfString[9] = null;
    a = new SparseArray();
    a.put(fileExtToKey("mid"), FileExtType.FILE_EXT_MID);
    a.put(fileExtToKey("wav"), FileExtType.FILE_EXT_WAV);
    a.put(fileExtToKey("mp3"), FileExtType.FILE_EXT_MP3);
    a.put(fileExtToKey("ape"), FileExtType.FILE_EXT_APE);
    a.put(fileExtToKey("flac"), FileExtType.FILE_EXT_FLAC);
    a.put(fileExtToKey("aac"), FileExtType.FILE_EXT_AAC);
    a.put(fileExtToKey("wma"), FileExtType.FILE_EXT_WMA);
    a.put(fileExtToKey("ogg"), FileExtType.FILE_EXT_OGG);
    a.put(fileExtToKey("amr"), FileExtType.FILE_EXT_AMR);
    a.put(fileExtToKey("m4a"), FileExtType.FILE_EXT_M4A);
    a.put(fileExtToKey("mpga"), FileExtType.FILE_EXT_MPGA);
    a.put(fileExtToKey("ra"), FileExtType.FILE_EXT_RA);
    a.put(fileExtToKey("mp4"), FileExtType.FILE_EXT_MP4);
    a.put(fileExtToKey("rm"), FileExtType.FILE_EXT_RM);
    a.put(fileExtToKey("rmvb"), FileExtType.FILE_EXT_RMVB);
    a.put(fileExtToKey("f4v"), FileExtType.FILE_EXT_F4V);
    a.put(fileExtToKey("flv"), FileExtType.FILE_EXT_FLV);
    a.put(fileExtToKey("avi"), FileExtType.FILE_EXT_AVI);
    a.put(fileExtToKey("3gp"), FileExtType.FILE_EXT_3GP);
    a.put(fileExtToKey("3gpp"), FileExtType.FILE_EXT_3GPP);
    a.put(fileExtToKey("mov"), FileExtType.FILE_EXT_MOV);
    a.put(fileExtToKey("asf"), FileExtType.FILE_EXT_ASF);
    a.put(fileExtToKey("wmv"), FileExtType.FILE_EXT_WMV);
    a.put(fileExtToKey("webm"), FileExtType.FILE_EXT_WEBM);
    a.put(fileExtToKey("mkv"), FileExtType.FILE_EXT_MKV);
    a.put(fileExtToKey("mpg"), FileExtType.FILE_EXT_MPG);
    a.put(fileExtToKey("mpeg"), FileExtType.FILE_EXT_MPEG);
    a.put(fileExtToKey("mpeg1"), FileExtType.FILE_EXT_MPEG1);
    a.put(fileExtToKey("mpeg2"), FileExtType.FILE_EXT_MPEG2);
    a.put(fileExtToKey("m3u8"), FileExtType.FILE_EXT_M3U8);
    a.put(fileExtToKey("ts"), FileExtType.FILE_EXT_TS);
    a.put(fileExtToKey("ogv"), FileExtType.FILE_EXT_OGV);
    a.put(fileExtToKey("vdat"), FileExtType.FILE_EXT_VDAT);
    a.put(fileExtToKey("xvid"), FileExtType.FILE_EXT_XVID);
    a.put(fileExtToKey("dvd"), FileExtType.FILE_EXT_DVD);
    a.put(fileExtToKey("vcd"), FileExtType.FILE_EXT_VCD);
    a.put(fileExtToKey("vob"), FileExtType.FILE_EXT_VOB);
    a.put(fileExtToKey("divx"), FileExtType.FILE_EXT_DIVX);
    a.put(fileExtToKey("svg"), FileExtType.FILE_EXT_SVG);
    a.put(fileExtToKey("jpg"), FileExtType.FILE_EXT_JPG);
    a.put(fileExtToKey("jpeg"), FileExtType.FILE_EXT_JPEG);
    a.put(fileExtToKey("gif"), FileExtType.FILE_EXT_GIF);
    a.put(fileExtToKey("png"), FileExtType.FILE_EXT_PNG);
    a.put(fileExtToKey("bmp"), FileExtType.FILE_EXT_BMP);
    a.put(fileExtToKey("webp"), FileExtType.FILE_EXT_WEBP);
    a.put(fileExtToKey("apk"), FileExtType.FILE_EXT_APK);
    a.put(fileExtToKey("xls"), FileExtType.FILE_EXT_XLS);
    a.put(fileExtToKey("xlsx"), FileExtType.FILE_EXT_XLSX);
    a.put(fileExtToKey("doc"), FileExtType.FILE_EXT_DOC);
    a.put(fileExtToKey("docx"), FileExtType.FILE_EXT_DOCX);
    a.put(fileExtToKey("ppt"), FileExtType.FILE_EXT_PPT);
    a.put(fileExtToKey("pptx"), FileExtType.FILE_EXT_PPTX);
    a.put(fileExtToKey("txt"), FileExtType.FILE_EXT_TXT);
    a.put(fileExtToKey("chm"), FileExtType.FILE_EXT_CHM);
    a.put(fileExtToKey("epub"), FileExtType.FILE_EXT_EPUB);
    a.put(fileExtToKey("pdf"), FileExtType.FILE_EXT_PDF);
    a.put(fileExtToKey("ini"), FileExtType.FILE_EXT_INI);
    a.put(fileExtToKey("log"), FileExtType.FILE_EXT_LOG);
    a.put(fileExtToKey("bat"), FileExtType.FILE_EXT_BAT);
    a.put(fileExtToKey("php"), FileExtType.FILE_EXT_PHP);
    a.put(fileExtToKey("js"), FileExtType.FILE_EXT_JS);
    a.put(fileExtToKey("lrc"), FileExtType.FILE_EXT_LRC);
    a.put(fileExtToKey("htm"), FileExtType.FILE_EXT_HTM);
    a.put(fileExtToKey("html"), FileExtType.FILE_EXT_HTML);
    a.put(fileExtToKey("mht"), FileExtType.FILE_EXT_MHT);
    a.put(fileExtToKey("url"), FileExtType.FILE_EXT_URL);
    a.put(fileExtToKey("xml"), FileExtType.FILE_EXT_XML);
    a.put(fileExtToKey("rar"), FileExtType.FILE_EXT_RAR);
    a.put(fileExtToKey("zip"), FileExtType.FILE_EXT_ZIP);
    a.put(fileExtToKey("7z"), FileExtType.FILE_EXT_7Z);
    a.put(fileExtToKey("tar"), FileExtType.FILE_EXT_TAR);
    a.put(fileExtToKey("gz"), FileExtType.FILE_EXT_GZ);
    a.put(fileExtToKey("qbx"), FileExtType.FILE_EXT_QBX);
    a.put(fileExtToKey("qbs"), FileExtType.FILE_EXT_QBS);
  }
  
  public static int fileExtToKey(String paramString)
  {
    int k = 0;
    if (paramString == null) {
      return 0;
    }
    paramString = paramString.toCharArray();
    int i = paramString.length - 1;
    int j = 0;
    while (i >= 0)
    {
      k += (paramString[i] << j);
      j += 8;
      i -= 1;
    }
    return k;
  }
  
  public static String getDownloadDir(byte paramByte)
  {
    return mDownloadDirs[paramByte];
  }
  
  public static FileExtType getFileTypeFromExt(String paramString)
  {
    if (paramString != null)
    {
      paramString = (FileExtType)a.get(fileExtToKey(paramString));
      if (paramString != null) {
        return paramString;
      }
    }
    return FileExtType.FILE_EXT_UNKNOWN;
  }
  
  public static enum FileExtType
  {
    public byte fileType;
    
    static
    {
      FILE_EXT_MID = new FileExtType("FILE_EXT_MID", 1, (byte)7);
      FILE_EXT_WAV = new FileExtType("FILE_EXT_WAV", 2, (byte)4);
      FILE_EXT_MP3 = new FileExtType("FILE_EXT_MP3", 3, (byte)4);
      FILE_EXT_APE = new FileExtType("FILE_EXT_APE", 4, (byte)4);
      FILE_EXT_FLAC = new FileExtType("FILE_EXT_FLAC", 5, (byte)7);
      FILE_EXT_AAC = new FileExtType("FILE_EXT_AAC", 6, (byte)7);
      FILE_EXT_WMA = new FileExtType("FILE_EXT_WMA", 7, (byte)4);
      FILE_EXT_OGG = new FileExtType("FILE_EXT_OGG", 8, (byte)7);
      FILE_EXT_AMR = new FileExtType("FILE_EXT_AMR", 9, (byte)7);
      FILE_EXT_M4A = new FileExtType("FILE_EXT_M4A", 10, (byte)4);
      FILE_EXT_MPGA = new FileExtType("FILE_EXT_MPGA", 11, (byte)7);
      FILE_EXT_RA = new FileExtType("FILE_EXT_RA", 12, (byte)7);
      FILE_EXT_MP4 = new FileExtType("FILE_EXT_MP4", 13, (byte)3);
      FILE_EXT_DAT = new FileExtType("FILE_EXT_DAT", 14, (byte)7);
      FILE_EXT_RM = new FileExtType("FILE_EXT_RM", 15, (byte)3);
      FILE_EXT_RMVB = new FileExtType("FILE_EXT_RMVB", 16, (byte)3);
      FILE_EXT_F4V = new FileExtType("FILE_EXT_F4V", 17, (byte)3);
      FILE_EXT_FLV = new FileExtType("FILE_EXT_FLV", 18, (byte)3);
      FILE_EXT_AVI = new FileExtType("FILE_EXT_AVI", 19, (byte)3);
      FILE_EXT_3GP = new FileExtType("FILE_EXT_3GP", 20, (byte)3);
      FILE_EXT_3GPP = new FileExtType("FILE_EXT_3GPP", 21, (byte)3);
      FILE_EXT_MOV = new FileExtType("FILE_EXT_MOV", 22, (byte)3);
      FILE_EXT_ASF = new FileExtType("FILE_EXT_ASF", 23, (byte)3);
      FILE_EXT_WMV = new FileExtType("FILE_EXT_WMV", 24, (byte)3);
      FILE_EXT_WEBM = new FileExtType("FILE_EXT_WEBM", 25, (byte)3);
      FILE_EXT_MKV = new FileExtType("FILE_EXT_MKV", 26, (byte)3);
      FILE_EXT_MPG = new FileExtType("FILE_EXT_MPG", 27, (byte)3);
      FILE_EXT_MPEG = new FileExtType("FILE_EXT_MPEG", 28, (byte)3);
      FILE_EXT_MPEG1 = new FileExtType("FILE_EXT_MPEG1", 29, (byte)3);
      FILE_EXT_MPEG2 = new FileExtType("FILE_EXT_MPEG2", 30, (byte)3);
      FILE_EXT_M3U8 = new FileExtType("FILE_EXT_M3U8", 31, (byte)3);
      FILE_EXT_TS = new FileExtType("FILE_EXT_TS", 32, (byte)7);
      FILE_EXT_OGV = new FileExtType("FILE_EXT_OGV", 33, (byte)3);
      FILE_EXT_VDAT = new FileExtType("FILE_EXT_VDAT", 34, (byte)3);
      FILE_EXT_XVID = new FileExtType("FILE_EXT_XVID", 35, (byte)7);
      FILE_EXT_DVD = new FileExtType("FILE_EXT_DVD", 36, (byte)7);
      FILE_EXT_VCD = new FileExtType("FILE_EXT_VCD", 37, (byte)7);
      FILE_EXT_VOB = new FileExtType("FILE_EXT_VOB", 38, (byte)7);
      FILE_EXT_DIVX = new FileExtType("FILE_EXT_DIVX", 39, (byte)7);
      FILE_EXT_JPG = new FileExtType("FILE_EXT_JPG", 40, (byte)2);
      FILE_EXT_JPEG = new FileExtType("FILE_EXT_JPEG", 41, (byte)2);
      FILE_EXT_GIF = new FileExtType("FILE_EXT_GIF", 42, (byte)2);
      FILE_EXT_PNG = new FileExtType("FILE_EXT_PNG", 43, (byte)2);
      FILE_EXT_BMP = new FileExtType("FILE_EXT_BMP", 44, (byte)2);
      FILE_EXT_WEBP = new FileExtType("FILE_EXT_WEBP", 45, (byte)2);
      FILE_EXT_SVG = new FileExtType("FILE_EXT_SVG", 46, (byte)7);
      FILE_EXT_APK = new FileExtType("FILE_EXT_APK", 47, (byte)1);
      FILE_EXT_XLS = new FileExtType("FILE_EXT_XLS", 48, (byte)5);
      FILE_EXT_XLSX = new FileExtType("FILE_EXT_XLSX", 49, (byte)5);
      FILE_EXT_DOC = new FileExtType("FILE_EXT_DOC", 50, (byte)5);
      FILE_EXT_DOCX = new FileExtType("FILE_EXT_DOCX", 51, (byte)5);
      FILE_EXT_PPT = new FileExtType("FILE_EXT_PPT", 52, (byte)5);
      FILE_EXT_PPTX = new FileExtType("FILE_EXT_PPTX", 53, (byte)5);
      FILE_EXT_TXT = new FileExtType("FILE_EXT_TXT", 54, (byte)5);
      FILE_EXT_EPUB = new FileExtType("FILE_EXT_EPUB", 55, (byte)5);
      FILE_EXT_PDF = new FileExtType("FILE_EXT_PDF", 56, (byte)5);
      FILE_EXT_INI = new FileExtType("FILE_EXT_INI", 57, (byte)7);
      FILE_EXT_LOG = new FileExtType("FILE_EXT_LOG", 58, (byte)7);
      FILE_EXT_BAT = new FileExtType("FILE_EXT_BAT", 59, (byte)7);
      FILE_EXT_PHP = new FileExtType("FILE_EXT_PHP", 60, (byte)7);
      FILE_EXT_JS = new FileExtType("FILE_EXT_JS", 61, (byte)7);
      FILE_EXT_LRC = new FileExtType("FILE_EXT_LRC", 62, (byte)7);
      FILE_EXT_HTM = new FileExtType("FILE_EXT_HTM", 63, (byte)5);
      FILE_EXT_HTML = new FileExtType("FILE_EXT_HTML", 64, (byte)5);
      FILE_EXT_MHT = new FileExtType("FILE_EXT_MHT", 65, (byte)5);
      FILE_EXT_URL = new FileExtType("FILE_EXT_URL", 66, (byte)5);
      FILE_EXT_XML = new FileExtType("FILE_EXT_XML", 67, (byte)7);
      FILE_EXT_CHM = new FileExtType("FILE_EXT_CHM", 68, (byte)5);
      FILE_EXT_RAR = new FileExtType("FILE_EXT_RAR", 69, (byte)6);
      FILE_EXT_ZIP = new FileExtType("FILE_EXT_ZIP", 70, (byte)6);
      FILE_EXT_7Z = new FileExtType("FILE_EXT_7Z", 71, (byte)6);
      FILE_EXT_TAR = new FileExtType("FILE_EXT_TAR", 72, (byte)6);
      FILE_EXT_GZ = new FileExtType("FILE_EXT_GZ", 73, (byte)6);
      FILE_EXT_QBX = new FileExtType("FILE_EXT_QBX", 74, (byte)7);
      FILE_EXT_QBS = new FileExtType("FILE_EXT_QBS", 75, (byte)7);
    }
    
    private FileExtType(byte paramByte)
    {
      this.fileType = paramByte;
    }
  }
  
  public static enum FileIconType
  {
    static
    {
      FILE_ICON_MOVIE = new FileIconType("FILE_ICON_MOVIE", 1);
      FILE_ICON_PICTURE = new FileIconType("FILE_ICON_PICTURE", 2);
      FILE_ICON_APK = new FileIconType("FILE_ICON_APK", 3);
      FILE_ICON_EXCEL = new FileIconType("FILE_ICON_EXCEL", 4);
      FILE_ICON_WORD = new FileIconType("FILE_ICON_WORD", 5);
      FILE_ICON_PPT = new FileIconType("FILE_ICON_PPT", 6);
      FILE_ICON_TXT = new FileIconType("FILE_ICON_TXT", 7);
      FILE_ICON_EPUB = new FileIconType("FILE_ICON_EPUB", 8);
      FILE_ICON_PDF = new FileIconType("FILE_ICON_PDF", 9);
      FILE_ICON_CHM = new FileIconType("FILE_ICON_CHM", 10);
      FILE_ICON_CORE = new FileIconType("FILE_ICON_CORE", 11);
      FILE_ICON_RAR = new FileIconType("FILE_ICON_RAR", 12);
      FILE_ICON_BT = new FileIconType("FILE_ICON_BT", 13);
      FILE_ICON_LINK = new FileIconType("FILE_ICON_LINK", 14);
      FILE_ICON_WEBPAGE = new FileIconType("FILE_ICON_WEBPAGE", 15);
    }
    
    private FileIconType() {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\DLMediaFileType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */