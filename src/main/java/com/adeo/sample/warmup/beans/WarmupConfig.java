package com.adeo.sample.warmup.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Warmup configuration bean.
 *
 * @author ccdpcloudops@adeo.com.
 */
@Data
@AllArgsConstructor
public class WarmupConfig {

    private List<String> uris;

    /**
     * Creates new {@link WarmupConfig} instance.
     */
    public WarmupConfig() {
        this.uris = new ArrayList<>();
    }
}
