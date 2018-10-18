package todo.domain.service.todo;

import static org.h2.engine.Constants.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class PersonDaoTest {

    private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    @BeforeClass
    public static void createSchema() throws Exception {
        RunScript.execute(JDBC_URL, USER, PASSWORD, "data/schema.sql", null, false);
    }

    private IDataSet readDataSet(String dataPath) throws Exception {
        // for XML
        return new FlatXmlDataSetBuilder().build(new File(dataPath));
        // for Excel
        // return new XlsDataSet(new File(dataPath));
    }

    private void cleanlyInsert(IDataSet dataSet) throws Exception {
        IDatabaseTester databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    @Test
    public void 存在する人を検索するテスト() throws Exception {
        // Arrange
        IDataSet dataSet = readDataSet("data/setup_dataset.xml");
        cleanlyInsert(dataSet);
        PersonDao dao = new PersonDao(dataSource());

        // Action
        Person person = dao.findPersonByFirstName("Theresa");

        // Assert
        assertThat(person.getFirstName(), is("Theresa"));
        assertThat(person.getLastName(), is("May"));
        assertThat(person.getAge(), is(43));
        assertThat(person.getGenderName(), is("Female"));
    }

    @Test
    public void 存在しない人を検索するテスト() throws Exception {
        // Arrange
        IDataSet dataSet = readDataSet("data/setup_dataset.xml");
        cleanlyInsert(dataSet);
        PersonDao dao = new PersonDao(dataSource());

        // Action
        Person person = dao.findPersonByFirstName("I don't exist");

        // Assert
        assertThat(person, is(nullValue()));
    }

    @Test
    public void テーブルのデータを比較するテスト() throws Exception {
        // Arrange
        IDataSet setupDataSet = readDataSet("data/setup_dataset.xml");
        cleanlyInsert(setupDataSet);

        // Action
        Connection conn = dataSource().getConnection();
        DatabaseConnection dbConn = new DatabaseConnection(conn);
        IDataSet databaseDataSet = dbConn.createDataSet();
        ITable actualTable = databaseDataSet.getTable("persons");

        // Assert
        IDataSet expectedDataSet = readDataSet("data/expected_dataset_table.xml");
        ITable expectedTable = expectedDataSet.getTable("persons");

        Assertion.assertEquals(expectedTable, actualTable);
        //assertThat(actualTable, is(expectedTable));
    }

    @Test
    public void ビューと比較するテスト() throws Exception {
        // Arrange
        IDataSet setupDataSet = readDataSet("data/setup_dataset.xml");
        cleanlyInsert(setupDataSet);

        // Action


        // Assert
        IDataSet expectedDataSet = readDataSet("data/expected_dataset_view.xml");
        ITable expectedTable = expectedDataSet.getTable("persons_view");
        Connection conn = dataSource().getConnection();
        DatabaseConnection dbConn = new DatabaseConnection(conn);
        String tableName = "persons";
        String sqlQuery = "SELECT first_name, last_name, age FROM persons ORDER BY age";
        String[] ignoreCols = new String[0];
        Assertion.assertEqualsByQuery(expectedTable, dbConn, tableName, sqlQuery, ignoreCols);
    }

    private DataSource dataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(JDBC_URL);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }

}