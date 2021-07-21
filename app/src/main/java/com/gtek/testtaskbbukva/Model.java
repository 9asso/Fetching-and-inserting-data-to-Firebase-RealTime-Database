package com.gtek.testtaskbbukva;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Model {

    public String name;
    public String description;
    public String url;

    public Model(String name, String description, String url) {
        this.name = name;
        this.description = description;
        this.url = url;
    }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setUrl(String url) { this.url = url; }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getUrl() { return url; }
}
