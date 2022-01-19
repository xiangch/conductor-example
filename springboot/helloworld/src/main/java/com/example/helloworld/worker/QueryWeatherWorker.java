package com.example.helloworld.worker;
import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zengxc
 */
@Component
public class QueryWeatherWorker implements Worker {
    Map<String, Integer> cityTemp = new HashMap<>();
    private final String taskDefName = "queryWeather";

    public QueryWeatherWorker() {
        cityTemp.put("广州", 38);
        cityTemp.put("湖南", 18);
    }

    @Override
    public String getTaskDefName() {
        return taskDefName;
    }

    @Override
    public TaskResult execute(Task task) {
        System.out.printf("WorkflowInstanceId: %s Executing: %s in:%s%n",task.getWorkflowInstanceId(), getTaskDefName(),task.getInputData().get("city"));
        String city = (String) task.getInputData().get("city");
        System.out.println(city + " 气温:" + cityTemp.get(city));

        TaskResult result = new TaskResult(task);
        result.setStatus(TaskResult.Status.COMPLETED);
        Map<String,Object> resultMap = new HashMap<>(2);
        resultMap.put("city",city);
        resultMap.put("temperature",cityTemp.get(city));
        //Register the output of the task
        //result.getOutputData().put("data", resultMap);
        result.getOutputData().put("temperature", resultMap.get("temperature"));
        return result;
    }

}
