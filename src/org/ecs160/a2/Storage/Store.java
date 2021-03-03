package org.ecs160.a2.Storage;

import com.codename1.io.Storage;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import com.codename1.io.Storage;
import org.ecs160.a2.model.Task;

public class Store {
    public void addTask(String taskName, Task task) {

        Vector              vector = new Vector();
        Map<String, String> map    = new HashMap<>();
        map.put("size", task.size);
        map.put("description",  task.description);
        map.put("startTime",  task.startTime.toString());
        map.put("endTime", task.endTime.toString());
        vector.addElement(map);
        Storage.getInstance().writeObject(taskName, vector);

    }

    public Task getTask(String taskName) {

        Vector val = (Vector)Storage.getInstance().readObject(taskName);
        Map<String, String> map = (Map<String, String>) val.get(0);
        try {
            return new Task(taskName,map);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
