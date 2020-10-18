package cbar.queries;

public class CbarQueries {

    public static final String save = "INSERT INTO currency(code,nominal,name,value) VALUES(?,?,?,?)";
    public static final String update = "UPDATE currency SET code=?,nominal=?,name=?,value=?";
    public static final String delete = "DELETE FROM  currency WHERE id=?";
    public static final String findById = "SELECt *  FROM currency WHERE id=?";
    public static final String findByCode = "SELECt *  FROM currency WHERE code=?";
    public static final String findAll = "SELECt *  FROM currency ";


}
