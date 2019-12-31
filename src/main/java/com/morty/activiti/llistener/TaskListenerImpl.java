package com.morty.activiti.llistener;

import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskListenerImpl implements org.activiti.engine.delegate.TaskListener {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee("50");// 指派个人任务
        logger.debug("触发了全局监听器, pid={}, tid={}, event={}", new Object[]{
                delegateTask.getProcessInstanceId(), delegateTask.getId(), delegateTask.getEventName()
        });
    }
}
