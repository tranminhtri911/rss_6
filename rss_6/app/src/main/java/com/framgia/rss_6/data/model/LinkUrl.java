package com.framgia.rss_6.data.model;

import com.framgia.rss_6.ultils.Constant;

import java.util.ArrayList;
import java.util.List;

public class LinkUrl {
    private String mName;
    private String mUrl;

    public LinkUrl() {
    }

    public LinkUrl(String name, String linkUrl) {
        this.mName = name;
        this.mUrl = linkUrl;
    }

    public static List getListNewUrl() {
        ArrayList<LinkUrl> newUrls = new ArrayList<>();
        newUrls.add(new LinkUrl(Constant.USA_NAME_URL, Constant.USA_LINK_URL));
        newUrls.add(new LinkUrl(Constant.AFRICA_NAME_URL, Constant.AFRICA_LINK_URL));
        newUrls
            .add(new LinkUrl(Constant.SOUTH_CHINA_SEA_NAME_URL, Constant.SOUTH_CHINA_SEA_LINK_URL));
        newUrls.add(new LinkUrl(Constant.MIDDLE_EAST_NAME_URL, Constant.MIDDLE_EAST_LINK_URL));
        newUrls.add(new LinkUrl(Constant.EUROPE_NAME_URL, Constant.EUROPE_LINK_URL));
        newUrls.add(new LinkUrl(Constant.ASIA_NAME_URL, Constant.ASIA_LINK_URL));
        newUrls.add(new LinkUrl(Constant.AMERICAS_NAME_URL, Constant.AMERICAS_LINK_URL));
        newUrls.add(new LinkUrl(Constant.ECONOMY_NAME_URL, Constant.ECONOMY_LINK_URL));
        newUrls.add(new LinkUrl(Constant.SILICON_VALLEY_AND_TECHNOLOGY_NAME_URL,
            Constant.SILICON_VALLEY_AND_TECHNOLOGY_LINK_URL));
        return newUrls;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
