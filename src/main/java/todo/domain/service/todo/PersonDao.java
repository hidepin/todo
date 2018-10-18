package todo.domain.service.todo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public class PersonDao {

    private final DataSource dataSource;

    public PersonDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Person findPersonByFirstName(String name) throws SQLException {
        Person person = null;
        PreparedStatement statement = dataSource.getConnection()
                .prepareStatement("SELECT first_name, last_name, age, gender_name FROM persons, genders WHERE gender_id = genders.id AND first_name = ?");
        statement.setString(1, name);
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                person = convertSingleRow(resultSet);
            }
        } finally {
            closeQuietly(resultSet);
            closeQuietly(statement);
        }
        return person;
    }

    private Person convertSingleRow(ResultSet resultSet) throws SQLException {
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        int age = resultSet.getInt("age");
        String genderName = resultSet.getString("gender_name");
        return new Person(firstName, lastName, age, genderName);
    }

    private void closeQuietly(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (SQLException exception) {
        }
    }

    private void closeQuietly(Statement statement) {
        try {
            statement.close();
        } catch (SQLException exception) {
        }
    }

}