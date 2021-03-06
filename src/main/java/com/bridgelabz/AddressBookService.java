package com.bridgelabz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddressBookService {
    public AddressBookJdbc addressBookJdbc;

    public int getQuery() throws SQLException {
        String query = "select p.firstname,p.lastname,p.phonenumber,p.email,a.residence_address,a.city,a.state,a.country,a.zip,r.type\r\n" +
                "from address a,address_book ab,person_details p,relation_type r\r\n" +
                "where ab.person_id = p.person_id\r\n" +
                "and ab.relation_id = r.relation_id\r\n" +
                "and p.person_id = a.person_id;";
        ResultSet queries = getQuerries(query);
        return printSet(queries);
    }

    private int printSet(ResultSet queries) throws SQLException {
        int i=0;
        while(queries.next()) {
            i++;
            String firstname = queries.getString("p.firstname");
            String lastname = queries.getString("p.lastname");
            System.out.println(firstname+" "+lastname);
        }
        return i;
    }

    private ResultSet getQuerries(String query) throws SQLException {
        addressBookJdbc = AddressBookJdbc.getInstance();
        Connection connection = addressBookJdbc.dbConnect();
        Statement statement = connection.createStatement();

        return statement.executeQuery(query);
    }

    public boolean getUpdate(String firstName, String lastName, String email) throws SQLException {
        String query = String.format("update person_details set email = '%s' where firstname = '%s' and lastname = '%s';",email,firstName,lastName);
        addressBookJdbc = AddressBookJdbc.getInstance();
        Connection connection = addressBookJdbc.dbConnect();
        Statement statement = connection.createStatement();
        int z = statement.executeUpdate(query);
        ResultSet queries = getQuerries(String.format("Select email from person_details where firstname = '%s' and lastname = '%s';",firstName,lastName));
        while(queries.next()) {
            if (queries.getString("email").equals(email)) return true;
        }
        return false;
    }
}