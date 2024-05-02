package manager.server.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import manager.impl.enums.Status;
import manager.impl.tasks.EpicTask;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

public class EpicTaskAdapterForSubTask extends TypeAdapter<EpicTask> {
    @Override
    public void write(JsonWriter jsonWriter, EpicTask epicTask) throws IOException {
        jsonWriter.value(epicTask.toString());
    }

    @Override
    public EpicTask read(JsonReader jsonReader) throws IOException {
        String[] values = jsonReader.nextString().split(",");
        int id = Integer.parseInt(values[0]);
        String name = values[2];
        String description = values[3];
        Status status = Status.valueOf(values[4]);
        Duration duration = Duration.ZERO;
        if (!values[5].equals("")) {
            duration = Duration.ofMinutes(Integer.parseInt(values[5]));
        }
        LocalDateTime startTime = null;
        if (!values[6].equals("0")) {
            startTime = LocalDateTime.parse(values[6]);
        }
        EpicTask epicTask = new EpicTask(name, description);
        epicTask.setIdTask(id);
        epicTask.setStatus(status);
        if (duration != null) {
            epicTask.setDuration(duration.toMinutesPart());
        }
        if (startTime != null) {
            epicTask.setStartTime(startTime.toString());
        }
        return epicTask;
    }
}
