import Exceptions.NoUserNameException;
import Exceptions.UsernameExistException;
import Exceptions.WrongPasswordException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;

public class Player {

    private String username;
    private String password;
    private int highestScore;
    private int rank;
    private static ObservableList<Player> allPlayers = FXCollections.observableArrayList();

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        allPlayers.add(this);
        this.highestScore = 0;
    }

    public void setUsername(String username) throws UsernameExistException {
        if (isThereUser(username))
            throw new UsernameExistException();
        else
            this.username = username;

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public int getRank() {
        return rank;
    }

    public String getPassword() {
        return password;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public static void createAccount(String username, String password) throws UsernameExistException {
        if (isThereUser(username))
            throw new UsernameExistException();
        new Player(username, password);
    }

    public static Player login(String username, String password) throws NoUserNameException, WrongPasswordException {
        Player player = getPlayer(username);
        if (!player.getPassword().equals(password))
            throw new WrongPasswordException();
        return player;
    }

    public static boolean isThereUser(String username) {
        for (Player player : allPlayers) {
            if (player.getUsername().equalsIgnoreCase(username))
                return true;
        }
        return false;
    }

    public static Player getPlayer(String username) throws NoUserNameException {
        for (Player player : allPlayers) {
            if (player.getUsername().equalsIgnoreCase(username))
                return player;
        }
        throw new NoUserNameException();
    }

    public void setScore(int score) {
        if (this.highestScore < score)
            this.highestScore = score;
    }

    public static ObservableList<Player> getAllPlayersSorted() {
        allPlayers.sort(Comparator.comparingInt(Player::getHighestScore));
        int i = 1;
        for (Player player : allPlayers) {
            player.rank = i;
            i++;
        }
        return allPlayers;
    }

}
