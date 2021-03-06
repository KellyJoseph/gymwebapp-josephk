package controllers;

import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

import java.util.List;

public class Accounts extends Controller
{
    public static void signup()
    {
        render("signup.html");
    }

    public static void login()
    {
        render("login.html");
    }
    public static void logout()
    {
        session.clear();
        redirect ("/");
    }
    public static void register(String firstname, String lastname, String email, String password)
    {
        Logger.info("Registering new user " + email);
        List<Member> members = Member.findAll();
        //verify that the entered email is not the same as the email for any existing members
        Member member = new Member(firstname, lastname, email, password);
        member.save();
        redirect("/");
    }
    public static void authenticate(String email, String password)
    {
        Logger.info("Attempting to authenticate with " + email + ":" + password);

        Member member = Member.findByEmail(email);
        if ((member != null) && (member.checkPassword(password) == true)) {
            Logger.info("Authentication successful");
            session.put("logged_in_Memberid", member.id);
            redirect ("/dashboard");
        } else  {
            Logger.info("Authentication failed");
            redirect("/login");
        }
    }

    public static void authenticateTrainer(String email, String password)
    {
        Logger.info("Attempting to authenticate with " + email + ":" + password);

        Trainer trainer = Trainer.findByEmail(email);
        if ((trainer != null) && (trainer.checkPassword(password) == true)) {
            Logger.info("Authentication successful");
            session.put("logged_in_Trainerid", trainer.id);
            redirect ("/admintrainer");
        } else  {
            Logger.info("Authentication failed");
            redirect("/login");
        }
    }

    public static Member getLoggedInMember()
    {
        Member member = null;
        if (session.contains("logged_in_Memberid")) {
            String memberId = session.get("logged_in_Memberid");
            member = Member.findById(Long.parseLong(memberId));
        } else {
            login();
        }
        return member;
    }
    public static Trainer getLoggedInTrainer()
    {
        Trainer trainer = null;
        if (session.contains("logged_in_Trainerid")) {
            String trainerId = session.get("logged_in_Trainerid");
            trainer = Trainer.findById(Long.parseLong(trainerId));
        } else {
            login();
        }
        return trainer;
    }

}