package models;

import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Member extends Model
{
    public String firstname;
    public String lastname;
    public String email;
    public String password;
    public float startWeight;
    public float height;
    public float BMI;
    public float weight;
    public String gender;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Assessment> assessments = new ArrayList<Assessment>();
   /* @OneToMany(cascade = CascadeType.ALL)
    public List <String> comments = new ArrayList<String>();*/


    public Member(String firstname, String lastname, String email, String password)
    {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.height = height;
        this.startWeight = startWeight;




        if (!assessments.isEmpty() ) {
            this.weight = getLatestAssessment().weight;
        }
        else this.weight = 60;
        this.BMI = calculateBMI();

    }

    public float calculateBMI()
    {
        float BMI = 0;
        if (getLatestAssessment()==null)
        {return 0;}
        else
            if (getLatestAssessment()!= null)
            {BMI = (getLatestAssessment().weight / (height*height));}
        return BMI;
    }

    public boolean decreaseWeight()
    {
        if ((getLatestAssessment().getWeight()) >= (getSecondLatestAssessment().getWeight()))
        {
            return false;
        }
         else   if ((getLatestAssessment().getWeight()) < (getSecondLatestAssessment().getWeight()))
        {
            return true;
        }
        else return false;
    }

    public float getStartWeight()
    {
        return startWeight;
    }

    public void setGender (String gender) {
        String sex = gender.substring(0, 1).toUpperCase();
        if (sex.equals("M"))
        {
            sex = "M";
            this.gender = sex;
        }
        if (sex.equals("F"))
        {
            sex = "F";
            this.gender = sex;
        }
        if ((!sex.equals("M")) &&(!sex.equals("F")))
        {
            sex = "Unspecified";
            this.gender = sex;
        }
    }



    public void setFirstName (String firstname)
    {
        this.firstname = firstname;
    }
    public void setLastName (String lastname)
    {
        this.lastname = lastname;
    }
    public void setEmail (String email)
    {
        this.email = email;
    }
    public void setPassword (String password)
    {
        this.password = password;
    }
    public void setHeight (float height)
    {
        this.height = height;
    }

    public void setStartWeight (float startWeight)
    {
        this.startWeight = startWeight;
    }

    public float getBMI ()
    {
        return BMI;
    }


    public Assessment getLatestAssessment ()
    {
        if (!assessments.isEmpty()) {

            Assessment assessment = assessments.get(assessments.size()-1);
            return assessment;
        }
        else return null;
    }

    public Assessment getSecondLatestAssessment ()
    {
        if (!assessments.isEmpty()) {

            Assessment assessment = assessments.get(assessments.size()-2);
            return assessment;
        }
        else return null;
    }


    public static Member findByEmail(String email)
    {
        return find("email", email).first();
    }

    public boolean checkPassword(String password)
    {
        return this.password.equals(password);
    }

    public float idealBodyWeight()
    {

        if ((gender!= null) && (gender.equals("M"))) {
            float heightInInches = ((height * 100f) * 0.39f); // 1.0m = 39.0"
            if (heightInInches < 60) {
                heightInInches = 60f;
            }
            if (heightInInches > 120) {
                heightInInches = 120;
            }
            float idealWeight = 50f + ((heightInInches - 60f) * 2.3f); //
            //For males, an ideal body weight is: 50 kg + 2.3 kg for each inch over 5 feet. 1.0m = 39"
            return idealWeight;
        }
        if ((gender!= null) && (gender.equals("F"))) {
            float heightInInches = ((height * 100f) * 0.39f);
            if (heightInInches < 60) {
                heightInInches = 60f;
            }
            if (heightInInches > 120) {
                heightInInches = 120f;
            }
            float idealWeight = 45f + ((heightInInches - 60f) * 2.3f);
            //For females, an ideal body weight is: 45.5 kg + 2.3 kg for each inch over 5 feet.
            return idealWeight;
        }

        return 0;
    }
}


