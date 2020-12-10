package com.component.config;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class DbServiceRibbonConfig {

    @LocalServerPort
    private int port;

    @Bean
    public ServerList<Server> serverList() {
        return new StaticServerList<>(new Server("127.0.0.1", 8082));
    }
}