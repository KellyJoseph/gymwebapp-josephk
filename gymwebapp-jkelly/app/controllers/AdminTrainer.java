
package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Member;
import models.Trainer;
import models.Assessment;
import play.Logger;
import play.mvc.Controller;

public class AdminTrainer extends Controller {

     public static void index ()
     {
         Logger.info("Rendering AdminTrainer");
         Trainer activeTrainer = Accounts.getLoggedInTrainer();
         List<Member> members = Member.findAll();
         List<Assessment> assessments= Assessment.findAll();
         render ("AdminTrainer.html", activeTrainer, assessments, members);
     }


    public static void addComment(Long id, String comment)
    {
        Logger.info("Adding a comment");
        Assessment assessment = Assessment.findById(id);
        assessment.setComment(comment);
        assessment.save();
        redirect("/admintrainer");
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

    public static void deleteAccount(Long id)
    {
        Member member = Member.findById(id);
        member._delete();
        Logger.info("Deleting");
        redirect("/admintrainer");
    }


/*  public static void addSong(Long id, String title, String artist, int duration, String genre) {
        Song song = new Song(title, artist, duration, genre);
        Playlist playlist = Playlist.findById(id);
        playlist.songs.add(song);
        playlist.save();
        redirect("/playlists/" + id);
        Logger.info("Add Song");
    }*/


}


