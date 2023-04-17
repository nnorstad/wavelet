import java.net.URI;
import java.io.IOException;

class HandlerSearch implements URLHandler{
    int default_size = 10;
    int size = 0;
    String[] searching = new String[default_size];
    public String handleRequest(URI url){
        if (url.getPath().contains("/add")){
            String [] addTo = url.getQuery().split("=");
            if (size == default_size){
                searching = new String[size + default_size];
            }
            searching[size] = addTo[1];
            size += 1;
            return String.format("%s has been added!", addTo[1]);
        }
        else if (url.getPath().contains("/search")){
            int count = 0;
            String result = "";
            String [] weSearch = url.getQuery().split("=");
            for (int i = 0; i < size; i ++){
                if (searching[i].contains(weSearch[1])){
                    result = result + searching[i] + ", ";
                    count += 1;
                }
            }
            return "We found " + count + " matches for you search: " + result;
        }
        else {
            return "Please enter a valid extension";
        }
    }
}

public class SearchEngine{
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new HandlerSearch());
    }
}
