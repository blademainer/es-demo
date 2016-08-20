package com.xiongyingqi.es.demo;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qi on 16-8-10.
 */
public class EsDemo {
  public static void main(String[] args) throws UnknownHostException {
    // on startup
    Client client = TransportClient.builder()
        .settings(Settings.settingsBuilder().put("cluster.name", "elasticsearch"))
        .build()
//                .addTransportAddress(
//                        new InetSocketTransportAddress(InetAddress.getByName("192.168.56.101"),
//                            9300));
        .addTransportAddress(
            new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

    Map<String, Object> json = new HashMap<String, Object>();
    json.put("user", "kimchy");
    json.put("postDate", "2016-08-08");
    json.put("message", "trying out Elasticsearch");
    IndexResponse indexResponse = client.prepareIndex("test", "test", "1")
        .setSource(json)
        .get();
    System.out.println("indexResponse====" + indexResponse);

    GetResponse response = client.prepareGet("test", "test", "1")
        .setOperationThreaded(false)
        .get();

    System.out.println("get response=========" + response.getSource());
    // on shutdown
    client.close();
  }
}
