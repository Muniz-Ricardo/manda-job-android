package br.com.nouva.mandajob.model;

import com.google.gson.annotations.SerializedName;

public class ModelRecomendedForYou {

    @SerializedName("name")
    private String name;

    @SerializedName("id_user")
    private String idUser;

    @SerializedName("id_project")
    private String idProject;

    @SerializedName("image_profile")
    private String imageProfileUrl;

    @SerializedName("scr_project")
    private String screenshotProject;

    @SerializedName("type")
    private String typeProject;


    public ModelRecomendedForYou(String name, String idUser, String idProject, String imageProfileUrl,
                                 String screenshotProject, String typeProject) {
        this.name = name;
        this.idUser = idUser;
        this.idProject = idProject;
        this.imageProfileUrl = imageProfileUrl;
        this.screenshotProject = screenshotProject;
        this.typeProject = typeProject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdProject() {
        return idProject;
    }

    public void setIdProject(String idProject) {
        this.idProject = idProject;
    }

    public String getImageProfileUrl() {
        return imageProfileUrl;
    }

    public void setImageProfileUrl(String imageProfileUrl) {
        this.imageProfileUrl = imageProfileUrl;
    }

    public String getScreenshotProject() {
        return screenshotProject;
    }

    public void setScreenshotProject(String screenshotProject) {
        this.screenshotProject = screenshotProject;
    }

    public String getTypeProject() {
        return typeProject;
    }

    public void setTypeProject(String typeProject) {
        this.typeProject = typeProject;
    }
}
