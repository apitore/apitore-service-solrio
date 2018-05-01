package com.apitore.solr.service;

import java.util.List;

import com.apitore.solr.entity.DocApi;


public interface SolrApiService {
  public void optimize();

  public void add(List<DocApi> docs);

  public List<String> getApiIds(String query, int offset, int limit);
}