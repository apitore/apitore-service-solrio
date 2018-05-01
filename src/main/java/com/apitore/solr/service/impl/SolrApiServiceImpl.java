package com.apitore.solr.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.apitore.solr.entity.DocApi;
import com.apitore.solr.helper.SolrCloudApi;
import com.apitore.solr.service.SolrApiService;


@Service
public class SolrApiServiceImpl implements SolrApiService {

  @Value("${solr.cloud.zookeeper.url}")
  private String zkHost;

  @Value("${solr.cloud.collection}")
  private String solrCollection;

  private SolrCloudApi solr = new SolrCloudApi();

  @Override
  public List<String> getApiIds(String query, int offset, int limit) {
    SolrQuery squery = new SolrQuery(query)
        .setStart(offset)
        .setRows(limit);
    return solr.searchApis(zkHost, solrCollection, squery);
  }

  @Override
  public void add(List<DocApi> docs) {
    solr.addApis(zkHost, solrCollection, docs);
  }

  @Override
  public void optimize() {
    solr.optimize(zkHost, solrCollection);
  }

}