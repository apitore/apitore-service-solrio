package com.apitore.solr.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.apitore.dao.model.DBApiCategory;
import com.apitore.dao.model.DBApiDetails;


public class DocApi implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 7830979017384908062L;

  private String id;
  private String title;
  private String developer;
  private String description;
  private String usages;
  private String updateInfo;
  private List<String> categoryList;
  private String license;
  private Boolean enabled;
  private Date solrRegisteredAt;


  /* Constructor */
  public DocApi() {}
  /**
   * Solr登録用 Docオブジェクト
   */
  public DocApi(DBApiDetails apiDetails) {
    this.id = apiDetails.getApiId().toString();
    this.title = apiDetails.getApiInfo().getTitle();
    if (apiDetails.getApiInfo().getAnonymous()) {
      this.developer = "Anonymous";
    } else {
      this.developer = apiDetails.getApiInfo().getDeveloper();
    }
    this.description = apiDetails.getDescription();
    this.usages = apiDetails.getUsages();
    this.updateInfo = apiDetails.getUpdateInfo();
    this.categoryList = new ArrayList<String>();
    for (DBApiCategory cat: apiDetails.getCategories()) {
      this.categoryList.add(cat.getCategory().getCategory());
    }
    this.license = apiDetails.getApiInfo().getLicense().getLicense();
    this.enabled = apiDetails.getApiInfo().getEnabled();
    this.solrRegisteredAt = new Date();
  }


  /* Getter, setter */
  public String getId() {
    return this.id;
  }
  public void setId(String val) {
    this.id = val;
  }

  public String getTitle() {
    return this.title;
  }
  public void setTitle(String val) {
    this.title = val;
  }

  public String getDeveloper() {
    return this.developer;
  }
  public void setDeveloper(String val) {
    this.developer = val;
  }

  public String getDescription() {
    return this.description;
  }
  public void setDescription(String val) {
    this.description = val;
  }

  public String getUsages() {
    return this.usages;
  }
  public void setUsages(String val) {
    this.usages = val;
  }

  public String getUpdateInfo() {
    return this.updateInfo;
  }
  public void setUpdateInfo(String val) {
    this.updateInfo = val;
  }

  public List<String> getCategoryList() {
    return this.categoryList;
  }
  public void setCategoryList(List<String> val) {
    this.categoryList = val;
  }

  public String getLicense() {
    return this.license;
  }
  public void setLicense(String val) {
    this.license = val;
  }

  public Boolean getEnabled() {
    return this.enabled;
  }
  public void setEnabled(Boolean val) {
    this.enabled = val;
  }

  public Date getSolrRegisteredAt() {
    return solrRegisteredAt;
  }
  public void setSolrRegisteredAt(Date val) {
    this.solrRegisteredAt = val;
  }

}
