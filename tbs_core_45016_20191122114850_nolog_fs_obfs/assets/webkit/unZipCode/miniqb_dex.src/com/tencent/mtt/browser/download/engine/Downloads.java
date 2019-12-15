package com.tencent.mtt.browser.download.engine;

public class Downloads
{
  public static final String ANNOTATION = "annotation";
  public static final String ANNOTATIONEXT = "annotationext";
  public static final String COSTTIME = "costtime";
  public static final String CREATEDATE = "createdate";
  public static final String DB_PATH = ".db";
  public static final String DONEDATE = "donedate";
  public static final int DOWNLOADDB_VER_CURRENT = 2;
  public static final String DOWNLOADEDSIZE = "downloadedsize";
  public static final String DOWNLOAD_DB_NAME = "download_database";
  public static final String DOWNLOAD_OPERATIONS = "download_operations";
  public static final String ENDPOS1 = "endpos1";
  public static final String ENDPOS2 = "endpos2";
  public static final String ENDPOS3 = "endpos3";
  public static final String ETAG = "etag";
  public static final String EXTEND_1 = "extend_1";
  public static final String EXTEND_2 = "extend_2";
  public static final String EXTEND_3 = "extend_3";
  public static final String EXTEND_4 = "extend_4";
  public static final String EXTEND_5 = "extend_5";
  public static final String EXTEND_6 = "extend_6";
  public static final String EXTEND_7 = "extend_7";
  public static final String EXTEND_8 = "extend_8";
  public static final String EXT_FLAG = "ext_flag";
  public static final String FILEFOLDERPATH = "filefolderpath";
  public static final String FILENAME = "filename";
  public static final String FILE_SIZE_FOR_HIJACK = "second_extend_2";
  public static final String FLAG = "flag";
  public static final String ICONURL = "extend_9";
  public static final String ID = "id";
  public static final String ISSUPPORTRESUME = "supportresume";
  public static final String PREDOWNLOAD_ANNOTATION = "@PREDOWNLOAD";
  public static final String REFERER = "referer";
  public static final String SECOND_EXTEND_1 = "second_extend_1";
  public static final String SECOND_EXTEND_10 = "second_extend_10";
  public static final String SECOND_EXTEND_3 = "second_extend_3";
  public static final String SECOND_EXTEND_4 = "second_extend_4";
  public static final String SECOND_EXTEND_5 = "second_extend_5";
  public static final String SECOND_EXTEND_6 = "second_extend_6";
  public static final String SECOND_EXTEND_7 = "second_extend_7";
  public static final String SECOND_EXTEND_8 = "second_extend_8";
  public static final String SECOND_EXTEND_9 = "second_extend_9";
  public static final String SQL_ALTER_RENAME_TABLE = "ALTER TABLE download RENAME TO download_temp;";
  public static final String SQL_DROP_TEMP_TABLE = "DROP TABLE download_temp;";
  public static final String SQL_INSERT_ALL_DATA = "INSERT INTO download SELECT * FROM download_temp;";
  public static final String SQL_MAX_ID = "SELECT MAX(id) FROM download;";
  public static final String STARTPOS1 = "startpos1";
  public static final String STARTPOS2 = "startpos2";
  public static final String STARTPOS3 = "startpos3";
  public static final String STATUS = "status";
  public static final String TABLE_DOWNLOAD = "download";
  public static final String TEMP_TABLE_DOWNLOAD = "download_temp";
  public static final String THREADNUM = "threadnum";
  public static final String TOTALSIZE = "totalsize";
  public static final String TOTALWRITENSIZE = "downloadsize";
  public static final String URL = "url";
  public static final String VERSIONNAME = "versionname";
  public static final String WRITEPOS1 = "writepos1";
  public static final String WRITEPOS2 = "writepos2";
  public static final String WRITEPOS3 = "writepos3";
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\browser\download\engine\Downloads.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */