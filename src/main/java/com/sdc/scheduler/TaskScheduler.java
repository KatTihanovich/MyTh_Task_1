package com.sdc.scheduler;

import com.sdc.entity.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class TaskScheduler {
    private static final Logger logger = LogManager.getLogger();

    private final BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();
    private final ExecutorService workerPool;
    private final ScheduledExecutorService delayScheduler;
    private final AtomicBoolean isRunning = new AtomicBoolean(true);

    public TaskScheduler(int threadPoolSize) {
        this.workerPool = Executors.newFixedThreadPool(threadPoolSize);
        this.delayScheduler = Executors.newScheduledThreadPool(1);
        for (int i = 0; i < threadPoolSize; i++) {
            workerPool.submit(this::workerLogic);
        }
    }

    private void workerLogic() {
        while (isRunning.get() || !taskQueue.isEmpty()) {
            try {
                Task task = taskQueue.poll(1, TimeUnit.SECONDS);
                if (task != null) {
                    logger.info("Executing task: " + task.getTaskName() + " on thread: " + Thread.currentThread().getName());
                    task.run();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn("Worker interrupted. Shutting down thread: " + Thread.currentThread().getName());
                break;
            } catch (Exception e) {
                logger.error("Error during task execution.", e);
            }
        }
        logger.info("Worker thread terminated: " + Thread.currentThread().getName());
    }

    public void scheduleTask(Task task, long delay, TimeUnit unit) {
        if (isRunning.get()) {
            delayScheduler.schedule(() -> {
                try {
                    taskQueue.put(task);
                    logger.info("Task scheduled: " + task.getTaskName());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.error("Task scheduling interrupted for: " + task.getTaskName(), e);
                }
            }, delay, unit);
        } else {
            logger.warn("Attempt to schedule task after scheduler shutdown: " + task.getTaskName());
        }
    }

    public void stopScheduler() {
        if (isRunning.compareAndSet(true, false)) {
            logger.info("Stopping scheduler...");
            delayScheduler.shutdown();
            workerPool.shutdown();
            try {
                if (!workerPool.awaitTermination(5, TimeUnit.SECONDS)) {
                    logger.warn("Forcing shutdown of worker pool...");
                    workerPool.shutdownNow();
                }
                if (!delayScheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    logger.warn("Forcing shutdown of delay scheduler...");
                    delayScheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Shutdown interrupted.", e);
            }
        }
    }
}