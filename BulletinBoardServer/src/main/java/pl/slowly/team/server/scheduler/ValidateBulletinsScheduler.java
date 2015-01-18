package pl.slowly.team.server.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import pl.slowly.team.server.repository.BulletinRepository;

/**
 * Created by Maxym on 2015-01-10.
 */
public class ValidateBulletinsScheduler implements Job {

    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        System.out.println("Validating bulletins!");
        BulletinRepository bulletinRepository = new BulletinRepository();
        if (bulletinRepository.validateBulletins())
            System.out.println("Bulletins validated");
        else System.out.println("Error while validating bulletins!");
    }
}
