package br.com.nouva.mandajob.model;

import com.google.gson.annotations.SerializedName;

public class ModelOverview {

    @SerializedName("content")
    private String content;

    @SerializedName("created")
    private String created;

    @SerializedName("id_project")
    private String id_project;

    @SerializedName("id_user")
    private String id_user;

    @SerializedName("name")
    private String name;

    @SerializedName("profile")
    private String profile;

    @SerializedName("title")
    private String title;

    @SerializedName("type")
    private String type;

    @SerializedName("wallpaper")
    private String wallpaper;


    public ModelOverview(String content, String created, String id_project, String id_user, String name,
                         String profile, String title, String type, String wallpaper) {
        this.content = content;
        this.created = created;
        this.id_project = id_project;
        this.id_user = id_user;
        this.name = name;
        this.profile = profile;
        this.title = title;
        this.type = type;
        this.wallpaper = wallpaper;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getId_project() {
        return id_project;
    }

    public void setId_project(String id_project) {
        this.id_project = id_project;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWallpaper() {
        return wallpaper;
    }

    public void setWallpaper(String wallpaper) {
        this.wallpaper = wallpaper;
    }
}
