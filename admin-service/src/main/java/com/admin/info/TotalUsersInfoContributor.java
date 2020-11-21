package com.admin.info;

import com.admin.client.DBClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TotalUsersInfoContributor implements InfoContributor {

    private final DBClient dbClient;

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Integer> userDetails = new HashMap<>();
        userDetails.put("total", dbClient.getUsers().size());

        builder.withDetail("users", userDetails);
    }
}
