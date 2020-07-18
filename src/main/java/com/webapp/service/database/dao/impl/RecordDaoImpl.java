package com.webapp.service.database.dao.impl;

import com.webapp.model.Record;
import com.webapp.service.database.DatabaseService;
import com.webapp.service.database.dao.RecordDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Juntao Peng
 */
public class RecordDaoImpl extends DatabaseService implements RecordDao {

    @Override
    public List<Record> listRecord(int skip, int limit, boolean verified) {
        Connection connection = getConnection();
        assert connection != null;
        List<Record> recordList = new ArrayList<Record>();
        String SELECT = "SELECT * FROM t_record WHERE verified = ? ORDER BY id ASC LIMIT ?, ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT);
            preparedStatement.setBoolean(1, verified);
            preparedStatement.setInt(2, skip);
            preparedStatement.setInt(3, limit);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Record tempRecord = new Record(rs.getInt("id"),
                        rs.getTimestamp("time").getTime(),
                        rs.getTimestamp("start_date").getTime(),
                        rs.getTimestamp("end_date").getTime(), rs.getInt("user_id"), rs.getInt("building_id"),
                        rs.getBoolean("verified"));
                recordList.add(tempRecord);
            }
            closeConnection(connection);
            return recordList;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace(System.err);
            return recordList;
        }
    }

    @Override
    public int queryNumberOfRecords(boolean verified) {
        Connection connection = getConnection();
        assert connection != null;
        int result = 0;
        String SELECT = "SELECT COUNT(*) FROM t_record WHERE verified = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT);
            preparedStatement.setBoolean(1, verified);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
            closeConnection(connection);
            return result;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace(System.err);
            return result;
        }
    }

    //    @Override
//    public List<Record> listRecordWithBuildId(int size, int buildId) {
//        Connection connection = getConnection();
//        assert connection != null;
//        List<Record> recordList = new ArrayList<Record>();
//        String SELECT = "SELECT * FROM t_record WHERE building_id = ? ORDER BY time DESC LIMIT ?";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(SELECT);
//            preparedStatement.setInt(1, buildId);
//            preparedStatement.setInt(2, size);
//            ResultSet rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                Record tempRecord = new Record(rs.getInt("id"), rs.getTimestamp("time"), rs.getTimestamp("start_date"),
//                        rs.getTimestamp("end_date"), rs.getInt("user_id"), rs.getInt("building_id"),
//                        rs.getBoolean("verified"));
//                recordList.add(tempRecord);
//            }
//            closeConnection(connection);
//            return recordList;
//        } catch (SQLException sqlException) {
//            sqlException.printStackTrace(System.err);
//            return recordList;
//        }
//    }

    @Override
    public List<Record> listRecordWithUserId(int skip, int limit, int userId) {
        Connection connection = getConnection();
        assert connection != null;
        List<Record> recordList = new ArrayList<Record>();
        String SELECT = "SELECT * FROM t_record WHERE user_id = ? ORDER BY id ASC LIMIT ?, ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, skip);
            preparedStatement.setInt(3, limit);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Record tempRecord = new Record(rs.getInt("id"), rs.getTimestamp("time").getTime(),
                        rs.getTimestamp("start_date").getTime(),
                        rs.getTimestamp("end_date").getTime(), rs.getInt("user_id"), rs.getInt("building_id"),
                        rs.getBoolean("verified"));
                recordList.add(tempRecord);
            }
            closeConnection(connection);
            return recordList;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace(System.err);
            return recordList;
        }
    }

    @Override
    public int queryNumberOfRecordsWithUserId(int userId) {
        Connection connection = getConnection();
        assert connection != null;
        int result = 0;
        String SELECT = "SELECT COUNT(*) FROM t_record WHERE user_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT);
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
            closeConnection(connection);
            return result;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace(System.err);
            return result;
        }
    }

    @Override
    public Record queryRecordById(int id) {
        Connection connection = getConnection();
        assert connection != null;
        Record result = null;
        String SELECT = "SELECT * FROM t_record WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result = new Record(rs.getInt("id"), rs.getTimestamp("time").getTime(), rs.getTimestamp("start_date").getTime(),
                        rs.getTimestamp("end_date").getTime(), rs.getInt("user_id"), rs.getInt("building_id"),
                        rs.getBoolean("verified"));
            }
            closeConnection(connection);
            return result;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace(System.err);
            return result;
        }
    }

    @Override
    public boolean addRecord(Record record) {
        Connection connection = getConnection();
        assert connection != null;
        assert record != null;
        String INSERT = "INSERT INTO t_record(time, start_date, end_date, user_id, building_id, verified)" +
                " VALUES(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setTimestamp(1, new Timestamp(record.getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(record.getStartDate()));
            preparedStatement.setTimestamp(3, new Timestamp(record.getEndDate()));
            preparedStatement.setInt(4, record.getUserId());
            preparedStatement.setInt(5, record.getBuildingId());
            preparedStatement.setBoolean(6, record.isVerified());
            preparedStatement.execute();
            closeConnection(connection);
            return true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace(System.err);
            return false;
        }
    }

    @Override
    public boolean deleteRecord(int id) {
        Connection connection = getConnection();
        assert connection != null;
        String DELETE = "DELETE FROM t_record WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            closeConnection(connection);
            return true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace(System.err);
            return false;
        }
    }

    @Override
    public boolean updateRecord(Record record) {
        Connection connection = getConnection();
        assert connection != null;
        assert record != null;
        String UPDATE = "UPDATE t_record SET time = ?, start_date = ?, end_date = ?, user_id = ?, building_id = ?" +
                ", verified = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setInt(7, record.getId());
            preparedStatement.setTimestamp(1, new Timestamp(record.getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(record.getStartDate()));
            preparedStatement.setTimestamp(3, new Timestamp(record.getEndDate()));
            preparedStatement.setInt(4, record.getUserId());
            preparedStatement.setInt(5, record.getBuildingId());
            preparedStatement.setBoolean(6, record.isVerified());
            preparedStatement.execute();
            closeConnection(connection);
            return true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace(System.err);
            return false;
        }
    }
}
