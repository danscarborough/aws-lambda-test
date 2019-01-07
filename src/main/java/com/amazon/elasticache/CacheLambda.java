package com.amazon.elasticache;

import java.io.IOException;
import java.net.InetSocketAddress;

// Import the AWS-provided library with Auto Discovery support
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import net.spy.memcached.MemcachedClient;

public class CacheLambda implements RequestHandler<String, String> {
    public String handleRequest(String s, Context context) {
        String configEndpoint = "clusterforlambdatest.fnv5j0.0001.use2.cache.amazonaws.com";
        int clusterPort = 11211;

        MemcachedClient client;
        Object o = null;
        try {
            client = new MemcachedClient(new InetSocketAddress(configEndpoint, clusterPort));
            client.set("theKey", 3600, "This is the data value");
            o = client.get("theKey");
        } catch (IOException e) {
            context.getLogger().log(e.getMessage());
        }
        return (o != null) ? o.toString() : "o was null for some reason";
    }
}