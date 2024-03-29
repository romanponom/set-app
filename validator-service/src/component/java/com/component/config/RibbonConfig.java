package com.component.config;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.context.annotation.Bean;

public class RibbonConfig {

    @LocalServerPort
    private int port;

    @Bean
    public ServerList<Server> serverList() {
        return new StaticServerList<>(new Server("127.0.0.1", port));
    }
}
