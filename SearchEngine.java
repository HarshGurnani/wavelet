import java.io.IOException;
import java.net.URI;
import java.util.*;

public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> myList = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return myList.toString();
        } else if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters.length > 1) {
                String toAdd = parameters[1];
                myList.add(toAdd);
                return "Added new String to list!";
            }
            return "Insufficient arguments.";
        } else if (url.getPath().contains("/search")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters.length > 1) {
                String toSearch = parameters[1];
                ArrayList<String> toReturn = new ArrayList<>();
                for (String s: myList) {
                    if (s.contains(toSearch)) {
                        toReturn.add(s);
                    }
                }
                return toReturn.toString();
            }
            return "Insufficient arguments";
        } else {
            return "404 Not Found!";
        }
    }
}
