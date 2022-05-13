import java.io.DataOutputStream;
import java.io.IOException;

public class Messages {
    byte type;
    Point point;
    boolean ready = false;

    public Messages(Point point) {
        this.type = 10;
        this.point = point;
    }

    public Messages (boolean ready){
        this.type = 3;
        this.ready = ready;
    }

    public void sendMessage (DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(type);
        //System.out.println("SENDING " + type);
        if (type == 10){
            dataOutputStream.writeInt(point.row);
            dataOutputStream.writeInt(point.col);
            //dataOutputStream.writeUTF(encodeMessage(point));
        }
        dataOutputStream.flush();

    }

    private String encodeMessage (Point point){
        String message = point.row + "|" + point.col;
        return message;
    }
}

