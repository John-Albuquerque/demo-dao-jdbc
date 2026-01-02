package models.dao.impl;

import db.DB;
import db.DbException;
import models.dao.DepartmentDao;
import models.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("INSERT INTO department (Name) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getName());
            int rowsAffected = st.executeUpdate();

            if(rowsAffected > 0){

                ResultSet rs = st.getGeneratedKeys();

                if(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Department obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("UPDATE department SET name = ? WHERE id = ?");
            st.setString(1, obj.getName());
            st.setInt(2, obj.getId());

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deletedById(Integer id) {
        PreparedStatement st = null;

        try{
            st = conn.prepareStatement(
                    "DELETE FROM department "
                            + "WHERE id = ?");
            st.setInt(1, id);

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            st = conn.prepareStatement(
                    "SELECT * FROM department "
                    + "WHERE id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();

            if(rs.next()){
                Department dep = new Department();
                dep.setId(rs.getInt("id"));
                dep.setName(rs.getString("name"));
                return dep;
            }

            return null;

        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{

        st = conn.prepareStatement("SELECT * from department");
        rs = st.executeQuery();

        List<Department> list = new ArrayList<>();

        while (rs.next()){

            int id = rs.getInt("Id");

                Department dep = new Department();
                dep.setId(id);
                dep.setName(rs.getString("Name"));

                list.add(dep);
        }
        return list;

        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
