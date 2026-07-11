import javax.swing.*;
import java.awt.*;
import java.sql.*;
public class FormBuku extends JPanel {
    JComboBox<Item> cbKategori = new JComboBox<>();
    JTextField txtJudul = new JTextField();
    JTextField txtHarga = new JTextField();
    public FormBuku() {
        setLayout(new GridLayout(4, 2, 5, 5));
        add(new JLabel("Kategori:")); add(cbKategori);
        add(new JLabel("Judul:")); add(txtJudul);
        add(new JLabel("Harga:")); add(txtHarga);
        JButton btn = new JButton("Simpan");
        btn.addActionListener(e -> simpan());
        add(btn); loadKategori();
    }
    private void loadKategori() {
        try (Connection c = DB.connect(); ResultSet rs = c.createStatement().executeQuery("SELECT * FROM kategori")) {
            while (rs.next()) cbKategori.addItem(new Item(rs.getInt(1), rs.getString(2)));
        } catch (Exception e) {}
    }
    private void simpan() {
        try (Connection c = DB.connect()) {
            PreparedStatement ps = c.prepareStatement("INSERT INTO buku (id_kategori, judul, harga) VALUES (?, ?, ?)");
            ps.setInt(1, ((Item) cbKategori.getSelectedItem()).id);
            ps.setString(2, txtJudul.getText());
            ps.setInt(3, Integer.parseInt(txtHarga.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Berhasil!");
        } catch (Exception e) { JOptionPane.showMessageDialog(this, e.getMessage()); }
    }
}