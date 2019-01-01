package Test.demo2;

import com.github.abel533.echarts.AxisPointer;
import com.github.abel533.echarts.Grid;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.PointerType;
import com.github.abel533.echarts.code.Position;
import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.style.ItemStyle;
import com.github.abel533.echarts.style.itemstyle.Normal;

import java.util.Arrays;

public class TestEcharts {
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        GsonOption option = new GsonOption();
        option.title("JSON序列化性能");
        option.tooltip().trigger(Trigger.axis).setAxisPointer(new AxisPointer().type(PointerType.shadow));
        option.legend().data(Arrays.asList("FastJSON", "Gson", "Jackson", "JsonLib").toArray());
        option.grid(new Grid().left("3%").right("4%").bottom("3%").containLabel(true));

        ValueAxis valueAxis = new ValueAxis();
        valueAxis.type(AxisType.value).boundaryGap(0, 0.01).name("秒");
        option.xAxis(valueAxis);

        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.type(AxisType.category);
        categoryAxis.data("n=1000", "n=10000", "n=100000");
        option.yAxis(categoryAxis);

        ItemStyle itemStyle = new ItemStyle();
        itemStyle.setNormal(new Normal().show(true).position(Position.inside));

        Bar bar1 = new Bar();
        bar1.name("FastJSON")
                .type(SeriesType.bar)
                .data(0.63, 0.56, 0.9)
                .label(itemStyle)
        ;

        Bar bar2 = new Bar();
        bar2.name("Gson")
                .type(SeriesType.bar)
                .data(0.34, 0.73, 3.18)
                .label(itemStyle)
        ;

        Bar bar3 = new Bar();
        bar3.name("Jackson")
                .type(SeriesType.bar)
                .data(0.72, 0.84, 1.46)
                .label(itemStyle)
        ;

        Bar bar4 = new Bar();
        bar4.name("JsonLib")
                .type(SeriesType.bar)
                .data(1.17, 4.2, 14.86)
                .label(itemStyle)
        ;


        option.series(bar1, bar2, bar3, bar4);
        System.out.println("option = " + option.toString());
    }
}
