package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Jake Goodman
 * @since 2018-05-10
 * @version 3 (current version)
 *
 * Version 3:
 * Date: 2018-05-20
 * Description: BIG refactoring of this file. I ran into many issues while trying to implement a way to
 *              describe liking and commenting in level description files so the way that level description files were
 *              written was redesigned to allow for liking and commenting posts
 *
 * Version 2:
 * Date: 2018-05-15
 * Description: Implemented hints for bio and posts
 *
 * Version 1:
 * Date: 2018-05-10
 * Description: Designed how the level description files and package organization should work. Worked with all
 *              profile related classes and made everything work together better.
 */
public class LevelBuilder {

    private List<Person> persons = new ArrayList<>();
    private Map<String, Person> personMap = new HashMap<>();

    /**
     * Reads from a text file, which describes each character from a level
     * This class creates the appropriate objects to model those people described in our program
     * This files that class reads from are written in a specific format so that this class may interpret them
     * The package organization system is very specific to the functionality of this program
     * Each profile level is organized in this manner:
     * levels -> all level information
     *   levelX -> X represents the level number, ie level1 or level1
     *     characterName -> the name of the character, in all lowercase letters
     *       photos -> this contains all of the photos the character will have in the photos section of their page,
     *                 the name of the photos in this package are irrelevant
     *       timeline -> this contains all of the photos in PicturePosts that appear in the character's timeline.
     *                   The link of the photos in this package are put in the level description file and specifically
     *                   associated with a caption.
     *       profile.jpg -> this is the character's profile picture
     * @param file link to a level description file
     */
    public LevelBuilder(String file) {
        // Opens the file as a reader
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line; // current line of the file
            String name = ""; // name of the character that is currently being created
            String bio = ""; // bio of the character that is currently being created
            String currLevel = file.substring(22, 28); // the level that is being created, e.g. level2 or level3
            String hint = null; // represents a hint that must be entered later on
            boolean readBio = false; // indicates weather the bio is currently being read
            boolean readTimeline = false; // indicates weather the timeline is currently being read
            boolean picturePost = false; // indicates weather a classes.PicturePost is currently being created
            boolean isPredator = false; // indicates weather the current person being read is a predator
            String photoLink = null; // link to a photo
            Post post = null; // the most recent created Post
            Date date = null; // the date of the current Post
            boolean firstLine = true; // only true on the first pass of the while loop
            int pIndex = -1; // keeps track of the person currently being read

            // keep reading as long as there are lines to read
            while ((line = br.readLine()) != null) {
                // if we are on the first line, create the appropriate persons for the level
                if (firstLine) {
                    String[] names = line.split(",");
                    for (String s : names) {
                        Person person = new Person(s);
                        persons.add(person);
                        personMap.put(s, person);
                    }
                }
                switch (LevelBuilder.numOfTabs(line)) {
                    case 0:
                        if (firstLine) {
                            firstLine = false;
                        } else {
                            if (line.equals("")) continue;
                            name = line.substring(0, line.indexOf(','));
                            String s = line.substring(line.indexOf(',') + 2);
                            isPredator = s.equals("Predator:");

                            pIndex++;
                            persons.get(pIndex).setPredator(isPredator);
                        }
                        break;
                    case 1:
                        readBio = line.substring(4, 8).equals("Bio:");
                        if (readBio) {
                            hint = line.substring(8);
                            bio = "";
                        } else {
                            readTimeline = line.substring(4).equals("Timeline:");
                            if (!readTimeline) {
                                persons.get(pIndex).getHints().put(
                                        line.substring(4, line.indexOf(':')),
                                        line.substring(line.indexOf(':') + 1)
                                );
                            }
                        }
                        break;
                    case 2:
                        if (readBio) {
                            bio += " " + line.substring(8);
                        } else {
                            picturePost = line.substring(8, 20).equals("PicturePost:");
                            hint = line.substring(line.indexOf(':') + 1);
                        }
                        break;
                    case 3:
                        if (date == null) {
                            // format the date with Calendar class
                            String dateLine = line.substring(12);
                            int month = Integer.valueOf(dateLine.substring(0, 2));
                            int day = Integer.valueOf(dateLine.substring(3, 5));
                            int year = Integer.valueOf(dateLine.substring(6));
                            Calendar calendar = new GregorianCalendar(year, month - 1, day);
                            Date d = calendar.getTime();
                            SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
                            String sDate = format.format(d);

                            try {
                                date = format.parse(sDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // firstNameLower is the character name in all lower case letters
                            String firstNameLower = name.toLowerCase();
                            // if a PicturePost is being created
                            if (picturePost) {
                                // if the link has not been read, assign the given link to photoLink
                                if (photoLink == null) {
                                    photoLink = String.format(
                                            "levels/%s/%s/timeline/%s", currLevel,
                                            firstNameLower, line.substring(12)
                                    );
                                    // if the link has been read, create a new PicturePost with the current line as the caption
                                } else {
                                    post = new PicturePost(
                                            date,
                                            new Photo(photoLink, 300, line.substring(12))
                                    );
                                    date = null;
                                    photoLink = null;
                                }
                                // if we are not reading a PicturePost, we must be reading a TextPost
                            } else {
                                // create a new TextPost with the current line as the caption
                                post = new TextPost(
                                        date,
                                        new TextArea(line.substring(12))
                                );
                                date = null;
                            }
                        }
                        break;
                    case 4:
                        if (line.substring(16, 22).equals("Likes:")) {
                            // Add likes for this post by the people indicated in the description
                            String[] peopleLiked = line.substring(22).split(",");
                            for (String str : peopleLiked) {
                                post.addLike(personMap.get(str));
                            }
                        } else {
                            // Add comments as specified by the level description
                            String[] peopleCommented = line.substring(25).split(",");
                            for (String str : peopleCommented) {
                                String[] comment = str.split(":");
                                post.addComment(personMap.get(comment[0]), comment[1]);
                            }
                        }
                        break;
                    default:
                        if (readBio) {
                            String firstNameLower = name.toLowerCase();
                            // creates a person with their name, bio and profile picture
                            persons.get(pIndex).setBio(bio);
                            persons.get(pIndex).setProfilePic(
                                    new Photo(String.format("levels/%s/%s/profile.jpg",
                                            currLevel, firstNameLower
                                    ))
                            );
                            persons.get(pIndex).getHints().put(
                                    "bio", hint
                            );
                            // loops through the images in the character's photos package and adds each one to the
                            // character's photos
                            File f = new File(String.format("src/levels/%s/%s/photos",
                                    currLevel, firstNameLower));
                            File[] files = f.listFiles();
                            if (files == null) {
                                throw new FileNotFoundException(
                                        String.format("%s directory not found!", firstNameLower)
                                );
                            } else {
                                for (File photo: files) {
                                    persons.get(pIndex).addPhoto(new Photo(
                                            photo.toString().substring(4),
                                            200));
                                }
                            }
                        } else if (readTimeline) {
                            // adds the most recent post to the character's timeline
                            post.setHint(hint);
                            persons.get(pIndex).getTimeline().addPost(post);
                        }
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        // Adds all friends to all people, so everybody is friends with everybody
        for (Person person : persons) {
            for (Person person1: persons) {
                if (!person.equals(person1)) person.addFriend(person1);
            }
        }
    }

    /**
     * Finds the number of tabs in a line
     * @param line line of the file
     * @return the number of tabs at the start of the line, or the number of spaces modulus four
     */
    public static int numOfTabs(String line) {
        int spaces = 0;
        for (char c : line.toCharArray()) {
            if (c == ' ') {
                spaces++;
            } else {
                if (spaces % 4 == 0)
                    return spaces / 4;
                else
                    throw new IllegalArgumentException(String.format(
                            "Invalid file formatting on this line: %s", line
                    ));
            }
        }
        // if the method reaches this point, line is a blank line
        return -1;
    }

    /**
     * Getter for persons of this file
     * @return persons
     */
    public List<Person> getPersons() {
        return persons;
    }
}
