import javax.swing.*;
import java.awt.*;
import java.sql.*;
public class FormJual extends JPanel {
    JTextField txtPembeli = new JTextField();
    JComboBox<Item> cbBuku = new JComboBox<>();
    JTextField txtQty = new JTextField();
    public FormJual() {
        setLayout(new GridLayout(4, 2, 5, 5));
        add(new JLabel("Pembeli:")); add(txtPembeli);
        add(new JLabel("Buku:")); add(cbBuku);
        add(new JLabel("Qty:")); add(txtQty);
        JButton btn = new JButton("Proses");
        btn.addActionListener(e -> proses());
        add(btn); loadBuku();
    }
    private void loadBuku() {
        try (Connection c = DB.connect(); ResultSet rs = c.createStatement().executeQuery("SELECT * FROM buku")) {
            while (rs.next()) cbBuku.addItem(new Item(rs.getInt(1), rs.getString(3)));
        } catch (Exception e) {}
    }
    private void proses() {
        try (Connection c = DB.connect()) {
            c.setAutoCommit(false);
            PreparedStatement ps1 = c.prepareStatement("INSERT INTO penjualan (nama_pembeli, tanggal) VALUES (?, CURRENT_DATE)", Statement.RETURN_GENERATED_KEYS);
            ps1.setString(1, txtPembeli.getText());
            ps1.executeUpdate();
            ResultSet rs = ps1.getGeneratedKeys(); rs.next();
            int id = rs.getInt(1);
            PreparedStatement ps2 = c.prepareStatement("INSERT INTO detail_penjualan (id_penjualan, id_buku, jumlah) VALUES (?, ?, ?)");
            ps2.setInt(1, id);
            ps2.setInt(2, ((Item) cbBuku.getSelectedItem()).id);
            ps2.setInt(3, Integer.parseInt(txtQty.getText()));
            ps2.executeUpdate();
            c.commit();
            JOptionPane.showMessageDialog(this, "Sukses!");
        } catch (Exception e) { JOptionPane.showMessageDialog(this, e.getMessage()); }
    }
}