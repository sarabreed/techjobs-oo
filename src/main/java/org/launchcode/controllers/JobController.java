package org.launchcode.controllers;

import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.launchcode.models.Job;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.launchcode.models.*;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        model.addAttribute("job", jobData.findById(id));

        // TODO #1 - get the Job with the given ID and pass it into the view

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model,  @Valid JobForm jobForm, Errors errors) {

        if(errors.hasErrors()){
            return "new-job";
        }
        //this should look exactly like the other validation from cheese, user-signup and the lineup card.
        //create Job properties from JobForm and get data from jobdata

        Job newJob = new Job(

                jobForm.getName(),
                jobData.getEmployers().findById(jobForm.getEmployerId()),
                jobData.getLocations().findById(jobForm.getLocationId()),
                jobData.getPositionTypes().findById(jobForm.getPositionTypeId()),
                jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId())
        );


//                String jobName = jobForm.getName();
//                Employer jobEmp = jobData.getEmployers().findById(jobForm.getEmployerId);
//                Location jobLoc = jobData.getLocations().findById(jobForm.getLocationId);
//                PositionType jobPos = jobData.getPositionTypes().findById(jobForm.getPositionTypeId);
//                CoreCompetency jobSkill =jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId);


        jobData.add(newJob);


        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        // review https://education.launchcode.org/skills-back-end-java/videos/intro-to-spring-boot-routes/ this has redirect info

        return "redirect:/job?id=" + newJob.getId();
    }
}
