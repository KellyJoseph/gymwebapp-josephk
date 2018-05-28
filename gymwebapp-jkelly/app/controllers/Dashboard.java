package controllers;

//import models.Comment;
import models.Member;
import models.Assessment;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;
import java.util.List;

public class Dashboard extends Controller {

    public float BMI;


    public static void index()
    {
        Logger.info("Rendering Dashboard");
        Member activeMember = Accounts.getLoggedInMember();
        float weight = 0;
        //weight = activeMember.getLatestAssessment().getWeight();
        //float height = activeMember.height;
        //int BMI = (int)( weight /(height*height));
        int BMI = (int)activeMember.calculateBMI();
        String bmiCategory = determineBMICategory(BMI);
        int idealWeight = (int)activeMember.idealBodyWeight();
        List<Assessment> assessments = activeMember.assessments;
        //List<Comment> comments =activeMember.comments;
        render("dashboard.html", activeMember, assessments, BMI, bmiCategory, idealWeight);
    }

    public static void addAssessment(float weight, float chest, float thigh, float upperArm, float waist, float hips)
    {
        Logger.info("Adding an assessment");
        Member member = Accounts.getLoggedInMember();
        Assessment assessment = new Assessment(weight, chest, thigh, upperArm, waist, hips);
        member.assessments.add(assessment);
        member.save();
        redirect("/dashboard");
    }
    public static void setFirstName(String firstname)
    {
        Logger.info("Setting first name");
        Member member = Accounts.getLoggedInMember();
        member.setFirstName(firstname);
        member.save();
        redirect("/user");
    }
    public static void setLastName(String lastname)
    {
        Logger.info("Setting last name");
        Member member = Accounts.getLoggedInMember();
        member.setLastName(lastname);
        member.save();
        redirect("/user");
    }
    public static void setEmail(String email)
    {
        Logger.info("Setting email");
        Member member = Accounts.getLoggedInMember();
        member.setEmail(email);
        member.save();
        redirect("/user");
    }
    public static void setPassword(String password)
    {
        Logger.info("Setting password");
        Member member = Accounts.getLoggedInMember();
        member.setPassword(password);
        member.save();
        redirect("/user");
    }
    public static void setHeight(float height)
    {
        Logger.info("Setting height");
        Member member = Accounts.getLoggedInMember();
        member.setHeight(height);
        member.save();
        redirect("/user");
    }
    public static void setStartWeight(float startWeight)
    {
        Logger.info("Setting starting weight");
        Member member = Accounts.getLoggedInMember();
        member.setStartWeight(startWeight);
        member.save();
        redirect("/user");
    }
    public static void setGender(String gender)
    {
        Logger.info("Setting starting weight");
        Member member = Accounts.getLoggedInMember();
        member.setGender(gender);
        member.save();
        redirect("/user");
    }




    public static void deleteAssessment(Long id)
    {
        Member member = Accounts.getLoggedInMember();
        Assessment assessment = Assessment.findById(id);
        member.assessments.remove(assessment);
        member.save();
        assessment.delete();
        Logger.info("Deleting" + assessment);
        redirect("/dashboard");
    }

    public static String determineBMICategory(int BMI) {
        int bmiValue = BMI;

        if (bmiValue < 16) {
            return "SEVERELY UNDERWEIGHT";
        }
        if ((bmiValue >= 16) && (bmiValue < 18.5)) {
            return "UNDERWEIGHT";
        }
        if ((bmiValue >= 18.5) && (bmiValue < 25)) {
            return "NORMAL";
        }
        if ((bmiValue >= 25) && (bmiValue < 30)) {
            return "OVERWEIGHT";
        }
        if ((bmiValue >= 30) && (bmiValue < 35)) {
            return "MODERATELY OVERWEIGHT";
        }
        if (bmiValue >= 35) {
            return "SEVERELY OVERWEIGHT";
        } else return "invalid input";
    }

}
