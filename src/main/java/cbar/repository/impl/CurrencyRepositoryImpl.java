package cbar.repository.impl;

import cbar.db.DbConnection;
import cbar.model.Valute;
import cbar.queries.CbarQueries;
import cbar.repository.CurrencyRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyRepositoryImpl implements CurrencyRepository {


    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @Override
    public Valute saveValute(Valute valute) {

        connection = DbConnection.getConnection();

        try {
            //INSERT INTO currency(code,nominal,name,value) VALUES(?,?,?,?)
            preparedStatement = connection.prepareStatement(CbarQueries.save);
            preparedStatement.setString(1, valute.getCode());
            preparedStatement.setString(2, valute.getNominal());
            preparedStatement.setString(3, valute.getName());
            preparedStatement.setBigDecimal(4, valute.getValue());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DbConnection.closeConnection(connection, preparedStatement, null);
        }
        return valute;
    }

    @Override
    public boolean saveBatchValutes(List<Valute> valuteList) {

        connection = DbConnection.getConnection();

        try {
            //INSERT INTO currency(code,nominal,name,value) VALUES(?,?,?,?)
            preparedStatement = connection.prepareStatement(CbarQueries.save);

            for (Valute valute : valuteList){
                preparedStatement.setString(1, valute.getCode());
                preparedStatement.setString(2, valute.getNominal());
                preparedStatement.setString(3, valute.getName());
                preparedStatement.setBigDecimal(4, valute.getValue());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DbConnection.closeConnection(connection, preparedStatement, null);
        }
        return true;
    }

    @Override
    public Valute updateValute(Valute valute) {

        connection = DbConnection.getConnection();

        try {
            //UPDATE currency SET code=?,nominal=?,name=?,value=?
            preparedStatement = connection.prepareStatement(CbarQueries.update);
            preparedStatement.setString(1, valute.getCode());
            preparedStatement.setString(2, valute.getNominal());
            preparedStatement.setString(3, valute.getName());
            preparedStatement.setBigDecimal(4, valute.getValue());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DbConnection.closeConnection(connection, preparedStatement, null);
        }
        return valute;

    }

    @Override
    public boolean removeValute(int id) {

        connection = DbConnection.getConnection();

        try {

            //DELETE FROM  currency WHERE id=?
            preparedStatement = connection.prepareStatement(CbarQueries.delete);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DbConnection.closeConnection(connection, preparedStatement, null);
        }
        return true;
    }

    @Override
    public Valute findById(int id) {

        connection = DbConnection.getConnection();
        Valute valute = null;


        try {
            //SELECt *  FROM currency WHERE id=?
            preparedStatement = connection.prepareStatement(CbarQueries.findById);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){

                String code = resultSet.getString("code");
                String nominal = resultSet.getString("nominal");
                String name = resultSet.getString("name");
                BigDecimal value = resultSet.getBigDecimal("value");

                valute = new Valute(code,nominal,name,value);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DbConnection.closeConnection(connection,preparedStatement,resultSet);
        }
        return valute;
    }

    @Override
    public Valute findByCode(String code) {

        connection = DbConnection.getConnection();
        Valute valute = null;
        try {
        //SELECt *  FROM currency WHERE id=?
        preparedStatement = connection.prepareStatement(CbarQueries.findById);
        preparedStatement.setString(1,code);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){

            String valuteCode = resultSet.getString("code");
            String nominal = resultSet.getString("nominal");
            String name = resultSet.getString("name");
            BigDecimal value = resultSet.getBigDecimal("value");

            valute = new Valute(valuteCode,nominal,name,value);
        }

    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }finally {
        DbConnection.closeConnection(connection,preparedStatement,resultSet);
    }
        return valute;
    }

    @Override
    public List<Valute> findValutes() {

        connection = DbConnection.getConnection();

        List<Valute> valuteList = new ArrayList<>();

        try {
            preparedStatement =  connection.prepareStatement(CbarQueries.findAll);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){

                int id = resultSet.getInt("id");
                String code = resultSet.getString("code");
                String nominal = resultSet.getString("nominal");
                String name = resultSet.getString("name");
                BigDecimal value = resultSet.getBigDecimal("value");


                Valute valute = new Valute(id,code,nominal,name,value);
                valuteList.add(valute);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DbConnection.closeConnection(connection,preparedStatement,resultSet);
        }

        return valuteList;
    }


}
