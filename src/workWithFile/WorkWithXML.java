package workWithFile;

import constant.Constants;
import model.User;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by Snap on 19.01.2016.
 */
public class WorkWithXML {
    /**
     * Маршаллинг юзера и всех его данных в файл
     *
     * @param user
     * @param nameUser
     * @throws IOException
     */
    public void marshallerUser(User user, String nameUser) throws IOException {
        try {
            File file = new File("C://taskTracker//" + nameUser + ".xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(user, file);
            //jaxbMarshaller.marshal(user, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }


    /**
     * Анмаршаллинг объекта юзера из файла
     *
     * @param nameUser
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public User unmarshallingUser(String nameUser) throws IOException, ClassNotFoundException {
        try {

            File file = new File("C://taskTracker//" + nameUser + ".xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(User.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            User user = (User) jaxbUnmarshaller.unmarshal(file);
            System.out.println(user);
            return user;

        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void marshallerListClient() throws IOException {
        try {
            File file = new File("C://taskTracker//listOfUsers.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Constants.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            Constants constants=new Constants();
            if(constants.getUsersList()==null && constants.getUsersList().isEmpty()){constants.setUsersList(new Vector());}
            jaxbMarshaller.marshal(constants, file);
            //jaxbMarshaller.marshal(user, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }



    public Vector  unmarshallingListClient() throws IOException, ClassNotFoundException {
        try {

            File file = new File("C://taskTracker//listOfUsers.xml");
            if(file.length()==0){return new Vector();}
            JAXBContext jaxbContext = JAXBContext.newInstance(Constants.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Constants constants = (Constants) jaxbUnmarshaller.unmarshal(file);
            Vector list=constants.getUsersList();
            //System.out.println(list);
            return list;

        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }



}
