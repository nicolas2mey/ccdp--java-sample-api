package com.adeo.sample.warmup.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Warmup configuration bean.
 *
 * @param uris
 *     List of warmup URIs.
 *
 * @author ccdpcloudops@adeo.com.
 */
public record WarmupConfig(List<String> uris) {

    /**
     * Creates new {@link WarmupConfig} instance.
     */
    public WarmupConfig() {
        this(new ArrayList<>());
    }
}
