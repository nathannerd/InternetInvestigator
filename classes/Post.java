package classes;

import javafx.scene.layout.HBox;

import java.util.Date;
import java.util.ArrayList;

/**
 * @author Jake Goodman
 * @since 2018-05-13
 * @version 1 (current version)
 *
 * Version 1:
 * Author: Jake Goodman
 * Date: 2018-05-13
 * Description: Super class of PicturePost and TextPost contains code that would have been duplicated between both
 *               This class mainly deals with formatting of the date and the clone method
 * Time spent: 1.5 hours
 */
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
        this.setMaxHeight(300);
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

    /**
     * Getter for hint
     * @return hint
     */
    public String getHint() {
        return hint;
    }

    /**
     * Setter for hint
     * @param hint hint to be set
     */
    public void setHint(String hint) {
        this.hint = hint;
    }

    /**
     * Method to clone a post
     * This method makes use of the constructor in each child class of post, that returns a clone of itself
     * @return
     */
    public Post clone() {
        if (this instanceof PicturePost) {
            return new PicturePost((PicturePost)this);
        }
        return new TextPost((TextPost)this);
    }
}