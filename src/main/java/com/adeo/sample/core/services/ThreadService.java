package com.adeo.sample.core.services;

import jakarta.annotation.PostConstruct;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service used to get and display thread information.
 *
 * @author ccdccloudops@adeo.com
 */
@Slf4j
public class ThreadService {

    private final ThreadInfo threadInfo;

    /**
     * Creates a new {@link ThreadService} instance.
     */
    public ThreadService() {
        this.threadInfo = new ThreadInfo();
    }

    /**
     * Runs service.
     */
    @PostConstruct
    public void run() {

        Thread.getAllStackTraces()
              .keySet()
              .forEach(this.threadInfo::add);

        log.info("Number of visible(s) CPU={}, thread data={}", Runtime.getRuntime().availableProcessors(), this.threadInfo.toString());
    }

    @Value
    private static class ThreadInfo {

        Map<String, List<String>> threads;

        private ThreadInfo() {
            this.threads = new ConcurrentHashMap<>();
        }

        private void add(final Thread thread) {
            final var groupName = thread.getThreadGroup().getName();

            this.threads.computeIfAbsent(groupName, key -> new ArrayList<>());
            this.threads.get(groupName).add(thread.getName());
        }
    }
}
