package com.example.helloworld.worker;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import org.springframework.stereotype.Component;

/**
 * @author zengxc
 */
@Component
public class SendMessageWorker implements Worker {

    private final String taskDefName="sendMessage";

    @Override
    public String getTaskDefName() {
        return taskDefName;
    }

    @Override
    public TaskResult execute(Task task) {
        System.out.printf("WorkflowInstanceId: %s Executing: %s in:%s%n",task.getWorkflowInstanceId(), getTaskDefName(),task.getInputData().get("content"));
        System.out.println("接收人:" + task.getInputData().get("receiver")+" "+task.getInputData().get("content"));

        TaskResult result = new TaskResult(task);
        result.setStatus(TaskResult.Status.COMPLETED);
        result.log("接收人:" + task.getInputData().get("receiver")+" "+task.getInputData().get("content"));
        return result;
    }
}
