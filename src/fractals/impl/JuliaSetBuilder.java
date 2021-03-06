package fractals.impl;

import fractals.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

public class JuliaSetBuilder extends RasterFractalBuilder<JuliaSet> {
    @Parameter.Marker(order = "100", description = "Real const (Cr)")
    private BigDecimal cr;
    @Parameter.Marker(order = "101", description = "Imag const (Ci)")
    private BigDecimal ci;

    static class ChangeCr extends SingleValueTransition {

        private ChangeCr(BigDecimal value) {
            super(value);
        }

        @Override
        public SingleValueTransition getSame(BigDecimal amount) {
            return new ChangeCr(amount);
        }

        @Override
        public void run(FractalBuilder b, View view) {
            JuliaSetBuilder set = (JuliaSetBuilder) b;
            MathContext mctx = Env.instance().getMathContext();
            set.setCr(set.getCr().add(getValue().multiply(set.getCr(), mctx), mctx));
        }

    }

    static class ChangeCi extends SingleValueTransition {

        private ChangeCi(BigDecimal value) {
            super(value);
        }

        @Override
        public SingleValueTransition getSame(BigDecimal amount) {
            return new ChangeCi(amount);
        }

        @Override
        public void run(FractalBuilder b, View view) {
            JuliaSetBuilder set = (JuliaSetBuilder) b;
            MathContext mctx = Env.instance().getMathContext();
            set.setCi(set.getCi().add(getValue().multiply(set.getCr(), mctx), mctx));
        }

    }

    public BigDecimal getCr() {
        return cr;
    }

    public void setCr(BigDecimal cr) {
        this.cr = cr;
    }

    public BigDecimal getCi() {
        return ci;
    }

    public void setCi(BigDecimal ci) {
        this.ci = ci;
    }

    @Override
    public void init() {
        setMaxIters(200);
        setX(BigDecimal.ZERO);
        setY(BigDecimal.ZERO);
        setWidth(new BigDecimal("4"));
        setCr(new BigDecimal("-0.400"));
        setCi(new BigDecimal("0.600"));
        setAngle(BigDecimal.ZERO);
    }

    @Override
    public JuliaSet getFractal() {
        return new JuliaSet(getX(), getY(), getWidth(), getAngle(), getCr(), getCi(), getMaxIters());
    }

    @Override
    public String getDescription() {
        return "<html><p><b>Julia set</b> is defined by the following iteration:</p>" + //
                "<p></p><p><b>z<sub>n+1</sub> = z<sub>n</sub> * z<sub>n</sub> + c</b></p>" + //
                "</html>";
    }

	@Override
	public List<UserTransition> getTransitions() {
		ArrayList<UserTransition> transitions = new ArrayList<>(super.getTransitions());
		transitions.add(new UserTransition(new ChangeCr(new BigDecimal("0.00125")), "CR+", "CR+"));
		transitions.add(new UserTransition(new ChangeCr(new BigDecimal("-0.00125")), "CR-", "CR-"));
		transitions.add(new UserTransition(new ChangeCi(new BigDecimal("0.00125")), "CI+", "CI+"));
		transitions.add(new UserTransition(new ChangeCi(new BigDecimal("-0.00125")), "CI-", "CI-"));
		return transitions;
	}

}
