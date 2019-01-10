package com.offbytwo.jenkins.model;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.offbytwo.jenkins.client.util.UrlUtils;
import lombok.Data;

import java.io.IOException;
import java.util.List;

/**
 * Created by msdg on 2019/1/8.
 * Look, there is a moon.
 */
@Data
public class ActionWithDetails extends Action {
    private String description;

    private String displayName;

    private boolean buildable;

    private List<Build> builds;

    private Build firstBuild;

    private Build lastBuild;

    private Build lastCompletedBuild;

    private Build lastFailedBuild;

    private Build lastStableBuild;

    private Build lastSuccessfulBuild;

    private Build lastUnstableBuild;

    private Build lastUnsuccessfulBuild;

    private int nextBuildNumber;

    private boolean inQueue;

    private QueueItem queueItem;

    private List<Job> downstreamProjects;

    private List<Job> upstreamProjects;


    public Build getBuildByNumber(final int number) {
        Predicate<Build> isMatchNumber = new Predicate<Build>() {
            @Override
            public boolean apply(Build Build) {
                return Build.getNumber() == number;
            }
        };
        Optional<Build> optional = Iterables.tryFind(builds, isMatchNumber);
        return optional.orNull() == null ? null : buildWithClient(optional.orNull());
    }

    private Build buildWithClient(Build from) {
        Build ret = from;
        if (from != null) {
            ret = new Build(from);
            ret.setClient(client);
        }
        return ret;
    }

    /**
     * Trigger a build without parameters
     *
     * @return {@link QueueReference} for further analysis of the queued build.
     * @throws IOException in case of an error.
     */
    public QueueReference build() throws IOException {
        ExtractHeader location = client.post(getUrl() + "build", null, ExtractHeader.class, false);
        return new QueueReference(location.getLocation());
    }

    public String getPromotionXml() throws IOException {
        return client.get(getUrl() + "/config.xml");
    }

    public void modifyPromotionXml(String newConfigXml) throws IOException {
        client.post_xml_no_api(getUrl() + "/config.xml", newConfigXml, false);
    }
}
