package classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jake Goodman
 * @since 2018-05-13
 * @version 2 (current version)
 *
 * Version 2:
 * Author: Jake Goodman
 * Date: 2018-05-20
 * Description: Added fields hints and isPredator. Also modified the class so it would work with only
 *              the name given on construction. This was to accommodate LevelBuilder at version 3.
 * Time spent: 1 hour
 *
 * Version 1:
 * Author: Jake Goodman
 * Date: 2018-05-13
 * Description: Person has fields: name, bio, profilePic, photos, timeline, friends.
 *              setup getters and setters for a person in its most basic form
 * Time spent: 5 hours
 */
public class Person {
    private String name;
    private String bio;
    private Photo profilePic;
    private List<Photo> photos;
    private Timeline timeline;
    private List<Person> friends;
    private Map<String, String> hints;
    private boolean isPredator;

    /**
     * Constructor for Person
     * Initializes fields photos, friends and hints to their class' default constructor
     * Initializes timeline to new Timeline with this Person instance
     * name is assigned to given name
     * @param name the first name of the person
     */
    public Person(String name) {
        this.name = name;
        this.photos = new ArrayList<>();
        this.timeline = new Timeline(this);
        this.friends = new ArrayList<>();
        this.hints = new HashMap<>();
    }

    /**
     * Getter for name
     * @return name
     */
    public String getName() {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    /**
     * Getter for bio
     * @return bio
     */
    public String getBio() { return bio; }

    /**
     * Getter for profilePic, as a copied object
     * This is to avoid an error caused by the same object being used in two different stages
     * @param height the height of the new phot
     * @return A new Photo that is identical to profilePic but as a different object reference
     */
    public Photo getNewProfilePic(int height) { return new Photo(profilePic.getLink(), height); }

    /**
     * Getter for photos
     * @return photos
     */
    public List<Photo> getPhotos() { return photos; }

    /**
     * Getter for timeline
     * @return timeline
     */
    public Timeline getTimeline() { return timeline; }

    /**
     * Adds a given Photo to photos
     * @param photo Photo to be added to list photos
     */
    public void addPhoto(Photo photo) { photos.add(photo); }

    /**
     * Adds a given Person to friends
     * @param p Person to be added to list friends
     */
    public void addFriend(Person p) { friends.add(p); }

    /**
     * Getter for friends
     * @return friends
     */
    public List<Person> getFriends() { return friends; }

    /**
     * Getter for hints
     * @return hints
     */
    public Map<String, String> getHints() { return hints; }

    /**
     * Setter for bio
     * @param bio new bio to be set
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Setter for profilePic
     * @param profilePic new profilePic to be set
     */
    public void setProfilePic(Photo profilePic) {
        this.profilePic = new Photo(profilePic.getLink(), 100, true);
    }

    /**
     * setter for isPredator
     * @param predator boolean value for isPredator
     */
    public void setPredator(boolean predator) {
        isPredator = predator;
    }

    /**
     * Getter for isPredator
     * @return isPredator
     */
    public boolean isPredator() { return isPredator; }
}