import javafx.scene.layout.HBox;

import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;


public abstract class Post extends HBox {
    private Date postingDate;
    private int numLikes;
    private ArrayList<Person> peopleLiked;
    private String hint;

    /**
     * Assigns postingDate to given Date
     * numLikes initialized to 0
     * peopleLiked initialized to empty ArrayList
     * @param postingDate
     */
    public Post(Date postingDate) {
        super();
        this.postingDate = postingDate;
        numLikes = 0;
        peopleLiked = new ArrayList<>();
    }

    /**
     * Formats the date to display the day of the week, month and day
     * THe method toString on the Date class is too ugly, and does not fit on a Post
     * @return formatted String to represent the date
     */
    public String formatDate() {
        String sDate = postingDate.toString();
        return sDate.substring(0, 10) + ", " + sDate.substring(sDate.length() - 4);
    }

    /**
     * Formats a condensed version on the post as how it would be seen on a Timeline
     * @return condensed version of this
     */
    public abstract HBox timelineView();

    /**
     * Adds a comment to this post
     * @param person person who left the comment
     * @param comment comment
     */
    public abstract void addComment(Person person, String comment);

    /**
     * Same function as addComment, but able to do it with multiple Persons
     * @param people list of people who left the comments
     * @param comments comments
     */
    public abstract void addComments(Person[] people, String[] comments);

    /**
     * Adds a like to Post
     * @param person person who left the like
     */
    public void addLike(Person person) {
        peopleLiked.add(person);
        numLikes++;
    }

    /**
     * Getter for postingDate
     * @return postingDate
     */
    public Date getPostingDate() {
        return postingDate;
    }

    /**
     * Adds 1 like for each person given in parameter
     * @param people list of people who liked the post
     */
    public void addLikes(Person[] people) {
        for (Person p : people) {
            numLikes++;
            peopleLiked.add(p);
        }
    }

    /**
     * Getter for numLikes
     * @return numLikes
     */
    public int getNumLikes() {
        return numLikes;
    }

    /**
     * Getter for people liked, but returned as an ArrayList instead of an array
     * @return ArrayList of people liked
     */
    public ArrayList<String> getPeopleLiked() {
        ArrayList<String> people = new ArrayList<>();
        for (Person p : peopleLiked) {
            people.add(p.getName());
        }
        return people;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
