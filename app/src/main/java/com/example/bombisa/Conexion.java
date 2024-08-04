package com.example.bombisa;

public class Conexion {
    private static final String URL = "jdbc:mysql://<HOST>:<3306>/PanSelectionDB";
    private static final String USER = "<root>";
    private static final String PASSWORD = "<>";
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void insertarPanSeleccion(String userId, String pan, int cantidad) {
        String query = "INSERT INTO PanSelection (user_id, pan, cantidad) VALUES (?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, pan);
            preparedStatement.setInt(3, cantidad);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}