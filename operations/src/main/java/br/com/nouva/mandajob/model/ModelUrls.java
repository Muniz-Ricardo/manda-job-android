package br.com.nouva.mandajob.model;

import com.google.gson.annotations.SerializedName;

public class ModelUrls {

    @SerializedName("url")
    private String url;

    public ModelUrls(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
