package inputUtils;

public class NameProccesser {
     public String setTrimName(String name){
         name = name.trim();
         if (name.isEmpty()) return "";
         StringBuffer tmp = new StringBuffer();
         tmp.setLength(0);
         String []castName = name.split("\\s+");
         for(String piece: castName){
             piece = piece.trim();
             piece = piece.toLowerCase();
             piece = piece.substring(0,1).toUpperCase() + piece.substring(1);
             tmp.append(piece+" ");
         }
         
         name = tmp.toString().trim();
         return name;
     }        
}
