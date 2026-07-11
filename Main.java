import javax.swing.*;

public class Main {
  
  public static void main(String[] args) {
    
    JFrame f = new JFrame("Toko Buku");
    f.setSize(400, 250);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JTabbedPane t = new JTabbedPane();
    t.add("Buku", new FormBuku());
    t.add("Jual", new FormJual());
    f.add(t); 
    f.setVisible(true);
  }
}