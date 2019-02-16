package org.lf.admin.api.baseapi.redis.key;

public interface KeyPrefix {

    int expireSeconds();

    String getPrefix();
}
