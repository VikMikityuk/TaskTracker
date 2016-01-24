package constant;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Vector;


/**
 * Created by Snap on 13.01.2016.
 */
@XmlRootElement
public class Constants {
    /**
     * Список зарегистрированных юзеров
     */
    @XmlElement
    @XmlElementWrapper
    public volatile static Vector<String> usersList = new Vector<>();

    @XmlElement
    public static final int WORKINGID = 10;
    @XmlElement
    public static final int MEETINGID = 11;
    @XmlElement
    public static final int PLANNINGSESSIONID = 12;
    @XmlElement
    public static final int NOTWORKINGID = 20;
    @XmlElement
    public static final int SLEEPINGID = 21;
    @XmlElement
    public static final int INHOUSEID = 22;
    @XmlElement
    public static final int SWIMMINGID = 30;
    @XmlElement
    public static final int GIMNASTICID = 31;


    public static Vector<String> getUsersList() {
        return usersList;
    }

    public static void setUsersList(Vector<String> usersList) {
        Constants.usersList = usersList;
    }
}
