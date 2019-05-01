package stashpullrequestbuilder.stashpullrequestbuilder;

import static java.lang.String.format;

import hudson.model.Job;
import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import stashpullrequestbuilder.stashpullrequestbuilder.stash.StashPullRequestResponseValue;

/** Created by Nathan McCarthy */
public class StashPullRequestsBuilder {
  private static final Logger logger =
      Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
  private Job<?, ?> job;
  private StashBuildTrigger trigger;
  private StashRepository repository;
  private StashBuilds builds;

  public StashPullRequestsBuilder(@Nonnull Job<?, ?> job, @Nonnull StashBuildTrigger trigger) {
    this.job = job;
    this.trigger = trigger;
    this.repository = new StashRepository(job, trigger);
    this.builds = new StashBuilds(trigger, repository);
  }

  public void run() {
    logger.info(format("Build Start (%s).", job.getName()));
    Collection<StashPullRequestResponseValue> targetPullRequests =
        this.repository.getTargetPullRequests();
    this.repository.addFutureBuildTasks(targetPullRequests);
  }

  public StashBuildTrigger getTrigger() {
    return this.trigger;
  }

  public StashBuilds getBuilds() {
    return this.builds;
  }
}
