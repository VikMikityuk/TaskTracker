package workWithFile;

import view.View;

import java.io.*;

/**
 * Created by Snap on 18.01.2016.
 */
public class WorkWithFile implements Serializable {
    private File file;
    //WorkWithFile.setTaskFileJM(taskFile);


    public void setTaskFileJM(File file) {
        if (existFile(file)) this.file = file;
        else View.printErrorIncorrectFile();
    }

    /**
     * Проверяет существование файла-хранилища. Если файл не существует, то создается новый пустой файл.
     *
     * @param file -адрес файла в системе
     * @return результат проверки
     */
    public boolean existFile(File file) {
        file.getParentFile().mkdirs();
        if (!(file.exists())) try {
            file.createNewFile();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Сериализация JournalModel в файл.
     *
     * @param obj - журнал задач
     */
    public void serJM(Object obj) throws IOException {
        FileOutputStream fos = new FileOutputStream(file.getAbsoluteFile());
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(obj);
        oos.flush();
        oos.close();

    }


    /**
     * Десериализация JournalModel из файла. Предназначен для метода валидной десереиализвации.
     *
     * @return j - журнал задач.
     */
    private Object deSer() throws IOException {
        FileInputStream fis = new FileInputStream(file.getAbsoluteFile());
        ObjectInputStream oin = new ObjectInputStream(fis);
        Object j = null;
        try {
            j =  oin.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return j;
    }


    /**
     * Десериализация JournalModel из файла c проверками наличия JournalModel в файле.
     */
    public Object validDeser() throws IOException {
        Object j;
        if (file.length() == 0) {
            j = null;
        } else {
            j = deSer();
        }
        return j;
    }

}
