import java.util.Properties;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.ChannelExec;

public class Main {
    public static void main(String[] args) throws Exception {
        String username = "";
        String password = "";
        String hostname = "";
        int port = 0;

        String command = "cd capstone17-2/CalculateSHA1/src/; javac com/capstone/Main.java; java -classpath . com.capstone.Main \"The quick brown fox jumps over the lazy dog.\"";

        JSch jsch = new JSch();
        Session session = jsch.getSession(username, hostname, port);
        session.setPassword(password);

        // Avoid asking for key confirmation
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");

        session.setConfig(prop);
        session.setTimeout(10000);
        session.connect();

        // SSH Channel
        ChannelExec channelssh = (ChannelExec) session.openChannel("exec");

        InputStream inputStream = channelssh.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        // Execute command
        channelssh.setCommand(command);
        channelssh.connect();
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append('\n');
        }
        channelssh.disconnect();

        System.out.println(stringBuilder.toString());
        if (channelssh.isClosed()) {
            System.exit(0);
        }
    }
}
