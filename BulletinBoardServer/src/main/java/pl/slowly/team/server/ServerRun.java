package pl.slowly.team.server;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import pl.slowly.team.server.connection.IServer;
import pl.slowly.team.server.connection.MultiThreadedServer;
import pl.slowly.team.server.controller.Controller;
import pl.slowly.team.server.helpers.PacketWrapper;
import pl.slowly.team.server.model.IModel;
import pl.slowly.team.server.model.Model;
import pl.slowly.team.server.scheduler.ValidateBulletinsScheduler;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Main class to run the server.
 * <p/>
 * Created by Emil Kleszcz on 2014-12-17.
 */
public class ServerRun {

    public static void main(String[] args) {
        final BlockingQueue<PacketWrapper> blockingQueue = new LinkedBlockingQueue<>();
        final IModel model = new Model();
        final IServer server;

        if (args.length == 1) {
            try {
                int port = Integer.parseInt(args[0]);
                System.out.println("Port: " + args[0]);
                server = new MultiThreadedServer(port, blockingQueue);

            } catch (Exception e) {
                MultiThreadedServer.LOGGER.error("Wrong port number: " + args[0], e);
                throw new RuntimeException();
            }
        } else {
            System.out.println("Port: 8081");
            server = new MultiThreadedServer(8081, blockingQueue);
        }

        server.listen();

        System.out.println("Serwer został uruchomiony.");

        JobDetail job = new JobDetail();
        job.setName("dummyJobName");
        job.setJobClass(ValidateBulletinsScheduler.class);

        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.SECOND, 0);
        startTime.set(Calendar.MILLISECOND, 0);

        if (startTime.getTime().before(new Date()))
            startTime.add(Calendar.DAY_OF_MONTH, 1);

        //configure the scheduler time
        SimpleTrigger trigger = new SimpleTrigger();
        trigger.setName("Validate bulletins");
        trigger.setStartTime(startTime.getTime());
        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        trigger.setRepeatInterval(TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));

        //schedule
        try {
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
            scheduler.triggerJob(job.getName(), job.getGroup());
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

        final Controller controller = new Controller(model, server, blockingQueue);
        controller.takePacketAndExecuteStrategy();
        System.out.println("Serwer został zamknięty.");
    }
}
