package cz.schutzpetr.stock.server.gui.server;

import cz.schutzpetr.stock.core.utils.CPULoad;
import cz.schutzpetr.stock.server.events.EventManager;
import cz.schutzpetr.stock.server.events.events.StageCloseRequest;
import cz.schutzpetr.stock.server.events.listeners.StageCloseRequestListener;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.util.Duration;

/**
 * Created by Petr Schutz on 13.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class CPULoadChart implements StageCloseRequestListener {

    private final XYChart.Series<String, Number> series;

    private final Timeline timeline;

    private short counter = 0;

    public CPULoadChart(LineChart<String, Number> cpu_Chart) {
        EventManager.registerListener(this);
        series = new XYChart.Series<>();


        cpu_Chart.getData().add(series);

        timeline = new Timeline(new KeyFrame(Duration.millis(1000), actionEvent -> {

            series.getData().add(new XYChart.Data<>(Short.toString(counter), CPULoad.getInstance().getCPULoad()));

            if (counter >= 60) {
                series.getData().remove(0);
            }

            counter++;

            if (counter >= Short.MAX_VALUE - 100) {
                series.getData().clear();
                counter = 0;
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }

    /**
     * Invoked when a specific event of the type for which this handler is registered happens.
     *
     * @param event {@code StageCloseRequest}
     */
    @Override
    public void onStageCloseRequest(StageCloseRequest event) {
        if (event.getStage().getTitle().equalsIgnoreCase("Stock")) timeline.stop();
    }
}
