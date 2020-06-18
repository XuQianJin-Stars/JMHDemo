package Test.demo2.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import com.github.abel533.echarts.series.Series;
import com.github.abel533.echarts.style.ItemStyle;
import com.github.abel533.echarts.style.itemstyle.Normal;
import org.openjdk.jmh.results.RunResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ResultExporter {
  private static ObjectMapper mapper = new ObjectMapper();

  public static void exportResult(
      String title, Collection<RunResult> results, String lable, String unit) {
    try {

      ArrayNode arrayNode = mapper.createArrayNode();
      results.forEach(
          x -> {
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("Benchmark", x.getParams().getBenchmark());
            objectNode.put("yLabels", "n=" + x.getParams().getParam(lable));
            BigDecimal bg = new BigDecimal(x.getPrimaryResult().getScore());
            double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            objectNode.put("Score", f1);
            objectNode.put("legend", x.getPrimaryResult().getLabel());
            objectNode.put("Units", x.getPrimaryResult().getScoreUnit());
            objectNode.put(x.getPrimaryResult().getLabel(), f1);
            arrayNode.add(objectNode);
          });

      Object[] yLabels =
          Arrays.stream(arrayNode.findValuesAsText("yLabels").toArray()).distinct().toArray();
      Object[] legend =
          Arrays.stream(arrayNode.findValuesAsText("legend").toArray()).distinct().toArray();

      GsonOption option = new GsonOption();
      option.title(title);
      option
          .tooltip()
          .trigger(Trigger.axis)
          .setAxisPointer(new AxisPointer().type(PointerType.shadow));
      option.legend().data(legend);
      option.grid(new Grid().left("3%").right("4%").bottom("3%").containLabel(true));

      ValueAxis valueAxis = new ValueAxis();
      valueAxis.type(AxisType.value).boundaryGap(0, 0.01).name("秒");
      option.xAxis(valueAxis);

      CategoryAxis categoryAxis = new CategoryAxis();
      categoryAxis.type(AxisType.category);
      categoryAxis.data(yLabels);
      option.yAxis(categoryAxis);

      ItemStyle itemStyle = new ItemStyle();
      itemStyle.setNormal(new Normal().show(true).position(Position.inside));

      List<Series> series = new ArrayList<>();
      for (Object leg : legend) {
        Bar bar = new Bar();
        bar.name(leg.toString())
            .type(SeriesType.bar)
            .data(arrayNode.findValuesAsText(leg.toString()).toArray())
            .label(itemStyle);
        series.add(bar);
      }

      option.setSeries(series);
      System.out.println("option = " + option.toString());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    Object[] arr = Arrays.asList("11", "11", "22", "11", "22", "33", "44", "55").toArray();
    Arrays.stream(arr)
        .distinct()
        .forEach(
            x -> {
              System.out.println(x);
            });
  }
}
