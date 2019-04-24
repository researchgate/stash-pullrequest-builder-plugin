package stashpullrequestbuilder.stashpullrequestbuilder;

import hudson.Extension;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.listeners.RunListener;
import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import jenkins.model.ParameterizedJobMixIn;

/** Created by Nathan McCarthy */
@Extension
public class StashBuildListener extends RunListener<Run<?, ?>> {
  private static final Logger logger =
      Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

  @Override
  public void onStarted(Run<?, ?> run, TaskListener listener) {
    logger.info("BuildListener onStarted called.");
    StashBuildTrigger trigger =
        ParameterizedJobMixIn.getTrigger(run.getParent(), StashBuildTrigger.class);
    if (trigger == null) {
      return;
    }
    trigger.getBuilder().getBuilds().onStarted(run);
  }

  @Override
  public void onCompleted(Run<?, ?> run, @Nonnull TaskListener listener) {
    StashBuildTrigger trigger =
        ParameterizedJobMixIn.getTrigger(run.getParent(), StashBuildTrigger.class);
    if (trigger == null) {
      return;
    }
    trigger.getBuilder().getBuilds().onCompleted(run, listener);
  }
}
