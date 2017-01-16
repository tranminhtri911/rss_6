package com.framgia.rss_6.ultils;

import android.Manifest;

/**
 * Created by tri on 10/01/2017.
 */
public class Constant {
    public static final String ITEM = "item";
    public static final String CATEGORY = "category";
    public static final String RSSLINK = "rsslink";
    public static final String TITLE = "title";
    public static final String IMAGE_URL = "image";
    public static final String ENCLOSURE = "enclosure";
    public static final String DESCRIPTION = "description";
    public static final String PUBLISHDATE = "pubDate";
    public static final String AUTHOR = "author";
    public static final String LINK = "link";
    public static final String BUNDLE_DATA = "bundle_data";
    public static final String BUNDLE_CHANNEL = "bundle_chanel";
    public static final String BUNDLE_NEWS = "bundle_news";
    public static final String HISTORY = "History";
    public static final String TEXT_PLAIN = "text/plain";
    public static final String PDF_EXTENSION = ".pdf";
    public static final String SD_PATH = "/sdcard/";
    public static final String VOA_URL = "http://www.voanews.com/api/";
    public static final String USA_NAME_URL = "USA";
    public static final String USA_LINK_URL = VOA_URL + "zq$omekvi_";
    public static final String AFRICA_NAME_URL = "Africa";
    public static final String AFRICA_LINK_URL = VOA_URL + "z-$otevtiq";
    public static final String ASIA_NAME_URL = "ASIA";
    public static final String ASIA_LINK_URL = VOA_URL + "zo$o_egviy";
    public static final String HISTORY_DAY = "10";
    public static final int BEGIN_INDEX_PUBLISHDAY = 0;
    public static final int END_INDEX_PUBLISHDAY = 16;
    public static final int BEGIN_INDEX_AUTHOR = 21;
    public static final String SOUTH_CHINA_SEA_NAME_URL = "SOUTH CHINA SEA";
    public static final String SOUTH_CHINA_SEA_LINK_URL = VOA_URL + "z-ipq_evjpqy";
    public static final String MIDDLE_EAST_NAME_URL = "MIDDLE EAST";
    public static final String MIDDLE_EAST_LINK_URL = VOA_URL + "zr$opeuvim";
    public static final String EUROPE_NAME_URL = "EUROPE";
    public static final String EUROPE_LINK_URL = VOA_URL + "zj$oveytit";
    public static final String AMERICAS_NAME_URL = "AMERICAS";
    public static final String AMERICAS_LINK_URL = VOA_URL + "zoripegtim";
    public static final String ECONOMY_NAME_URL = "ECONOMY";
    public static final String ECONOMY_LINK_URL = VOA_URL + "zy$oqeqtii";
    public static final String SILICON_VALLEY_AND_TECHNOLOGY_NAME_URL =
        "SILICON_VALLEY_AND_TECHNOLOGY";
    public static final String SILICON_VALLEY_AND_TECHNOLOGY_LINK_URL = VOA_URL + "zyritequir";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static final String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
}
