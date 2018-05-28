package controllers;

import java.util.List;

import models.Member;
import models.Assessment;
import models.Trainer;
import play.mvc.Controller;
import play.Logger;

public class AssessmentsTrainer extends Controller {

    public static void index (Long id ) {
        Logger.info ("Displaying assessments for" + id);
        Member activeMember = Member.findById(id);
        List<Assessment> assessments = activeMember.assessments;
        render("AssessmentTrainer.html",activeMember, assessments);
    }
}
