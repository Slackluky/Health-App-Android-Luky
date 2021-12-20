import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class XAxisValueFormatter implements IAxisValueFormatter {

    private String[] values;

    public XAxisValueFormatter(String[] values) {
        this.values = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return this.values[(int) value];
    }
}
