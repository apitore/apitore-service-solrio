package com.apitore.solr.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apitore.solr.entity.DocApi;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;


public class SolrCloudApi {
  /** ロガー */
  private final Logger LOG = LoggerFactory.getLogger(SolrCloudApi.class);


  /**
   * 最適化
   *
   * @param solrUrl
   */
  public void optimize(String zkHost, String collection) {
    LOG.info("Optimize solr index start.");
    try {
      CloudSolrClient server = new CloudSolrClient.Builder().withZkHost(zkHost).build();
      server.setDefaultCollection(collection);
      server.optimize();
      server.close();
    } catch (SolrServerException e) {
      LOG.error("SolrServerException",e);
    } catch (IOException e) {
      LOG.error("IOException",e);
    }
    LOG.info("Optimize solr index end.");
  }

  /**
   * インデクス更新
   *
   * @param solrUrl
   * @param list
   */
  public void addApis(String zkHost, String collection, List<DocApi> list){
    LOG.info("Update solr index start.");
    try {
      CloudSolrClient server = new CloudSolrClient.Builder().withZkHost(zkHost).build();
      server.setDefaultCollection(collection);
      Collection<SolrInputDocument> sdocs = new ArrayList<SolrInputDocument>();
      for (DocApi doc: list) {
        SolrInputDocument sdoc = new SolrInputDocument();
        sdoc.addField("id", doc.getId());
        sdoc.addField("title", doc.getTitle());
        sdoc.addField("developer", doc.getDeveloper());
        sdoc.addField("description", doc.getDescription());
        sdoc.addField("usage", doc.getUsages());
        sdoc.addField("updateInfo", doc.getUpdateInfo());
        sdoc.addField("categoryList", doc.getCategoryList().toArray());
        sdoc.addField("license", doc.getLicense());
        sdoc.addField("enabled", doc.getEnabled());
        sdoc.addField("solrRegisteredAt", doc.getSolrRegisteredAt());
        sdocs.add(sdoc);
      }
      server.add( sdocs );
      server.commit();
      server.close();
    } catch (SolrServerException e) {
      LOG.error("SolrServerException",e);
    } catch (IOException e) {
      LOG.error("IOException",e);
    }
    LOG.info("Update solr index end.");
  }

  /**
   * 検索
   * 出力はIDのみ
   *
   * @param solrUrl
   * @param query
   * @return
   */
  public List<String> searchApis(String zkHost, String collection, SolrQuery query) {
    LOG.info("Search solr start.");
    List<String> apiIds = new ArrayList<String>();
    try {
      CloudSolrClient server = new CloudSolrClient.Builder().withZkHost(zkHost).build();
      server.setDefaultCollection(collection);
      QueryResponse response = server.query(query);
      SolrDocumentList list = response.getResults();
      LOG.debug(list.getNumFound() + " hits.");
      for (SolrDocument doc : list) {
        apiIds.add((String)doc.get("id"));
      }
      server.close();
    } catch (SolrServerException e) {
      LOG.error("SolrServerException",e);
    } catch (IOException e) {
      LOG.error("IOException",e);
    }
    LOG.info("Search solr end.");
    return apiIds;
  }

}
