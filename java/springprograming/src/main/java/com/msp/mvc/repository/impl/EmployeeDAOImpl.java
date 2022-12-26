package com.msp.mvc.repository.impl;

import com.msp.mvc.model.Employee;
import com.msp.mvc.repository.EmployeeDAO;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Optional;


@NoArgsConstructor
//@SpringBootApplication(exclude =  {DataSourceAutoConfiguration.class })
public class EmployeeDAOImpl implements EmployeeDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SAVE_EMPLOYEE = "INSERT INTO Employee(Employee.Name, Employee.Age, Employee.Email) VALUES(?,?,?)";

    private static final String UPDATE_EMPLOYEE = "UPDATE Employee SET Name=?, Age=?, Email=? WHERE id=?";

    private static final String FIND_BY_ID = "SELECT * FROM Employee WHERE id = ?";

    private static final String DELETE_BY_ID = "DELETE FROM Employee WHERE id=?";

    private static final String FIND_ALL = "SELECT * FROM Employee";

    private static final String FIND_BY_NAME = "SELECT * FROM Employee WHERE Name = ?";

    private static final String DELETE_ALL = "DELETE FROM Employee";

    @Override
    public int save(Employee employee) {
        return jdbcTemplate.update(SAVE_EMPLOYEE, new Object[]{employee.getName(), employee.getAge(), employee.getEmail()});
    }

    @Override
    public int update(Employee employee) {
        return jdbcTemplate.update(UPDATE_EMPLOYEE, new Object[]{employee.getId(), employee.getName(), employee.getAge(), employee.getEmail()});
    }

    @Override
    public Optional<Employee> findById(Long id){

        Employee employee = jdbcTemplate.queryForObject(FIND_BY_ID, BeanPropertyRowMapper.newInstance(Employee.class), id);
        return Optional.ofNullable(employee);
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public List<Employee> findAll() {
        return jdbcTemplate.query(FIND_ALL, BeanPropertyRowMapper.newInstance(Employee.class));
    }

    @Override
    public List<Employee> findByName(String name) {
        return jdbcTemplate.query(FIND_BY_NAME, BeanPropertyRowMapper.newInstance(Employee.class), name);
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update(DELETE_ALL);
    }

    //    @Value("${url}")
//    private String url;
//
//    @Value("${username}")
//    private String username;
//
//    @Value("${password}")
//    private String password;

//    private static final String url = "jdbc:mysql://localhost:3306/btlsql";
//    private static final String username = "root";
//    private static final String password = "Hala171.";
//
//    private Connection getConnection(){
//        Connection connection = null;
//        try{
//            connection = DriverManager.getConnection(url, username, password);
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//        return connection;
//    }
//
//    private static final String FIND_ALL = "select* from Employee";
//
//    private static final String SAVE_EMPLOYEE = "insert into Employee values(?, ?, ?);";
//
//    private static final String DELETE_BY_ID = "delete from Employee where Employee.id=?;";
//
//    private static final String UPDATE_BY_ID = "update Employee set name = ?, age = ?, email = ? where Employee.id = ?;";
//
//    private static final String FIND_BY_ID = "select* from Employee where Employee.id = ?;";
//
//    @Override
//    public List<Employee> findAll() {
//        Employee employee = null;
//        List<Employee> list = new ArrayList<>();
//        try(Connection connection = getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)){
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()){
//                Long id = rs.getLong(1);
//                String name = rs.getString(2);
//                Integer age = rs.getInt(3);
//                String email = rs.getString(4);
//
//                employee = new Employee(id, name,age , email);
//
//                list.add(employee);
//
//            }
//        } catch (SQLException e) {
//            printfSQLException(e);
//        }
//        return list;
//    }
//
//    @Override
//    public void save(Employee employee){
//
//        try(Connection connection = getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_EMPLOYEE)){
//
//            preparedStatement.setString(1, employee.getName());
//            preparedStatement.setInt(2, employee.getAge());
//            preparedStatement.setString(3, employee.getEmail());
//            preparedStatement.executeUpdate();
//        }catch (SQLException e){
//            printfSQLException(e);
//        }
//    }
//
//    @Override
//    public void deleteById(Long id) {
//
//        try(Connection connection = getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)){
//            preparedStatement.setLong(1, id);
//            preparedStatement.executeUpdate();
//
//        } catch (SQLException e) {
//            printfSQLException(e);
//        }
//    }
//
//    @Override
//    public void updateEmployee(Long id) throws SQLException {
//
//        try(Connection connection = getConnection();
//        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID)){
//            preparedStatement.setLong(4, id);
//
//            Employee employee = null;
//            preparedStatement.setString(1, employee.getName());
//            preparedStatement.setInt(2, employee.getAge());
//            preparedStatement.setString(3, employee.getEmail());
//
//            preparedStatement.executeUpdate();
//
//        }catch (SQLException e){
//            printfSQLException(e);
//        }
//    }
//
//    @Override
//    public Optional<Employee> findById(Long id) {
//        Optional<Employee> employee = null;
//        try (Connection connection = getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
//            preparedStatement.setLong(1, id);
//
//            ResultSet rs = preparedStatement.executeQuery();
//            while (rs.next()){
//                String name = rs.getString(2);
//                Integer age = rs.getInt(3);
//                String email = rs.getString(4);
//
//                employee = Optional.of(new Employee(id, name, age, email));
//
//            }
//        } catch (SQLException e) {
//            printfSQLException(e);
//        }
//        return employee;
//        }
//
//    private void printfSQLException(SQLException e) {
//        for (Throwable i : e) {
//            if (e instanceof SQLException) {
//                System.err.println("SQLState: " + ((SQLException) i).getSQLState());
//                System.err.println("Error Code: " + ((SQLException) i).getErrorCode());
//                System.err.println("Message: " + e.getMessage());
//                Throwable t = e.getCause();
//                while (t != null) {
//                    System.out.println("Cause: " + t);
//                    t = t.getCause();
//                }
//            }
//        }
//    }

}

