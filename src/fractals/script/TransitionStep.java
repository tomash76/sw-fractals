package fractals.script;

import fractals.FractalBuilder;
import fractals.Transition;
import fractals.View;

public class TransitionStep<B extends FractalBuilder<?>> implements Step<B> {
    private Transition transition;

    private long duration;
    private long plannedStartTime;

    public TransitionStep(Transition transition, long duration, long plannedStartTime) {
        this.transition = transition;
        this.duration = duration;
        this.plannedStartTime = plannedStartTime;
    }

    @Override
    public long getDuration() {
        return duration;
    }

    @Override
    public long getPlannedStartTime() {
        return plannedStartTime;
    }

    public TransitionStep(Transition transition) {
        this.transition = transition;
    }

    public Transition getTransition() {
        return transition;
    }

    public void run(B fractalBuilder, View view) {
        transition.run(fractalBuilder, view);
    }

    @Override
    public String toString() {
        return transition.toString();
    }

}
