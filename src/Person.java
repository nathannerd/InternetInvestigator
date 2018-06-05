import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Person {
    private String name;
    private String bio;
    private Photo profilePic;
    private List<Photo> photos;
    private Timeline timeline;
    private List<Person> friends;
    private Map<String, String> hints;
    private boolean isPredator;

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
     * Getter for profilePic
     * @return profilePic
     */
    public Photo getRawProfilePic() { return profilePic; }

    /**
     * Getter for profilePic, as a copied object
     * This is to avoid an error caused by the same object being used in two different stages
     * @param height the height of the new photo
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

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setProfilePic(Photo profilePic) {
        this.profilePic = new Photo(profilePic.getLink(), 100, true);
    }

    public void setPredator(boolean predator) {
        isPredator = predator;
    }

    public boolean isPredator() { return isPredator; }
}